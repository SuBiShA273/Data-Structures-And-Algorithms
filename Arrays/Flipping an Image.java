/*
Problem: Given an n x n binary matrix image, flip the image horizontally,
then invert it, and return the resulting image.

- Flip horizontally: reverse each row.
- Invert: change 0 → 1 and 1 → 0.

Source: LeetCode Problem #832 → https://leetcode.com/problems/flipping-an-image/

Approaches:
1. Brute Force
   - Idea:
     - Create a new matrix.
     - For each row, reverse it into new matrix.
     - Then invert values (0→1, 1→0).
   - Time Complexity: O(n^2).
   - Space Complexity: O(n^2).

2. Optimal (Two-Pointer In‑Place)
   - Idea:
     - For each row, use two pointers (left, right).
     - Swap elements symmetrically and invert during swap.
     - If left == right (middle element in odd length row), just invert once.
   - Time Complexity: O(n^2).
   - Space Complexity: O(1).
*/

class BruteForce {
    public int[][] flipAndInvertImage(int[][] image) {
        int n = image.length;
        int[][] res = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                res[i][j] = image[i][n - 1 - j] ^ 1; // flip + invert
            }
        }
        return res;
    }
}

class Optimal {
    public int[][] flipAndInvertImage(int[][] image) {
        int n = image.length;
        for (int[] row : image) {
            int left = 0, right = n - 1;
            while (left <= right) {
                if (row[left] == row[right]) {
                    // If both are same, after flip they remain same → invert both
                    row[left] ^= 1;
                    row[right] ^= 1;
                }
                // If different, after flip + invert they remain same → no change needed
                left++;
                right--;
            }
        }
        return image;
    }
}

// Utility: print 2D matrix
class Util {
    public static void printMatrix(int[][] mat) {
        for (int[] row : mat) {
            for (int val : row) {
                System.out.print(val + " ");
            }
            System.out.println();
        }
    }
}

// Driver class with testcases
public class Main {
    public static void main(String[] args) {
        BruteForce brute = new BruteForce();
        Optimal opt = new Optimal();

        // Testcase 1
        int[][] image1 = {
            {1,1,0},
            {1,0,1},
            {0,0,0}
        };
        System.out.println("Brute Force Result (Testcase 1):");
        Util.printMatrix(brute.flipAndInvertImage(image1));
        // Expected:
        // 1 0 0
        // 0 1 0
        // 1 1 1

        int[][] image2 = {
            {1,1,0},
            {1,0,1},
            {0,0,0}
        };
        System.out.println("\nOptimal Result (Testcase 1):");
        Util.printMatrix(opt.flipAndInvertImage(image2));
        // Expected same as above

        // Testcase 2
        int[][] image3 = {
            {1,1,0,0},
            {1,0,0,1},
            {0,1,1,1},
            {1,0,1,0}
        };
        System.out.println("\nBrute Force Result (Testcase 2):");
        Util.printMatrix(brute.flipAndInvertImage(image3));

        int[][] image4 = {
            {1,1,0,0},
            {1,0,0,1},
            {0,1,1,1},
            {1,0,1,0}
        };
        System.out.println("\nOptimal Result (Testcase 2):");
        Util.printMatrix(opt.flipAndInvertImage(image4));
    }
}
