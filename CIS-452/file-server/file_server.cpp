// Compile with clang++ -Wall -std=c++11 -lpthread
#include <thread>
#include <iostream>
#include <cstdlib>
#include <mutex>
#include <chrono>
#include <csignal>

#include <unistd.h>

// Global data
int files_requested = 0;
std::mutex data_lock;

bool running = true;

/**
 * Handles signal interrupts on program exit
 * signal - Signal that triggered the interrupt
 */
void exit_handler(int signal);

/**
 * Simulate file accessing.
 * filename - the filename to simulate access to
 */
void file_access(std::string filename);

int main() {
    srand(getpid());

    // Exit handler
    signal(SIGINT, exit_handler);

    std::string input; 
    while (running) {
        std::cout << "Enter a file to access: ";
        std::getline(std::cin, input);

        std::thread t(file_access, input);
        t.detach();
    }

    return 0;
}

void exit_handler(int signal) {
    if (signal == SIGINT) {
        running = false;
        std::cout << "Files Requested: " << files_requested << std::endl;
        std::exit(0);
    }
}

void file_access(std::string filename) {
    int val = rand() % 10;
    if (val >= 0 && val < 8) {
        std::this_thread::sleep_for(std::chrono::seconds(1));
        std::cout << std::endl << filename << " accessed successfully" << std::endl;
    } else {
        std::this_thread::sleep_for(std::chrono::seconds(7 + rand() % 4));
        std::cout << std::endl << "Error accessing " << filename << std::endl;
    }
    // Increment files_accessed regardless of success
    data_lock.lock();
    files_requested++;
    data_lock.unlock();
}

