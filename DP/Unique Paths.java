/*
Problem: Unique Paths

There is a robot on an m x n grid.

The robot starts at the top-left corner and wants to reach
the bottom-right corner.

The robot can only move:
- Down
- Right

Return the total number of unique paths.

Source: LeetCode Problem #62
https://leetcode.com/problems/unique-paths/

Approaches:

1. Brute Force (Recursion)
   - Idea:
     - From every cell, we have two choices:
         * Move Down
         * Move Right
     - Recursively explore all possible paths.
     - If destination is reached, return 1.
     - If out of bounds, return 0.

   - Time Complexity: O(2^(m+n))
   - Space Complexity: O(m+n)
     (Recursion stack)

2. Optimized (Dynamic Programming)
   - Idea:
     - Let dp[i][j] represent the number of paths
       to reach cell (i, j).
     - A cell can only be reached from:
         * Top cell
         * Left cell
     - Therefore:
           dp[i][j] = dp[i-1][j] + dp[i][j-1]

   - Time Complexity: O(m * n)
   - Space Complexity: O(m * n)
*/

import java.util.*;

// Approach 1: Brute Force (Recursion)
class UniquePathsBrute {

    public int uniquePaths(int m, int n) {
        return helper(0, 0, m, n);
    }

    private int helper(int row, int col, int m, int n) {

        if (row == m - 1 && col == n - 1) {
            return 1;
        }

        if (row >= m || col >= n) {
            return 0;
        }

        return helper(row + 1, col, m, n)
                + helper(row, col + 1, m, n);
    }
}

// Approach 2: Optimized (DP)
class UniquePathsOptimized {

    public int uniquePaths(int m, int n) {

        int[][] dp = new int[m][n];

        for (int i = 0; i < m; i++) {
            dp[i][0] = 1;
        }

        for (int j = 0; j < n; j++) {
            dp[0][j] = 1;
        }

        for (int i = 1; i < m; i++) {

            for (int j = 1; j < n; j++) {

                dp[i][j] =
                        dp[i - 1][j]
                      + dp[i][j - 1];
            }
        }

        return dp[m - 1][n - 1];
    }
}

// Driver Class
public class Main {

    public static void main(String[] args) {

        UniquePathsBrute brute =
                new UniquePathsBrute();

        System.out.println(
                "Brute Force Output: "
                        + brute.uniquePaths(3, 7)
        ); // 28

        UniquePathsOptimized optimized =
                new UniquePathsOptimized();

        System.out.println(
                "Optimized Output: "
                        + optimized.uniquePaths(3, 7)
        ); // 28
    }
}
