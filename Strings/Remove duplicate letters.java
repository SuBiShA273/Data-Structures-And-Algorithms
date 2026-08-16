/*
Problem: Given a string s, remove duplicate letters so that every letter appears once 
and only once. You must make sure your result is the smallest in lexicographical order 
among all possible results.

Source: LeetCode Problem #316 → https://leetcode.com/problems/remove-duplicate-letters/

Approaches:
1. Brute Force (Inefficient)
   - Idea: Generate all subsequences that contain each character once.
   - Compare lexicographically and choose the smallest valid string.
   - Time Complexity: Exponential (not practical for large inputs)
   - Space Complexity: O(n)

2. Optimized (Greedy + Stack)
   - Idea:
     - Count frequency of each character.
     - Use a stack to build the result string.
     - Ensure each character appears once, and maintain lexicographic order.
     - Pop from stack if current char is smaller than top and top will appear later.
   - Time Complexity: O(n)
   - Space Complexity: O(1) (since alphabet size is fixed at 26)
*/

import java.util.*;

// Approach 1: Brute Force (for understanding, not practical)
class RemoveDuplicateBruteForce {
    public String removeDuplicateLetters(String s) {
        // This brute force approach is conceptual:
        // Generate all subsequences with unique chars, then pick lexicographically smallest.
        // Not implemented fully due to exponential complexity.
        return "Brute force approach is impractical for large inputs.";
    }
}

// Approach 2: Optimized (Greedy + Stack)
class RemoveDuplicateOptimized {
    public String removeDuplicateLetters(String s) {
        int[] freq = new int[26];       // frequency of each char
        boolean[] inStack = new boolean[26]; // track if char is already in stack

        for (char c : s.toCharArray()) {
            freq[c - 'a']++;
        }

        Stack<Character> stack = new Stack<>();

        for (char c : s.toCharArray()) {
            freq[c - 'a']--; // one occurrence used

            if (inStack[c - 'a']) continue; // skip if already in stack

            // Maintain lexicographic order
            while (!stack.isEmpty() && c < stack.peek() && freq[stack.peek() - 'a'] > 0) {
                inStack[stack.pop() - 'a'] = false;
            }

            stack.push(c);
            inStack[c - 'a'] = true;
        }

        // Build result
        StringBuilder result = new StringBuilder();
        for (char c : stack) {
            result.append(c);
        }
        return result.toString();
    }
}

// Driver class
public class Main {
    public static void main(String[] args) {
        String s = "cbacdcbc";

        // Brute Force (conceptual)
        RemoveDuplicateBruteForce brute = new RemoveDuplicateBruteForce();
        System.out.println("Brute Force Result: " + brute.removeDuplicateLetters(s));

        // Optimized
        RemoveDuplicateOptimized optimized = new RemoveDuplicateOptimized();
        System.out.println("Optimized Result: " + optimized.removeDuplicateLetters(s));
        // Expected Output: "acdb"
    }
}
