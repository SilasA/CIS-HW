#include <iostream>
#include <string>
#include <vector>
#include <sstream>
#include <cstring>
#include <chrono>

#include <errno.h>
#include <unistd.h>
#include <sys/wait.h>
#include <sys/resource.h>

int main(int argc, char** argv) {

    std::string line;
    long cxtsw = 0;
    std::cout << "cmd [or quit]>";
    while (std::getline(std::cin, line)) {        
        // Check for empty/whitespace inputs
        if (line.empty() || line.find_first_not_of(" ") == std::string::npos) {
            std::cout << "cmd [or quit]>";
            continue;
        }

        // Tokenize input
        std::istringstream sstr(line);
        std::string token;
        std::vector<std::string> exe_argv;

        while (sstr >> token) { 
            exe_argv.push_back(token);
        }

        // Check the tokenized input for quit command
        if (exe_argv[0] == "quit") break;
        
        // Translate string vector to C
        char** argv = new char*[exe_argv.size() + 1];
        for (unsigned long i = 0; i < exe_argv.size(); i++) {
            argv[i] = new char[exe_argv.size() + 1];
            std::strcpy(argv[i], exe_argv[i].c_str());
        }
        argv[exe_argv.size()] = nullptr; // for execvp

        // Branch to run provided command as child
        int pid = fork();
        if (pid < 0) {
            // Unable to fork
            std::cout << "Error starting command." << std::endl;
        }
        else if (pid == 0) {
            // Attempt to run command in child
            int err = execvp(argv[0], argv);
            if (err == -1 || errno != 0) {
                std::cout << "Command not found." << std::endl;
                exit(1);
            }
            exit(0);
        }
        else {
            // Execution time tracking while waiting for child to terminate
            std::chrono::milliseconds start = std::chrono::duration_cast<std::chrono::milliseconds>(
                    std::chrono::system_clock::now().time_since_epoch()
                );
            waitpid(pid, NULL, 0);
            std::chrono::milliseconds end = std::chrono::duration_cast<std::chrono::milliseconds>(
                    std::chrono::system_clock::now().time_since_epoch()
                );

            // Get cxt switches
            struct rusage stats;
            getrusage(RUSAGE_CHILDREN, &stats);
            cxtsw = stats.ru_nivcsw - cxtsw;
            if (cxtsw < 0) cxtsw = 0;

            std::cout << "<Executed in " << (end - start).count() << "ms with " 
                << stats.ru_nivcsw << " inv. context switches>" << std::endl;

            // Clear up memory
            for (unsigned long i = 0; i < exe_argv.size(); i++) 
                delete argv[i];
            delete[] argv;
        }
 
        std::cout << "cmd [or quit]>";
    }

    return 0;
}
