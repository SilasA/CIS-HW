CC=gcc
CFLAGS=-I

p1: proj1.c
	gcc -o p1 proj1.c -I.

test1: p1
	./p1 1 apple_juice encrypt_input.txt encrypt_output.txt

test2: p1
	./p1 2 shephards+pie decrypt_input.txt decrypt_output.txt
