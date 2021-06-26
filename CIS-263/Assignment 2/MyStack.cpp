#include "MyStack.h"

// Vector Implementation
void MyStack_vector::push(const int &elem)
{
    elements.push_back(elem);
}

void MyStack_vector::pop()
{
    if (elements.empty()) return;
    elements.pop_back();
}

int MyStack_vector::top() const
{
    return elements.back();
}

bool MyStack_vector::empty() const
{
    return elements.empty();
}

int MyStack_vector::GetSize() const
{
    return elements.size();
}

// List Implementation
void MyStack_list::push(const int &elem)
{
    elements.push_back(elem);
}

void MyStack_list::pop()
{
    if (elements.empty()) return;
    elements.pop_back();
}

int MyStack_list::top() const
{
    return elements.back();
}

bool MyStack_list::empty() const
{
    return elements.empty();
}

int MyStack_list::GetSize() const
{
    return elements.size();
}

