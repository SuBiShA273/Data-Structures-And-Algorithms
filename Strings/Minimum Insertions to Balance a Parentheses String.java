/*
Problem: Given a parentheses string s, return the minimum number of insertions
needed to make the string valid. A valid string must satisfy:
  - Every '(' is matched with exactly two consecutive ')'.
  - '(' must appear before its matching ')'.

Source: LeetCode Problem #1541 → https://leetcode.com/problems/minimum-insertions-to-balance-a-parentheses-string/

Approaches:
1. Brute Force (Stack Simulation)
   - Idea:
     - Use a stack to track '('.
     - For each ')', check if it forms a pair.
     - If not enough ')' → insertions needed.
     - If unmatched '(' remain → insertions needed.
   - Time Complexity: O(n).
   - Space Complexity: O(n).

2. Optimal (Greedy Two‑Pointer)
   - Idea:
     - Traverse string with index i.
     - For each '(' → expect 2 ')' later.
     - For each ')':
       - If single ')' without pair → insert one more.
       - If no '(' available → insert '('.
     - Track insertions and balance.
   - Time Complexity: O(n).
   - Space Complexity: O(1).
*/

import java.util.*;

class BruteForce {
    public int minInsertions(String s) {
        Stack<Character> stack = new Stack<>();
        int insertions = 0;
        int i = 0;
        while (i < s.length()) {
            char c = s.charAt(i);
            if (c == '(') {
                stack.push('(');
                i++;
            } else {
                // Case: we see ')'
                if (i+1 < s.length() && s.charAt(i+1) == ')') {
                    if (!stack.isEmpty()) stack.pop();
                    else insertions++; // need '('
                    i += 2;
                } else {
                    if (!stack.isEmpty()) stack.pop();
                    else insertions++; // need '('
                    insertions++; // need one more ')'
                    i++;
                }
            }
        }
        // Each remaining '(' needs 2 ')'
        insertions += stack.size() * 2;
        return insertions;
    }
}

class Optimal {
    public int minInsertions(String s) {
        int insertions = 0, open = 0;
        int i = 0;
        while (i < s.length()) {
            if (s.charAt(i) == '(') {
                open++;
                i++;
            } else {
                // handle ')'
                if (i+1 < s.length() && s.charAt(i+1) == ')') {
                    if (open > 0) open--;
                    else insertions++; // need '('
                    i += 2;
                } else {
                    if (open > 0) open--;
                    else insertions++; // need '('
                    insertions++; // need one more ')'
                    i++;
                }
            }
        }
        return insertions + open*2;
    }
}

// Driver class with testcases
public class Main {
    public static void main(String[] args) {
        BruteForce brute = new BruteForce();
        Optimal opt = new Optimal();

        // Testcase 1
        String s1 = "(()))";
        System.out.println("Brute Force Result (Testcase 1): " + brute.minInsertions(s1)); // Expected: 1
        System.out.println("Optimal Result (Testcase 1): " + opt.minInsertions(s1));       // Expected: 1

        // Testcase 2
        String s2 = "())";
        System.out.println("\nBrute Force Result (Testcase 2): " + brute.minInsertions(s2)); // Expected: 0
        System.out.println("Optimal Result (Testcase 2): " + opt.minInsertions(s2));         // Expected: 0

        // Testcase 3
        String s3 = "))())(";
        System.out.println("\nBrute Force Result (Testcase 3): " + brute.minInsertions(s3)); // Expected: 3
        System.out.println("Optimal Result (Testcase 3): " + opt.minInsertions(s3));         // Expected: 3

        // Testcase 4
        String s4 = "((((((";
        System.out.println("\nBrute Force Result (Testcase 4): " + brute.minInsertions(s4)); // Expected: 12
        System.out.println("Optimal Result (Testcase 4): " + opt.minInsertions(s4));         // Expected: 12
    }
}
