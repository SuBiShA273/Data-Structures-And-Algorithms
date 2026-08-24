/*
Problem: Given a string s, return true if s can be a palindrome after deleting at most one character.

Source: LeetCode Problem #680 → https://leetcode.com/problems/valid-palindrome-ii/

Approaches:
1. Brute Force (Try Removing Each Character)
   - Idea:
     - For each index, remove that character and check if the remaining string is a palindrome.
     - If any removal works, return true.
   - Time Complexity: O(n^2).
   - Space Complexity: O(n).

2. Optimized (Two Pointers)
   - Idea:
     - Use two pointers (left, right).
     - If characters match, move inward.
     - If mismatch occurs, try skipping either left or right character once.
     - Check if either substring is palindrome.
   - Time Complexity: O(n).
   - Space Complexity: O(1).
*/

import java.util.*;

// Approach 1: Brute Force
class ValidPalindromeBrute {
    public boolean validPalindrome(String s) {
        for (int i = 0; i < s.length(); i++) {
            String candidate = s.substring(0, i) + s.substring(i + 1);
            if (isPalindrome(candidate)) return true;
        }
        return isPalindrome(s);
    }

    private boolean isPalindrome(String str) {
        int l = 0, r = str.length() - 1;
        while (l < r) {
            if (str.charAt(l) != str.charAt(r)) return false;
            l++;
            r--;
        }
        return true;
    }
}

// Approach 2: Optimized (Two Pointers)
class ValidPalindromeOptimized {
    public boolean validPalindrome(String s) {
        int l = 0, r = s.length() - 1;
        while (l < r) {
            if (s.charAt(l) != s.charAt(r)) {
                return isPalindrome(s, l + 1, r) || isPalindrome(s, l, r - 1);
            }
            l++;
            r--;
        }
        return true;
    }

    private boolean isPalindrome(String s, int l, int r) {
        while (l < r) {
            if (s.charAt(l) != s.charAt(r)) return false;
            l++;
            r--;
        }
        return true;
    }
}

// Driver class
public class Main {
    public static void main(String[] args) {
        String s1 = "aba";
        String s2 = "abca";

        // Test Brute Force
        ValidPalindromeBrute brute = new ValidPalindromeBrute();
        System.out.println("Brute Force Result (aba): " + brute.validPalindrome(s1)); // true
        System.out.println("Brute Force Result (abca): " + brute.validPalindrome(s2)); // true

        // Test Optimized
        ValidPalindromeOptimized opt = new ValidPalindromeOptimized();
        System.out.println("Optimized Result (aba): " + opt.validPalindrome(s1)); // true
        System.out.println("Optimized Result (abca): " + opt.validPalindrome(s2)); // true
    }
}
