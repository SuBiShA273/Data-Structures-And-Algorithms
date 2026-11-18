/*
Problem: Best Time to Buy and Sell Stock II

You are given an integer array prices where prices[i] is the
price of a given stock on the ith day.

On each day, you may decide to buy and/or sell the stock.
You can only hold at most one share of the stock at any time.
However, you can buy it then immediately sell it on the same day.

Return the maximum profit you can achieve.

Source: LeetCode Problem #122
https://leetcode.com

Approaches:

1. Recursion
   - Idea:
     - At each day i and state j (0 = can buy, 1 = can sell):
       - If j == 0: max(-prices[i] + f(i+1, 1), f(i+1, 0))
       - If j == 1: max(prices[i] + f(i+1, 0), f(i+1, 1))

   - Time Complexity: O(2^n)
   - Space Complexity: O(n)

2. Memoization
   - Idea:
     - Store maximum profit for each (day, state) pair in dp[n][2].
     - Avoid recalculating overlapping subproblems.

   - Time Complexity: O(n)
   - Space Complexity: O(n)

3. Tabulation
   - Idea:
     - dp[i][j] = max profit from day i to n-1 in state j.
     - Iterate backwards from n-1 down to 0.

   - Time Complexity: O(n)
   - Space Complexity: O(n)

4. Space Optimization
   - Idea:
     - State on day i depends only on day i+1.
     - Maintain two 1D arrays of size 2: dp (ahead) and cur (current).

   - Time Complexity: O(n)
   - Space Complexity: O(1)
*/

import java.util.*;

// Approach 1: Recursion
class BestTimeStock2Recursion {

    public int maxProfit(int[] prices) {
        return helper(0, 0, prices);
    }

    private int helper(int i, int j, int[] prices) {

        if (i == prices.length) {
            return 0;
        }

        if (j == 1) {
            return Math.max(helper(i + 1, 0, prices) + prices[i], helper(i + 1, 1, prices));
        } else {
            return Math.max(-prices[i] + helper(i + 1, 1, prices), helper(i + 1, 0, prices));
        }
    }
}

// Approach 2: Memoization
class BestTimeStock2Memoization {

    public int maxProfit(int[] prices) {

        int n = prices.length;
        Integer[][] dp = new Integer[n][2];

        return helper(0, 0, prices, dp);
    }

    private int helper(int i, int j, int[] prices, Integer[][] dp) {

        if (i == prices.length) {
            return 0;
        }

        if (dp[i][j] != null) {
            return dp[i][j];
        }

        if (j == 1) {
            dp[i][j] = Math.max(helper(i + 1, 0, prices, dp) + prices[i], helper(i + 1, 1, prices, dp));
        } else {
            dp[i][j] = Math.max(-prices[i] + helper(i + 1, 1, prices, dp), helper(i + 1, 0, prices, dp));
        }

        return dp[i][j];
    }
}

// Approach 3: Tabulation
class BestTimeStock2Tabulation {

    public int maxProfit(int[] prices) {

        int n = prices.length;
        int[][] dp = new int[n + 1][2];

        for (int i = n - 1; i >= 0; i--) {
            for (int j = 0; j < 2; j++) {
                if (j == 1) {
                    dp[i][j] = Math.max(dp[i + 1][0] + prices[i], dp[i + 1][1]);
                } else {
                    dp[i][j] = Math.max(-prices[i] + dp[i + 1][1], dp[i + 1][0]);
                }
            }
        }

        return dp[0][0];
    }
}

// Approach 4: Space Optimization
class BestTimeStock2Optimized {

    public int maxProfit(int[] prices) {

        int n = prices.length;
        int[] dp = new int[2];
        int[] cur = new int[2];

        for (int i = n - 1; i >= 0; i--) {
            for (int j = 0; j < 2; j++) {
                cur[j] = 0;
                if (j == 1)
                    cur[j] = Math.max(dp[0] + prices[i], dp[1]);
                else
                    cur[j] = Math.max(-prices[i] + dp[1], dp[0]);
            }
            dp = cur.clone();
        }

        return dp[0];
    }
}

// Driver Class
public class Main {

    public static void main(String[] args) {

        int[] prices = {7, 1, 5, 3, 6, 4};

        BestTimeStock2Recursion rec = new BestTimeStock2Recursion();
        System.out.println("Recursion Output: " + rec.maxProfit(prices)); // 7

        BestTimeStock2Memoization memo = new BestTimeStock2Memoization();
        System.out.println("Memoization Output: " + memo.maxProfit(prices)); // 7

        BestTimeStock2Tabulation tab = new BestTimeStock2Tabulation();
        System.out.println("Tabulation Output: " + tab.maxProfit(prices)); // 7

        BestTimeStock2Optimized opt = new BestTimeStock2Optimized();
        System.out.println("Space Optimized Output: " + opt.maxProfit(prices)); // 7
    }
}
