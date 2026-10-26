/*
Problem: Count Islands With Total Value Divisible by K

You are given an m x n matrix grid and a positive integer k.

An island is a group of positive integers that are connected
horizontally or vertically.

The total value of an island is the sum of all cells belonging
to that island.

Return the number of islands whose total value is divisible by k.

Source: LeetCode Problem #3619
https://leetcode.com/problems/count-islands-with-total-value-divisible-by-k/

Approaches:

1. DFS Traversal
   - Idea:
     - Traverse the grid.
     - Whenever an unvisited land cell (> 0) is found,
       start a DFS.
     - Compute the total sum of the entire island.
     - If islandSum % k == 0, increment answer.

   - Time Complexity: O(m * n)
   - Space Complexity: O(m * n)
     (DFS recursion stack in worst case)

2. BFS Traversal
   - Idea:
     - Similar to DFS but use a queue.
     - For every unvisited island:
         * Find total island sum using BFS.
         * Check divisibility by k.
     - Count valid islands.

   - Time Complexity: O(m * n)
   - Space Complexity: O(m * n)
*/

import java.util.*;

// Approach 1: DFS Traversal
class SolutionDFS {

    private int rows;
    private int cols;

    public int countIslands(int[][] grid, int k) {

        rows = grid.length;
        cols = grid[0].length;

        int count = 0;

        for (int r = 0; r < rows; r++) {

            for (int c = 0; c < cols; c++) {

                if (grid[r][c] > 0) {

                    long islandSum = dfs(grid, r, c);

                    if (islandSum % k == 0) {
                        count++;
                    }
                }
            }
        }

        return count;
    }

    private long dfs(int[][] grid, int r, int c) {

        if (r < 0 || c < 0 ||
            r >= rows || c >= cols ||
            grid[r][c] <= 0) {

            return 0;
        }

        long sum = grid[r][c];

        grid[r][c] = 0; // mark visited

        sum += dfs(grid, r + 1, c);
        sum += dfs(grid, r - 1, c);
        sum += dfs(grid, r, c + 1);
        sum += dfs(grid, r, c - 1);

        return sum;
    }
}

// Approach 2: BFS Traversal
class SolutionBFS {

    private final int[][] DIRS = {
            {1, 0},
            {-1, 0},
            {0, 1},
            {0, -1}
    };

    public int countIslands(int[][] grid, int k) {

        int rows = grid.length;
        int cols = grid[0].length;

        int answer = 0;

        for (int r = 0; r < rows; r++) {

            for (int c = 0; c < cols; c++) {

                if (grid[r][c] > 0) {

                    long sum = 0;

                    Queue<int[]> queue = new LinkedList<>();
                    queue.offer(new int[]{r, c});

                    sum += grid[r][c];
                    grid[r][c] = 0;

                    while (!queue.isEmpty()) {

                        int[] curr = queue.poll();

                        for (int[] dir : DIRS) {

                            int nr = curr[0] + dir[0];
                            int nc = curr[1] + dir[1];

                            if (nr >= 0 && nc >= 0 &&
                                nr < rows && nc < cols &&
                                grid[nr][nc] > 0) {

                                sum += grid[nr][nc];
                                queue.offer(new int[]{nr, nc});
                                grid[nr][nc] = 0;
                            }
                        }
                    }

                    if (sum % k == 0) {
                        answer++;
                    }
                }
            }
        }

        return answer;
    }
}

// Driver Class
public class Main {

    public static void main(String[] args) {

        int[][] grid1 = {
                {0, 2, 1, 0, 0},
                {0, 5, 0, 0, 5},
                {0, 0, 1, 0, 0},
                {0, 1, 4, 7, 0},
                {0, 2, 0, 0, 8}
        };

        SolutionDFS dfs = new SolutionDFS();

        System.out.println(
                "DFS Output: " +
                dfs.countIslands(grid1, 5)
        ); // 2


        int[][] grid2 = {
                {3, 0, 3, 0},
                {0, 3, 0, 3},
                {3, 0, 3, 0}
        };

        SolutionBFS bfs = new SolutionBFS();

        System.out.println(
                "BFS Output: " +
                bfs.countIslands(grid2, 3)
        ); // 6
    }
}
