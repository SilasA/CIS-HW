#include <stdlib.h>
#include <stdio.h>
#include <stdbool.h>
#include <string.h>

#define MAX_LENGTH 94

void usage();
bool found(char*, int, char);
void initializeEncryptArray(char*, char*);
void initializeDecryptArray(char*, char*);
void processInput(FILE*, FILE*, char*);

// Use ASCII 33 to 126
int main(int argc, char **argv)
{
    if (argc != 5)
    {
        usage();
        return 1;
    }

    char encrypt[MAX_LENGTH], decrypt[MAX_LENGTH];

    bool is_encrypt = strcmp(argv[1], "1") == 0;

    FILE *in, *out;
    in = fopen(argv[3], "r");
    out = fopen(argv[4], "w");

    initializeEncryptArray(argv[2], encrypt);
    initializeDecryptArray(encrypt, decrypt);

    int i;

    if (is_encrypt)
        processInput(in, out, encrypt);
    else processInput(in, out, decrypt);
}

void usage()
{
    printf("--Program Usage--------------------------------------\n");
    printf("./program [function] [key] [input file] [output file]\n\n");
    printf("  function:\n");
    printf("    \"1\" - Encrypt input using provided key\n");
    printf("    \"2\" - Decrypt input using provided key\n");
    printf("-----------------------------------------------------\n");
}

bool found(char list[], int n, char target)
{
    int i;
    for (i = 0; i < n; i++)
        if (list[i] == target)
            return true;
    return false;
}

void initializeEncryptArray(char key[], char encrypt[])
{
    int i, size = 0;

    for (i = 0; i < strlen(key); i++)
    {
        if (!found(encrypt, size, key[i]))
            encrypt[size++] = key[i];
    }

    char ch = 126;
    while (size <= MAX_LENGTH)
    {
        if (!found(encrypt, size, ch))
            encrypt[size++] = ch;
        ch--;
    }
}

void initializeDecryptArray(char key[], char decrypt[])
{
    int i, j;
    for (i = 0; i < MAX_LENGTH; i++)
    {
        char ch = i + 33;
        for (j = 0; j < MAX_LENGTH; j++)
        {
            if (key[j] == ch)
            {
                decrypt[i] = j + 33;
                break;
            }
        }
    }
}

void processInput(FILE *inf, FILE *outf, char substitute[])
{
    char ch;
    while (fscanf(inf, "%c", &ch) != EOF)
    {
        if (ch == ' ' || ch == '\n')
        {
            fprintf(outf, "%c", ch);
            continue;
        }

        fprintf(outf, "%c", substitute[ch-33]);
    }
}
