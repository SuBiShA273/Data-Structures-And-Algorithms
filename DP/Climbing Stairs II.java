/*
Problem: Climbing Stairs II

You are climbing a staircase with n + 1 steps numbered
from 0 to n.

You are given a 1-indexed array costs where costs[i]
is the cost of step i.

From step i, you can jump only to:
- i + 1
- i + 2
- i + 3

Cost of jumping from i to j:

costs[j] + (j - i)^2

Return the minimum total cost to reach step n.

Source: LeetCode Problem #3693
https://leetcode.com/problems/climbing-stairs-ii/

Approaches:

1. Recursion
   - Idea:
     - From each step, try jumps of length 1, 2, and 3.
     - Take the minimum possible cost.

   - Time Complexity: O(3^n)
   - Space Complexity: O(n)

2. Memoization
   - Idea:
     - Store minimum cost for each step.
     - Avoid recalculating subproblems.

   - Time Complexity: O(n)
   - Space Complexity: O(n)

3. Tabulation
   - Idea:
     - dp[i] = minimum cost to reach step i.
     - Check transitions from:
           i-1, i-2, i-3

   - Time Complexity: O(n)
   - Space Complexity: O(n)

4. Space Optimization
   - Idea:
     - State depends only on previous 3 values.
     - Store only last three DP states.

   - Time Complexity: O(n)
   - Space Complexity: O(1)
*/

import java.util.*;

// Approach 1: Recursion
class ClimbingStairs2Recursion {

    public int minCost(int n, int[] costs) {
        return helper(n, costs);
    }

    private int helper(int step, int[] costs) {

        if (step == 0) {
            return 0;
        }

        int ans = Integer.MAX_VALUE;

        for (int jump = 1; jump <= 3; jump++) {

            int prev = step - jump;

            if (prev >= 0) {
                ans = Math.min(ans, helper(prev, costs) + costs[step - 1] + jump * jump);
            }
        }

        return ans;
    }
}

// Approach 2: Memoization
class ClimbingStairs2Memoization {

    public int minCost(int n, int[] costs) {

        Integer[] dp = new Integer[n + 1];

        return helper(n, costs, dp);
    }

    private int helper(int step, int[] costs, Integer[] dp) {

        if (step == 0) {
            return 0;
        }

        if (dp[step] != null) {
            return dp[step];
        }

        int ans = Integer.MAX_VALUE;

        for (int jump = 1; jump <= 3; jump++) {

            int prev = step - jump;

            if (prev >= 0) {
                ans = Math.min(ans, helper(prev, costs, dp) + costs[step - 1] + jump * jump);
            }
        }

        return dp[step] = ans;
    }
}

// Approach 3: Tabulation
class ClimbingStairs2Tabulation {

    public int minCost(int n, int[] costs) {

        int[] dp = new int[n + 1];

        Arrays.fill(dp, Integer.MAX_VALUE);

        dp[0] = 0;

        for (int i = 1; i <= n; i++) {

            for (int jump = 1; jump <= 3; jump++) {

                int prev = i - jump;

                if (prev >= 0) {
                    dp[i] = Math.min(dp[i], dp[prev] + costs[i - 1] + jump * jump);
                }
            }
        }

        return dp[n];
    }
}

// Approach 4: Space Optimization
class ClimbingStairs2Optimized {

    public int minCost(int n, int[] costs) {

        if (n == 1) {
            return costs[0] + 1;
        }

        int dp0 = 0;
        int dp1 = costs[0] + 1;

        if (n == 1) {
            return dp1;
        }

        int dp2 = Math.min(dp1 + costs[1] + 1, dp0 + costs[1] + 4);

        if (n == 2) {
            return dp2;
        }

        for (int i = 3; i <= n; i++) {

            int curr = Math.min(dp2 + costs[i - 1] + 1,
                        Math.min(dp1 + costs[i - 1] + 4,
                                 dp0 + costs[i - 1] + 9));

            dp0 = dp1;
            dp1 = dp2;
            dp2 = curr;
        }

        return dp2;
    }
}

// Driver Class
public class Main {

    public static void main(String[] args) {

        int n = 4;
        int[] costs = {1, 2, 3, 4};

        ClimbingStairs2Recursion rec = new ClimbingStairs2Recursion();
        System.out.println("Recursion Output: " + rec.minCost(n, costs)); // 13

        ClimbingStairs2Memoization memo = new ClimbingStairs2Memoization();
        System.out.println("Memoization Output: " + memo.minCost(n, costs)); // 13

        ClimbingStairs2Tabulation tab = new ClimbingStairs2Tabulation();
        System.out.println("Tabulation Output: " + tab.minCost(n, costs)); // 13

        ClimbingStairs2Optimized opt = new ClimbingStairs2Optimized();
        System.out.println("Space Optimized Output: " + opt.minCost(n, costs)); // 13
    }
}
