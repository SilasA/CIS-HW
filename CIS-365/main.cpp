#include <stdio.h>
#include <cctype>

int main(int argc, char* argv[]) {
    if (argc < 4) return -1;
    if (argv[2] != "0" && argv[2] != "1") return -1;
    if (argv[3] != "0" && argv[3] != "1") return -1;

    if (tolower(argv[1]) == "and") {
        std::cout << argv[2] << " AND " << argv[3] << " is " <<
            argv[2] == "1" && argv[3] == "1";
    }
    else if (tolower(argv[1]) == "or") {
        std::cout << argv[2] << " OR " << argv[3] << " is " <<
            argv[2] == "1" || argv[3] == "1";
    }
    else if (tolower(argv[1]) == "xor") {
        std::cout << argv[2] << " XOR " << argv[3] << " is " <<
            argv[2] == "1" ^ argv[3] == "1";
    }
    else {
        std::cout << argv[1] + " is not a supported gate." << std::endl;
    }
}
