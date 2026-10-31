/*
Problem: Minimum Path Sum

Given an m x n grid filled with non-negative numbers.

Find a path from the top-left corner to the bottom-right corner
which minimizes the sum of all numbers along its path.

You can only move:
- Right
- Down

Return the minimum path sum.

Source: LeetCode Problem #64
https://leetcode.com/problems/minimum-path-sum/

Approaches:

1. Brute Force (Recursion)
   - Idea:
     - From every cell, we have two choices:
         * Move Right
         * Move Down
     - Recursively compute the minimum cost path.
     - If we move outside the grid, return a large value.
     - Destination cell contributes its own value.

   - Time Complexity: O(2^(m+n))
   - Space Complexity: O(m+n)
     (Recursion stack)

2. Optimized (Dynamic Programming)
   - Idea:
     - Let dp[i][j] represent the minimum path sum
       to reach cell (i, j).
     - Current cell can be reached only from:
         * Top Cell
         * Left Cell
     - Therefore:
           dp[i][j] = grid[i][j] +
                      min(dp[i-1][j], dp[i][j-1])

   - Time Complexity: O(m * n)
   - Space Complexity: O(m * n)
*/

import java.util.*;

// Approach 1: Brute Force (Recursion)
class MinimumPathSumBrute {

    public int minPathSum(int[][] grid) {

        return helper(0, 0, grid);
    }

    private int helper(int row, int col, int[][] grid) {

        int m = grid.length;
        int n = grid[0].length;

        if (row >= m || col >= n) {
            return Integer.MAX_VALUE;
        }

        if (row == m - 1 && col == n - 1) {
            return grid[row][col];
        }

        int right = helper(row, col + 1, grid);
        int down = helper(row + 1, col, grid);

        return grid[row][col] + Math.min(right, down);
    }
}

// Approach 2: Optimized (Dynamic Programming)
class MinimumPathSumOptimized {

    public int minPathSum(int[][] grid) {

        int m = grid.length;
        int n = grid[0].length;

        int[][] dp = new int[m][n];

        dp[0][0] = grid[0][0];

        for (int i = 1; i < m; i++) {
            dp[i][0] = dp[i - 1][0] + grid[i][0];
        }

        for (int j = 1; j < n; j++) {
            dp[0][j] = dp[0][j - 1] + grid[0][j];
        }

        for (int i = 1; i < m; i++) {

            for (int j = 1; j < n; j++) {

                dp[i][j] = grid[i][j]
                        + Math.min(
                                dp[i - 1][j],
                                dp[i][j - 1]
                        );
            }
        }

        return dp[m - 1][n - 1];
    }
}

// Driver Class
public class Main {

    public static void main(String[] args) {

        int[][] grid = {
                {1, 3, 1},
                {1, 5, 1},
                {4, 2, 1}
        };

        MinimumPathSumBrute brute =
                new MinimumPathSumBrute();

        System.out.println(
                "Brute Force Output: "
                        + brute.minPathSum(grid)
        ); // 7

        MinimumPathSumOptimized optimized =
                new MinimumPathSumOptimized();

        System.out.println(
                "Optimized Output: "
                        + optimized.minPathSum(grid)
        ); // 7
    }
}
