# !/usr/bin python3

import sys

def usage():
    print("Usage - char_to_ascii [input] [output] [<options>]")
    print("\tinput  \tthe name of the file to convert from")
    print("\toutput \tthe name of the file to store the conversion")
    print("\toptions\tr - reverse the conversion (ascii to char)")

def main():
    if (len(sys.argv) < 3):
        usage()
        return

    input_name = sys.argv[1]
    output_name = sys.argv[2]

    try:
        in_file = open(input_name, 'r')
    except FileNotFoundError:
        print("Error: file not found by name: " + str(input_name))
        return

    out_file = open(output_name, 'w')

    with in_file:
        text = in_file.read()

        if (len(sys.argv) == 4 and sys.argv[3] == "-r"):
            text = [text[i:i + 3] for i in range(0, len(text), 3)]
            for s in text:
                out_file.write(str(chr(int(s))))
        else:
            for c in text:
                out_file.write("{:03d}".format(ord(c)))

        in_file.close()
        out_file.close()

main()
        
