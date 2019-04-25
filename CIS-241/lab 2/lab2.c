#include <stdio.h>
#include <ctype.h>

int main()
{
    char ch;

    printf("Enter text (Ctrl+D to quit).\n");
    while (ch = getchar(), ch != EOF)
    {
        if (islower(ch))
            printf("%d", ch - 'a');
        else if (isupper(ch))
            printf("%d", ch - 'A');
        else
            putchar(ch);
    }

    return 0;
}
