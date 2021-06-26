#include "file.h"
#include <stdio.h>
#include <string.h>

size_t read_file(char* filename, char** buffer) {
    FILE* in = fopen(filename, "r");
    if (in == NULL) 
        return -1;
    
    unsigned long size;

    // Check for RIFF
    char riff[5];
    fscanf(in, "%4s", riff);
    if (strcmp(riff, "RIFF") != 0)
        return -2;

    // Parse size of file
    unsigned char size0, size1, size2, size3;
    fscanf(in, "%c%c%c%c", &size0, &size1, &size2, &size3);

    // Assemble bytes
    size = (long)size0 | ((long)size1) << 8 | ((long)size2) << 16 | ((long)size3) << 24;
    *buffer = malloc(sizeof(char) * size + 8);

    if (*buffer == NULL) return -3; // Out of Memory

    // Add "RIFF" to bytes 0-3
    (*buffer)[0] = 'R';
    (*buffer)[1] = 'I';
    (*buffer)[2] = 'F';
    (*buffer)[3] = 'F';

    // Add chunk size to bytes 4-7
    (*buffer)[4] = size0;
    (*buffer)[5] = size1;
    (*buffer)[6] = size2;
    (*buffer)[7] = size3;

    // Read in file char by char based on chunk size
    char c;
    fscanf(in, "%c", &c);
    for (int i = 8; i < size + 8; i++) {
        (*buffer)[i] = c;
        fscanf(in, "%c", &c);
    }

    fclose(in);

    return size + 8;
}

size_t write_file(char* filename, char* buffer, size_t size) {
    FILE* out = fopen(filename, "w");
    if (out == NULL)
        return -1;

    for (int i = 0; i < size; i++) {
        fprintf(out, "%c", buffer[i]);
    }

    fclose(out);

    return size;
}

