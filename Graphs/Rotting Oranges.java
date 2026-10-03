/*
Problem: You are given an m x n grid where each cell can have one of three values:
- 0 representing an empty cell,
- 1 representing a fresh orange,
- 2 representing a rotten orange.
Every minute, any fresh orange that is 4-directionally adjacent to a rotten orange becomes rotten.
Return the minimum number of minutes that must elapse until no cell has a fresh orange.
If this is impossible, return -1.

Source: LeetCode Problem #994 → https://leetcode.com/problems/rotting-oranges/

Approach: BFS (Level Order Traversal)
   - Idea:
     - Push all initially rotten oranges into a queue.
     - Perform BFS level by level (each level = 1 minute).
     - For each rotten orange, rot its adjacent fresh oranges.
     - Track total time and count of fresh oranges.
     - If all fresh oranges rot, return time; else return -1.
   - Time Complexity: O(m * n).
   - Space Complexity: O(m * n).
*/

import java.util.*;

class Solution {
    public int orangesRotting(int[][] grid) {
        int m = grid.length, n = grid[0].length;
        Queue<int[]> q = new LinkedList<>();
        int fresh = 0;

        // Step 1: Add all rotten oranges to queue, count fresh
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 2) {
                    q.offer(new int[]{i, j});
                } else if (grid[i][j] == 1) {
                    fresh++;
                }
            }
        }

        if (fresh == 0) return 0; // no fresh oranges

        int minutes = -1;
        int[][] dirs = {{1,0},{-1,0},{0,1},{0,-1}};

        // Step 2: BFS
        while (!q.isEmpty()) {
            int size = q.size();
            minutes++;
            for (int s = 0; s < size; s++) {
                int[] cell = q.poll();
                for (int[] d : dirs) {
                    int ni = cell[0] + d[0], nj = cell[1] + d[1];
                    if (ni >= 0 && nj >= 0 && ni < m && nj < n && grid[ni][nj] == 1) {
                        grid[ni][nj] = 2;
                        fresh--;
                        q.offer(new int[]{ni, nj});
                    }
                }
            }
        }

        return fresh == 0 ? minutes : -1;
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
            {2,1,1},
            {1,1,0},
            {0,1,1}
        };
        Util.printResult("BFS Result (Testcase 1): ", sol.orangesRotting(grid1)); // Expected: 4

        // Testcase 2
        int[][] grid2 = {
            {2,1,1},
            {0,1,1},
            {1,0,1}
        };
        Util.printResult("BFS Result (Testcase 2): ", sol.orangesRotting(grid2)); // Expected: -1

        // Testcase 3
        int[][] grid3 = {
            {0,2}
        };
        Util.printResult("BFS Result (Testcase 3): ", sol.orangesRotting(grid3)); // Expected: 0
    }
}
