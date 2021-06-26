#include <iostream>
#include <cerrno>
#include <vector>
#include <fstream>
#include <cctype>
#include <cstdio>
#include <csignal>
#include <cstdlib>

#include <unistd.h>
#include <sys/types.h>
#include <sys/wait.h>

typedef struct {
    int index;
    int lines;
    int words;
    int bytes;
} wc_data;

#define SIZE sizeof(wc_data)

bool waiting = true;

/**
 * Prints usage of the command
 */
void usage();

/**
 * Iterates through a file char by char computing line, word, and byte count.
 * source - input file
 * lines  - reference to line output
 * words  - reference to words output
 * bytes  - references to bytes output
 */
void get_wc(std::ifstream& source, int& lines, int& words, int& bytes);

/**
 * Displays the results in the proper format to match WC
 * results - result data from each child
 * files   - application input file names
 */
void display_results(std::vector<wc_data>& results, char** files);

/**
 * Handle childs SIGUSR1
 */
void sigHandler(int signal);

int main(int argc, char** argv) {
    if (argc < 2) {
        usage();
        return EINVAL;
    }

    std::vector<pid_t> children;
    std::vector<wc_data> result_data;
    std::vector<int> pipes;

    // Create children
    int i;
    int fd[2];
    for (i = 1; i < argc; i++) {
        // Create childs pipes
        if (pipe(fd) == -1) {
            perror("Pipe failed");
            return 1;
        }
        
        // Store read end of pipe in vector
        pipes.push_back(fd[0]);

        // Fork
        pid_t cur = fork();
        if (cur < 0) {
            close(fd[0]);
            close(fd[1]);
            return ECANCELED;
        }
        
        // Close unnecessary pipes
        if (cur == 0) {
            close(fd[0]);
            break;
        } else {
            close(fd[1]);
        }
        children.push_back(cur);
    }

    // Parent code block
    if (children.size() == argc - 1) {
        // Wait for each child to finish evaluation
        int count = 0;
        while (count < argc - 1 && wait(NULL)) count++;

        // Read the data from each child written to the pipe
        // Store it in a result vector
        for (int c = 1; c < argc; c++) {
            wc_data data;
            read(pipes[c - 1], &data, SIZE);
            result_data.push_back(data);
            close(pipes[c - 1]);
        }

        display_results(result_data, argv);
    }
    // Child code block
    else {

        signal(SIGUSR1, sigHandler);

        std::ifstream in;
        in.open(argv[i]);
        
        if (!in.is_open()) {
            std::cout << "Error reading file: " << argv[i] << std::endl;
            return ENOENT;
        }

        // Initialize struct values
        wc_data data;
        data.index = i;
        data.lines = 0;
        data.words = 0;
        data.bytes = 0;

        // Calculate counts
        get_wc(in, data.lines, data.words, data.bytes);

        in.close();

        // Suspend process for signal
        while (waiting) pause();

        // Communicate data back to parent
        write(fd[1], &data, SIZE);
        close(fd[1]);
    }

    return 0;
}

void usage() {
    std::cout << "Improper arguments." << std::endl;
    std::cout << "Usage: wc <filename> ..." << std::endl;
}

void get_wc(std::ifstream& source, int& lines, int& words, int& bytes) {
    char c = 0;
    bool isword = false;
    while (!source.eof()) {

        c = source.get();

        // Bytes increases with every successful read
        bytes++;

        // Newline increments lines and a word state machine increments words
        if (c == '\n') lines++;
        if (!isspace(c)) {
            isword = true;
        } else if (isword) {
            isword = false;
            words++;
        }
    }
}

void display_results(std::vector<wc_data>& results, char** files) {
    wc_data total;
    total.lines = 0;
    total.words = 0;
    total.bytes = 0;

    for (auto& d : results) {
        total.bytes += d.bytes;
    }
    int width = std::to_string(total.bytes).length();

    for (auto& d : results) {
        // Display results
        printf(" %*s %*s %*s %s\n", 
                width, std::to_string(d.lines).c_str(),
                width, std::to_string(d.words).c_str(),
                width, std::to_string(d.bytes).c_str(),
                files[d.index]);

        // Add to totals
        total.lines += d.lines;
        total.words += d.words;
    }

    // Total results
    printf(" %*s %*s %*s total\n", 
            width, std::to_string(total.lines).c_str(), 
            width, std::to_string(total.words).c_str(), 
            width, std::to_string(total.bytes).c_str());
}

void sigHandler(int signal) {
    if (signal == SIGUSR1) waiting = false;
}

