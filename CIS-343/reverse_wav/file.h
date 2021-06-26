#ifndef FILE_H
#define FILE_H

#include <stdlib.h>

/**
 * Reads in bytes from a wav file
 *  filename - name of file to read from
 *  buffer - dynamically allocated output of bytes
 * returns size of file or negative error code if unsuccessful
 */
size_t read_file(char* filename, char** buffer);

/**
 * Writes a buffer of bytes to a file
 *  filename - name of file to write to
 *  buffer - bytes to write
 *  size - size of buffer
 * returns size of file
 */
size_t write_file(char* filename, char* buffer, size_t size);

#endif // FILE_H
