#include <stdio.h>
#include <unistd.h>
#include <stdlib.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <sys/ipc.h>
#include <sys/shm.h>

#define FOO 4096

int main() {
    int shmId;
    char* shmPtr;

    key_t key = ftok("./lab05_a.c", 42069);
    printf("%d\n", key);

    if ((shmId = 
         shmget(key, FOO,
                IPC_CREAT | S_IRUSR | S_IWUSR)) < 0) {
        perror("i can't get no..\n");
        exit(1);
    }

    if ((shmPtr = shmat(shmId, 0, 0)) == (void*) -1) {
        perror("can't attach\n");
        exit(1);
    }

    printf("Value a: %lu\t value b: %lu\n", (unsigned long)shmPtr,
            (unsigned long)shmPtr + FOO);

    pause();

    if (shmdt(shmPtr) < 0) {
        perror("just can't let go\n");
        exit(1);
    }

    if (shmctl(shmId, IPC_RMID, 0) < 0) {
        perror("can't deallocate\n");
        exit(1);
    }

    return 0;
}

