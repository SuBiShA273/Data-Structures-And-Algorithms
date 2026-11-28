/*
Problem: Minimum Cost to Cut a Stick

Given a wooden stick of length n units. The stick is labelled from 0 to n.
Given an integer array cuts where cuts[i] denotes a position you should perform
a cut at.

You should perform the cuts in order, you can change the order of the cuts as
you wish.

The cost of one cut is the length of the stick to be cut, the total cost is the
sum of costs of all cuts. When you cut a stick, it will be split into two
smaller sticks (i.e. the sum of their lengths is the length of the stick before
the cut).

Return the minimum total cost of the cuts.

Source: LeetCode Problem #1547
https://leetcode.com

Approaches:

1. Recursion (MCM / Partition DP Pattern)
   - Idea:
     - Sort the cuts array and pad it with boundaries 0 at the start and n at
       the end (arr of size c + 2).
     - For any range of cuts [i, j], try every cut k as the first cut in that
       segment.
     - Current stick length is arr[j + 1] - arr[i - 1], plus the minimum cost
       of left [i, k - 1] and right [k + 1, j] subproblems.

   - Time Complexity: Exponential -> O(c * 2^c)
   - Space Complexity: O(c) (recursion stack)

2. Memoization
   - Idea:
     - Store results of overlapping subproblems in a 2D DP table: dp[i][j].
     - Avoid recalculating already solved cut intervals [i, j].

   - Time Complexity: O(c^3) (where c = cuts.length)
   - Space Complexity: O(c^2) + O(c) -> O(c^2)

3. Tabulation (Bottom-Up Optimal)
   - Idea:
     - Build the 2D DP table of size (c + 2) x (c + 2) bottom-up.
     - Loop i from c down to 1, and j from i up to c so smaller intervals
       are computed before larger ones.
     - Base cases (i > j) are already 0 by default in Java.

   - Time Complexity: O(c^3)
   - Space Complexity: O(c^2)
*/

import java.util.*;

// Approach 1: Recursion
class MinCostCutRecursion {

    public int minCost(int n, int[] cuts) {
        int c = cuts.length;
        int[] arr = prepareArray(n, cuts);
        return helper(1, c, arr);
    }

    private int helper(int i, int j, int[] arr) {
        if (i > j) {
            return 0;
        }

        int mini = Integer.MAX_VALUE;
        int stickLen = arr[j + 1] - arr[i - 1];

        for (int k = i; k <= j; k++) {
            int cost = stickLen + helper(i, k - 1, arr) + helper(k + 1, j, arr);
            mini = Math.min(mini, cost);
        }

        return mini;
    }

    private int[] prepareArray(int n, int[] cuts) {
        int c = cuts.length;
        int[] arr = new int[c + 2];
        arr[0] = 0;
        arr[c + 1] = n;

        int[] sortedCuts = cuts.clone();
        Arrays.sort(sortedCuts);
        for (int i = 0; i < c; i++) {
            arr[i + 1] = sortedCuts[i];
        }
        return arr;
    }
}

// Approach 2: Memoization
class MinCostCutMemoization {

    public int minCost(int n, int[] cuts) {
        int c = cuts.length;
        int[] arr = prepareArray(n, cuts);
        Integer[][] dp = new Integer[c + 1][c + 1];

        return helper(1, c, arr, dp);
    }

    private int helper(int i, int j, int[] arr, Integer[][] dp) {
        if (i > j) {
            return 0;
        }

        if (dp[i][j] != null) {
            return dp[i][j];
        }

        int mini = Integer.MAX_VALUE;
        int stickLen = arr[j + 1] - arr[i - 1];

        for (int k = i; k <= j; k++) {
            int cost = stickLen + helper(i, k - 1, arr, dp) + helper(k + 1, j, arr, dp);
            mini = Math.min(mini, cost);
        }

        return dp[i][j] = mini;
    }

    private int[] prepareArray(int n, int[] cuts) {
        int c = cuts.length;
        int[] arr = new int[c + 2];
        arr[0] = 0;
        arr[c + 1] = n;

        int[] sortedCuts = cuts.clone();
        Arrays.sort(sortedCuts);
        for (int i = 0; i < c; i++) {
            arr[i + 1] = sortedCuts[i];
        }
        return arr;
    }
}

// Approach 3: Tabulation
class MinCostCutTabulation {

    public int minCost(int n, int[] cuts) {
        int c = cuts.length;
        int[] arr = new int[c + 2];
        arr[0] = 0;
        arr[c + 1] = n;

        int[] sortedCuts = cuts.clone();
        Arrays.sort(sortedCuts);
        for (int i = 0; i < c; i++) {
            arr[i + 1] = sortedCuts[i];
        }

        // Size c + 2 prevents out-of-bounds when k == c (dp[k + 1][j])
        // Base cases (i > j) are already 0 in Java default initialization
        int[][] dp = new int[c + 2][c + 2];

        for (int i = c; i >= 1; i--) {
            for (int j = i; j <= c; j++) {
                int mini = Integer.MAX_VALUE;
                int stickLen = arr[j + 1] - arr[i - 1];

                for (int k = i; k <= j; k++) {
                    int cost = stickLen + dp[i][k - 1] + dp[k + 1][j];
                    mini = Math.min(mini, cost);
                }

                dp[i][j] = mini;
            }
        }

        return dp[1][c];
    }
}

// Driver Class
public class Main {

    public static void main(String[] args) {

        int n = 7;
        int[] cuts = {1, 3, 4, 5};

        MinCostCutRecursion rec = new MinCostCutRecursion();
        System.out.println("Recursion Output: " + rec.minCost(n, cuts)); // 16

        MinCostCutMemoization memo = new MinCostCutMemoization();
        System.out.println("Memoization Output: " + memo.minCost(n, cuts)); // 16

        MinCostCutTabulation tab = new MinCostCutTabulation();
        System.out.println("Tabulation Output: " + tab.minCost(n, cuts)); // 16
    }
}
