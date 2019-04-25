#include "house.h"

#include <iostream>

house::house()
{
    this->color = "unknown";
    this->price = -1;
    this->num_rooms = -1;
}

house::house(const string &ColorValue, int PriceValue, int Num_roomsValue)
{
    this->color = ColorValue;
    this->price = PriceValue;
    this->num_rooms = Num_roomsValue;
}

void house::SetColor(const string &ColorValue)
{
    this->color = ColorValue;
}

string house::GetColor() const { return this->color; }

void house::SetPrice(int PriceValue)
{
    this->price = PriceValue;
}

int house::GetPrice() const { return this->price; }

void house::SetNum_rooms(int Num_roomsValue)
{
    this->num_rooms = Num_roomsValue;
}

int house::GetNum_rooms() const { return this->num_rooms; }

void house::PrintInfo() const
{
    cout << this->color << " " << this->price << " " << this->num_rooms << endl;
}
