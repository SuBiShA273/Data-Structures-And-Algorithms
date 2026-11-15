/*
Problem: Distinct Subsequences

Given two strings s and t.

Return the number of distinct subsequences of s
which equals t.

Source: LeetCode Problem #115
https://leetcode.com/problems/distinct-subsequences/

Approaches:

1. Recursion
   - Idea:
     - If characters match:
           Take current character
           OR
           Skip current character
     - If not match:
           Skip current character of s

   - Time Complexity: O(2^n)
   - Space Complexity: O(n)

2. Memoization
   - Idea:
     - Store states:
           dp[i][j]
     - Avoid recalculating overlapping subproblems.

   - Time Complexity: O(m*n)
   - Space Complexity: O(m*n)

3. Tabulation
   - Idea:
     - dp[i][j] stores number of ways
       to form first j characters of t
       using first i characters of s.

   - Time Complexity: O(m*n)
   - Space Complexity: O(m*n)

4. Space Optimization
   - Idea:
     - Current row depends only on previous row.
     - Use a 1D DP array.

   - Time Complexity: O(m*n)
   - Space Complexity: O(n)
*/

import java.util.*;

// Approach 1: Recursion
class DistinctSubsequencesRecursion {

    public int numDistinct(String s, String t) {
        return helper(0, 0, s, t);
    }

    private int helper(int i, int j, String s, String t) {

        if (j == t.length()) {
            return 1;
        }

        if (i == s.length()) {
            return 0;
        }

        if (s.charAt(i) == t.charAt(j)) {
            return helper(i + 1, j + 1, s, t)
                 + helper(i + 1, j, s, t);
        }

        return helper(i + 1, j, s, t);
    }
}

// Approach 2: Memoization
class DistinctSubsequencesMemoization {

    public int numDistinct(String s, String t) {

        Integer[][] dp = new Integer[s.length()][t.length()];

        return helper(0, 0, s, t, dp);
    }

    private int helper(int i, int j, String s, String t, Integer[][] dp) {

        if (j == t.length()) {
            return 1;
        }

        if (i == s.length()) {
            return 0;
        }

        if (dp[i][j] != null) {
            return dp[i][j];
        }

        if (s.charAt(i) == t.charAt(j)) {

            return dp[i][j] =
                    helper(i + 1, j + 1, s, t, dp)
                  + helper(i + 1, j, s, t, dp);
        }

        return dp[i][j] = helper(i + 1, j, s, t, dp);
    }
}

// Approach 3: Tabulation
class DistinctSubsequencesTabulation {

    public int numDistinct(String s, String t) {

        int m = s.length();
        int n = t.length();

        int[][] dp = new int[m + 1][n + 1];

        for (int i = 0; i <= m; i++) {
            dp[i][0] = 1;
        }

        for (int i = 1; i <= m; i++) {

            for (int j = 1; j <= n; j++) {

                if (s.charAt(i - 1) == t.charAt(j - 1)) {

                    dp[i][j] =
                            dp[i - 1][j - 1]
                          + dp[i - 1][j];

                } else {

                    dp[i][j] =
                            dp[i - 1][j];
                }
            }
        }

        return dp[m][n];
    }
}

// Approach 4: Space Optimization
class DistinctSubsequencesOptimized {

    public int numDistinct(String s, String t) {

        int m = s.length();
        int n = t.length();

        long[] dp = new long[n + 1];

        dp[0] = 1;

        for (int i = 1; i <= m; i++) {

            for (int j = n; j >= 1; j--) {

                if (s.charAt(i - 1) == t.charAt(j - 1)) {
                    dp[j] += dp[j - 1];
                }
            }
        }

        return (int) dp[n];
    }
}

// Driver Class
public class Main {

    public static void main(String[] args) {

        String s = "rabbbit";
        String t = "rabbit";

        DistinctSubsequencesRecursion rec = new DistinctSubsequencesRecursion();
        System.out.println("Recursion Output: " + rec.numDistinct(s, t)); // 3

        DistinctSubsequencesMemoization memo = new DistinctSubsequencesMemoization();
        System.out.println("Memoization Output: " + memo.numDistinct(s, t)); // 3

        DistinctSubsequencesTabulation tab = new DistinctSubsequencesTabulation();
        System.out.println("Tabulation Output: " + tab.numDistinct(s, t)); // 3

        DistinctSubsequencesOptimized opt = new DistinctSubsequencesOptimized();
        System.out.println("Space Optimized Output: " + opt.numDistinct(s, t)); // 3
    }
}
