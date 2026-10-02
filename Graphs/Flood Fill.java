/*
Problem: An image is represented by a 2D array of integers, each integer representing the pixel color.
Given a starting pixel (sr, sc) and a newColor, perform a "flood fill":
- Replace the starting pixel and all connected pixels (up, down, left, right) of the same color with newColor.
Return the modified image.

Source: LeetCode Problem #733 → https://leetcode.com/problems/flood-fill/

Approach: DFS Traversal
   - Idea:
     - Record the original color at (sr, sc).
     - If the original color is already newColor, return image.
     - Perform DFS from (sr, sc), changing connected pixels of original color to newColor.
   - Time Complexity: O(m * n).
   - Space Complexity: O(m * n) recursion stack in worst case.
*/

class Solution {
    public int[][] floodFill(int[][] image, int sr, int sc, int newColor) {
        int originalColor = image[sr][sc];
        if (originalColor == newColor) return image;
        dfs(image, sr, sc, originalColor, newColor);
        return image;
    }

    private void dfs(int[][] image, int i, int j, int originalColor, int newColor) {
        if (i < 0 || j < 0 || i >= image.length || j >= image[0].length) return;
        if (image[i][j] != originalColor) return;

        image[i][j] = newColor;

        dfs(image, i + 1, j, originalColor, newColor);
        dfs(image, i - 1, j, originalColor, newColor);
        dfs(image, i, j + 1, originalColor, newColor);
        dfs(image, i, j - 1, originalColor, newColor);
    }
}

// Utility: print 2D array
class Util {
    public static void printImage(int[][] image) {
        for (int[] row : image) {
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
        Solution sol = new Solution();

        // Testcase 1
        int[][] image1 = {
            {1,1,1},
            {1,1,0},
            {1,0,1}
        };
        System.out.println("Testcase 1 Before:");
        Util.printImage(image1);
        sol.floodFill(image1, 1, 1, 2);
        System.out.println("Testcase 1 After:");
        Util.printImage(image1);
        // Expected:
        // 2 2 2
        // 2 2 0
        // 2 0 1

        // Testcase 2
        int[][] image2 = {
            {0,0,0},
            {0,0,0}
        };
        System.out.println("\nTestcase 2 Before:");
        Util.printImage(image2);
        sol.floodFill(image2, 0, 0, 2);
        System.out.println("Testcase 2 After:");
        Util.printImage(image2);
        // Expected:
        // 2 2 2
        // 2 2 2

        // Testcase 3
        int[][] image3 = {
            {0,1,1},
            {1,1,0},
            {0,0,0}
        };
        System.out.println("\nTestcase 3 Before:");
        Util.printImage(image3);
        sol.floodFill(image3, 0, 1, 3);
        System.out.println("Testcase 3 After:");
        Util.printImage(image3);
        // Expected:
        // 0 3 3
        // 3 3 0
        // 0 0 0
    }
}
