#include <stdlib.h>
#include <stdio.h>
#include <errno.h>
#include "file.h"
#include "wav.h"

/**
 * Displays how to properly use the application.
 */
void usage();

/**
 * Displays wav information
 *  file - wav file data to display
 */
void display_file_info(wav_file* file);

/**
 * Swaps 2 bytes in place
 */
void swap(char* a, char* b);

int main(int argc, char** argv) {

    if (argc < 3) {
        usage();
        return EINVAL;
    }

    printf("File: %s\n", argv[1]);

    char* buffer;
    int size = read_file(argv[1], &buffer);

    // Check for read errors
    if (size == -1) {
        printf("File not found\n");
        return ENOENT; // Something
    }
    else if (size == -2) {
        printf("Incorrect format\n");
        return 1; // Something
    }
    else if (size == -3) {
        printf("Out of Memory\n");
        return ENOMEM;
    }
    
    // Parse bytes
    wav_file* song = parse(buffer);
  
    if (song == NULL) {
        printf("Song parse was unsuccessful\n");
        return 3;
    }

    display_file_info(song);

    // Reverse song data
    for (int i = 0; i < song->data_size / 2; i += song->block_align) {
        for (int b = 0; b < song->block_align; b++) {
            swap(
                    song->data + i + b,
                    song->data + song->data_size - song->block_align - i + b
                );
        }
    }
    
    // Write reversal to buffer
    for (int i = 0; i < song->data_size; i++) {
        buffer[i + 44] = song->data[i];
    }

    // Write reversal buffer to file
    size = write_file(argv[2], buffer, size);

    free(buffer);
    free(song);

    return 0;
}

void usage() {
    printf("");
}

void display_file_info(wav_file* file) {
    printf("========================================\n");
    printf("File size: %luB; Read: %luB\n",
            file->chunk_size + 8, file->chunk_size);
    printf("Format: \"%4s\" with length %d\n",
            file->fmt_chunk_marker, file->fmt_section_length);
    printf("Format type: %d\n", file->fmt_type);
    printf("Channels: %d; Sample rate: %d\n",
            file->channels, file->sample_rate);
    printf("Byte rate: %d; Alignment: %d; Bits/Sample: %d\n",
            file->bits_per_sample_adjusted, file->block_align, file->bits_per_sample);
    printf("Data size: %luB\n", file->data_size);
    printf("========================================\n");
}

void swap(char* a, char* b) {
    char temp = *a;
    *a = *b;
    *b = temp;
}

