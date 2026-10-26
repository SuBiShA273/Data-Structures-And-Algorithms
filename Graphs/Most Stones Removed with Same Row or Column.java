/*
Problem: Most Stones Removed with Same Row or Column

On a 2D plane, we place n stones at some integer coordinate points.
A stone can be removed if it shares the same row or the same column
with another stone that has not been removed.

Return the maximum number of stones that can be removed.

Source: LeetCode Problem #947
https://leetcode.com/problems/most-stones-removed-with-same-row-or-column/

Approaches:

1. DFS / Connected Components
   - Idea:
     - Treat each stone as a node.
     - Connect two stones if they share the same row or column.
     - Find the number of connected components using DFS.
     - In each component of size k, we can remove (k - 1) stones.
     - Therefore:
           Answer = Total Stones - Connected Components

   - Time Complexity: O(n²)
   - Space Complexity: O(n)

2. Optimized (Union Find / DSU)
   - Idea:
     - Treat each stone as a separate node.
     - Union two stones if they share the same row or column.
     - Count the number of connected components.
     - Maximum removable stones:
           Total Stones - Components

   - Time Complexity: O(n² * α(n))
   - Space Complexity: O(n)
*/

import java.util.*;

// Approach 1: DFS / Connected Components
class SolutionDFS {

    public int removeStones(int[][] stones) {

        int n = stones.length;
        boolean[] visited = new boolean[n];

        int components = 0;

        for (int i = 0; i < n; i++) {
            if (!visited[i]) {
                dfs(i, stones, visited);
                components++;
            }
        }

        return n - components;
    }

    private void dfs(int curr, int[][] stones, boolean[] visited) {

        visited[curr] = true;

        for (int next = 0; next < stones.length; next++) {

            if (!visited[next] &&
                (stones[curr][0] == stones[next][0] ||
                 stones[curr][1] == stones[next][1])) {

                dfs(next, stones, visited);
            }
        }
    }
}

// Approach 2: Optimized (Union Find / DSU)
class SolutionDSU {

    private int[] parent;

    public int removeStones(int[][] stones) {

        int n = stones.length;

        parent = new int[n];

        for (int i = 0; i < n; i++) {
            parent[i] = i;
        }

        int components = n;

        for (int i = 0; i < n; i++) {

            for (int j = i + 1; j < n; j++) {

                if (stones[i][0] == stones[j][0] ||
                    stones[i][1] == stones[j][1]) {

                    int pi = find(i);
                    int pj = find(j);

                    if (pi != pj) {
                        parent[pi] = pj;
                        components--;
                    }
                }
            }
        }

        return n - components;
    }

    private int find(int x) {

        if (parent[x] != x) {
            parent[x] = find(parent[x]);
        }

        return parent[x];
    }
}

// Driver Class
public class Main {

    public static void main(String[] args) {

        // DFS Solution
        SolutionDFS dfs = new SolutionDFS();

        int[][] stones1 = {
                {0, 0},
                {0, 1},
                {1, 0},
                {1, 2},
                {2, 1},
                {2, 2}
        };

        System.out.println("DFS Output: "
                + dfs.removeStones(stones1)); // 5

        // DSU Solution
        SolutionDSU dsu = new SolutionDSU();

        int[][] stones2 = {
                {0, 0},
                {0, 2},
                {1, 1},
                {2, 0},
                {2, 2}
        };

        System.out.println("DSU Output: "
                + dsu.removeStones(stones2)); // 3
    }
}
