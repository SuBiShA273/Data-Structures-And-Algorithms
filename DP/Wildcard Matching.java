/*
Problem: Wildcard Matching

Given an input string (s) and a pattern (p), implement wildcard pattern matching
with support for '?' and '*' where:
- '?' Matches any single character.
- '*' Matches any sequence of characters (including the empty sequence).

The matching should cover the entire input string (not partial).

Source: LeetCode Problem #44
https://leetcode.com

Approaches:

1. Recursion
   - Idea:
     - Compare characters from right to left (indices i and j).
     - If characters match or p[j-1] == '?', move both pointers back (i-1, j-1).
     - If p[j-1] == '*', either ignore '*' (i, j-1) or consume one char from s (i-1, j).

   - Time Complexity: O(2^(n1 + n2))
   - Space Complexity: O(n1 + n2)

2. Memoization
   - Idea:
     - Cache results of (i, j) states in a Boolean[][] DP table.
     - Avoid recomputing overlapping subproblems.

   - Time Complexity: O(n1 * n2)
   - Space Complexity: O(n1 * n2)

3. Tabulation
   - Idea:
     - dp[i][j] = true if first i chars of s match first j chars of p.
     - Fill base cases for empty string s and empty pattern p, then build bottom-up.

   - Time Complexity: O(n1 * n2)
   - Space Complexity: O(n1 * n2)

4. Space Optimization
   - Idea:
     - Current row (cur) depends only on previous row (dp) and current row's left cell.
     - Maintain two 1D boolean arrays and swap them after each row.

   - Time Complexity: O(n1 * n2)
   - Space Complexity: O(n2)
*/

import java.util.*;

// Approach 1: Recursion
class WildcardMatchingRecursion {

    public boolean isMatch(String s, String p) {
        return helper(s.length(), p.length(), s, p);
    }

    private boolean helper(int i, int j, String s, String p) {

        if (i == 0 && j == 0) {
            return true;
        }

        if (j == 0 && i > 0) {
            return false;
        }

        if (i == 0 && j > 0) {
            while (j > 0) {
                if (p.charAt(j - 1) != '*') {
                    return false;
                }
                j--;
            }
            return true;
        }

        if (s.charAt(i - 1) == p.charAt(j - 1) || p.charAt(j - 1) == '?') {
            return helper(i - 1, j - 1, s, p);
        }

        if (p.charAt(j - 1) == '*') {
            return helper(i, j - 1, s, p) || helper(i - 1, j, s, p);
        }

        return false;
    }
}

// Approach 2: Memoization
class WildcardMatchingMemoization {

    public boolean isMatch(String s, String p) {

        int n1 = s.length();
        int n2 = p.length();

        Boolean[][] dp = new Boolean[n1 + 1][n2 + 1];

        return helper(n1, n2, s, p, dp);
    }

    private boolean helper(int i, int j, String s, String p, Boolean[][] dp) {

        if (i == 0 && j == 0) {
            return true;
        }

        if (j == 0 && i > 0) {
            return false;
        }

        if (i == 0 && j > 0) {
            for (int k = 1; k <= j; k++) {
                if (p.charAt(k - 1) != '*') {
                    return false;
                }
            }
            return true;
        }

        if (dp[i][j] != null) {
            return dp[i][j];
        }

        if (s.charAt(i - 1) == p.charAt(j - 1) || p.charAt(j - 1) == '?') {
            return dp[i][j] = helper(i - 1, j - 1, s, p, dp);
        }

        if (p.charAt(j - 1) == '*') {
            return dp[i][j] = helper(i, j - 1, s, p, dp) || helper(i - 1, j, s, p, dp);
        }

        return dp[i][j] = false;
    }
}

// Approach 3: Tabulation
class WildcardMatchingTabulation {

    public boolean isMatch(String s, String p) {

        int n1 = s.length();
        int n2 = p.length();

        boolean[][] dp = new boolean[n1 + 1][n2 + 1];

        dp[0][0] = true;

        for (int j = 1; j <= n2; j++) {
            if (p.charAt(j - 1) == '*') {
                dp[0][j] = true;
            } else {
                break;
            }
        }

        for (int i = 1; i <= n1; i++) {
            for (int j = 1; j <= n2; j++) {
                if (s.charAt(i - 1) == p.charAt(j - 1) || p.charAt(j - 1) == '?') {
                    dp[i][j] = dp[i - 1][j - 1];
                } else if (p.charAt(j - 1) == '*') {
                    dp[i][j] = dp[i][j - 1] || dp[i - 1][j];
                }
            }
        }

        return dp[n1][n2];
    }
}

// Approach 4: Space Optimization
class WildcardMatchingOptimized {

    public boolean isMatch(String s, String p) {

        int n1 = s.length();
        int n2 = p.length();

        boolean[] dp = new boolean[n2 + 1];
        boolean[] cur = new boolean[n2 + 1];

        dp[0] = true;

        for (int j = 1; j <= n2; j++) {
            if (p.charAt(j - 1) == '*') {
                dp[j] = true;
            } else {
                break;
            }
        }

        for (int i = 1; i <= n1; i++) {
            cur[0] = false;
            for (int j = 1; j <= n2; j++) {
                if (s.charAt(i - 1) == p.charAt(j - 1) || p.charAt(j - 1) == '?') {
                    cur[j] = dp[j - 1];
                } else if (p.charAt(j - 1) == '*') {
                    cur[j] = cur[j - 1] || dp[j];
                } else {
                    cur[j] = false;
                }
            }
            boolean[] t = dp;
            dp = cur;
            cur = t;
        }

        return dp[n2];
    }
}

// Driver Class
public class Main {

    public static void main(String[] args) {

        String s = "adceb";
        String p = "*a*b";

        WildcardMatchingRecursion rec = new WildcardMatchingRecursion();
        System.out.println("Recursion Output: " + rec.isMatch(s, p)); // true

        WildcardMatchingMemoization memo = new WildcardMatchingMemoization();
        System.out.println("Memoization Output: " + memo.isMatch(s, p)); // true

        WildcardMatchingTabulation tab = new WildcardMatchingTabulation();
        System.out.println("Tabulation Output: " + tab.isMatch(s, p)); // true

        WildcardMatchingOptimized opt = new WildcardMatchingOptimized();
        System.out.println("Space Optimized Output: " + opt.isMatch(s, p)); // true
    }
}
