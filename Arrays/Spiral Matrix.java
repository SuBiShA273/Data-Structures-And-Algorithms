/*
Problem: Spiral Matrix

Given an m x n matrix, return all elements of the matrix
in spiral order (clockwise, starting from top-left).

Source: LeetCode Problem #54
https://leetcode.com

Approaches:

1. Brute Force (Visited Matrix Simulation)
   - Idea:
     - Maintain a separate m x n boolean matrix to track visited cells.
     - Move in the current direction (Right -> Down -> Left -> Up).
     - Turn 90 degrees clockwise whenever the next cell is out of bounds
       or already visited.

   - Time Complexity: O(m * n)
   - Space Complexity: O(m * n)

2. Better (In-Place Modification / Marking)
   - Idea:
     - Eliminate the extra boolean matrix by modifying visited cells
       in-place with a sentinel value (e.g., 101, since values are -100 to 100).
     - Turn 90 degrees clockwise when hitting boundaries or the sentinel value.
     - Note: Mutates the input matrix, which may not be allowed in interviews.

   - Time Complexity: O(m * n)
   - Space Complexity: O(1) (excluding output list)

3. Optimized (Four Boundaries Shrink)
   - Idea:
     - Maintain four pointers: top, bottom, left, right.
     - Traverse top row (left to right), then increment top.
     - Traverse right column (top to bottom), then decrement right.
     - Check if top <= bottom, traverse bottom row (right to left), decrement bottom.
     - Check if left <= right, traverse left column (bottom to top), increment left.
     - Repeat until boundaries cross.

   - Time Complexity: O(m * n)
   - Space Complexity: O(1) (excluding output list, does not mutate input)

   - Most Interview Preferred ✅
*/

import java.util.*;

// Approach 1: Brute Force (Visited Matrix Simulation)
class SpiralMatrixBrute {

    public List<Integer> spiralOrder(int[][] matrix) {
        List<Integer> result = new ArrayList<>();
        if (matrix == null || matrix.length == 0) return result;

        int rows = matrix.length;
        int cols = matrix[0].length;
        boolean[][] visited = new boolean[rows][cols];

        // Directions: Right, Down, Left, Up
        int[] dr = {0, 1, 0, -1};
        int[] dc = {1, 0, -1, 0};

        int r = 0, c = 0, dir = 0;

        for (int i = 0; i < rows * cols; i++) {
            result.add(matrix[r][c]);
            visited[r][c] = true;

            int nextR = r + dr[dir];
            int nextC = c + dc[dir];

            if (nextR >= 0 && nextR < rows && nextC >= 0 && nextC < cols && !visited[nextR][nextC]) {
                r = nextR;
                c = nextC;
            } else {
                dir = (dir + 1) % 4;
                r += dr[dir];
                c += dc[dir];
            }
        }

        return result;
    }
}

// Approach 2: Better (In-Place Marking)
class SpiralMatrixBetter {

    public List<Integer> spiralOrder(int[][] matrix) {
        List<Integer> result = new ArrayList<>();
        if (matrix == null || matrix.length == 0) return result;

        int rows = matrix.length;
        int cols = matrix[0].length;
        int VISITED = 101; // Out of constraint range [-100, 100]

        // Create a copy if we want to preserve original for driver testing
        int[][] grid = new int[rows][cols];
        for (int i = 0; i < rows; i++) {
            grid[i] = matrix[i].clone();
        }

        int[] dr = {0, 1, 0, -1};
        int[] dc = {1, 0, -1, 0};

        int r = 0, c = 0, dir = 0;

        for (int i = 0; i < rows * cols; i++) {
            result.add(grid[r][c]);
            grid[r][c] = VISITED;

            int nextR = r + dr[dir];
            int nextC = c + dc[dir];

            if (nextR >= 0 && nextR < rows && nextC >= 0 && nextC < cols && grid[nextR][nextC] != VISITED) {
                r = nextR;
                c = nextC;
            } else {
                dir = (dir + 1) % 4;
                r += dr[dir];
                c += dc[dir];
            }
        }

        return result;
    }
}

// Approach 3: Optimized (Four Boundaries Shrink)
class SpiralMatrixOptimized {

    public List<Integer> spiralOrder(int[][] matrix) {
        List<Integer> result = new ArrayList<>();
        if (matrix == null || matrix.length == 0) return result;

        int top = 0;
        int bottom = matrix.length - 1;
        int left = 0;
        int right = matrix[0].length - 1;

        while (top <= bottom && left <= right) {

            // 1. Traverse from Left to Right along top boundary
            for (int col = left; col <= right; col++) {
                result.add(matrix[top][col]);
            }
            top++;

            // 2. Traverse from Top to Bottom along right boundary
            for (int row = top; row <= bottom; row++) {
                result.add(matrix[row][right]);
            }
            right--;

            // 3. Traverse from Right to Left along bottom boundary
            if (top <= bottom) {
                for (int col = right; col >= left; col--) {
                    result.add(matrix[bottom][col]);
                }
                bottom--;
            }

            // 4. Traverse from Bottom to Top along left boundary
            if (left <= right) {
                for (int row = bottom; row >= top; row--) {
                    result.add(matrix[row][left]);
                }
                left++;
            }
        }

        return result;
    }
}

// Driver Class
public class Main {

    public static void main(String[] args) {

        int[][] matrix = {
            {1, 2, 3},
            {4, 5, 6},
            {7, 8, 9}
        };

        SpiralMatrixBrute brute = new SpiralMatrixBrute();
        System.out.println("Brute Force Output: " + brute.spiralOrder(matrix)); // [1, 2, 3, 6, 9, 8, 7, 4, 5]

        SpiralMatrixBetter better = new SpiralMatrixBetter();
        System.out.println("Better Output: " + better.spiralOrder(matrix)); // [1, 2, 3, 6, 9, 8, 7, 4, 5]

        SpiralMatrixOptimized optimized = new SpiralMatrixOptimized();
        System.out.println("Optimized Output: " + optimized.spiralOrder(matrix)); // [1, 2, 3, 6, 9, 8, 7, 4, 5]
    }
}
