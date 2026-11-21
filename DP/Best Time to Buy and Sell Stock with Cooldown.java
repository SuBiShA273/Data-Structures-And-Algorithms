/*
Problem: Best Time to Buy and Sell Stock with Cooldown

You are given an array prices where prices[i] is the price of a given stock
on the ith day.

Find the maximum profit you can achieve. You may complete as many transactions
as you like (i.e., buy one and sell one share of the stock multiple times) with
the following restrictions:
- After you sell your stock, you cannot buy stock on the next day
  (i.e., cooldown one day).

Note: You may not engage in multiple transactions simultaneously
(i.e., you must sell the stock before you buy again).

Source: LeetCode Problem #309
https://leetcode.com

Approaches:

1. Recursion
   - Idea:
     - At each day, we track two states: day index and whether we can buy (1 or 0).
     - If we sell on day index, we must jump to index + 2 due to the 1-day cooldown.
     - Explore both choices (buy/skip or sell/skip) and take the maximum profit.

   - Time Complexity: O(2^n)
   - Space Complexity: O(n)

2. Memoization
   - Idea:
     - Store results in a 2D DP table: dp[index][canBuy].
     - Avoid recalculating overlapping subproblems.

   - Time Complexity: O(n * 2) -> O(n)
   - Space Complexity: O(n * 2) + O(n) -> O(n)

3. Tabulation
   - Idea:
     - Build the 2D DP table bottom-up from day n - 1 down to 0.
     - Use size n + 2 to safely handle index + 2 jumps on the last day.
     - Base cases: when index >= n, profit is 0.

   - Time Complexity: O(n * 2) -> O(n)
   - Space Complexity: O(n * 2) -> O(n)

4. Space Optimization
   - Idea:
     - Current day's states depend only on 1 day ahead (ahead1) and 2 days ahead (ahead2).
     - Replace the 2D table with three small 1D arrays of size 2 (curr, ahead1, ahead2).

   - Time Complexity: O(n)
   - Space Complexity: O(1)
*/

import java.util.*;

// Approach 1: Recursion
class StockCooldownRecursion {

    public int maxProfit(int[] prices) {
        return helper(0, 1, prices);
    }

    private int helper(int index, int canBuy, int[] prices) {

        if (index >= prices.length) {
            return 0;
        }

        if (canBuy == 1) {
            int buy = -prices[index] + helper(index + 1, 0, prices);
            int skip = 0 + helper(index + 1, 1, prices);
            return Math.max(buy, skip);
        } else {
            int sell = prices[index] + helper(index + 2, 1, prices);
            int skip = 0 + helper(index + 1, 0, prices);
            return Math.max(sell, skip);
        }
    }
}

// Approach 2: Memoization
class StockCooldownMemoization {

    public int maxProfit(int[] prices) {

        int n = prices.length;
        Integer[][] dp = new Integer[n][2];

        return helper(0, 1, prices, dp);
    }

    private int helper(int index, int canBuy, int[] prices, Integer[][] dp) {

        if (index >= prices.length) {
            return 0;
        }

        if (dp[index][canBuy] != null) {
            return dp[index][canBuy];
        }

        int profit = 0;

        if (canBuy == 1) {
            int buy = -prices[index] + helper(index + 1, 0, prices, dp);
            int skip = 0 + helper(index + 1, 1, prices, dp);
            profit = Math.max(buy, skip);
        } else {
            int sell = prices[index] + helper(index + 2, 1, prices, dp);
            int skip = 0 + helper(index + 1, 0, prices, dp);
            profit = Math.max(sell, skip);
        }

        return dp[index][canBuy] = profit;
    }
}

// Approach 3: Tabulation
class StockCooldownTabulation {

    public int maxProfit(int[] prices) {

        int n = prices.length;
        int[][] dp = new int[n + 2][2];

        // Base cases (dp[n] and dp[n + 1]) are already 0 in Java default initialization

        for (int index = n - 1; index >= 0; index--) {
            for (int canBuy = 0; canBuy <= 1; canBuy++) {

                if (canBuy == 1) {
                    int buy = -prices[index] + dp[index + 1][0];
                    int skip = 0 + dp[index + 1][1];
                    dp[index][canBuy] = Math.max(buy, skip);
                } else {
                    int sell = prices[index] + dp[index + 2][1];
                    int skip = 0 + dp[index + 1][0];
                    dp[index][canBuy] = Math.max(sell, skip);
                }
            }
        }

        return dp[0][1];
    }
}

// Approach 4: Space Optimization
class StockCooldownOptimized {

    public int maxProfit(int[] prices) {

        int n = prices.length;
        int[] ahead2 = new int[2]; // Represents day index + 2
        int[] ahead1 = new int[2]; // Represents day index + 1
        int[] curr = new int[2];   // Represents current day index

        for (int index = n - 1; index >= 0; index--) {

            // canBuy == 1
            curr[1] = Math.max(-prices[index] + ahead1[0], 0 + ahead1[1]);

            // canBuy == 0
            curr[0] = Math.max(prices[index] + ahead2[1], 0 + ahead1[0]);

            // Shift windows backward for the next iteration
            System.arraycopy(ahead1, 0, ahead2, 0, 2);
            System.arraycopy(curr, 0, ahead1, 0, 2);
        }

        return ahead1[1];
    }
}

// Driver Class
public class Main {

    public static void main(String[] args) {

        int[] prices = {1, 2, 3, 0, 2};

        StockCooldownRecursion rec = new StockCooldownRecursion();
        System.out.println("Recursion Output: " + rec.maxProfit(prices)); // 3

        StockCooldownMemoization memo = new StockCooldownMemoization();
        System.out.println("Memoization Output: " + memo.maxProfit(prices)); // 3

        StockCooldownTabulation tab = new StockCooldownTabulation();
        System.out.println("Tabulation Output: " + tab.maxProfit(prices)); // 3

        StockCooldownOptimized opt = new StockCooldownOptimized();
        System.out.println("Space Optimized Output: " + opt.maxProfit(prices)); // 3
    }
}
