/*
Problem: Write a function that reverses a string.
The input string is given as an array of characters s.
You must do this by modifying the input array in-place with O(1) extra memory.

Source: LeetCode Problem #344 → https://leetcode.com/problems/reverse-string/

Approaches:
1. Brute Force (Extra Array)
   - Idea: Copy characters into a new array in reverse order, then overwrite.
   - Time Complexity: O(n)
   - Space Complexity: O(n)

2. Two-Pointer Technique (Optimal)
   - Idea: Use two pointers (start and end), swap characters until they meet.
   - Time Complexity: O(n)
   - Space Complexity: O(1)
*/

#include <bits/stdc++.h>
using namespace std;

// Approach 1: Brute Force using Extra Array
void reverseStringBrute(vector<char>& s) {
    vector<char> temp(s.rbegin(), s.rend()); // reversed copy
    for (int i = 0; i < s.size(); i++) {
        s[i] = temp[i];
    }
}

// Approach 2: Two-Pointer (Optimal)
void reverseStringOptimal(vector<char>& s) {
    int left = 0, right = s.size() - 1;
    while (left < right) {
        swap(s[left], s[right]);
        left++;
        right--;
    }
}

int main() {
    vector<char> s = {'h','e','l','l','o'};

    // Test Brute Force
    vector<char> s1 = s;
    reverseStringBrute(s1);
    cout << "Brute Force Result: ";
    for (char c : s1) cout << c;
    cout << endl;

    // Test Optimal
    vector<char> s2 = s;
    reverseStringOptimal(s2);
    cout << "Optimal Result: ";
    for (char c : s2) cout << c;
    cout << endl;

    return 0;
}
