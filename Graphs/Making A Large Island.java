/*
Problem: You are given an n x n binary matrix grid.
You are allowed to change at most one 0 into 1.
Return the size of the largest island after this operation.

An island is a group of connected 1's (4-directionally connected).

Source: LeetCode Problem #827 → https://leetcode.com/problems/making-a-large-island/

Approaches:
1. Brute Force (Try Every Zero)
   - Idea:
     - For every cell containing 0:
       - Change it to 1.
       - Run DFS to find the largest island size.
       - Restore back to 0.
     - Keep track of maximum island obtained.
   - Time Complexity: O(n^4)
   - Space Complexity: O(n^2)

2. Optimal (DFS Coloring)
   - Idea:
     - First, assign a unique id (2,3,4...) to every island.
     - Store island sizes in a HashMap.
     - For each 0 cell:
       - Check its 4 neighbors.
       - Combine distinct island sizes.
       - Add 1 (for current 0 converted to 1).
     - Return maximum possible size.
   - Time Complexity: O(n^2)
   - Space Complexity: O(n^2)
*/

import java.util.*;

// --------------------------------------------------
// Approach 1 : Brute Force
// --------------------------------------------------
class BruteForce {

    int[] dr = {1,-1,0,0};
    int[] dc = {0,0,1,-1};

    public int largestIsland(int[][] grid) {
        int n = grid.length;
        int ans = 0;

        boolean hasZero = false;

        for(int i=0;i<n;i++) {
            for(int j=0;j<n;j++) {

                if(grid[i][j] == 0) {
                    hasZero = true;

                    grid[i][j] = 1;

                    boolean[][] vis = new boolean[n][n];
                    ans = Math.max(ans, dfs(grid, i, j, vis));

                    grid[i][j] = 0;
                }
            }
        }

        return hasZero ? ans : n*n;
    }

    private int dfs(int[][] grid,int r,int c,boolean[][] vis) {

        int n = grid.length;

        if(r<0 || c<0 || r>=n || c>=n ||
           grid[r][c]==0 || vis[r][c])
            return 0;

        vis[r][c] = true;

        int size = 1;

        for(int k=0;k<4;k++) {
            size += dfs(grid,r+dr[k],c+dc[k],vis);
        }

        return size;
    }
}

// --------------------------------------------------
// Approach 2 : Optimal (Island Coloring)
// --------------------------------------------------
class Optimal {

    int[] dr = {1,-1,0,0};
    int[] dc = {0,0,1,-1};

    public int largestIsland(int[][] grid) {

        int n = grid.length;
        Map<Integer,Integer> sizeMap = new HashMap<>();

        int color = 2;
        int ans = 0;

        // Color each island with unique id
        for(int i=0;i<n;i++) {
            for(int j=0;j<n;j++) {
                if(grid[i][j] == 1) {
                    int size = dfs(grid,i,j,color);
                    sizeMap.put(color,size);
                    ans = Math.max(ans,size);
                    color++;
                }
            }
        }

        // Try converting every 0 into 1
        for(int i=0;i<n;i++) {
            for(int j=0;j<n;j++) {

                if(grid[i][j] == 0) {

                    HashSet<Integer> set = new HashSet<>();
                    int currSize = 1;

                    for(int k=0;k<4;k++) {

                        int nr = i + dr[k];
                        int nc = j + dc[k];

                        if(nr>=0 && nc>=0 && nr<n && nc<n) {

                            int islandId = grid[nr][nc];

                            if(islandId > 1 && set.add(islandId)) {
                                currSize += sizeMap.get(islandId);
                            }
                        }
                    }

                    ans = Math.max(ans,currSize);
                }
            }
        }

        return ans;
    }

    private int dfs(int[][] grid,int r,int c,int color) {

        int n = grid.length;

        if(r<0 || c<0 || r>=n || c>=n || grid[r][c] != 1)
            return 0;

        grid[r][c] = color;

        int size = 1;

        for(int k=0;k<4;k++) {
            size += dfs(grid,r+dr[k],c+dc[k],color);
        }

        return size;
    }
}

// --------------------------------------------------
// Driver Class
// --------------------------------------------------
public class Main {

    public static void main(String[] args) {

        BruteForce brute = new BruteForce();
        Optimal opt = new Optimal();

        // Testcase 1
        int[][] grid1 = {
            {1,0},
            {0,1}
        };

        System.out.println("Brute Force Result (Testcase 1): "
                + brute.largestIsland(copy(grid1))); // Expected: 3

        System.out.println("Optimal Result (Testcase 1): "
                + opt.largestIsland(copy(grid1)));   // Expected: 3


        // Testcase 2
        int[][] grid2 = {
            {1,1},
            {1,0}
        };

        System.out.println("\nBrute Force Result (Testcase 2): "
                + brute.largestIsland(copy(grid2))); // Expected: 4

        System.out.println("Optimal Result (Testcase 2): "
                + opt.largestIsland(copy(grid2)));   // Expected: 4


        // Testcase 3
        int[][] grid3 = {
            {1,1},
            {1,1}
        };

        System.out.println("\nBrute Force Result (Testcase 3): "
                + brute.largestIsland(copy(grid3))); // Expected: 4

        System.out.println("Optimal Result (Testcase 3): "
                + opt.largestIsland(copy(grid3)));   // Expected: 4
    }

    private static int[][] copy(int[][] grid) {
        int[][] res = new int[grid.length][grid[0].length];
        for(int i=0;i<grid.length;i++) {
            res[i] = grid[i].clone();
        }
        return res;
    }
}
