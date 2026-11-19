/*
Problem: Set Matrix Zeroes

Given an m x n integer matrix matrix, if an element is 0, set its entire row
and column to 0's.

You must do it in place.

Source: LeetCode Problem #73
https://leetcode.com

Approaches:

1. Brute Force
   - Idea:
     - Traverse the matrix to find cells containing 0.
     - For every 0 found, mark all non-zero elements in its row and column
       with a temporary marker (e.g., -1e9 or Integer.MIN_VALUE + 1).
     - In a second pass, convert all temporary markers into 0.
     - Note: Fails if the matrix legitimately contains the marker value.

   - Time Complexity: O((m * n) * (m + n))
   - Space Complexity: O(1)

2. Better
   - Idea:
     - Use two auxiliary arrays: row[m] and col[n].
     - First pass: whenever matrix[i][j] == 0, set row[i] = 1 and col[j] = 1.
     - Second pass: if row[i] == 1 or col[j] == 1, set matrix[i][j] = 0.

   - Time Complexity: O(m * n)
   - Space Complexity: O(m + n)

3. Optimized
   - Idea:
     - Use the first row and first column of the matrix itself as the marker arrays.
     - Since matrix[0][0] overlaps for both row 0 and col 0, use an extra
       variable `col0` to track the first column.
     - Process inner cells first, then update row 0, and finally col 0.

   - Time Complexity: O(m * n)
   - Space Complexity: O(1)

   - Most Interview Preferred ✅
*/

import java.util.*;

// Approach 1: Brute Force
class SetMatrixZeroesBrute {

    public void setZeroes(int[][] matrix) {

        int m = matrix.length;
        int n = matrix[0].length;
        int marker = -1000000007; // Assumes matrix doesn't contain this value

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (matrix[i][j] == 0) {
                    markRow(matrix, i, n, marker);
                    markCol(matrix, j, m, marker);
                }
            }
        }

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (matrix[i][j] == marker) {
                    matrix[i][j] = 0;
                }
            }
        }
    }

    private void markRow(int[][] matrix, int row, int cols, int marker) {
        for (int j = 0; j < cols; j++) {
            if (matrix[row][j] != 0) {
                matrix[row][j] = marker;
            }
        }
    }

    private void markCol(int[][] matrix, int col, int rows, int marker) {
        for (int i = 0; i < rows; i++) {
            if (matrix[i][col] != 0) {
                matrix[i][col] = marker;
            }
        }
    }
}

// Approach 2: Better
class SetMatrixZeroesBetter {

    public void setZeroes(int[][] matrix) {

        int m = matrix.length;
        int n = matrix[0].length;

        int[] row = new int[m];
        int[] col = new int[n];

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (matrix[i][j] == 0) {
                    row[i] = 1;
                    col[j] = 1;
                }
            }
        }

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (row[i] == 1 || col[j] == 1) {
                    matrix[i][j] = 0;
                }
            }
        }
    }
}

// Approach 3: Optimized
class SetMatrixZeroesOptimized {

    public void setZeroes(int[][] matrix) {

        int m = matrix.length;
        int n = matrix[0].length;
        int col0 = 1;

        // Step 1: Mark first row and first column
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (matrix[i][j] == 0) {
                    matrix[i][0] = 0;
                    if (j == 0) {
                        col0 = 0;
                    } else {
                        matrix[0][j] = 0;
                    }
                }
            }
        }

        // Step 2: Zero out inner cells (1 to m-1, 1 to n-1)
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                if (matrix[i][0] == 0 || matrix[0][j] == 0) {
                    matrix[i][j] = 0;
                }
            }
        }

        // Step 3: Zero out first row if needed
        if (matrix[0][0] == 0) {
            for (int j = 1; j < n; j++) {
                matrix[0][j] = 0;
            }
        }

        // Step 4: Zero out first column if needed
        if (col0 == 0) {
            for (int i = 0; i < m; i++) {
                matrix[i][0] = 0;
            }
        }
    }
}

// Driver Class
public class Main {

    public static void main(String[] args) {

        int[][] matrix1 = {{1, 1, 1}, {1, 0, 1}, {1, 1, 1}};
        SetMatrixZeroesBrute brute = new SetMatrixZeroesBrute();
        brute.setZeroes(matrix1);
        System.out.println("Brute Force Output: " + Arrays.deepToString(matrix1));
        // [[1, 0, 1], [0, 0, 0], [1, 0, 1]]

        int[][] matrix2 = {{1, 1, 1}, {1, 0, 1}, {1, 1, 1}};
        SetMatrixZeroesBetter better = new SetMatrixZeroesBetter();
        better.setZeroes(matrix2);
        System.out.println("Better Output: " + Arrays.deepToString(matrix2));
        // [[1, 0, 1], [0, 0, 0], [1, 0, 1]]

        int[][] matrix3 = {{1, 1, 1}, {1, 0, 1}, {1, 1, 1}};
        SetMatrixZeroesOptimized optimized = new SetMatrixZeroesOptimized();
        optimized.setZeroes(matrix3);
        System.out.println("Optimized Output: " + Arrays.deepToString(matrix3));
        // [[1, 0, 1], [0, 0, 0], [1, 0, 1]]
    }
}
