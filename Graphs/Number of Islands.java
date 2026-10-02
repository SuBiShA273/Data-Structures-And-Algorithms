/*
Problem: Given an m x n 2D binary grid grid which represents a map of '1's (land) and '0's (water),
return the number of islands. An island is surrounded by water and is formed by connecting adjacent lands
horizontally or vertically. You may assume all four edges of the grid are surrounded by water.

Source: LeetCode Problem #200 → https://leetcode.com/problems/number-of-islands/

Approach: DFS Traversal
   - Idea:
     - Traverse the grid.
     - When a '1' (land) is found, perform DFS to mark all connected land as visited.
     - Increment island count for each DFS call.
   - Time Complexity: O(m * n).
   - Space Complexity: O(m * n) recursion stack in worst case.
*/

class Solution {
    public int numIslands(char[][] grid) {
        int m = grid.length, n = grid[0].length;
        int count = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == '1') {
                    dfs(grid, i, j);
                    count++;
                }
            }
        }
        return count;
    }

    private void dfs(char[][] grid, int i, int j) {
        if (i < 0 || j < 0 || i >= grid.length || j >= grid[0].length || grid[i][j] == '0') return;
        grid[i][j] = '0'; // mark visited
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
        char[][] grid1 = {
            {'1','1','1','1','0'},
            {'1','1','0','1','0'},
            {'1','1','0','0','0'},
            {'0','0','0','0','0'}
        };
        Util.printResult("DFS Result (Testcase 1): ", sol.numIslands(grid1)); // Expected: 1

        // Testcase 2
        char[][] grid2 = {
            {'1','1','0','0','0'},
            {'1','1','0','0','0'},
            {'0','0','1','0','0'},
            {'0','0','0','1','1'}
        };
        Util.printResult("DFS Result (Testcase 2): ", sol.numIslands(grid2)); // Expected: 3

        // Testcase 3
        char[][] grid3 = {
            {'0','0','0'},
            {'0','0','0'},
            {'0','0','0'}
        };
        Util.printResult("DFS Result (Testcase 3): ", sol.numIslands(grid3)); // Expected: 0
    }
}
