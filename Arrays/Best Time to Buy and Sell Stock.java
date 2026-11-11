/*
Problem: Best Time to Buy and Sell Stock

You are given an array prices where prices[i]
is the price of a given stock on the ith day.

You want to maximize your profit by choosing:
- One day to buy
- One future day to sell

Return the maximum profit you can achieve.

If no profit is possible, return 0.

Source: LeetCode Problem #121
https://leetcode.com/problems/best-time-to-buy-and-sell-stock/

Approaches:

1. Brute Force
   - Idea:
     - Check every possible buy and sell pair.
     - Calculate profit for each pair.
     - Keep track of maximum profit.

   - Time Complexity: O(n²)
   - Space Complexity: O(1)

2. Better
   - Idea:
     - For each day, find the maximum selling price
       in future days.
     - Calculate profit.
     - Keep the maximum profit.

   - Time Complexity: O(n²)
   - Space Complexity: O(1)

3. Optimized
   - Idea:
     - Maintain minimum price seen so far.
     - For each day:
         Profit = Current Price - Minimum Price
     - Update maximum profit.

   - Time Complexity: O(n)
   - Space Complexity: O(1)

   - Most Interview Preferred ✅
*/

import java.util.*;

// Approach 1: Brute Force
class BestTimeToBuySellBrute {

    public int maxProfit(int[] prices) {

        int maxProfit = 0;

        for (int i = 0; i < prices.length; i++) {

            for (int j = i + 1; j < prices.length; j++) {

                maxProfit = Math.max(maxProfit, prices[j] - prices[i]);
            }
        }

        return maxProfit;
    }
}

// Approach 2: Better
class BestTimeToBuySellBetter {

    public int maxProfit(int[] prices) {

        int maxProfit = 0;

        for (int i = 0; i < prices.length; i++) {

            int maxFuturePrice = prices[i];

            for (int j = i + 1; j < prices.length; j++) {
                maxFuturePrice = Math.max(maxFuturePrice, prices[j]);
            }

            maxProfit = Math.max(maxProfit, maxFuturePrice - prices[i]);
        }

        return maxProfit;
    }
}

// Approach 3: Optimized
class BestTimeToBuySellOptimized {

    public int maxProfit(int[] prices) {

        int minPrice = Integer.MAX_VALUE;
        int maxProfit = 0;

        for (int price : prices) {

            minPrice = Math.min(minPrice, price);

            maxProfit = Math.max(maxProfit, price - minPrice);
        }

        return maxProfit;
    }
}

// Driver Class
public class Main {

    public static void main(String[] args) {

        int[] prices = {7, 1, 5, 3, 6, 4};

        BestTimeToBuySellBrute brute = new BestTimeToBuySellBrute();
        System.out.println("Brute Force Output: " + brute.maxProfit(prices)); // 5

        BestTimeToBuySellBetter better = new BestTimeToBuySellBetter();
        System.out.println("Better Output: " + better.maxProfit(prices)); // 5

        BestTimeToBuySellOptimized optimized = new BestTimeToBuySellOptimized();
        System.out.println("Optimized Output: " + optimized.maxProfit(prices)); // 5
    }
}
