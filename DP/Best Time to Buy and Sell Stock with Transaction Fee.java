/*
Problem: Best Time to Buy and Sell Stock with Transaction Fee

You are given an array prices where prices[i] is the price of a given stock
on the ith day, and an integer fee representing a transaction fee.

Find the maximum profit you can achieve. You may complete as many transactions
as you like, but you need to pay the transaction fee for each transaction.

Note: You may not engage in multiple transactions simultaneously
(i.e., you must sell the stock before you buy again).
The transaction fee is only charged once for each stock purchase and sale.

Source: LeetCode Problem #714
https://leetcode.com

Approaches:

1. Recursion
   - Idea:
     - At each day, track two states: day index and whether we can buy (1 or 0).
     - Subtract the transaction fee either when buying or when selling (here, deducted on sell).
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
     - Base case: when index == n, profit is 0.

   - Time Complexity: O(n * 2) -> O(n)
   - Space Complexity: O(n * 2) -> O(n)

4. Space Optimization
   - Idea:
     - Current day's states depend only on the next day's states (ahead).
     - Replace the 2D table with two 1D arrays (ahead and curr) of size 2.

   - Time Complexity: O(n * 2) -> O(n)
   - Space Complexity: O(2) -> O(1)
*/

import java.util.*;

// Approach 1: Recursion
class StockFeeRecursion {

    public int maxProfit(int[] prices, int fee) {
        return helper(0, 1, prices, fee);
    }

    private int helper(int index, int canBuy, int[] prices, int fee) {

        if (index == prices.length) {
            return 0;
        }

        if (canBuy == 1) {
            int buy = -prices[index] + helper(index + 1, 0, prices, fee);
            int skip = 0 + helper(index + 1, 1, prices, fee);
            return Math.max(buy, skip);
        } else {
            int sell = prices[index] - fee + helper(index + 1, 1, prices, fee);
            int skip = 0 + helper(index + 1, 0, prices, fee);
            return Math.max(sell, skip);
        }
    }
}

// Approach 2: Memoization
class StockFeeMemoization {

    public int maxProfit(int[] prices, int fee) {

        int n = prices.length;
        Integer[][] dp = new Integer[n][2];

        return helper(0, 1, prices, fee, dp);
    }

    private int helper(int index, int canBuy, int[] prices, int fee, Integer[][] dp) {

        if (index == prices.length) {
            return 0;
        }

        if (dp[index][canBuy] != null) {
            return dp[index][canBuy];
        }

        int profit = 0;

        if (canBuy == 1) {
            int buy = -prices[index] + helper(index + 1, 0, prices, fee, dp);
            int skip = 0 + helper(index + 1, 1, prices, fee, dp);
            profit = Math.max(buy, skip);
        } else {
            int sell = prices[index] - fee + helper(index + 1, 1, prices, fee, dp);
            int skip = 0 + helper(index + 1, 0, prices, fee, dp);
            profit = Math.max(sell, skip);
        }

        return dp[index][canBuy] = profit;
    }
}

// Approach 3: Tabulation
class StockFeeTabulation {

    public int maxProfit(int[] prices, int fee) {

        int n = prices.length;
        int[][] dp = new int[n + 1][2];

        // Base case dp[n][0] = dp[n][1] = 0 is already set by Java default initialization

        for (int index = n - 1; index >= 0; index--) {
            for (int canBuy = 0; canBuy <= 1; canBuy++) {

                if (canBuy == 1) {
                    int buy = -prices[index] + dp[index + 1][0];
                    int skip = 0 + dp[index + 1][1];
                    dp[index][canBuy] = Math.max(buy, skip);
                } else {
                    int sell = prices[index] - fee + dp[index + 1][1];
                    int skip = 0 + dp[index + 1][0];
                    dp[index][canBuy] = Math.max(sell, skip);
                }
            }
        }

        return dp[0][1];
    }
}

// Approach 4: Space Optimization
class StockFeeOptimized {

    public int maxProfit(int[] prices, int fee) {

        int n = prices.length;
        int[] ahead = new int[2];
        int[] curr = new int[2];

        for (int index = n - 1; index >= 0; index--) {
            for (int canBuy = 0; canBuy <= 1; canBuy++) {

                if (canBuy == 1) {
                    int buy = -prices[index] + ahead[0];
                    int skip = 0 + ahead[1];
                    curr[canBuy] = Math.max(buy, skip);
                } else {
                    int sell = prices[index] - fee + ahead[1];
                    int skip = 0 + ahead[0];
                    curr[canBuy] = Math.max(sell, skip);
                }
            }
            // Copy curr states into ahead for the next iteration
            System.arraycopy(curr, 0, ahead, 0, 2);
        }

        return ahead[1];
    }
}

// Driver Class
public class Main {

    public static void main(String[] args) {

        int[] prices = {1, 3, 2, 8, 4, 9};
        int fee = 2;

        StockFeeRecursion rec = new StockFeeRecursion();
        System.out.println("Recursion Output: " + rec.maxProfit(prices, fee)); // 8

        StockFeeMemoization memo = new StockFeeMemoization();
        System.out.println("Memoization Output: " + memo.maxProfit(prices, fee)); // 8

        StockFeeTabulation tab = new StockFeeTabulation();
        System.out.println("Tabulation Output: " + tab.maxProfit(prices, fee)); // 8

        StockFeeOptimized opt = new StockFeeOptimized();
        System.out.println("Space Optimized Output: " + opt.maxProfit(prices, fee)); // 8
    }
}
