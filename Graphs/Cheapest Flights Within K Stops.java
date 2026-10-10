/*
Problem: There are n cities connected by m flights. Each flight is represented as (u, v, w),
where u is the source city, v is the destination city, and w is the price.
You are given src, dst, and K (maximum number of stops allowed).
Return the cheapest price from src to dst with at most K stops. If no such route exists, return -1.

Source: LeetCode Problem #787 → https://leetcode.com/problems/cheapest-flights-within-k-stops/

Approaches:
1. Brute Force (DFS Recursion)
   - Idea:
     - Explore all possible paths from src to dst with depth ≤ K+1.
     - Track cost along each path.
     - Return minimum cost found.
   - Time Complexity: Exponential in worst case.
   - Space Complexity: O(n).

2. Optimal (BFS with Queue — your code)
   - Idea:
     - Use a queue storing (stop, node, cost).
     - Traverse level by level up to K stops.
     - Update dist[] when a cheaper cost is found.
     - Return dist[dst] if reachable, else -1.
   - Time Complexity: O(m * K).
   - Space Complexity: O(n + m).
*/

import java.util.*;

class BruteForce {
    private int minCost;
    public int findCheapestPrice(int n, int[][] flights, int src, int dst, int K) {
        Map<Integer, List<int[]>> adj = new HashMap<>();
        for (int[] f : flights) {
            adj.computeIfAbsent(f[0], k -> new ArrayList<>()).add(new int[]{f[1], f[2]});
        }
        minCost = Integer.MAX_VALUE;
        dfs(adj, src, dst, K+1, 0);
        return minCost == Integer.MAX_VALUE ? -1 : minCost;
    }

    private void dfs(Map<Integer, List<int[]>> adj, int u, int dst, int stops, int cost) {
        if (u == dst) {
            minCost = Math.min(minCost, cost);
            return;
        }
        if (stops == 0) return;
        if (!adj.containsKey(u)) return;
        for (int[] nei : adj.get(u)) {
            if (cost + nei[1] >= minCost) continue; // prune
            dfs(adj, nei[0], dst, stops-1, cost + nei[1]);
        }
    }
}

class Pair {
    int stop, node, dist;
    public Pair(int stop, int node, int dist) {
        this.stop = stop;
        this.node = node;
        this.dist = dist;
    }
}

class Optimal {
    public int findCheapestPrice(int n, int[][] flights, int src, int dst, int k) {
        List<List<int[]>> adj = new ArrayList<>();
        for (int i = 0; i < n; i++) adj.add(new ArrayList<>());
        for (int[] f : flights) {
            adj.get(f[0]).add(new int[]{f[1], f[2]});
        }

        int[] dist = new int[n];
        Arrays.fill(dist, Integer.MAX_VALUE);

        Queue<Pair> q = new LinkedList<>();
        q.add(new Pair(0, src, 0));

        while (!q.isEmpty()) {
            Pair cur = q.poll();
            int stop = cur.stop, node = cur.node, dis = cur.dist;
            if (stop > k) continue;
            for (int[] it : adj.get(node)) {
                if (dis + it[1] < dist[it[0]]) {
                    dist[it[0]] = dis + it[1];
                    q.add(new Pair(stop+1, it[0], dist[it[0]]));
                }
            }
        }
        return dist[dst] == Integer.MAX_VALUE ? -1 : dist[dst];
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
        int[][] flights1 = {{0,1,100},{1,2,100},{0,2,500}};
        Util.printResult("Brute Force Result (Testcase 1): ", brute.findCheapestPrice(3, flights1, 0, 2, 1)); // Expected: 200
        Util.printResult("Optimal Result (Testcase 1): ", opt.findCheapestPrice(3, flights1, 0, 2, 1));       // Expected: 200

        // Testcase 2
        int[][] flights2 = {{0,1,100},{1,2,100},{0,2,500}};
        Util.printResult("\nBrute Force Result (Testcase 2): ", brute.findCheapestPrice(3, flights2, 0, 2, 0)); // Expected: 500
        Util.printResult("Optimal Result (Testcase 2): ", opt.findCheapestPrice(3, flights2, 0, 2, 0));         // Expected: 500

        // Testcase 3
        int[][] flights3 = {{0,1,2},{1,2,1},{2,3,1},{0,3,10}};
        Util.printResult("\nBrute Force Result (Testcase 3): ", brute.findCheapestPrice(4, flights3, 0, 3, 1)); // Expected: 10
        Util.printResult("Optimal Result (Testcase 3): ", opt.findCheapestPrice(4, flights3, 0, 3, 1));         // Expected: 10
    }
}
