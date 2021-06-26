#include <thread>
#include <iostream>
#include <mutex>
#include <cstdlib>
#include <ctime>
#include <chrono>
#include <iomanip>

/**
 * Producer worker thread function
 * buffer - pointer to buffer array for numbers
 * bufferSize - size of buffer
 * sleepTime - pointer to time in ms to sleep in between iteration (changed by main thread)
 * exit - exit condition for worker loop (changed by main thread)
 */
void producer(int* buffer, int bufferSize, int* sleepTime, bool* exit);

/**
 * Consumer worker thread function
 * buffer - pointer to buffer array for numbers
 * bufferSize - size of buffer
 * sleepTime - pointer to time in ms to sleep in between iteration (changed by main thread)
 * exit - exit condition for worker loop (changed by main thread)
 */
void consumer(int* buffer, int bufferSize, int* sleepTime, bool* exit);

// Mutex lock for buffer
std::mutex bufferLock;

// Counting semaphore for filled buffer slots
std::mutex filledLock;
int filled = 0;

// Counting semaphore for empty buffer slots
std::mutex emptyLock;
int empty;

int main(int argc, char** argv) {
    if (argc != 4) {
        std::cout << "Improper argument count" << std::endl;
        return 1;
    }

    // Read argv
    int bufferSize = std::atoi(argv[1]);
    int producerSleepTime = std::atoi(argv[2]);
    int consumerSleepTime = std::atoi(argv[3]);
    
    // Initialize values
    bool exit = false;
    std::srand(std::time(NULL));
    empty = bufferSize;
    int* buffer = new int[bufferSize];

    // Producer/Consumer threads
    std::thread prodThread(producer, buffer, bufferSize, &producerSleepTime, &exit);
    std::thread consThread(consumer, buffer, bufferSize, &consumerSleepTime, &exit);

    char in;
    std::cin >> in;
    while (in != 'q') {
        switch (in) {
        case 'a':
            producerSleepTime += 250;
            break;
        case 'z':
            producerSleepTime -= 250;
            if (producerSleepTime < 0) producerSleepTime = 0;
            break;
        case 's':
            consumerSleepTime += 250;
            break;
        case 'x':
            consumerSleepTime -= 250;
            if (consumerSleepTime < 0) consumerSleepTime = 0;
            break;
        default:
            break;
        }
        std::cin >> in;
    }

    // Trigger thread exit
    exit = true;
    prodThread.join();
    consThread.join();

    // Cleanup
    delete[] buffer;

    return 0;
}

void producer(int* buffer, int bufferSize, int* sleepTime, bool* exit) {
    int index = 0;
    while (!(*exit)) {
        int num = 1000 + std::rand() % 8000;

        // Empty wait
        emptyLock.lock();
        // Use this instead of busy wait to prevent exit problems
        if (empty <= 0) {
            emptyLock.unlock();
            continue;
        }
        empty--;
        emptyLock.unlock();

        // Critical section
        bufferLock.lock();
        buffer[index] = num;
        std::cout << "Put " << num << " into bin " << std::setw(2) << index << std::endl;
        index++;
        index %= bufferSize;
        bufferLock.unlock();

        // Filled signal
        filledLock.lock();
        filled++;
        filledLock.unlock();

        std::this_thread::sleep_for(std::chrono::milliseconds(*sleepTime));
    }
}

void consumer(int* buffer, int bufferSize, int* sleepTime, bool* exit) {
    int index = 0;
    // Loop until exit signal and all items are consumed
    while (!(*exit) || filled > 0) {
        int num;

        // Filled wait
        filledLock.lock();
        // Use this instead of busy wait ot prevent exit problems
        // (It's still technically a busy wait b/c its inside the while)
        if (filled <= 0) {
            filledLock.unlock();
            continue;
        }
        filled--;
        filledLock.unlock();


        // Critical section
        bufferLock.lock();
        num = buffer[index];
        std::cout << "                      " << "Get " << num << " from bin " << index << std::endl;
        index++;
        index %= bufferSize;
        bufferLock.unlock();

        // Emtpy signal
        emptyLock.lock();
        empty++;
        emptyLock.unlock();

        std::this_thread::sleep_for(std::chrono::milliseconds(*sleepTime));
    }
}

