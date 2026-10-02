/*
Problem: There are n cities. Some of them are connected, while some are not.
A province is a group of directly or indirectly connected cities with no other connections.
Given an n x n matrix isConnected where isConnected[i][j] = 1 if the ith city and jth city are directly connected,
and isConnected[i][j] = 0 otherwise, return the total number of provinces.

Source: LeetCode Problem #547 → https://leetcode.com/problems/number-of-provinces/

Approach: DFS Traversal
   - Idea:
     - Treat the matrix as an adjacency matrix.
     - Use DFS to mark all cities in the same province.
     - Count how many times we start a new DFS.
   - Time Complexity: O(n^2).
   - Space Complexity: O(n).
*/

import java.util.*;

class Solution {
    public int findCircleNum(int[][] isConnected) {
        int n = isConnected.length;
        boolean[] visited = new boolean[n];
        int provinces = 0;
        for (int i = 0; i < n; i++) {
            if (!visited[i]) {
                dfs(isConnected, visited, i);
                provinces++;
            }
        }
        return provinces;
    }

    private void dfs(int[][] isConnected, boolean[] visited, int i) {
        visited[i] = true;
        for (int j = 0; j < isConnected.length; j++) {
            if (isConnected[i][j] == 1 && !visited[j]) {
                dfs(isConnected, visited, j);
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
        int[][] isConnected1 = {{1,1,0},{1,1,0},{0,0,1}};
        Util.printResult("DFS Result (Testcase 1): ", sol.findCircleNum(isConnected1)); // Expected: 2

        // Testcase 2
        int[][] isConnected2 = {{1,0,0},{0,1,0},{0,0,1}};
        Util.printResult("DFS Result (Testcase 2): ", sol.findCircleNum(isConnected2)); // Expected: 3

        // Testcase 3
        int[][] isConnected3 = {{1,1,0},{1,1,1},{0,1,1}};
        Util.printResult("DFS Result (Testcase 3): ", sol.findCircleNum(isConnected3)); // Expected: 1
    }
}
