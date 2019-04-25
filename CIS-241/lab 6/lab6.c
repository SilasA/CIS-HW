#include <stdio.h>

#define TRUE 1
#define FALSE 0
#define MAX 25		// maximum size of stack

typedef int EType;	// element type 
typedef int BOOLEAN;

typedef struct {
	EType data[MAX];
	int top;
} STACK;

// initialize the stack
void initialize(STACK *p) 
{
	p->top = -1;
}

// return true if the stack is empty
BOOLEAN isEmpty(STACK *p) 
{
	return p->top < 0;
}

// return true if the statck is full
BOOLEAN isFull(STACK *p)  	
{
	return p->top == MAX-1; 
}

// return the top of the stack without removing it
EType top(STACK *p) 
{
	return p->data[p->top];
}

// remove the top from the stack and return it
EType pop(STACK *p)
{
    if (isEmpty(p)) return -1;
    return p->data[p->top--];
}

// put a value on the top of the stack
void push(EType elem, STACK *p)	
{
    if (isFull(p)) return;
    p->data[++(p->top)] = elem;
}

int main ()
{
	STACK s;
	int i, n;
	
	initialize (&s);

	for (i=0; i<10; i++)
	       push (i, &s);

	// write code below to pop a value from the stack repeatedly
	// until it becomes empty. also display each of the values. 
	while (!isEmpty(&s))
        {
            int data = pop(&s);
            printf("%d ", data);
        }
	
	return 0;
}
