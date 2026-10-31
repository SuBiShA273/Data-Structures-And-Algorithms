/*
Problem: Minimum Falling Path Sum

Given an n x n integer matrix.

Return the minimum sum of any falling path through the matrix.

A falling path starts at any element in the first row and chooses
one element from each row.

The next row's choice can be:
- Directly below
- Diagonally left
- Diagonally right

Source: LeetCode Problem #931
https://leetcode.com/problems/minimum-falling-path-sum/

Approaches:

1. Brute Force (Recursion)
   - Idea:
     - Start from each cell in the first row.
     - For every cell, we have three choices:
         * Down
         * Down-Left
         * Down-Right
     - Recursively calculate the minimum path sum.
     - Take the minimum among all starting points.

   - Time Complexity: O(3^n)
   - Space Complexity: O(n)
     (Recursion stack)

2. Optimized (Dynamic Programming)
   - Idea:
     - Let dp[i][j] represent the minimum falling path
       sum reaching cell (i, j).
     - Current cell can be reached from:
         * Up
         * Up-Left
         * Up-Right
     - Therefore:
           dp[i][j] = matrix[i][j] +
                      min(up, upLeft, upRight)

   - Time Complexity: O(n²)
   - Space Complexity: O(n²)
*/

import java.util.*;

// Approach 1: Brute Force (Recursion)
class MinimumFallingPathBrute {

    public int minFallingPathSum(int[][] matrix) {

        int n = matrix.length;

        int answer = Integer.MAX_VALUE;

        for (int col = 0; col < n; col++) {

            answer = Math.min(
                    answer,
                    helper(0, col, matrix)
            );
        }

        return answer;
    }

    private int helper(
            int row,
            int col,
            int[][] matrix) {

        int n = matrix.length;

        if (col < 0 || col >= n) {
            return Integer.MAX_VALUE;
        }

        if (row == n - 1) {
            return matrix[row][col];
        }

        int down =
                helper(row + 1, col, matrix);

        int leftDiagonal =
                helper(row + 1, col - 1, matrix);

        int rightDiagonal =
                helper(row + 1, col + 1, matrix);

        return matrix[row][col]
                + Math.min(
                    down,
                    Math.min(
                        leftDiagonal,
                        rightDiagonal
                    )
                );
    }
}

// Approach 2: Optimized (Dynamic Programming)
class MinimumFallingPathOptimized {

    public int minFallingPathSum(int[][] matrix) {

        int n = matrix.length;

        int[][] dp = new int[n][n];

        for (int col = 0; col < n; col++) {
            dp[0][col] = matrix[0][col];
        }

        for (int row = 1; row < n; row++) {

            for (int col = 0; col < n; col++) {

                int up = dp[row - 1][col];

                int upLeft = Integer.MAX_VALUE;
                int upRight = Integer.MAX_VALUE;

                if (col > 0) {
                    upLeft = dp[row - 1][col - 1];
                }

                if (col < n - 1) {
                    upRight = dp[row - 1][col + 1];
                }

                dp[row][col] =
                        matrix[row][col]
                      + Math.min(
                            up,
                            Math.min(
                                upLeft,
                                upRight
                            )
                        );
            }
        }

        int answer = Integer.MAX_VALUE;

        for (int col = 0; col < n; col++) {
            answer = Math.min(
                    answer,
                    dp[n - 1][col]
            );
        }

        return answer;
    }
}

// Driver Class
public class Main {

    public static void main(String[] args) {

        int[][] matrix = {
                {2, 1, 3},
                {6, 5, 4},
                {7, 8, 9}
        };

        MinimumFallingPathBrute brute =
                new MinimumFallingPathBrute();

        System.out.println(
                "Brute Force Output: "
                        + brute.minFallingPathSum(matrix)
        ); // 13

        MinimumFallingPathOptimized optimized =
                new MinimumFallingPathOptimized();

        System.out.println(
                "Optimized Output: "
                        + optimized.minFallingPathSum(matrix)
        ); // 13
    }
}
