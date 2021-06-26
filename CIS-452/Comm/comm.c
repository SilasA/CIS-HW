// Compile with clang -Wall

#include <signal.h>
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <stdbool.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <time.h>

bool running = true;
pid_t pid;

// Handler for all signals
void sigHandler(int number) {
    // Handle exit commands as parent
    if (number == SIGINT && pid != 0) {
        printf("Shutting Down.\n");
        kill(pid, SIGINT);
        running = false;
    }
    // Exit command as child
    else if (number == SIGINT) {
        running = false;
    }
    // Handle USR1 signal
    else if (number == SIGUSR1) {
        printf("SIGUSR1 (%d) signal recieved\n", SIGUSR1);
    }
    // Handle USR2 signal
    else if (number == SIGUSR2) {
        printf("SIGUSR2 (%d) signal recieved\n", SIGUSR2);
    }
}

int main() {
    // Exit handler
    signal(SIGINT, sigHandler);

    // Terminate if child was not created
    if ((pid = fork()) < 0) {
        perror("fork failed");
        return 1;
    }

    if (pid == 0) {
        srand(time(NULL));
        while (running) {
            // Time 1 thru 5 sec
            int t = 1 + rand() % 5;
            sleep(t);
            // Check if during sleep an exit was signaled
            if (running) kill(getppid(), t % 2 == 0 ? SIGUSR1 : SIGUSR2);
        }
    }
    else {
        printf("Child: %d\n", pid);

        // USR signal handlers
        signal(SIGUSR1, sigHandler);
        signal(SIGUSR2, sigHandler);
        
        // Wait for signals
        while (running) {
            printf("waiting...\t\t");
            pause();
            fflush(stdout);
        }
        // After exit, wait for child to terminate
        waitpid(pid, NULL, 0);
    }

    return 0;
}
