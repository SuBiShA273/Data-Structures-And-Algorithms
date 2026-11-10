/*
Problem: Maximum Subarray

Given an integer array nums.

Find the contiguous subarray with the largest sum
and return its sum.

Source: LeetCode Problem #53
https://leetcode.com/problems/maximum-subarray/

Approaches:

1. Recursion
   - Idea:
     - At every index, either:
         * Extend current subarray
         * Start a new subarray
     - Compute maximum sum recursively.

   - Time Complexity: O(2^n)
   - Space Complexity: O(n)

2. Memoization
   - Idea:
     - Store maximum subarray sum ending at each index.
     - Avoid recalculating subproblems.

   - Time Complexity: O(n)
   - Space Complexity: O(n)

3. Tabulation
   - Idea:
     - dp[i] = Maximum subarray sum ending at i.
     - Either:
           Start new subarray from nums[i]
       or
           Extend previous subarray.

   - Time Complexity: O(n)
   - Space Complexity: O(n)

4. Space Optimization (Kadane's Algorithm)
   - Idea:
     - Maintain current sum and maximum sum.
     - If current sum becomes negative,
       start a new subarray.
     - Most Interview Preferred ✅

   - Time Complexity: O(n)
   - Space Complexity: O(1)
*/

import java.util.*;

// Approach 1: Recursion
class MaximumSubarrayRecursion {

    int answer = Integer.MIN_VALUE;

    public int maxSubArray(int[] nums) {

        helper(nums, nums.length - 1);

        return answer;
    }

    private int helper(int[] nums, int index) {

        if (index == 0) {
            answer = Math.max(answer, nums[0]);
            return nums[0];
        }

        int prev = helper(nums, index - 1);

        int curr = Math.max(nums[index], nums[index] + prev);

        answer = Math.max(answer, curr);

        return curr;
    }
}

// Approach 2: Memoization
class MaximumSubarrayMemoization {

    int answer = Integer.MIN_VALUE;

    public int maxSubArray(int[] nums) {

        Integer[] dp = new Integer[nums.length];

        helper(nums, nums.length - 1, dp);

        return answer;
    }

    private int helper(int[] nums, int index, Integer[] dp) {

        if (index == 0) {
            answer = Math.max(answer, nums[0]);
            return nums[0];
        }

        if (dp[index] != null) {
            return dp[index];
        }

        int prev = helper(nums, index - 1, dp);

        int curr = Math.max(nums[index], nums[index] + prev);

        answer = Math.max(answer, curr);

        return dp[index] = curr;
    }
}

// Approach 3: Tabulation
class MaximumSubarrayTabulation {

    public int maxSubArray(int[] nums) {

        int n = nums.length;

        int[] dp = new int[n];

        dp[0] = nums[0];

        int answer = nums[0];

        for (int i = 1; i < n; i++) {

            dp[i] = Math.max(nums[i], nums[i] + dp[i - 1]);

            answer = Math.max(answer, dp[i]);
        }

        return answer;
    }
}

// Approach 4: Space Optimization (Kadane's Algorithm)
class MaximumSubarrayOptimized {

    public int maxSubArray(int[] nums) {

        int currentSum = nums[0];
        int maxSum = nums[0];

        for (int i = 1; i < nums.length; i++) {

            currentSum = Math.max(nums[i], currentSum + nums[i]);

            maxSum = Math.max(maxSum, currentSum);
        }

        return maxSum;
    }
}

// Driver Class
public class Main {

    public static void main(String[] args) {

        int[] nums = {-2, 1, -3, 4, -1, 2, 1, -5, 4};

        MaximumSubarrayRecursion rec = new MaximumSubarrayRecursion();
        System.out.println("Recursion Output: " + rec.maxSubArray(nums)); // 6

        MaximumSubarrayMemoization memo = new MaximumSubarrayMemoization();
        System.out.println("Memoization Output: " + memo.maxSubArray(nums)); // 6

        MaximumSubarrayTabulation tab = new MaximumSubarrayTabulation();
        System.out.println("Tabulation Output: " + tab.maxSubArray(nums)); // 6

        MaximumSubarrayOptimized opt = new MaximumSubarrayOptimized();
        System.out.println("Space Optimized Output: " + opt.maxSubArray(nums)); // 6
    }
}
