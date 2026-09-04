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

class Solution {
    // Helper: Check if character is alphanumeric
    private boolean isAlphaNum(char c) {
        return Character.isLetterOrDigit(c);
    }

    // Approach 1: Extreme Brute Force
    public boolean isPalindromeBrute(String s) {
        StringBuilder cleaned = new StringBuilder();
        for (char c : s.toCharArray()) {
            if (isAlphaNum(c)) cleaned.append(Character.toLowerCase(c));
        }
        String str = cleaned.toString();
        String reversed = cleaned.reverse().toString();
        return str.equals(reversed);
    }

    // Approach 2: Two-Pointer (Optimal)
    public boolean isPalindromeTwoPointer(String s) {
        int left = 0, right = s.length() - 1;
        while (left < right) {
            while (left < right && !isAlphaNum(s.charAt(left))) left++;
            while (left < right && !isAlphaNum(s.charAt(right))) right--;
            if (Character.toLowerCase(s.charAt(left)) != Character.toLowerCase(s.charAt(right))) {
                return false;
            }
            left++;
            right--;
        }
        return true;
    }
}

// Driver class
public class Main {
    public static void main(String[] args) {
        Solution sol = new Solution();
        String s = "A man, a plan, a canal: Panama";

        // Test Brute Force
        System.out.println("Brute Force Result: " + sol.isPalindromeBrute(s)); // true

        // Test Two-Pointer
        System.out.println("Two-Pointer Result: " + sol.isPalindromeTwoPointer(s)); // true
    }
}
