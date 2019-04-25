#include <iostream>
#include <fstream>
#include <string>
#include <map>

using namespace std;

bool isdigit(string &word);
bool isexception(string &word);
void tolower(string &word);
void removepunct(string &word);

int main()
{
    // File Streams
    ifstream inFile;
    ofstream outFile;
    string word;

    map<string, int> wordFreq;

    inFile.open("input.txt");
    outFile.open("result.txt");
    
    while(inFile>>word)
    {
        if (isdigit(word) || isexception(word) || word.empty()) continue;

        removepunct(word);

        if (word.empty()) continue;

        tolower(word);

        if (wordFreq.find(word) == wordFreq.end())
            wordFreq[word] = 1;
        else wordFreq[word]++;
    }

    for (auto const &p : wordFreq)
        outFile << p.first << " " << p.second << std::endl;
    
    inFile.close();
    outFile.close();
    return 0;
}

bool isdigit(string &word)
{
    for (char c : word)
        if (isdigit(c)) 
            return true;
    return false;
}

bool isexception(string &word)
{
    return word == "a" || word == "the" || word == "\n";
}

void tolower(string &word)
{
    for (int i = 0; i < word.length(); i++)
    {
        word[i] = tolower(word[i]);
    }
}

void removepunct(string &word)
{
    string temp = word;
    word = "";
    for (char c : temp)
        if (!ispunct(c))
            word += c;
}
