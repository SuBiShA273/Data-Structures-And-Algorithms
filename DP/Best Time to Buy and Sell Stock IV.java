/*
Problem: Best Time to Buy and Sell Stock IV

You are given an integer array prices where prices[i] is the price of a given stock
on the ith day, and an integer k.

Find the maximum profit you can achieve. You may complete at most k transactions:
i.e. you may buy at most k times and sell at most k times.

Note: You may not engage in multiple transactions simultaneously
(i.e., you must sell the stock before you buy again).

Source: LeetCode Problem #188
https://leetcode.com

Approaches:

1. Recursion
   - Idea:
     - At each day, we track three states: day index, whether we can buy (1 or 0),
       and remaining transactions (cap = k).
     - Explore both choices (buy/skip or sell/skip) and take the maximum profit.

   - Time Complexity: O(2^n)
   - Space Complexity: O(n)

2. Memoization
   - Idea:
     - Store results in a 3D DP table: dp[index][canBuy][cap].
     - Avoid recalculating overlapping subproblems.

   - Time Complexity: O(n * 2 * k) -> O(n * k)
   - Space Complexity: O(n * 2 * k) + O(n) -> O(n * k)

3. Tabulation
   - Idea:
     - Build the 3D DP table bottom-up from day n - 1 down to 0.
     - Base cases: when index == n or cap == 0, profit is 0.

   - Time Complexity: O(n * 2 * k) -> O(n * k)
   - Space Complexity: O(n * 2 * k) -> O(n * k)

4. Space Optimization
   - Idea:
     - Current day's states depend only on the next day's states (ahead).
     - Replace the 3D table with two 2D arrays (ahead and curr) of size 2 x (k + 1).

   - Time Complexity: O(n * 2 * k) -> O(n * k)
   - Space Complexity: O(2 * (k + 1)) -> O(k)
*/

import java.util.*;

// Approach 1: Recursion
class Stock4Recursion {

    public int maxProfit(int k, int[] prices) {
        return helper(0, 1, k, prices);
    }

    private int helper(int index, int canBuy, int cap, int[] prices) {

        if (index == prices.length || cap == 0) {
            return 0;
        }

        if (canBuy == 1) {
            int buy = -prices[index] + helper(index + 1, 0, cap, prices);
            int skip = 0 + helper(index + 1, 1, cap, prices);
            return Math.max(buy, skip);
        } else {
            int sell = prices[index] + helper(index + 1, 1, cap - 1, prices);
            int skip = 0 + helper(index + 1, 0, cap, prices);
            return Math.max(sell, skip);
        }
    }
}

// Approach 2: Memoization
class Stock4Memoization {

    public int maxProfit(int k, int[] prices) {

        int n = prices.length;
        Integer[][][] dp = new Integer[n][2][k + 1];

        return helper(0, 1, k, prices, dp);
    }

    private int helper(int index, int canBuy, int cap, int[] prices, Integer[][][] dp) {

        if (index == prices.length || cap == 0) {
            return 0;
        }

        if (dp[index][canBuy][cap] != null) {
            return dp[index][canBuy][cap];
        }

        int profit = 0;

        if (canBuy == 1) {
            int buy = -prices[index] + helper(index + 1, 0, cap, prices, dp);
            int skip = 0 + helper(index + 1, 1, cap, prices, dp);
            profit = Math.max(buy, skip);
        } else {
            int sell = prices[index] + helper(index + 1, 1, cap - 1, prices, dp);
            int skip = 0 + helper(index + 1, 0, cap, prices, dp);
            profit = Math.max(sell, skip);
        }

        return dp[index][canBuy][cap] = profit;
    }
}

// Approach 3: Tabulation
class Stock4Tabulation {

    public int maxProfit(int k, int[] prices) {

        int n = prices.length;
        int[][][] dp = new int[n + 1][2][k + 1];

        // Base cases are already 0 in Java default initialization

        for (int index = n - 1; index >= 0; index--) {
            for (int canBuy = 0; canBuy <= 1; canBuy++) {
                for (int cap = 1; cap <= k; cap++) {

                    if (canBuy == 1) {
                        int buy = -prices[index] + dp[index + 1][0][cap];
                        int skip = 0 + dp[index + 1][1][cap];
                        dp[index][canBuy][cap] = Math.max(buy, skip);
                    } else {
                        int sell = prices[index] + dp[index + 1][1][cap - 1];
                        int skip = 0 + dp[index + 1][0][cap];
                        dp[index][canBuy][cap] = Math.max(sell, skip);
                    }
                }
            }
        }

        return dp[0][1][k];
    }
}

// Approach 4: Space Optimization
class Stock4Optimized {

    public int maxProfit(int k, int[] prices) {

        int n = prices.length;
        int[][] ahead = new int[2][k + 1];
        int[][] curr = new int[2][k + 1];

        for (int index = n - 1; index >= 0; index--) {
            for (int canBuy = 0; canBuy <= 1; canBuy++) {
                for (int cap = 1; cap <= k; cap++) {

                    if (canBuy == 1) {
                        int buy = -prices[index] + ahead[0][cap];
                        int skip = 0 + ahead[1][cap];
                        curr[canBuy][cap] = Math.max(buy, skip);
                    } else {
                        int sell = prices[index] + ahead[1][cap - 1];
                        int skip = 0 + ahead[0][cap];
                        curr[canBuy][cap] = Math.max(sell, skip);
                    }
                }
            }
            // Copy curr states into ahead for the next iteration
            for (int canBuy = 0; canBuy <= 1; canBuy++) {
                System.arraycopy(curr[canBuy], 0, ahead[canBuy], 0, k + 1);
            }
        }

        return ahead[1][k];
    }
}

// Driver Class
public class Main {

    public static void main(String[] args) {

        int k = 2;
        int[] prices = {3, 2, 6, 5, 0, 3};

        Stock4Recursion rec = new Stock4Recursion();
        System.out.println("Recursion Output: " + rec.maxProfit(k, prices)); // 7

        Stock4Memoization memo = new Stock4Memoization();
        System.out.println("Memoization Output: " + memo.maxProfit(k, prices)); // 7

        Stock4Tabulation tab = new Stock4Tabulation();
        System.out.println("Tabulation Output: " + tab.maxProfit(k, prices)); // 7

        Stock4Optimized opt = new Stock4Optimized();
        System.out.println("Space Optimized Output: " + opt.maxProfit(k, prices)); // 7
    }
}
