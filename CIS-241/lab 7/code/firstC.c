/* A simple C program */

#include <stdio.h>

int main ()
{
   int num1, num2, larger;

   printf ("enter two numbers: ");
   scanf ("%d%d", &num1, &num2);

   if (num1 > num2)
     larger = num1;
   else
     larger = num2;

   printf ("\n%s%d\n", "the larger number is ", larger);

   return 0;
}
