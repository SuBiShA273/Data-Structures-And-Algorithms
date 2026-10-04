/*
Problem: Given an undirected graph, return true if and only if it is bipartite.
A graph is bipartite if the nodes can be divided into two sets such that
every edge connects a node in one set to a node in the other set.

Source: LeetCode Problem #785 → https://leetcode.com/problems/is-graph-bipartite/

Approach: DFS Coloring
   - Idea:
     - Use an array `color` to mark each node: -1 (uncolored), 0 or 1 (two colors).
     - For each unvisited node, start DFS and assign a color.
     - For each neighbor:
       - If uncolored, assign opposite color and continue DFS.
       - If already colored and same as current, return false.
     - If all nodes are colored without conflict, return true.
   - Time Complexity: O(V + E).
   - Space Complexity: O(V).
*/

import java.util.*;

class Solution {
    public boolean isBipartite(int[][] graph) {
        int n = graph.length;
        int[] color = new int[n];
        Arrays.fill(color, -1);

        for (int i = 0; i < n; i++) {
            if (color[i] == -1) {
                if (!dfs(graph, color, i, 0)) return false;
            }
        }
        return true;
    }

    private boolean dfs(int[][] graph, int[] color, int node, int c) {
        color[node] = c;
        for (int nei : graph[node]) {
            if (color[nei] == -1) {
                if (!dfs(graph, color, nei, 1 - c)) return false;
            } else if (color[nei] == c) {
                return false;
            }
        }
        return true;
    }
}

// Utility: print result
class Util {
    public static void printResult(String label, boolean result) {
        System.out.println(label + result);
    }
}

// Driver class with testcases
public class Main {
    public static void main(String[] args) {
        Solution sol = new Solution();

        // Testcase 1
        int[][] graph1 = {{1,3},{0,2},{1,3},{0,2}};
        Util.printResult("DFS Result (Testcase 1): ", sol.isBipartite(graph1)); // Expected: true

        // Testcase 2
        int[][] graph2 = {{1,2,3},{0,2},{0,1,3},{0,2}};
        Util.printResult("DFS Result (Testcase 2): ", sol.isBipartite(graph2)); // Expected: false

        // Testcase 3
        int[][] graph3 = {{},{},{},{}};
        Util.printResult("DFS Result (Testcase 3): ", sol.isBipartite(graph3)); // Expected: true
    }
}
