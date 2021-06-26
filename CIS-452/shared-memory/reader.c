#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <string.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <sys/ipc.h>
#include <sys/shm.h>

#define SEED_FILE "./writer.c"
#define SEED_INT 112358

#define SIZE (1024 + sizeof(char))
#define COUNTER_OFFSET (SIZE - sizeof(char))

// Globals
char _read_flag = 0;

void read_shm(char* mem, char* line);

int main() {
    int shmId;
    char* shmPtr;
    char line[1024];

    // Get shared memory with key and attach
    key_t key = ftok(SEED_FILE, SEED_INT);

    // Segment has 1024 byte string + a waiting counter byte
    if ((shmId = shmget(key, SIZE, S_IRUSR | S_IWUSR)) < 0) {
        perror("Unable to create shared memory.");
        return 1;
    }

    if ((shmPtr = shmat(shmId, NULL, 0)) == (void*) -1) {
        perror("Unable to attach reader to shared memory.");
        return 1;
    }

    while (1) {
        read_shm(shmPtr, line);

        // exit the program
        if (!strcmp(line, "quit\n")) break;

        printf("Received: %s", line);
    }

    // Detach reader
    if (shmdt(shmPtr) < 0) {
        perror("Unable to detach reader.");
        return 1;
    }

    return 0;
}

void read_shm(char* mem, char* line) {
    // Wait until read counter is at zero before writing
    while (*(mem + COUNTER_OFFSET) == 0 || _read_flag) {
        if (*(mem + COUNTER_OFFSET) == 2) _read_flag = 0;
        usleep(1000); // wait for 1ms to save CPU cycles
    }

    strncpy(line, mem, 1024);    // copy shm into line
    (*(mem + COUNTER_OFFSET))--; // decrement counter for writer
    _read_flag = 1;
}

