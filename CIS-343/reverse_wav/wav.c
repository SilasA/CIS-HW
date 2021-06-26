#include <stdlib.h>
#include <string.h>
#include <stdio.h>
#include "wav.h"

wav_file* parse(char* contents) {
    wav_file* song = malloc(sizeof(wav_file));

    song->chunk_size = (byte)contents[4] | 
        ((byte)contents[5]) << 8 |
        ((byte)contents[6]) << 16 |
        ((byte)contents[7]) << 24;
    
    if (contents[8] != 'W' || contents[9] != 'A' ||
        contents[10] != 'V' || contents[11] != 'E') {
        free(song);
        return NULL;
    }

    song->fmt_chunk_marker[0] = contents[12];
    song->fmt_chunk_marker[1] = contents[13];
    song->fmt_chunk_marker[2] = contents[14];
    song->fmt_chunk_marker[3] = contents[15];

    song->fmt_section_length = (byte)contents[16] |
            ((byte)contents[17]) << 8 |
            ((byte)contents[18]) << 16 |
            ((byte)contents[19]) << 24;

    song->fmt_type = (byte)contents[20] | ((byte)contents[21]) << 8;

    song->channels = (byte)contents[22] | ((byte)contents[23]) << 8;

    song->sample_rate = (byte)contents[24] |
        ((byte)contents[25]) << 8 |
        ((byte)contents[26]) << 16 | 
        ((byte)contents[27]) << 24;

    song->bits_per_sample_adjusted = (byte)contents[28] |
        ((byte)contents[29]) << 8 |
        ((byte)contents[30]) << 16 | 
        ((byte)contents[31]) << 24;

    song->block_align = (byte)contents[32] | ((byte)contents[33]) << 8;

    song->bits_per_sample = (byte)contents[34] | ((byte)contents[35]) << 8;
 
    if (contents[36] != 'd' || contents[37] != 'a' ||
        contents[38] != 't' || contents[39] != 'a') {
        free(song);
        return NULL;
    }

    song->data_size = (byte)contents[40] |
        ((byte)contents[41]) << 8 |
        ((byte)contents[42]) << 16 | 
        ((byte)contents[43]) << 24;

    song->data = contents + 44;

    return song;
}

