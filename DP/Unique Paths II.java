/*
Problem: Unique Paths II

A robot is located at the top-left corner of an m x n grid.

The robot can move only:
- Right
- Down

Some cells contain obstacles.

An obstacle and empty space are marked as:
- 1 = Obstacle
- 0 = Empty Cell

Return the number of unique paths from the top-left corner
to the bottom-right corner.

Source: LeetCode Problem #63
https://leetcode.com/problems/unique-paths-ii/

Approaches:

1. Brute Force (Recursion)
   - Idea:
     - From each cell:
         * Move Down
         * Move Right
     - If an obstacle is encountered, return 0.
     - If destination is reached, return 1.
     - Explore all possible paths recursively.

   - Time Complexity: O(2^(m+n))
   - Space Complexity: O(m+n)
     (Recursion stack)

2. Optimized (Dynamic Programming)
   - Idea:
     - Let dp[i][j] represent the number of ways
       to reach cell (i, j).
     - If current cell is an obstacle:
           dp[i][j] = 0
     - Otherwise:
           dp[i][j] = dp[i-1][j] + dp[i][j-1]

   - Time Complexity: O(m * n)
   - Space Complexity: O(m * n)
*/

import java.util.*;

// Approach 1: Brute Force (Recursion)
class UniquePaths2Brute {

    public int uniquePathsWithObstacles(int[][] grid) {

        return helper(0, 0, grid);
    }

    private int helper(int row, int col, int[][] grid) {

        int m = grid.length;
        int n = grid[0].length;

        if (row >= m || col >= n || grid[row][col] == 1) {
            return 0;
        }

        if (row == m - 1 && col == n - 1) {
            return 1;
        }

        return helper(row + 1, col, grid)
                + helper(row, col + 1, grid);
    }
}

// Approach 2: Optimized (DP)
class UniquePaths2Optimized {

    public int uniquePathsWithObstacles(int[][] obstacleGrid) {

        int m = obstacleGrid.length;
        int n = obstacleGrid[0].length;

        int[][] dp = new int[m][n];

        if (obstacleGrid[0][0] == 1) {
            return 0;
        }

        dp[0][0] = 1;

        for (int i = 1; i < m; i++) {

            if (obstacleGrid[i][0] == 0) {
                dp[i][0] = dp[i - 1][0];
            }
        }

        for (int j = 1; j < n; j++) {

            if (obstacleGrid[0][j] == 0) {
                dp[0][j] = dp[0][j - 1];
            }
        }

        for (int i = 1; i < m; i++) {

            for (int j = 1; j < n; j++) {

                if (obstacleGrid[i][j] == 1) {
                    dp[i][j] = 0;
                } else {

                    dp[i][j] =
                            dp[i - 1][j]
                          + dp[i][j - 1];
                }
            }
        }

        return dp[m - 1][n - 1];
    }
}

// Driver Class
public class Main {

    public static void main(String[] args) {

        int[][] obstacleGrid = {
                {0, 0, 0},
                {0, 1, 0},
                {0, 0, 0}
        };

        UniquePaths2Brute brute =
                new UniquePaths2Brute();

        System.out.println(
                "Brute Force Output: "
                        + brute.uniquePathsWithObstacles(obstacleGrid)
        ); // 2


        UniquePaths2Optimized optimized =
                new UniquePaths2Optimized();

        System.out.println(
                "Optimized Output: "
                        + optimized.uniquePathsWithObstacles(obstacleGrid)
        ); // 2
    }
}
