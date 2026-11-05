/*
Problem: Target Sum

You are given an integer array nums and an integer target.

You want to build an expression by adding either '+' or '-'
before each number and then concatenate all the integers.

Return the number of different expressions that evaluate
to target.

Source: LeetCode Problem #494
https://leetcode.com/problems/target-sum/

Approaches:

1. Recursion
   - Idea:
     - For every element, we have two choices:
         * Add current number
         * Subtract current number
     - Recursively explore all possibilities.
     - Count ways that result in target.

   - Time Complexity: O(2^n)
   - Space Complexity: O(n)

2. Memoization (Top-Down DP)
   - Idea:
     - State:
           (index, currentSum)
     - Store already computed states.
     - Avoid repeated calculations.

   - Time Complexity: O(n * totalSum)
   - Space Complexity: O(n * totalSum)

3. Tabulation (Subset Sum Transformation)
   - Idea:
     - Let:
           PositiveSum = P
           NegativeSum = N

       P - N = target
       P + N = totalSum

     - Adding both:

           2P = totalSum + target

           P = (totalSum + target) / 2

     - Problem becomes:
       Count subsets whose sum equals P.

   - Time Complexity: O(n * P)
   - Space Complexity: O(n * P)

4. Space Optimization
   - Idea:
     - Tabulation depends only on previous row.
     - Use 1D DP array.
     - dp[s] = number of ways to make sum s.

   - Time Complexity: O(n * P)
   - Space Complexity: O(P)
*/

import java.util.*;

// Approach 1: Recursion
class TargetSumRecursion {

    public int findTargetSumWays(
            int[] nums,
            int target) {

        return helper(nums, 0, 0, target);
    }

    private int helper(
            int[] nums,
            int index,
            int sum,
            int target) {

        if (index == nums.length) {
            return sum == target ? 1 : 0;
        }

        int add =
                helper(
                        nums,
                        index + 1,
                        sum + nums[index],
                        target
                );

        int subtract =
                helper(
                        nums,
                        index + 1,
                        sum - nums[index],
                        target
                );

        return add + subtract;
    }
}

// Approach 2: Memoization
class TargetSumMemoization {

    private Map<String, Integer> memo =
            new HashMap<>();

    public int findTargetSumWays(
            int[] nums,
            int target) {

        return helper(
                nums,
                0,
                0,
                target
        );
    }

    private int helper(
            int[] nums,
            int index,
            int sum,
            int target) {

        if (index == nums.length) {
            return sum == target ? 1 : 0;
        }

        String key = index + "," + sum;

        if (memo.containsKey(key)) {
            return memo.get(key);
        }

        int add =
                helper(
                        nums,
                        index + 1,
                        sum + nums[index],
                        target
                );

        int subtract =
                helper(
                        nums,
                        index + 1,
                        sum - nums[index],
                        target
                );

        memo.put(key, add + subtract);

        return memo.get(key);
    }
}

// Approach 3: Tabulation
class TargetSumTabulation {

    public int findTargetSumWays(
            int[] nums,
            int target) {

        int totalSum = 0;

        for (int num : nums) {
            totalSum += num;
        }

        if ((totalSum + target) % 2 != 0 ||
            Math.abs(target) > totalSum) {
            return 0;
        }

        int subsetSum =
                (totalSum + target) / 2;

        int n = nums.length;

        int[][] dp =
                new int[n + 1][subsetSum + 1];

        dp[0][0] = 1;

        for (int i = 1; i <= n; i++) {

            for (int sum = 0;
                 sum <= subsetSum;
                 sum++) {

                dp[i][sum] =
                        dp[i - 1][sum];

                if (nums[i - 1] <= sum) {

                    dp[i][sum] +=
                            dp[i - 1][
                                sum - nums[i - 1]
                            ];
                }
            }
        }

        return dp[n][subsetSum];
    }
}

// Approach 4: Space Optimization
class TargetSumOptimized {

    public int findTargetSumWays(
            int[] nums,
            int target) {

        int totalSum = 0;

        for (int num : nums) {
            totalSum += num;
        }

        if ((totalSum + target) % 2 != 0 ||
            Math.abs(target) > totalSum) {
            return 0;
        }

        int subsetSum =
                (totalSum + target) / 2;

        int[] dp =
                new int[subsetSum + 1];

        dp[0] = 1;

        for (int num : nums) {

            for (int sum = subsetSum;
                 sum >= num;
                 sum--) {

                dp[sum] +=
                        dp[sum - num];
            }
        }

        return dp[subsetSum];
    }
}

// Driver Class
public class Main {

    public static void main(String[] args) {

        int[] nums = {1, 1, 1, 1, 1};
        int target = 3;

        TargetSumRecursion rec =
                new TargetSumRecursion();

        System.out.println(
                "Recursion Output: "
                + rec.findTargetSumWays(
                        nums, target)
        ); // 5

        TargetSumMemoization memo =
                new TargetSumMemoization();

        System.out.println(
                "Memoization Output: "
                + memo.findTargetSumWays(
                        nums, target)
        ); // 5

        TargetSumTabulation tab =
                new TargetSumTabulation();

        System.out.println(
                "Tabulation Output: "
                + tab.findTargetSumWays(
                        nums, target)
        ); // 5

        TargetSumOptimized opt =
                new TargetSumOptimized();

        System.out.println(
                "Space Optimized Output: "
                + opt.findTargetSumWays(
                        nums, target)
        ); // 5
    }
}
