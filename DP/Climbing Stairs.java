/*
Problem: Climbing Stairs

You are climbing a staircase.

It takes n steps to reach the top.

Each time you can either climb:
- 1 step
- 2 steps

Return the number of distinct ways to reach the top.

Source: LeetCode Problem #70
https://leetcode.com/problems/climbing-stairs/

Approaches:

1. Brute Force (Recursion)
   - Idea:
     - From every step, we have two choices:
         * Take 1 step
         * Take 2 steps
     - Recursively explore all possibilities.
     - Many overlapping subproblems occur.

   - Time Complexity: O(2^n)
   - Space Complexity: O(n)
     (Recursion stack)

2. Optimized (Iterative DP - Space Optimized)
   - Idea:
     - Let dp[i] be the number of ways to reach step i.
     - Since:
           dp[i] = dp[i-1] + dp[i-2]
     - This is identical to Fibonacci.
     - Store only the last two values.

   - Time Complexity: O(n)
   - Space Complexity: O(1)
*/

import java.util.*;

// Approach 1: Brute Force (Recursion)
class ClimbingStairsBrute {

    public int climbStairs(int n) {

        if (n <= 2) {
            return n;
        }

        return climbStairs(n - 1)
                + climbStairs(n - 2);
    }
}

// Approach 2: Optimized (Iterative DP)
class ClimbingStairsOptimized {

    public int climbStairs(int n) {

        if (n <= 2) {
            return n;
        }

        int prev1 = 2;
        int prev2 = 1;

        for (int i = 3; i <= n; i++) {

            int cur = prev1 + prev2;

            prev2 = prev1;
            prev1 = cur;
        }

        return prev1;
    }
}

// Driver Class
public class Main {

    public static void main(String[] args) {

        ClimbingStairsBrute brute =
                new ClimbingStairsBrute();

        System.out.println(
                "Brute Force Output: "
                        + brute.climbStairs(5)
        ); // 8

        ClimbingStairsOptimized optimized =
                new ClimbingStairsOptimized();

        System.out.println(
                "Optimized Output: "
                        + optimized.climbStairs(5)
        ); // 8
    }
}
