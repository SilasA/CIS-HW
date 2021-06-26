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

void write_shm(char* mem, char* line);

int main() {
    int shmId;
    char* shmPtr;
    char line[1024];

    // Create shared memory with key and attach
    key_t key = ftok(SEED_FILE, SEED_INT);

    // Segment has 1024 byte string + a waiting counter byte
    if ((shmId = shmget(key, SIZE, IPC_CREAT | S_IRUSR | S_IWUSR)) < 0) {
        perror("Unable to create shared memory.");
        return 1;
    }

    if ((shmPtr = shmat(shmId, 0, 0)) == (void*) -1) {
        perror("Unable to attach writer to shared memory.");
        return 1;
    }

    // Set counter to initial value
    *(shmPtr + COUNTER_OFFSET) = 0;

    // Loop user input
    printf("Enter line: ");
    fgets(line, 1024, stdin);
    while (1) {
        write_shm(shmPtr, line);

        // exit the program
        if (!strcmp(line, "quit\n")) break;

        printf("Enter line: ");
        fgets(line, 1024, stdin);
    }

    // Wait for each reader to quit
    while (*(shmPtr + COUNTER_OFFSET) != 0)
    {
        usleep(10000);
    }

    // Detach and deallocate memory
    if (shmdt(shmPtr) < 0) {
        perror("Unable to detach writer.");
        return 1;
    }

    if (shmctl(shmId, IPC_RMID, 0) < 0) {
        perror("Unable to deallocate memory.");
        return 1;
    }

    return 0;
}

void write_shm(char* mem, char* line) {
    // Wait until read counter is at zero before writing
    while (*(mem + COUNTER_OFFSET) != 0/* || *(mem + FLAG_OFFSET) & 3*/)
        usleep(1000); // wait for 1ms to save CPU cycles
    
    strncpy(mem, line, 1024);    // copy line into shm
    *(mem + COUNTER_OFFSET) = 2; // set counter to 2 for readers
}

