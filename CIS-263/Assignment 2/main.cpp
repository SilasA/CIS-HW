#include <iostream>
#include "MyStack.h"
using namespace std;

int main()
{
    int i;
    cout<<"-----testing the stack_vector-----"<<endl;      //testing the stack_vector
    MyStack_vector Stack_vector;        //create a stack using vector
    for(i=0;i<5;i++)
    {
        Stack_vector.push(i);
        cout<<Stack_vector.top()<<" ";
    }
    cout<<"have been pushed!"<<endl;

    cout<<"Current stack size is "<<Stack_vector.GetSize()<<endl;

    for(i=0;i<5;i++)
    {
        cout<<Stack_vector.top()<<" ";
        Stack_vector.pop();
    }
    cout<<"have been popped!"<<endl;
    Stack_vector.pop();    //an additional pop() intentionally

    cout<<"Current stack size is "<<Stack_vector.GetSize()<<endl;

    if (Stack_vector.empty())
    {
        cout<<"Current stack is empty!"<<endl;
    }
    else
    {
        cout<<"Current Stack is not empty!"<<endl;
    }

    cout<<endl<<"-----testing the stack_list-----"<<endl;      //testing the stack_list
    MyStack_list Stack_list;            //create a stack using list

    for(i=0;i<5;i++)
    {
        Stack_list.push(i);
        cout<<Stack_list.top()<<" ";
    }
    cout<<"have been pushed!"<<endl;

    cout<<"Current stack size is "<<Stack_list.GetSize()<<endl;

    for(i=0;i<5;i++)
    {
        cout<<Stack_list.top()<<" ";
        Stack_list.pop();
    }
    cout<<"have been popped!"<<endl;
    Stack_list.pop();    //an additional pop() intentionally

    cout<<"Current stack size is "<<Stack_list.GetSize()<<endl;

    if (Stack_list.empty())
    {
        cout<<"Current stack is empty!"<<endl;
    }
    else
    {
        cout<<"Current Stack is not empty!"<<endl;
    }

    return 0;
}
