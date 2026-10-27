/*
Problem: Critical Connections in a Network

There are n servers numbered from 0 to n - 1 connected by
undirected connections.

A critical connection (bridge) is an edge that, if removed,
makes some servers unreachable from others.

Return all critical connections in any order.

Source: LeetCode Problem #1192
https://leetcode.com/problems/critical-connections-in-a-network/

Approaches:

1. Brute Force (Remove Every Edge)
   - Idea:
     - Remove each edge one by one.
     - Check if graph remains connected using DFS/BFS.
     - If graph becomes disconnected, that edge is critical.

   - Time Complexity: O(E * (V + E))
   - Space Complexity: O(V + E)

2. Optimized (Tarjan's Bridge Algorithm)
   - Idea:
     - Use DFS traversal.
     - Maintain:
         * disc[node] → discovery time
         * low[node]  → lowest reachable discovery time
     - If:
           low[neighbor] > disc[node]
       then edge (node, neighbor) is a bridge.
     - Store all such edges.

   - Time Complexity: O(V + E)
   - Space Complexity: O(V + E)
*/

import java.util.*;

// Approach 1: Brute Force
class CriticalConnectionsBrute {

    public List<List<Integer>> criticalConnections(
            int n,
            List<List<Integer>> connections) {

        List<List<Integer>> result = new ArrayList<>();

        for (int remove = 0; remove < connections.size(); remove++) {

            List<Integer>[] graph = new ArrayList[n];

            for (int i = 0; i < n; i++) {
                graph[i] = new ArrayList<>();
            }

            for (int i = 0; i < connections.size(); i++) {

                if (i == remove) continue;

                int u = connections.get(i).get(0);
                int v = connections.get(i).get(1);

                graph[u].add(v);
                graph[v].add(u);
            }

            boolean[] visited = new boolean[n];
            dfs(0, graph, visited);

            for (boolean node : visited) {

                if (!node) {
                    result.add(connections.get(remove));
                    break;
                }
            }
        }

        return result;
    }

    private void dfs(
            int node,
            List<Integer>[] graph,
            boolean[] visited) {

        visited[node] = true;

        for (int next : graph[node]) {
            if (!visited[next]) {
                dfs(next, graph, visited);
            }
        }
    }
}

// Approach 2: Optimized (Tarjan Algorithm)
class CriticalConnectionsOptimized {

    private List<List<Integer>> bridges;
    private List<Integer>[] graph;
    private int[] disc;
    private int[] low;
    private int time;

    public List<List<Integer>> criticalConnections(
            int n,
            List<List<Integer>> connections) {

        bridges = new ArrayList<>();

        graph = new ArrayList[n];

        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<>();
        }

        for (List<Integer> edge : connections) {

            int u = edge.get(0);
            int v = edge.get(1);

            graph[u].add(v);
            graph[v].add(u);
        }

        disc = new int[n];
        low = new int[n];

        dfs(0, -1);

        return bridges;
    }

    private void dfs(int node, int parent) {

        disc[node] = low[node] = ++time;

        for (int neighbor : graph[node]) {

            if (neighbor == parent) {
                continue;
            }

            if (disc[neighbor] == 0) {

                dfs(neighbor, node);

                low[node] = Math.min(low[node], low[neighbor]);

                if (low[neighbor] > disc[node]) {

                    bridges.add(
                            Arrays.asList(node, neighbor)
                    );
                }

            } else {

                low[node] = Math.min(
                        low[node],
                        disc[neighbor]
                );
            }
        }
    }
}

// Driver Class
public class Main {

    public static void main(String[] args) {

        List<List<Integer>> connections = new ArrayList<>();

        connections.add(Arrays.asList(0, 1));
        connections.add(Arrays.asList(1, 2));
        connections.add(Arrays.asList(2, 0));
        connections.add(Arrays.asList(1, 3));

        CriticalConnectionsOptimized obj =
                new CriticalConnectionsOptimized();

        System.out.println(
                obj.criticalConnections(4, connections)
        );

        // Output:
        // [[1, 3]]
    }
}
