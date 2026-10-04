/*
Problem: A node in a directed graph is a "safe node" if every possible path starting from that node
leads to a terminal node (a node with no outgoing edges).
Return an array containing all the safe nodes in ascending order.

Source: LeetCode Problem #802 → https://leetcode.com/problems/find-eventual-safe-states/

Approach: DFS + Coloring
   - Idea:
     - Use DFS to detect cycles.
     - Maintain a state array:
       0 = unvisited, 1 = visiting, 2 = safe.
     - If during DFS we revisit a "visiting" node → cycle → not safe.
     - If DFS completes without cycle → mark node as safe.
     - Collect all safe nodes.
   - Time Complexity: O(V + E).
   - Space Complexity: O(V).
*/

import java.util.*;

class Solution {
    public List<Integer> eventualSafeNodes(int[][] graph) {
        int n = graph.length;
        int[] state = new int[n]; // 0=unvisited, 1=visiting, 2=safe
        List<Integer> res = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            if (dfs(graph, state, i)) {
                res.add(i);
            }
        }
        return res;
    }

    private boolean dfs(int[][] graph, int[] state, int node) {
        if (state[node] != 0) return state[node] == 2;
        state[node] = 1; // visiting
        for (int nei : graph[node]) {
            if (!dfs(graph, state, nei)) return false;
        }
        state[node] = 2; // safe
        return true;
    }
}

// Driver class with testcases
public class Main {
    public static void main(String[] args) {
        Solution sol = new Solution();

        // Testcase 1
        int[][] graph1 = {{1,2},{2,3},{5},{0},{5},{},{}};
        System.out.println("DFS Result (Testcase 1): " + sol.eventualSafeNodes(graph1));
        // Expected: [2,4,5,6]

        // Testcase 2
        int[][] graph2 = {{1,2,3,4},{1,2},{3,4},{0,4},{}};
        System.out.println("DFS Result (Testcase 2): " + sol.eventualSafeNodes(graph2));
        // Expected: [4]

        // Testcase 3
        int[][] graph3 = {{},{},{},{},{}};
        System.out.println("DFS Result (Testcase 3): " + sol.eventualSafeNodes(graph3));
        // Expected: [0,1,2,3,4]
    }
}
