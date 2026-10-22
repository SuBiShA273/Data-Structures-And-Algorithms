/*
Problem: Number of Operations to Make Network Connected

Given n computers and a list of connections where connections[i] = [a, b]
represents a cable connecting computers a and b.

You can remove an extra cable and reconnect it between any two disconnected
computers.

Return the minimum number of operations needed to connect all computers.
If it is impossible, return -1.

Source: LeetCode Problem #1319
https://leetcode.com/problems/number-of-operations-to-make-network-connected/

Approaches:

1. DFS / Connected Components
   - Idea:
     - To connect n nodes, at least (n - 1) cables are required.
     - If connections.length < n - 1, return -1.
     - Build an adjacency list.
     - Count connected components using DFS.
     - If there are 'components' groups, we need
       (components - 1) operations.

   - Time Complexity: O(V + E)
   - Space Complexity: O(V + E)

2. Optimized (Union Find / DSU)
   - Idea:
     - Initially every node is its own component.
     - For every connection:
         * If both nodes already belong to the same component,
           the cable is redundant.
         * Otherwise union them.
     - The number of operations needed is
       (numberOfComponents - 1).

   - Time Complexity: O(E * α(N))
   - Space Complexity: O(N)
*/

import java.util.*;

// Approach 1: DFS / Connected Components
class SolutionDFS {

    public int makeConnected(int n, int[][] connections) {

        if (connections.length < n - 1) {
            return -1;
        }

        List<Integer>[] graph = new ArrayList[n];

        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<>();
        }

        for (int[] edge : connections) {
            int u = edge[0];
            int v = edge[1];

            graph[u].add(v);
            graph[v].add(u);
        }

        boolean[] visited = new boolean[n];
        int components = 0;

        for (int i = 0; i < n; i++) {
            if (!visited[i]) {
                dfs(i, graph, visited);
                components++;
            }
        }

        return components - 1;
    }

    private void dfs(int node, List<Integer>[] graph, boolean[] visited) {

        visited[node] = true;

        for (int neighbor : graph[node]) {
            if (!visited[neighbor]) {
                dfs(neighbor, graph, visited);
            }
        }
    }
}

// Approach 2: Optimized (Union Find / DSU)
class SolutionDSU {

    private int[] parent;

    public int makeConnected(int n, int[][] connections) {

        if (connections.length < n - 1) {
            return -1;
        }

        parent = new int[n];

        for (int i = 0; i < n; i++) {
            parent[i] = i;
        }

        int components = n;

        for (int[] edge : connections) {

            int u = edge[0];
            int v = edge[1];

            int pu = find(u);
            int pv = find(v);

            if (pu != pv) {
                parent[pu] = pv;
                components--;
            }
        }

        return components - 1;
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

        int[][] connections1 = {
                {0, 1},
                {0, 2},
                {1, 2}
        };

        System.out.println("DFS Output: "
                + dfs.makeConnected(4, connections1)); // 1

        // DSU Solution
        SolutionDSU dsu = new SolutionDSU();

        int[][] connections2 = {
                {0, 1},
                {0, 2},
                {0, 3},
                {1, 2},
                {1, 3}
        };

        System.out.println("DSU Output: "
                + dsu.makeConnected(6, connections2)); // 2
    }
}
