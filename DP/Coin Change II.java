/*
Problem: Coin Change II

You are given an integer array coins representing coins of
different denominations and an integer amount.

Return the number of combinations that make up that amount.

You may use each coin an unlimited number of times.

If no combination exists, return 0.

Source: LeetCode Problem #518
https://leetcode.com/problems/coin-change-ii/

Approaches:

1. Recursion
   - Idea:
     - For each coin:
         * Take the coin (stay at same index)
         * Skip the coin (move to next index)
     - Sum both possibilities.

   - Time Complexity: O(2^n)
   - Space Complexity: O(amount)

2. Memoization (Top-Down DP)
   - Idea:
     - Store states:
           dp[index][amount]
     - Avoid recomputation of overlapping subproblems.

   - Time Complexity: O(n * amount)
   - Space Complexity: O(n * amount)

3. Tabulation (Bottom-Up DP)
   - Idea:
     - dp[i][j] represents number of ways
       to form amount j using first i coins.
     - For every coin:
         * Take
         * Not Take

   - Time Complexity: O(n * amount)
   - Space Complexity: O(n * amount)

4. Space Optimization
   - Idea:
     - Since current row depends on current row
       and previous row, we can use a 1D DP array.
     - dp[j] stores number of ways to make amount j.

   - Time Complexity: O(n * amount)
   - Space Complexity: O(amount)
*/

import java.util.*;

// Approach 1: Recursion
class CoinChange2Recursion {

    public int change(int amount, int[] coins) {

        return helper(coins, 0, amount);
    }

    private int helper(
            int[] coins,
            int index,
            int amount) {

        if (amount == 0) {
            return 1;
        }

        if (index == coins.length) {
            return 0;
        }

        int take = 0;

        if (coins[index] <= amount) {

            take = helper(
                    coins,
                    index,
                    amount - coins[index]
            );
        }

        int notTake = helper(
                coins,
                index + 1,
                amount
        );

        return take + notTake;
    }
}

// Approach 2: Memoization
class CoinChange2Memoization {

    public int change(int amount, int[] coins) {

        Integer[][] dp =
                new Integer[coins.length][amount + 1];

        return helper(
                coins,
                0,
                amount,
                dp
        );
    }

    private int helper(
            int[] coins,
            int index,
            int amount,
            Integer[][] dp) {

        if (amount == 0) {
            return 1;
        }

        if (index == coins.length) {
            return 0;
        }

        if (dp[index][amount] != null) {
            return dp[index][amount];
        }

        int take = 0;

        if (coins[index] <= amount) {

            take = helper(
                    coins,
                    index,
                    amount - coins[index],
                    dp
            );
        }

        int notTake = helper(
                coins,
                index + 1,
                amount,
                dp
        );

        return dp[index][amount]
                = take + notTake;
    }
}

// Approach 3: Tabulation
class CoinChange2Tabulation {

    public int change(int amount, int[] coins) {

        int n = coins.length;

        int[][] dp =
                new int[n + 1][amount + 1];

        for (int i = 0; i <= n; i++) {
            dp[i][0] = 1;
        }

        for (int i = 1; i <= n; i++) {

            for (int currAmount = 1;
                 currAmount <= amount;
                 currAmount++) {

                int notTake =
                        dp[i - 1][currAmount];

                int take = 0;

                if (coins[i - 1] <= currAmount) {

                    take =
                            dp[i][currAmount - coins[i - 1]];
                }

                dp[i][currAmount] =
                        take + notTake;
            }
        }

        return dp[n][amount];
    }
}

// Approach 4: Space Optimization
class CoinChange2Optimized {

    public int change(int amount, int[] coins) {

        int[] dp =
                new int[amount + 1];

        dp[0] = 1;

        for (int coin : coins) {

            for (int currAmount = coin;
                 currAmount <= amount;
                 currAmount++) {

                dp[currAmount] +=
                        dp[currAmount - coin];
            }
        }

        return dp[amount];
    }
}

// Driver Class
public class Main {

    public static void main(String[] args) {

        int amount = 5;
        int[] coins = {1, 2, 5};

        CoinChange2Recursion rec =
                new CoinChange2Recursion();

        System.out.println(
                "Recursion Output: "
                + rec.change(amount, coins)
        ); // 4

        CoinChange2Memoization memo =
                new CoinChange2Memoization();

        System.out.println(
                "Memoization Output: "
                + memo.change(amount, coins)
        ); // 4

        CoinChange2Tabulation tab =
                new CoinChange2Tabulation();

        System.out.println(
                "Tabulation Output: "
                + tab.change(amount, coins)
        ); // 4

        CoinChange2Optimized opt =
                new CoinChange2Optimized();

        System.out.println(
                "Space Optimized Output: "
                + opt.change(amount, coins)
        ); // 4
    }
}
