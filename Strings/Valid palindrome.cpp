/*
Problem: Given a string s, return true if it is a palindrome,
considering only alphanumeric characters and ignoring cases.

Source: LeetCode Problem #125 → https://leetcode.com/problems/valid-palindrome/

Approaches:
1. Extreme Brute Force
   - Idea: Clean the string (remove non-alphanumeric, lowercase it),
           then reverse and compare with original.
   - Time Complexity: O(n)
   - Space Complexity: O(n)

2. Two-Pointer (Optimal)
   - Idea: Use two pointers from start and end, skip non-alphanumeric,
           compare characters ignoring case.
   - Time Complexity: O(n)
   - Space Complexity: O(1)
*/

#include <bits/stdc++.h>
using namespace std;

// Helper: Check if character is alphanumeric
bool isAlphaNum(char c) {
    return isalnum(c);
}

// Approach 1: Extreme Brute Force
bool isPalindromeBrute(string s) {
    string cleaned = "";
    for (char c : s) {
        if (isAlphaNum(c)) cleaned += tolower(c);
    }
    string reversed = cleaned;
    reverse(reversed.begin(), reversed.end());
    return cleaned == reversed;
}

// Approach 2: Two-Pointer (Optimal)
bool isPalindromeTwoPointer(string s) {
    int left = 0, right = s.size() - 1;
    while (left < right) {
        while (left < right && !isAlphaNum(s[left])) left++;
        while (left < right && !isAlphaNum(s[right])) right--;
        if (tolower(s[left]) != tolower(s[right])) return false;
        left++;
        right--;
    }
    return true;
}

int main() {
    string s = "A man, a plan, a canal: Panama";

    // Test Brute Force
    cout << "Brute Force Result: "
         << (isPalindromeBrute(s) ? "True" : "False") << endl;

    // Test Two-Pointer
    cout << "Two-Pointer Result: "
         << (isPalindromeTwoPointer(s) ? "True" : "False") << endl;

    return 0;
}
