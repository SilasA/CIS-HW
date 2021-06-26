#include <dirent.h>
#include <errno.h>
#include <grp.h>
#include <math.h>
#include <pwd.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/stat.h>
#include <sys/types.h>
#include <time.h>

#define FULL 1
#define INODE 2
#define DISKU 4
#define LIST 8

#define MAX_COLUMNS 4

/**
 * Struct for optimal width data on display.
 * Each field is the optimal width of the output column based on a scan of the directory.
 */
typedef struct {
    int w_inode;
    int w_block;
    int w_links;
    int w_owner;
    int w_group;
    int w_size;
    int w_name;
} widths_t;

/**
 * Current directory
 * defaults to local directory
 */
char _prefix[256] = ".";

/**
 * Sets the prefix to a specific string.
 * This will add necessary '/' if none are there for preppending to filename.
 *  str - string to set prefix to
 */
void set_prefix(char* str);

/**
 * Displays directory information depending on the input modes.
 *  dir  - directory descriptor to read
 *  mode - mode flags based on input arguments
 */
void show_directory(DIR* dir, int mode);

/**
 * Calculate the optimal widths of every possible column in the output.
 *  dir  - directory descriptor to read
 *  mode - mode flags based on input arguments
 * returns a struct with all the optimal widths. 
 */
widths_t calc_dir_widths(DIR* dir, int mode);

/**
 * Calculates the printed width (in columns) of a base-10 integer.
 *  n - number to measure
 * returns the column width of the number
 */
int int_columns(unsigned long long n);

/**
 * Generate a string from the type/permissions data for a file.
 * Format will look like, "[type]rwxrwxrwx" and the characters will be removed
 * depending on the actual permissions.
 *  str  - referenced string to write in
 *  type - the type data for the file
 */
void type_perm_string(char* str, unsigned long type);

/**
 * Outputs how to use the program.
 */
void usage(char* procname);

int main(int argc, char** argv) {

    int mode = 0;
    DIR* dir;

    // Parse input arguments
    for (int i = 1; i < argc; i++) {
        // Help
        if (strcmp("-h", argv[i]) == 0 || strcmp("--help", argv[i]) == 0) {
            usage(argv[0]);
            return 0;
        }
        // Options
        else if (argv[i][0] == '-') {
            for (int j = 1; j < strlen(argv[i]); j++) {
                if (argv[i][j] == 'n')
                    mode |= FULL;
                else if (argv[i][j] == 'i')
                    mode |= INODE;
                else if (argv[i][j] == 's')
                    mode |= DISKU;
                else if (argv[i][j] == 'l')
                    mode |= LIST;
                else {
                    printf("Improper argument '%c'\n\n", argv[i][j]);
                    usage(argv[0]);
                    return EINVAL;
                }
            }
        }
        // Directory name
        else {
            if ((dir = opendir(argv[i])) == NULL) {
                perror("simple-ls");
                return ENOENT;
            }
            set_prefix(argv[i]);
        }
    }
    
    // If no directory name was provided assume this directory
    if (!dir &&(dir = opendir(".")) == NULL) {
        perror("simple-ls");
        return errno;
    }

    // Output and close directory
    show_directory(dir, mode);
    closedir(dir);

    return 0;
}

void set_prefix(char* str) {
    strcpy(_prefix, str);
    if (_prefix[strlen(_prefix) - 1] != '/') {
        strcat(_prefix, "/");
    }
}

void show_directory(DIR* dir, int mode) {
    struct dirent* entry;
    struct stat stats;

    widths_t widths = calc_dir_widths(dir, mode);

    int i = 0;
    while ((entry = readdir(dir))) {
        char path[256] = "";
        strcat(path, _prefix);
        strcat(path, entry->d_name);

        // If stats are needed in the current mode get them
        if (mode & (FULL | DISKU | LIST)) {
            if (stat(path, &stats) < 0) {
                perror("simple-ls stat");
                exit(errno);
            }
        }

        // print inode number
        if (mode & INODE)
            printf("%*lu ", widths.w_inode, entry->d_ino);

        // print block size
        if (mode & DISKU)
            printf("%*ld ", widths.w_block, stats.st_blocks / 2); 

        // Print full info
        if (mode & (FULL | LIST)) {
            char perms[11];
            type_perm_string(perms, stats.st_mode);
            
            // get username and groupname and convert id to string
            // these may or may not be used in the output depending on the mode
            struct passwd* pw = getpwuid(stats.st_uid);
            struct group* grp = getgrgid(stats.st_gid);
            char user[11];
            char group[11];
            sprintf(user, "%u", stats.st_uid);
            sprintf(group, "%u", stats.st_gid);

            // convert unix to ascii time and remove trailing newline
            char* date = asctime(localtime(&stats.st_mtime));
            date[24] = '\0';

            printf("%s %*lu %*s %*s %*ld %s ", 
                   perms,
                   widths.w_links,  stats.st_nlink,
                   widths.w_owner,  pw && (mode & LIST) ? pw->pw_name  : user,  // Print name if available and in LIST mode
                   widths.w_group, grp && (mode & LIST) ? grp->gr_name : group, // Print name if available and in LIST mode
                   widths.w_size,  stats.st_size,
                   date
                  );
        }

        // print file name
        printf("%-*s  ", widths.w_name, entry->d_name);
        i++;

        // Newline added every line or every MAX_COLUMNS lines depending on mode
        if (i % MAX_COLUMNS == 0 || mode & (FULL | LIST)) printf("\n");
    }
    printf("\n");
}

widths_t calc_dir_widths(DIR* dir, int mode) {
    struct dirent* entry;
    struct stat stats;

    // Initialize widths
    widths_t widths;
    widths.w_inode = 0;
    widths.w_block = 0;
    widths.w_links = 0;
    widths.w_size  = 0;
    widths.w_name  = 0;
    widths.w_owner = 0;
    widths.w_group = 0;

    while ((entry = readdir(dir))) {
        
        // Concat path to file
        char path[256] = "";
        strcat(path, _prefix);
        strcat(path, entry->d_name);

        stat(path, &stats);

        // Inode number
        if (widths.w_inode < int_columns(entry->d_ino))
            widths.w_inode = int_columns(entry->d_ino);
        
        // Block size 
        if (widths.w_block < int_columns(stats.st_blocks / 2))
            widths.w_block = int_columns(stats.st_blocks / 2);
        
        // Hard link count
        if (widths.w_links < int_columns(stats.st_nlink))
            widths.w_links = int_columns(stats.st_nlink);
        
        // File size
        if (widths.w_size  < int_columns(stats.st_size))
            widths.w_size  = int_columns(stats.st_size);
        
        // file/dir name
        if (widths.w_name  < strlen(entry->d_name))
            widths.w_name  = strlen(entry->d_name);
        
        // Owner name lookup
        struct passwd* pw = getpwuid(stats.st_uid);
        int tempLen = pw && (mode & LIST) ? strlen(pw->pw_name) : int_columns(stats.st_uid);
        if (widths.w_owner < tempLen)
            widths.w_owner = tempLen;

        // Group name lookup
        struct group* grp = getgrgid(stats.st_gid);
        tempLen = grp && (mode & LIST) ? strlen(grp->gr_name) : int_columns(stats.st_gid);
        if (widths.w_group < tempLen)
            widths.w_group = tempLen;
        
    }

    // Reset to beginning of directory
    rewinddir(dir);
    return widths;
}

int int_columns(unsigned long long n) {
    return (int)log10l((long double)n) + 1;
}

void type_perm_string(char* str, unsigned long type) {
    
    // start with base format
    strcpy(str, "-rwxrwxrwx");

    // replace all false flags with '-'
    for (int i = 0; i < 9; i++) {
        str[9 - i] = (type >> i) & 1 ? str[9 - i] : '-';
    }

    // determine type of file
    switch (type & S_IFMT) {
        case S_IFBLK:
            str[0] = 'b';
            break;
        case S_IFCHR:
            str[0] = 'c';
            break;
        case S_IFDIR:
            str[0] = 'd';
             break;
        case S_IFIFO:
             str[0] = 'p';
             break;
        case S_IFLNK:
             str[0] = 'l';
             break;
        case S_IFSOCK:
             str[0] = 's';
             break;
        default:    // Includes reg file
             break;
    }
}

void usage(char* procname) {
    printf("Proper usage:\n");
    printf("\t%s [option]... [file]...\n", procname);
    printf("All arguments can be used like -[options] or -[option] -[option]...\n");
    printf("\t-i - include inode number of each file\n");
    printf("\t-l - long list format\n");
    printf("\t-n - like -l but with uid and gid\n");
    printf("\t-s - include size of each file in blocks\n");
}

