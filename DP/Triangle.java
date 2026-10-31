/*
Problem: Triangle

Given a triangle array, return the minimum path sum from top to bottom.

For each step, you may move to:
- The same index in the next row
- The adjacent index in the next row

Return the minimum path sum.

Source: LeetCode Problem #120
https://leetcode.com/problems/triangle/

Approaches:

1. Brute Force (Recursion)
   - Idea:
     - Start from the top element.
     - For each position, we have two choices:
         * Move Down
         * Move Diagonally Down-Right
     - Recursively calculate the minimum path sum.

   - Time Complexity: O(2^n)
   - Space Complexity: O(n)
     (Recursion stack)

2. Optimized (Dynamic Programming)
   - Idea:
     - Start from the second-last row and move upwards.
     - For every element:
           current value +
           min(down, diagonal)
     - Store results in a DP array.
     - The top element finally contains the answer.

   - Time Complexity: O(n²)
   - Space Complexity: O(n)
*/

import java.util.*;

// Approach 1: Brute Force (Recursion)
class TriangleBrute {

    public int minimumTotal(List<List<Integer>> triangle) {

        return helper(0, 0, triangle);
    }

    private int helper(
            int row,
            int col,
            List<List<Integer>> triangle) {

        if (row == triangle.size() - 1) {
            return triangle.get(row).get(col);
        }

        int down =
                helper(row + 1, col, triangle);

        int diagonal =
                helper(row + 1, col + 1, triangle);

        return triangle.get(row).get(col)
                + Math.min(down, diagonal);
    }
}

// Approach 2: Optimized (DP)
class TriangleOptimized {

    public int minimumTotal(List<List<Integer>> triangle) {

        int n = triangle.size();

        int[] dp = new int[n];

        for (int i = 0; i < n; i++) {
            dp[i] = triangle.get(n - 1).get(i);
        }

        for (int row = n - 2; row >= 0; row--) {

            for (int col = 0;
                 col <= row;
                 col++) {

                dp[col] =
                        triangle.get(row).get(col)
                      + Math.min(
                            dp[col],
                            dp[col + 1]
                        );
            }
        }

        return dp[0];
    }
}

// Driver Class
public class Main {

    public static void main(String[] args) {

        List<List<Integer>> triangle =
                Arrays.asList(
                        Arrays.asList(2),
                        Arrays.asList(3, 4),
                        Arrays.asList(6, 5, 7),
                        Arrays.asList(4, 1, 8, 3)
                );

        TriangleBrute brute =
                new TriangleBrute();

        System.out.println(
                "Brute Force Output: "
                        + brute.minimumTotal(triangle)
        ); // 11

        TriangleOptimized optimized =
                new TriangleOptimized();

        System.out.println(
                "Optimized Output: "
                        + optimized.minimumTotal(triangle)
        ); // 11
    }
}
