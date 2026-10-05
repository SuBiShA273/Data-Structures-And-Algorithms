/*
Problem: Given an n x n binary matrix grid, return the length of the shortest clear path
from the top-left cell (0,0) to the bottom-right cell (n-1,n-1).
A clear path consists of only cells with value 0, and moves can be made in 8 directions.
If no such path exists, return -1.

Source: LeetCode Problem #1091 → https://leetcode.com/problems/shortest-path-in-binary-matrix/

Approach: BFS (Shortest Path in Unweighted Graph)
   - Idea:
     - If start or end cell is blocked (1), return -1.
     - Use BFS from (0,0), exploring all 8 directions.
     - Each BFS level represents path length.
     - If we reach (n-1,n-1), return current path length.
     - If BFS ends without reaching target, return -1.
   - Time Complexity: O(n^2).
   - Space Complexity: O(n^2).
*/

import java.util.*;

class Solution {
    public int shortestPathBinaryMatrix(int[][] grid) {
        int n = grid.length;
        if (grid[0][0] == 1 || grid[n-1][n-1] == 1) return -1;

        Queue<int[]> q = new LinkedList<>();
        q.offer(new int[]{0,0,1}); // row, col, path length
        grid[0][0] = 1; // mark visited

        int[][] dirs = {
            {1,0},{-1,0},{0,1},{0,-1},
            {1,1},{1,-1},{-1,1},{-1,-1}
        };

        while (!q.isEmpty()) {
            int[] cell = q.poll();
            int i = cell[0], j = cell[1], dist = cell[2];
            if (i == n-1 && j == n-1) return dist;

            for (int[] d : dirs) {
                int ni = i + d[0], nj = j + d[1];
                if (ni >= 0 && nj >= 0 && ni < n && nj < n && grid[ni][nj] == 0) {
                    q.offer(new int[]{ni, nj, dist+1});
                    grid[ni][nj] = 1; // mark visited
                }
            }
        }
        return -1;
    }
}

// Utility: print result
class Util {
    public static void printResult(String label, int result) {
        System.out.println(label + result);
    }
}

// Driver class with testcases
public class Main {
    public static void main(String[] args) {
        Solution sol = new Solution();

        // Testcase 1
        int[][] grid1 = {
            {0,1},
            {1,0}
        };
        Util.printResult("BFS Result (Testcase 1): ", sol.shortestPathBinaryMatrix(grid1));
        // Expected: 2

        // Testcase 2
        int[][] grid2 = {
            {0,0,0},
            {1,1,0},
            {1,1,0}
        };
        Util.printResult("BFS Result (Testcase 2): ", sol.shortestPathBinaryMatrix(grid2));
        // Expected: 4

        // Testcase 3
        int[][] grid3 = {
            {1,0,0},
            {1,1,0},
            {1,1,0}
        };
        Util.printResult("BFS Result (Testcase 3): ", sol.shortestPathBinaryMatrix(grid3));
        // Expected: -1
    }
}
