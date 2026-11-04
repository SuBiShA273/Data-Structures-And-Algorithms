/*
Problem: Partition Equal Subset Sum

Given an integer array nums.

Return true if you can partition the array into two subsets
such that the sum of elements in both subsets is equal.

Source: LeetCode Problem #416
https://leetcode.com/problems/partition-equal-subset-sum/

Approaches:

1. Recursion
   - Idea:
     - Find total sum of array.
     - If sum is odd, partition is impossible.
     - Target = sum / 2.
     - For each element:
         * Take it into subset.
         * Do not take it.
     - Check if target sum can be achieved.

   - Time Complexity: O(2^n)
   - Space Complexity: O(n)

2. Memoization (Top-Down DP)
   - Idea:
     - Store states:
           dp[index][target]
     - Avoid recomputation of overlapping subproblems.

   - Time Complexity: O(n * target)
   - Space Complexity: O(n * target)

3. Tabulation (Bottom-Up DP)
   - Idea:
     - dp[i][j] = true if sum j can be formed
       using first i elements.
     - Build answers bottom-up.

   - Time Complexity: O(n * target)
   - Space Complexity: O(n * target)

4. Space Optimization
   - Idea:
     - Current row depends only on previous row.
     - Use a 1D DP array.

   - Time Complexity: O(n * target)
   - Space Complexity: O(target)
*/

import java.util.*;

// Approach 1: Recursion
class PartitionRecursion {

    public boolean canPartition(int[] nums) {

        int sum = 0;

        for (int num : nums) {
            sum += num;
        }

        if (sum % 2 != 0) {
            return false;
        }

        return helper(nums, 0, sum / 2);
    }

    private boolean helper(
            int[] nums,
            int index,
            int target) {

        if (target == 0) {
            return true;
        }

        if (index == nums.length ||
            target < 0) {
            return false;
        }

        return helper(
                    nums,
                    index + 1,
                    target - nums[index]
               )
               ||
               helper(
                    nums,
                    index + 1,
                    target
               );
    }
}

// Approach 2: Memoization
class PartitionMemoization {

    public boolean canPartition(int[] nums) {

        int sum = 0;

        for (int num : nums) {
            sum += num;
        }

        if (sum % 2 != 0) {
            return false;
        }

        Boolean[][] dp =
                new Boolean[nums.length][sum / 2 + 1];

        return helper(
                nums,
                0,
                sum / 2,
                dp
        );
    }

    private boolean helper(
            int[] nums,
            int index,
            int target,
            Boolean[][] dp) {

        if (target == 0) {
            return true;
        }

        if (index == nums.length ||
            target < 0) {
            return false;
        }

        if (dp[index][target] != null) {
            return dp[index][target];
        }

        boolean take =
                helper(
                        nums,
                        index + 1,
                        target - nums[index],
                        dp
                );

        boolean notTake =
                helper(
                        nums,
                        index + 1,
                        target,
                        dp
                );

        return dp[index][target]
                = take || notTake;
    }
}

// Approach 3: Tabulation
class PartitionTabulation {

    public boolean canPartition(int[] nums) {

        int sum = 0;

        for (int num : nums) {
            sum += num;
        }

        if (sum % 2 != 0) {
            return false;
        }

        int target = sum / 2;
        int n = nums.length;

        boolean[][] dp =
                new boolean[n][target + 1];

        for (int i = 0; i < n; i++) {
            dp[i][0] = true;
        }

        if (nums[0] <= target) {
            dp[0][nums[0]] = true;
        }

        for (int i = 1; i < n; i++) {

            for (int j = 1; j <= target; j++) {

                boolean notTake =
                        dp[i - 1][j];

                boolean take = false;

                if (nums[i] <= j) {
                    take =
                        dp[i - 1][j - nums[i]];
                }

                dp[i][j] =
                        take || notTake;
            }
        }

        return dp[n - 1][target];
    }
}

// Approach 4: Space Optimization
class PartitionOptimized {

    public boolean canPartition(int[] nums) {

        int sum = 0;

        for (int num : nums) {
            sum += num;
        }

        if (sum % 2 != 0) {
            return false;
        }

        int target = sum / 2;

        boolean[] dp =
                new boolean[target + 1];

        dp[0] = true;

        for (int num : nums) {

            for (int j = target;
                 j >= num;
                 j--) {

                dp[j] =
                        dp[j]
                        || dp[j - num];
            }
        }

        return dp[target];
    }
}

// Driver Class
public class Main {

    public static void main(String[] args) {

        int[] nums = {1, 5, 11, 5};

        PartitionRecursion recursion =
                new PartitionRecursion();

        System.out.println(
                "Recursion Output: "
                + recursion.canPartition(nums)
        ); // true

        PartitionMemoization memo =
                new PartitionMemoization();

        System.out.println(
                "Memoization Output: "
                + memo.canPartition(nums)
        ); // true

        PartitionTabulation tab =
                new PartitionTabulation();

        System.out.println(
                "Tabulation Output: "
                + tab.canPartition(nums)
        ); // true

        PartitionOptimized opt =
                new PartitionOptimized();

        System.out.println(
                "Space Optimized Output: "
                + opt.canPartition(nums)
        ); // true
    }
}
