/*
Problem: Cherry Pickup II

Two robots are collecting cherries in a grid.

Robot 1 starts at (0, 0).
Robot 2 starts at (0, cols - 1).

For each move, both robots move to the next row and can choose:
- Down-Left
- Down
- Down-Right

When both robots land on the same cell, cherries are counted only once.

Return the maximum number of cherries both robots can collect.

Source: LeetCode Problem #1463
https://leetcode.com/problems/cherry-pickup-ii/

Approaches:

1. Recursion
   - Idea:
     - State:
           (row, col1, col2)
       where col1 and col2 are positions of Robot 1 and Robot 2.
     - Try all 9 possible moves for the next row.
     - Take the maximum cherries obtainable.

   - Time Complexity: O(9^rows)
   - Space Complexity: O(rows)

2. Memoization (Top-Down DP)
   - Idea:
     - Store results for every state:
           dp[row][col1][col2]
     - Avoid recomputing overlapping subproblems.

   - Time Complexity: O(rows * cols * cols * 9)
   - Space Complexity: O(rows * cols * cols)

3. Tabulation (Bottom-Up DP)
   - Idea:
     - Build dp[row][col1][col2] from the last row upward.
     - For every pair of robot positions, check all 9 possible
       next states.

   - Time Complexity: O(rows * cols * cols * 9)
   - Space Complexity: O(rows * cols * cols)

4. Space Optimization
   - Idea:
     - Current row only depends on the next row.
     - Replace 3D DP with two 2D arrays:
           front[][] and curr[][]

   - Time Complexity: O(rows * cols * cols * 9)
   - Space Complexity: O(cols * cols)
*/

import java.util.*;

// Approach 1: Recursion
class CherryPickupRecursion {

    public int cherryPickup(int[][] grid) {

        int cols = grid[0].length;

        return helper(0, 0, cols - 1, grid);
    }

    private int helper(
            int row,
            int col1,
            int col2,
            int[][] grid) {

        int rows = grid.length;
        int cols = grid[0].length;

        if (col1 < 0 || col1 >= cols ||
            col2 < 0 || col2 >= cols) {
            return Integer.MIN_VALUE;
        }

        if (row == rows - 1) {

            if (col1 == col2) {
                return grid[row][col1];
            }

            return grid[row][col1] + grid[row][col2];
        }

        int max = Integer.MIN_VALUE;

        for (int move1 = -1; move1 <= 1; move1++) {

            for (int move2 = -1; move2 <= 1; move2++) {

                int value =
                        helper(
                                row + 1,
                                col1 + move1,
                                col2 + move2,
                                grid
                        );

                max = Math.max(max, value);
            }
        }

        int cherries =
                (col1 == col2)
                ? grid[row][col1]
                : grid[row][col1] + grid[row][col2];

        return cherries + max;
    }
}

// Approach 2: Memoization
class CherryPickupMemoization {

    public int cherryPickup(int[][] grid) {

        int rows = grid.length;
        int cols = grid[0].length;

        Integer[][][] dp =
                new Integer[rows][cols][cols];

        return helper(
                0,
                0,
                cols - 1,
                grid,
                dp
        );
    }

    private int helper(
            int row,
            int col1,
            int col2,
            int[][] grid,
            Integer[][][] dp) {

        int rows = grid.length;
        int cols = grid[0].length;

        if (col1 < 0 || col1 >= cols ||
            col2 < 0 || col2 >= cols) {
            return Integer.MIN_VALUE;
        }

        if (row == rows - 1) {

            if (col1 == col2) {
                return grid[row][col1];
            }

            return grid[row][col1] + grid[row][col2];
        }

        if (dp[row][col1][col2] != null) {
            return dp[row][col1][col2];
        }

        int max = Integer.MIN_VALUE;

        for (int move1 = -1; move1 <= 1; move1++) {

            for (int move2 = -1; move2 <= 1; move2++) {

                max = Math.max(
                        max,
                        helper(
                                row + 1,
                                col1 + move1,
                                col2 + move2,
                                grid,
                                dp
                        )
                );
            }
        }

        int cherries =
                (col1 == col2)
                ? grid[row][col1]
                : grid[row][col1] + grid[row][col2];

        return dp[row][col1][col2]
                = cherries + max;
    }
}

// Approach 3: Tabulation
class CherryPickupTabulation {

    public int cherryPickup(int[][] grid) {

        int rows = grid.length;
        int cols = grid[0].length;

        int[][][] dp =
                new int[rows][cols][cols];

        for (int col1 = 0; col1 < cols; col1++) {

            for (int col2 = 0; col2 < cols; col2++) {

                if (col1 == col2) {
                    dp[rows - 1][col1][col2]
                            = grid[rows - 1][col1];
                } else {
                    dp[rows - 1][col1][col2]
                            = grid[rows - 1][col1]
                            + grid[rows - 1][col2];
                }
            }
        }

        for (int row = rows - 2; row >= 0; row--) {

            for (int col1 = 0; col1 < cols; col1++) {

                for (int col2 = 0; col2 < cols; col2++) {

                    int max = Integer.MIN_VALUE;

                    for (int d1 = -1; d1 <= 1; d1++) {

                        for (int d2 = -1; d2 <= 1; d2++) {

                            int nc1 = col1 + d1;
                            int nc2 = col2 + d2;

                            if (nc1 >= 0 && nc1 < cols &&
                                nc2 >= 0 && nc2 < cols) {

                                max = Math.max(
                                        max,
                                        dp[row + 1][nc1][nc2]
                                );
                            }
                        }
                    }

                    int cherries =
                            (col1 == col2)
                            ? grid[row][col1]
                            : grid[row][col1]
                            + grid[row][col2];

                    dp[row][col1][col2]
                            = cherries + max;
                }
            }
        }

        return dp[0][0][cols - 1];
    }
}

// Approach 4: Space Optimized
class CherryPickupOptimized {

    public int cherryPickup(int[][] grid) {

        int rows = grid.length;
        int cols = grid[0].length;

        int[][] front = new int[cols][cols];

        for (int col1 = 0; col1 < cols; col1++) {

            for (int col2 = 0; col2 < cols; col2++) {

                if (col1 == col2) {
                    front[col1][col2]
                            = grid[rows - 1][col1];
                } else {
                    front[col1][col2]
                            = grid[rows - 1][col1]
                            + grid[rows - 1][col2];
                }
            }
        }

        for (int row = rows - 2; row >= 0; row--) {

            int[][] curr = new int[cols][cols];

            for (int col1 = 0; col1 < cols; col1++) {

                for (int col2 = 0; col2 < cols; col2++) {

                    int max = Integer.MIN_VALUE;

                    for (int d1 = -1; d1 <= 1; d1++) {

                        for (int d2 = -1; d2 <= 1; d2++) {

                            int nc1 = col1 + d1;
                            int nc2 = col2 + d2;

                            if (nc1 >= 0 && nc1 < cols &&
                                nc2 >= 0 && nc2 < cols) {

                                max = Math.max(
                                        max,
                                        front[nc1][nc2]
                                );
                            }
                        }
                    }

                    int cherries =
                            (col1 == col2)
                            ? grid[row][col1]
                            : grid[row][col1]
                            + grid[row][col2];

                    curr[col1][col2]
                            = cherries + max;
                }
            }

            front = curr;
        }

        return front[0][cols - 1];
    }
}

// Driver Class
public class Main {

    public static void main(String[] args) {

        int[][] grid = {
                {3, 1, 1},
                {2, 5, 1},
                {1, 5, 5},
                {2, 1, 1}
        };

        CherryPickupOptimized obj =
                new CherryPickupOptimized();

        System.out.println(
                obj.cherryPickup(grid)
        ); // 24
    }
}
