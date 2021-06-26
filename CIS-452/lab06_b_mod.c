#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>
#include <semaphore.h>

void* swapper(void*);

int arr[2];
sem_t mutex;

int main(int argc, char** argv) {
    pthread_t who;
    long int loop;

    if (argc != 2) return 1;
    loop = atoi(argv[1]);
    
    // Create semaphore
    sem_init(&mutex, 0, 1);

    arr[0] = 0;
    arr[1] = 1;
    pthread_create(&who, NULL, swapper, &loop);
    for (int k = 0; k < loop; k++) {

        // Lock arr
        sem_wait(&mutex);

        int temp = arr[0];
        arr[0] = arr[1];
        arr[1] = temp;

        // Release arr
        sem_post(&mutex);
   }

    int rc;
    pthread_join(who, (void**) &rc);
    printf("Values: %5d %5d\n", arr[0], arr[1]);

    sem_destroy(&mutex);
}

void* swapper(void* arg) {
    long int* num = (long int*) arg;
    for (int k = 0; k < *num; k++) {

        // Lock arr
        sem_wait(&mutex);

        int temp = arr[0];
        arr[0] = arr[1];
        arr[1] = temp;
    
        // Release arr
        sem_post(&mutex);
    }
    return 0;
}

