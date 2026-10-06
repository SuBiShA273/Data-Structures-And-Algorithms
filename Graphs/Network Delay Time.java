/*
Problem: You are given a network of n nodes, labeled 1 to n.
You are also given times, a list of travel times as directed edges times[i] = (u, v, w),
where u is the source node, v is the target node, and w is the time it takes for a signal
to travel from source to target.
We send a signal from a given node k. Return the time it takes for all nodes to receive the signal.
If it is impossible, return -1.

Source: LeetCode Problem #743 → https://leetcode.com/problems/network-delay-time/

Approach: DFS (Recursive Shortest Path)
   - Idea:
     - Build adjacency list from edges.
     - Use DFS to explore paths from source k.
     - Maintain distance array with shortest known times.
     - Update distances when a shorter path is found.
     - At the end, return max distance if all nodes reached, else -1.
   - Time Complexity: O(V + E) in practice, though DFS may revisit nodes.
   - Space Complexity: O(V + E).
*/

import java.util.*;

class Solution {
    public int networkDelayTime(int[][] times, int n, int k) {
        List<List<int[]>> adj = new ArrayList<>();
        for (int i = 0; i <= n; i++) adj.add(new ArrayList<>());
        for (int[] edge : times) {
            adj.get(edge[0]).add(new int[]{edge[1], edge[2]});
        }

        int[] dist = new int[n+1];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[k] = 0;

        dfs(adj, dist, k);

        int max = 0;
        for (int i = 1; i <= n; i++) {
            if (dist[i] == Integer.MAX_VALUE) return -1;
            max = Math.max(max, dist[i]);
        }
        return max;
    }

    private void dfs(List<List<int[]>> adj, int[] dist, int node) {
        for (int[] nei : adj.get(node)) {
            int v = nei[0], w = nei[1];
            if (dist[node] + w < dist[v]) {
                dist[v] = dist[node] + w;
                dfs(adj, dist, v);
            }
        }
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
        int[][] times1 = {{2,1,1},{2,3,1},{3,4,1}};
        int n1 = 4, k1 = 2;
        Util.printResult("DFS Result (Testcase 1): ", sol.networkDelayTime(times1, n1, k1));
        // Expected: 2

        // Testcase 2
        int[][] times2 = {{1,2,1}};
        int n2 = 2, k2 = 1;
        Util.printResult("DFS Result (Testcase 2): ", sol.networkDelayTime(times2, n2, k2));
        // Expected: 1

        // Testcase 3
        int[][] times3 = {{1,2,1}};
        int n3 = 2, k3 = 2;
        Util.printResult("DFS Result (Testcase 3): ", sol.networkDelayTime(times3, n3, k3));
        // Expected: -1
    }
}
