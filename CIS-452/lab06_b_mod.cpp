#include <iostream>
#include <iomanip>
#include <thread>
#include <mutex>
#include <cstdlib>

void swapper(long int);

int arr[2];
std::mutex mtx;

int main(int argc, char* argv[]) {
    long int loop;

    if (argc != 2) return 1;
    loop = std::atoi(argv[1]);

    arr[0] = 0;
    arr[1] = 1;

    std::thread t1(swapper, loop);
    for (int k = 0; k < loop; k++) {

        mtx.lock();

        int temp = arr[0];
        arr[0] = arr[1];
        arr[1] = temp;

        mtx.unlock();
    }

    t1.join();
    std::cout << "Values: " << std::setw(5) << arr[0] << std::setw(5) << arr[1] << std::endl;
}

void swapper(long int num) {
    for (int k = 0; k < num; k++) {

        mtx.lock();

        int temp = arr[0];
        arr[0] = arr[1];
        arr[1] = temp;

        mtx.unlock();
    }
}

