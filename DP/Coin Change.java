/*
Problem: Coin Change

You are given an integer array coins representing coins of
different denominations and an integer amount.

Return the fewest number of coins needed to make up that amount.

If it is not possible to make up the amount,
return -1.

Source: LeetCode Problem #322
https://leetcode.com/problems/coin-change/

Approaches:

1. Recursion
   - Idea:
     - For every coin:
         * Pick the coin again (unlimited supply)
         * Try to form the remaining amount
     - Take the minimum number of coins among all choices.

   - Time Complexity: Exponential
   - Space Complexity: O(amount)

2. Memoization (Top-Down DP)
   - Idea:
     - Store minimum coins required for each amount.
     - Avoid recalculating the same subproblems.

   - Time Complexity: O(n * amount)
   - Space Complexity: O(amount)

3. Tabulation (Bottom-Up DP)
   - Idea:
     - dp[i] = minimum coins needed for amount i.
     - For each amount, try every coin.
     - Build the answer from smaller amounts.

   - Time Complexity: O(n * amount)
   - Space Complexity: O(amount)

4. Space Optimization
   - Idea:
     - Tabulation already uses a 1D DP array.
     - Further optimization is not possible.
     - Therefore, Tabulation itself is the Space Optimized solution.

   - Time Complexity: O(n * amount)
   - Space Complexity: O(amount)
*/

import java.util.*;

// Approach 1: Recursion
class CoinChangeRecursion {

    public int coinChange(int[] coins, int amount) {

        int ans = helper(coins, amount);

        return ans == Integer.MAX_VALUE
                ? -1
                : ans;
    }

    private int helper(
            int[] coins,
            int amount) {

        if (amount == 0) {
            return 0;
        }

        if (amount < 0) {
            return Integer.MAX_VALUE;
        }

        int minCoins = Integer.MAX_VALUE;

        for (int coin : coins) {

            int result =
                    helper(
                            coins,
                            amount - coin
                    );

            if (result != Integer.MAX_VALUE) {
                minCoins = Math.min(
                        minCoins,
                        1 + result
                );
            }
        }

        return minCoins;
    }
}

// Approach 2: Memoization
class CoinChangeMemoization {

    public int coinChange(
            int[] coins,
            int amount) {

        Integer[] dp =
                new Integer[amount + 1];

        int ans =
                helper(
                        coins,
                        amount,
                        dp
                );

        return ans == Integer.MAX_VALUE
                ? -1
                : ans;
    }

    private int helper(
            int[] coins,
            int amount,
            Integer[] dp) {

        if (amount == 0) {
            return 0;
        }

        if (amount < 0) {
            return Integer.MAX_VALUE;
        }

        if (dp[amount] != null) {
            return dp[amount];
        }

        int minCoins = Integer.MAX_VALUE;

        for (int coin : coins) {

            int result =
                    helper(
                            coins,
                            amount - coin,
                            dp
                    );

            if (result != Integer.MAX_VALUE) {

                minCoins = Math.min(
                        minCoins,
                        result + 1
                );
            }
        }

        return dp[amount] = minCoins;
    }
}

// Approach 3: Tabulation
class CoinChangeTabulation {

    public int coinChange(
            int[] coins,
            int amount) {

        int[] dp =
                new int[amount + 1];

        Arrays.fill(
                dp,
                amount + 1
        );

        dp[0] = 0;

        for (int currAmount = 1;
             currAmount <= amount;
             currAmount++) {

            for (int coin : coins) {

                if (coin <= currAmount) {

                    dp[currAmount] = Math.min(
                            dp[currAmount],
                            1 + dp[currAmount - coin]
                    );
                }
            }
        }

        return dp[amount] > amount
                ? -1
                : dp[amount];
    }
}

// Approach 4: Space Optimized
class CoinChangeOptimized {

    public int coinChange(
            int[] coins,
            int amount) {

        int[] dp =
                new int[amount + 1];

        Arrays.fill(
                dp,
                amount + 1
        );

        dp[0] = 0;

        for (int currAmount = 1;
             currAmount <= amount;
             currAmount++) {

            for (int coin : coins) {

                if (coin <= currAmount) {

                    dp[currAmount] = Math.min(
                            dp[currAmount],
                            1 + dp[currAmount - coin]
                    );
                }
            }
        }

        return dp[amount] > amount
                ? -1
                : dp[amount];
    }
}

// Driver Class
public class Main {

    public static void main(String[] args) {

        int[] coins = {1, 2, 5};
        int amount = 11;

        CoinChangeRecursion rec =
                new CoinChangeRecursion();

        System.out.println(
                "Recursion Output: "
                + rec.coinChange(coins, amount)
        ); // 3

        CoinChangeMemoization memo =
                new CoinChangeMemoization();

        System.out.println(
                "Memoization Output: "
                + memo.coinChange(coins, amount)
        ); // 3

        CoinChangeTabulation tab =
                new CoinChangeTabulation();

        System.out.println(
                "Tabulation Output: "
                + tab.coinChange(coins, amount)
        ); // 3

        CoinChangeOptimized opt =
                new CoinChangeOptimized();

        System.out.println(
                "Space Optimized Output: "
                + opt.coinChange(coins, amount)
        ); // 3
    }
}
