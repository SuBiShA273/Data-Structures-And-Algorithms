/*
Problem: Rotate Image

You are given an n x n 2D matrix representing an image,
rotate the image by 90 degrees (clockwise).

You have to rotate the image in-place, which means you have to modify
the input 2D matrix directly. DO NOT allocate another 2D matrix and do the rotation.

Source: LeetCode Problem #48
https://leetcode.com

Approaches:

1. Brute Force (Extra Matrix)
   - Idea:
     - Create a new n x n matrix.
     - Place each element from matrix[i][j] into rotated[j][n - 1 - i].
     - Copy the rotated matrix back into the original matrix.

   - Time Complexity: O(n²)
   - Space Complexity: O(n²)

2. Better (Layer-by-Layer / 4-Way Cyclic Swap)
   - Idea:
     - Process the matrix ring by ring from the outermost layer to the innermost.
     - For each layer, rotate groups of 4 corresponding corner/edge elements in-place
       using a single temporary variable.

   - Time Complexity: O(n²)
   - Space Complexity: O(1)

3. Optimized (Transpose + Reverse Each Row)
   - Idea:
     - Step 1: Transpose the matrix in-place (swap matrix[i][j] with matrix[j][i] for j > i).
     - Step 2: Reverse each row from left to right.
     - Cleanest and easiest in-place logic to write without off-by-one index errors.

   - Time Complexity: O(n²)
   - Space Complexity: O(1)

   - Most Interview Preferred ✅
*/

import java.util.*;

// Approach 1: Brute Force
class RotateImageBrute {

    public void rotate(int[][] matrix) {

        int n = matrix.length;
        int[][] rotated = new int[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                rotated[j][n - 1 - i] = matrix[i][j];
            }
        }

        for (int i = 0; i < n; i++) {
            System.arraycopy(rotated[i], 0, matrix[i], 0, n);
        }
    }
}

// Approach 2: Better
class RotateImageBetter {

    public void rotate(int[][] matrix) {

        int n = matrix.length;

        for (int layer = 0; layer < n / 2; layer++) {
            int first = layer;
            int last = n - 1 - layer;

            for (int i = first; i < last; i++) {
                int offset = i - first;

                // Save top-left
                int top = matrix[first][i];

                // Move bottom-left -> top-left
                matrix[first][i] = matrix[last - offset][first];

                // Move bottom-right -> bottom-left
                matrix[last - offset][first] = matrix[last][last - offset];

                // Move top-right -> bottom-right
                matrix[last][last - offset] = matrix[i][last];

                // Move saved top-left -> top-right
                matrix[i][last] = top;
            }
        }
    }
}

// Approach 3: Optimized
class RotateImageOptimized {

    public void rotate(int[][] matrix) {

        int n = matrix.length;

        // Step 1: Transpose the matrix
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                int temp = matrix[i][j];
                matrix[i][j] = matrix[j][i];
                matrix[j][i] = temp;
            }
        }

        // Step 2: Reverse each row
        for (int i = 0; i < n; i++) {
            int left = 0;
            int right = n - 1;
            while (left < right) {
                int temp = matrix[i][left];
                matrix[i][left] = matrix[i][right];
                matrix[i][right] = temp;
                left++;
                right--;
            }
        }
    }
}

// Driver Class
public class Main {

    private static int[][] copyMatrix(int[][] original) {
        int[][] copy = new int[original.length][];
        for (int i = 0; i < original.length; i++) {
            copy[i] = original[i].clone();
        }
        return copy;
    }

    public static void main(String[] args) {

        int[][] matrix = {
            {1, 2, 3},
            {4, 5, 6},
            {7, 8, 9}
        };

        int[][] m1 = copyMatrix(matrix);
        RotateImageBrute brute = new RotateImageBrute();
        brute.rotate(m1);
        System.out.println("Brute Force Output: " + Arrays.deepToString(m1)); // [[7, 4, 1], [8, 5, 2], [9, 6, 3]]

        int[][] m2 = copyMatrix(matrix);
        RotateImageBetter better = new RotateImageBetter();
        better.rotate(m2);
        System.out.println("Better Output: " + Arrays.deepToString(m2)); // [[7, 4, 1], [8, 5, 2], [9, 6, 3]]

        int[][] m3 = copyMatrix(matrix);
        RotateImageOptimized optimized = new RotateImageOptimized();
        optimized.rotate(m3);
        System.out.println("Optimized Output: " + Arrays.deepToString(m3)); // [[7, 4, 1], [8, 5, 2], [9, 6, 3]]
    }
}
