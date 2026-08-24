/*
Problem: Given a string s containing only '(', ')', and '*'.
- '*' can represent either '(' or ')' or an empty string.
Return true if the string is valid.

Source: LeetCode Problem #678 → https://leetcode.com/problems/valid-parenthesis-string/

Approaches:
1. Brute Force (Backtracking)
   - Idea:
     - For each '*', try all 3 possibilities: '(', ')', or empty.
     - Recursively check if the resulting string is valid.
   - Time Complexity: Exponential (O(3^k), k = number of '*').
   - Space Complexity: O(n).

2. Optimized (Greedy / Range Tracking)
   - Idea:
     - Track the possible range of open parentheses count.
     - For each character:
       - '(' → increment both min and max open counts.
       - ')' → decrement both min and max open counts.
       - '*' → treat as flexible:
           - decrement min (if >0), increment max.
     - If max < 0 → invalid.
     - At the end, valid if min == 0.
   - Time Complexity: O(n).
   - Space Complexity: O(1).
*/

import java.util.*;

// Approach 1: Brute Force (Backtracking)
class ValidParenthesisBrute {
    public boolean checkValidString(String s) {
        return backtrack(s.toCharArray(), 0);
    }

    private boolean backtrack(char[] arr, int pos) {
        if (pos == arr.length) return isValid(arr);

        if (arr[pos] == '*') {
            arr[pos] = '(';
            if (backtrack(arr, pos + 1)) return true;
            arr[pos] = ')';
            if (backtrack(arr, pos + 1)) return true;
            arr[pos] = ' ';
            if (backtrack(arr, pos + 1)) return true;
            arr[pos] = '*'; // reset
            return false;
        }
        return backtrack(arr, pos + 1);
    }

    private boolean isValid(char[] arr) {
        int balance = 0;
        for (char c : arr) {
            if (c == '(') balance++;
            else if (c == ')') {
                balance--;
                if (balance < 0) return false;
            }
        }
        return balance == 0;
    }
}

// Approach 2: Optimized (Greedy / Range Tracking)
class ValidParenthesisOptimized {
    public boolean checkValidString(String s) {
        int minOpen = 0, maxOpen = 0;
        for (char c : s.toCharArray()) {
            if (c == '(') {
                minOpen++;
                maxOpen++;
            } else if (c == ')') {
                if (minOpen > 0) minOpen--;
                maxOpen--;
            } else { // '*'
                if (minOpen > 0) minOpen--; // treat '*' as ')'
                maxOpen++; // treat '*' as '('
            }
            if (maxOpen < 0) return false; // too many ')'
        }
        return minOpen == 0;
    }
}

// Driver class
public class Main {
    public static void main(String[] args) {
        String s1 = "()";
        String s2 = "(*)";
        String s3 = "(*))";

        // Test Brute Force
        ValidParenthesisBrute brute = new ValidParenthesisBrute();
        System.out.println("Brute Force Result (s1): " + brute.checkValidString(s1)); // true
        System.out.println("Brute Force Result (s2): " + brute.checkValidString(s2)); // true
        System.out.println("Brute Force Result (s3): " + brute.checkValidString(s3)); // true

        // Test Optimized
        ValidParenthesisOptimized opt = new ValidParenthesisOptimized();
        System.out.println("Optimized Result (s1): " + opt.checkValidString(s1)); // true
        System.out.println("Optimized Result (s2): " + opt.checkValidString(s2)); // true
        System.out.println("Optimized Result (s3): " + opt.checkValidString(s3)); // true
    }
}
