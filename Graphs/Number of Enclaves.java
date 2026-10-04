/*
Problem: You are given an m x n binary matrix grid, where 0 represents water and 1 represents land.
A move consists of walking from one land cell to another adjacent (up, down, left, right) land cell.
Return the number of land cells in grid for which we cannot walk off the boundary of the grid.

Source: LeetCode Problem #1020 → https://leetcode.com/problems/number-of-enclaves/

Approach: DFS Traversal
   - Idea:
     - Any land connected to boundary cannot be part of enclave.
     - Perform DFS from boundary land cells to mark them as water (visited).
     - After DFS, count remaining land cells → enclaves.
   - Time Complexity: O(m * n).
   - Space Complexity: O(m * n) recursion stack in worst case.
*/

class Solution {
    public int numEnclaves(int[][] grid) {
        int m = grid.length, n = grid[0].length;

        // Step 1: DFS from boundary land cells
        for (int i = 0; i < m; i++) {
            dfs(grid, i, 0);
            dfs(grid, i, n - 1);
        }
        for (int j = 0; j < n; j++) {
            dfs(grid, 0, j);
            dfs(grid, m - 1, j);
        }

        // Step 2: Count remaining land cells
        int count = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 1) count++;
            }
        }
        return count;
    }

    private void dfs(int[][] grid, int i, int j) {
        if (i < 0 || j < 0 || i >= grid.length || j >= grid[0].length || grid[i][j] == 0) return;
        grid[i][j] = 0; // mark visited
        dfs(grid, i + 1, j);
        dfs(grid, i - 1, j);
        dfs(grid, i, j + 1);
        dfs(grid, i, j - 1);
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
            {0,0,0,0},
            {1,0,1,0},
            {0,1,1,0},
            {0,0,0,0}
        };
        Util.printResult("DFS Result (Testcase 1): ", sol.numEnclaves(grid1)); // Expected: 3

        // Testcase 2
        int[][] grid2 = {
            {0,1,1,0},
            {0,0,1,0},
            {0,0,1,0},
            {0,0,0,0}
        };
        Util.printResult("DFS Result (Testcase 2): ", sol.numEnclaves(grid2)); // Expected: 0

        // Testcase 3
        int[][] grid3 = {
            {1,1,1},
            {1,0,1},
            {1,1,1}
        };
        Util.printResult("DFS Result (Testcase 3): ", sol.numEnclaves(grid3)); // Expected: 0
    }
}
