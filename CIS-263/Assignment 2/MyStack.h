#ifndef HW2_MYSTACK_H
#define HW2_MYSTACK_H

#include <vector>
#include <list>
#include <iostream>

using namespace std;

class MyStack_vector    //using vector to implement a stack
{
private:
    vector<int> elements;
public:
    void push(const int&);   //push an item in the stack
    void pop(); //pop an item from the stack
    int top() const; //return the item on the top of the stack, no actual pop() happens
    bool empty() const; //whether the stack is empty?
    int GetSize() const;    //get the number of elements in the stack
};

class MyStack_list      //using list to implement a stack
{
private:
    list<int> elements;
public:
    void push(const int&);   //push an item in the stack
    void pop(); //pop an item from the stack
    int top() const; //return the item on the top of the stack, no actual pop() happens
    bool empty() const; //whether the stack is empty?
    int GetSize() const;    //get the number of elements in the stack
};

#endif //HW2_MYSTACK_H
