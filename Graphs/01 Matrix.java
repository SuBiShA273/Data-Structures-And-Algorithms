/*
Problem: Given an m x n binary matrix mat, return the distance of the nearest 0 for each cell.
The distance between two adjacent cells is 1.

Source: LeetCode Problem #542 → https://leetcode.com/problems/01-matrix/

Approach: Multi-source BFS
   - Idea:
     - Push all cells with value 0 into a queue initially.
     - Perform BFS from all 0s simultaneously.
     - Each step updates distance for neighboring 1s.
     - Ensures shortest distance to nearest 0.
   - Time Complexity: O(m * n).
   - Space Complexity: O(m * n).
*/

import java.util.*;

class Solution {
    public int[][] updateMatrix(int[][] mat) {
        int m = mat.length, n = mat[0].length;
        int[][] dist = new int[m][n];
        boolean[][] visited = new boolean[m][n];
        Queue<int[]> q = new LinkedList<>();

        // Step 1: Add all 0s to queue
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (mat[i][j] == 0) {
                    q.offer(new int[]{i, j});
                    visited[i][j] = true;
                }
            }
        }

        int[][] dirs = {{1,0},{-1,0},{0,1},{0,-1}};

        // Step 2: BFS
        while (!q.isEmpty()) {
            int[] cell = q.poll();
            int i = cell[0], j = cell[1];
            for (int[] d : dirs) {
                int ni = i + d[0], nj = j + d[1];
                if (ni >= 0 && nj >= 0 && ni < m && nj < n && !visited[ni][nj]) {
                    dist[ni][nj] = dist[i][j] + 1;
                    visited[ni][nj] = true;
                    q.offer(new int[]{ni, nj});
                }
            }
        }

        return dist;
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
        Solution sol = new Solution();

        // Testcase 1
        int[][] mat1 = {
            {0,0,0},
            {0,1,0},
            {1,1,1}
        };
        System.out.println("Testcase 1 Input:");
        Util.printMatrix(mat1);
        int[][] res1 = sol.updateMatrix(mat1);
        System.out.println("Testcase 1 Output:");
        Util.printMatrix(res1);
        // Expected:
        // 0 0 0
        // 0 1 0
        // 1 2 1

        // Testcase 2
        int[][] mat2 = {
            {0,1,1},
            {1,1,1},
            {1,1,0}
        };
        System.out.println("\nTestcase 2 Input:");
        Util.printMatrix(mat2);
        int[][] res2 = sol.updateMatrix(mat2);
        System.out.println("Testcase 2 Output:");
        Util.printMatrix(res2);
        // Expected:
        // 0 1 2
        // 1 2 1
        // 2 1 0

        // Testcase 3
        int[][] mat3 = {
            {1,1,1},
            {1,0,1},
            {1,1,1}
        };
        System.out.println("\nTestcase 3 Input:");
        Util.printMatrix(mat3);
        int[][] res3 = sol.updateMatrix(mat3);
        System.out.println("Testcase 3 Output:");
        Util.printMatrix(res3);
        // Expected:
        // 2 1 2
        // 1 0 1
        // 2 1 2
    }
}
