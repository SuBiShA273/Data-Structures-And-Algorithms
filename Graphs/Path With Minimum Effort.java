/*
Problem: You are given an m x n grid of heights.
You want to travel from top-left (0,0) to bottom-right (m-1,n-1).
Effort of a path = maximum absolute difference in heights between consecutive cells.
Return the minimum effort required to reach the destination.

Source: LeetCode Problem #1631 → https://leetcode.com/problems/path-with-minimum-effort/

Approaches:
1. Brute Force (DFS Backtracking)
   - Idea:
     - Explore all possible paths recursively.
     - Track max difference along each path.
     - Return minimum among all paths.
   - Time Complexity: Exponential (O(4^(m*n)) worst case).
   - Space Complexity: O(m*n).

2. Optimal (Dijkstra / Min‑Heap)
   - Idea:
     - Treat grid as graph, edge weight = abs(height difference).
     - Use Dijkstra: effort to reach a cell = min(max effort along path).
     - PriorityQueue ensures smallest effort expanded first.
     - Stop when reaching bottom-right.
   - Time Complexity: O(m*n log(m*n)).
   - Space Complexity: O(m*n).
*/

import java.util.*;

class BruteForce {
    private int minEffort;
    private int[][] dirs = {{1,0},{-1,0},{0,1},{0,-1}};
    public int minimumEffortPath(int[][] heights) {
        int m = heights.length, n = heights[0].length;
        minEffort = Integer.MAX_VALUE;
        boolean[][] visited = new boolean[m][n];
        dfs(heights, 0, 0, visited, 0, m, n);
        return minEffort;
    }

    private void dfs(int[][] h, int i, int j, boolean[][] visited, int effort, int m, int n) {
        if (i == m-1 && j == n-1) {
            minEffort = Math.min(minEffort, effort);
            return;
        }
        visited[i][j] = true;
        for (int[] d : dirs) {
            int ni = i + d[0], nj = j + d[1];
            if (ni>=0 && nj>=0 && ni<m && nj<n && !visited[ni][nj]) {
                int newEffort = Math.max(effort, Math.abs(h[i][j] - h[ni][nj]));
                dfs(h, ni, nj, visited, newEffort, m, n);
            }
        }
        visited[i][j] = false;
    }
}

class Optimal {
    public int minimumEffortPath(int[][] heights) {
        int m = heights.length, n = heights[0].length;
        int[][] dist = new int[m][n];
        for (int[] row : dist) Arrays.fill(row, Integer.MAX_VALUE);
        dist[0][0] = 0;

        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a[2]));
        pq.offer(new int[]{0,0,0}); // i, j, effort

        int[][] dirs = {{1,0},{-1,0},{0,1},{0,-1}};
        while (!pq.isEmpty()) {
            int[] cur = pq.poll();
            int i = cur[0], j = cur[1], e = cur[2];
            if (i == m-1 && j == n-1) return e;
            if (e > dist[i][j]) continue;
            for (int[] d : dirs) {
                int ni = i + d[0], nj = j + d[1];
                if (ni>=0 && nj>=0 && ni<m && nj<n) {
                    int newEffort = Math.max(e, Math.abs(heights[i][j] - heights[ni][nj]));
                    if (newEffort < dist[ni][nj]) {
                        dist[ni][nj] = newEffort;
                        pq.offer(new int[]{ni, nj, newEffort});
                    }
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
        BruteForce brute = new BruteForce();
        Optimal opt = new Optimal();

        // Testcase 1
        int[][] h1 = {{1,2,2},{3,8,2},{5,3,5}};
        Util.printResult("Brute Force Result (Testcase 1): ", brute.minimumEffortPath(h1)); // Expected: 2
        Util.printResult("Optimal Result (Testcase 1): ", opt.minimumEffortPath(h1));       // Expected: 2

        // Testcase 2
        int[][] h2 = {{1,2,3},{3,8,4},{5,3,5}};
        Util.printResult("\nBrute Force Result (Testcase 2): ", brute.minimumEffortPath(h2)); // Expected: 1
        Util.printResult("Optimal Result (Testcase 2): ", opt.minimumEffortPath(h2));         // Expected: 1

        // Testcase 3
        int[][] h3 = {{1,2,1,1,1},{1,2,1,2,1},{1,2,1,2,1},{1,2,1,2,1},{1,1,1,2,1}};
        Util.printResult("\nBrute Force Result (Testcase 3): ", brute.minimumEffortPath(h3)); // Expected: 0
        Util.printResult("Optimal Result (Testcase 3): ", opt.minimumEffortPath(h3));         // Expected: 0
    }
}
