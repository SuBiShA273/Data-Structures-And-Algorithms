/*
Problem: Given a balanced parentheses string s, return the score of the string.
Scoring rules:
- "()" has score 1
- AB has score A + B, where A and B are balanced parentheses strings
- "(A)" has score 2 * A

Source: LeetCode Problem #856 → https://leetcode.com/problems/score-of-parentheses/

Approaches:
1. Brute Force (Recursive Divide & Conquer)
   - Idea:
     - Recursively evaluate substrings.
     - If "()" → score 1.
     - If "(A)" → score 2 * score(A).
     - If concatenation AB → score(A) + score(B).
   - Time Complexity: O(n^2) (due to substring splitting).
   - Space Complexity: O(n).

2. Optimal (Stack / Depth Counting)
   - Idea:
     - Use stack or depth counter.
     - Each time we see "()", add 2^depth to score.
     - Depth increases with '(' and decreases with ')'.
   - Time Complexity: O(n).
   - Space Complexity: O(1).
*/

class BruteForce {
    public int scoreOfParentheses(String s) {
        return helper(s, 0, s.length());
    }

    private int helper(String s, int l, int r) {
        int score = 0, bal = 0, start = l;
        for (int i = l; i < r; i++) {
            if (s.charAt(i) == '(') bal++;
            else bal--;
            if (bal == 0) {
                if (i - start == 1) score += 1; // "()"
                else score += 2 * helper(s, start + 1, i);
                start = i + 1;
            }
        }
        return score;
    }
}

class Optimal {
    public int scoreOfParentheses(String s) {
        int score = 0, depth = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '(') {
                depth++;
            } else {
                depth--;
                if (s.charAt(i-1) == '(') {
                    score += 1 << depth; // 2^depth
                }
            }
        }
        return score;
    }
}

// Driver class with testcases
public class Main {
    public static void main(String[] args) {
        BruteForce brute = new BruteForce();
        Optimal opt = new Optimal();

        // Testcase 1
        String s1 = "()";
        System.out.println("Brute Force Result (Testcase 1): " + brute.scoreOfParentheses(s1)); // Expected: 1
        System.out.println("Optimal Result (Testcase 1): " + opt.scoreOfParentheses(s1));       // Expected: 1

        // Testcase 2
        String s2 = "(())";
        System.out.println("\nBrute Force Result (Testcase 2): " + brute.scoreOfParentheses(s2)); // Expected: 2
        System.out.println("Optimal Result (Testcase 2): " + opt.scoreOfParentheses(s2));         // Expected: 2

        // Testcase 3
        String s3 = "()()";
        System.out.println("\nBrute Force Result (Testcase 3): " + brute.scoreOfParentheses(s3)); // Expected: 2
        System.out.println("Optimal Result (Testcase 3): " + opt.scoreOfParentheses(s3));         // Expected: 2

        // Testcase 4
        String s4 = "(()(()))";
        System.out.println("\nBrute Force Result (Testcase 4): " + brute.scoreOfParentheses(s4)); // Expected: 6
        System.out.println("Optimal Result (Testcase 4): " + opt.scoreOfParentheses(s4));         // Expected: 6
    }
}
