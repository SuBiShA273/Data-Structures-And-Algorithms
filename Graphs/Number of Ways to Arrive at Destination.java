/*
Problem: You are given n intersections (0 to n-1) and roads[i] = [u, v, time].
Each road is bidirectional with travel time. You start at node 0 and want to reach node n-1.
Return the number of distinct shortest paths from 0 to n-1. Since the answer can be large, return it modulo 1e9+7.

Source: LeetCode Problem #1976 → https://leetcode.com/problems/number-of-ways-to-arrive-at-destination/

Approaches:
1. Brute Force (DFS Backtracking)
   - Idea:
     - Explore all possible paths recursively.
     - Track minimum distance found and count how many paths achieve it.
     - Exponential, only for conceptual clarity.
   - Time Complexity: Exponential.
   - Space Complexity: O(n).

2. Optimal (Dijkstra with Path Counting)
   - Idea:
     - Use Dijkstra’s algorithm to compute shortest distances.
     - Maintain ways[] to count number of shortest paths to each node.
     - Update ways[v] when a new shortest path or equal path is found.
   - Time Complexity: O((V+E) log V).
   - Space Complexity: O(V+E).
*/

import java.util.*;

class BruteForce {
    private long minDist = Long.MAX_VALUE;
    private long ways = 0;
    private final int MOD = 1_000_000_007;

    public int countPaths(int n, int[][] roads) {
        Map<Integer,List<int[]>> adj = new HashMap<>();
        for (int[] r : roads) {
            adj.computeIfAbsent(r[0], k -> new ArrayList<>()).add(new int[]{r[1], r[2]});
            adj.computeIfAbsent(r[1], k -> new ArrayList<>()).add(new int[]{r[0], r[2]});
        }
        dfs(adj, 0, n-1, new boolean[n], 0);
        return (int)(ways % MOD);
    }

    private void dfs(Map<Integer,List<int[]>> adj, int u, int dst, boolean[] vis, long dist) {
        if (u == dst) {
            if (dist < minDist) {
                minDist = dist;
                ways = 1;
            } else if (dist == minDist) {
                ways++;
            }
            return;
        }
        vis[u] = true;
        if (!adj.containsKey(u)) { vis[u] = false; return; }
        for (int[] nei : adj.get(u)) {
            if (!vis[nei[0]]) dfs(adj, nei[0], dst, vis, dist + nei[1]);
        }
        vis[u] = false;
    }
}

class Optimal {
    public int countPaths(int n, int[][] roads) {
        List<List<int[]>> graph = new ArrayList<>();
        for (int i = 0; i < n; i++) graph.add(new ArrayList<>());
        for (int[] r : roads) {
            graph.get(r[0]).add(new int[]{r[1], r[2]});
            graph.get(r[1]).add(new int[]{r[0], r[2]});
        }

        final int MOD = 1_000_000_007;
        long[] dist = new long[n];
        Arrays.fill(dist, Long.MAX_VALUE);
        long[] ways = new long[n];
        dist[0] = 0; ways[0] = 1;

        PriorityQueue<long[]> pq = new PriorityQueue<>(Comparator.comparingLong(a -> a[0]));
        pq.offer(new long[]{0,0}); // {distance, node}

        while (!pq.isEmpty()) {
            long[] cur = pq.poll();
            long d = cur[0]; int u = (int)cur[1];
            if (d > dist[u]) continue;
            for (int[] nei : graph.get(u)) {
                int v = nei[0], w = nei[1];
                if (d + w < dist[v]) {
                    dist[v] = d + w;
                    ways[v] = ways[u];
                    pq.offer(new long[]{dist[v], v});
                } else if (d + w == dist[v]) {
                    ways[v] = (ways[v] + ways[u]) % MOD;
                }
            }
        }
        return (int)(ways[n-1] % MOD);
    }
}

// Driver class with testcases
public class Main {
    public static void main(String[] args) {
        BruteForce brute = new BruteForce();
        Optimal opt = new Optimal();

        // Testcase 1
        int[][] roads1 = {{0,6,7},{0,1,2},{1,2,3},{1,3,3},{6,3,3},{3,5,1},{6,5,1},{2,5,1},{0,4,5},{4,6,2}};
        System.out.println("Brute Force Result (Testcase 1): " + brute.countPaths(7, roads1)); // Expected: 4
        System.out.println("Optimal Result (Testcase 1): " + opt.countPaths(7, roads1));       // Expected: 4

        // Testcase 2
        int[][] roads2 = {{0,1,1},{1,2,1},{0,2,2}};
        System.out.println("\nBrute Force Result (Testcase 2): " + brute.countPaths(3, roads2)); // Expected: 2
        System.out.println("Optimal Result (Testcase 2): " + opt.countPaths(3, roads2));         // Expected: 2

        // Testcase 3
        int[][] roads3 = {{0,1,10},{0,2,5},{1,2,2},{1,3,1},{2,3,9}};
        System.out.println("\nBrute Force Result (Testcase 3): " + brute.countPaths(4, roads3)); // Expected: 1
        System.out.println("Optimal Result (Testcase 3): " + opt.countPaths(4, roads3));         // Expected: 1
    }
}
