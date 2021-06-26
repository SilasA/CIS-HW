#ifndef WAV_H
#define WAV_H

typedef unsigned char byte;

typedef struct {
    unsigned long chunk_size;
    char fmt_chunk_marker[4];
    int fmt_section_length;
    short fmt_type;
    short channels;
    int sample_rate;
    int bits_per_sample_adjusted;
    short block_align;
    short bits_per_sample;
    unsigned long data_size;
    char* data;
} wav_file;

/**
 * Parses sequence of bytes into the wav file structure.
 *  contents - sequence of bytes from wav file
 * returns pointer to created struct of data or NULL if unsuccessful.
 */
wav_file* parse(char* contents);

#endif // WAV_H
