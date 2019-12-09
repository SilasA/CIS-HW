#!/usr/bin/python3
import sys
from DFA import DFA

def usage():
    print("Usage: main.py [filename]")
    print("\tfilename\tthe name of the file with input strings")
    print("\toptions \tt - output the tokenized string")

# starting point
def main():
    if (len(sys.argv) <= 1):
        usage()
        return
    file_name = sys.argv[1]

    try:
        file = open(file_name, 'r');
    except FileNotFoundError:
        print("Error: Could not find the given file by name.")
        sys.exit()

    with file:
        input = file.read()

        if (len(sys.argv) > 2 and sys.argv[2] == "t"):
            letters = [input[i:i + 3] for i in range(0, len(input), 3)]
            print(str(letters))
            return

        # Test Input
        dfa = DFA()
        if (dfa.is_accepted(input)):
            print("ACCEPT")
        else:
            print("REJECT")
    return

main()

