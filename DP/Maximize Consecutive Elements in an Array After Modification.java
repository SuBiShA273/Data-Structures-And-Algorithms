/*
Problem: Maximize Consecutive Elements in an Array After Modification

You are given a 0-indexed array nums consisting of positive integers.
Initially, you can increase the value of any element in the array by at most 1.
After that, you need to select one or more elements from the final array such 
that those elements are consecutive when sorted in ascending order.

Return the maximum number of elements that you can select.

Source: LeetCode Problem #3041
https://leetcode.com

Approaches:

1. Recursion
   - Idea:
     - Sort the array first since order does not matter for consecutive sequences.
     - At each index i, given the previous picked value `prev` (0 means none picked yet),
       explore three choices: skip nums[i], keep nums[i], or increment nums[i] to nums[i] + 1.

   - Time Complexity: O(3^n) (or O(2^n) since at most two branches fire per state)
   - Space Complexity: O(n)

2. Memoization
   - Idea:
     - Store results in a 2D DP table: dp[index][prev].
     - Avoid recalculating overlapping subproblems.

   - Time Complexity: O(n * M) where M is max(nums)
   - Space Complexity: O(n * M) + O(n)

3. Tabulation
   - Idea:
     - Build the 2D DP table bottom-up from index n - 1 down to 0.
     - Base case: when index == n, max length is 0.

   - Time Complexity: O(n * M)
   - Space Complexity: O(n * M)

4. Space Optimization (Optimal)
   - Idea:
     - Current index states depend only on index + 1 (`ahead`).
     - Moreover, at index i with x = nums[i], only prev = 0, prev = x - 1, and prev = x
       ever change, allowing in-place O(1) updates per step on a single 1D array.

   - Time Complexity: O(n log n + n) -> O(n log n)
   - Space Complexity: O(M)
*/

import java.util.*;

// Approach 1: Recursion
class MaxConsecutiveRecursion {

    public int maxSelectedElements(int[] nums) {
        int[] arr = nums.clone();
        Arrays.sort(arr);
        return helper(0, 0, arr);
    }

    private int helper(int index, int prev, int[] nums) {
        if (index == nums.length) {
            return 0;
        }

        // 1. Skip current element
        int res = helper(index + 1, prev, nums);

        // 2. Keep nums[index] as is
        if (prev == 0 || prev + 1 == nums[index]) {
            res = Math.max(res, 1 + helper(index + 1, nums[index], nums));
        }

        // 3. Increment nums[index] -> nums[index] + 1
        if (prev == 0 || prev + 1 == nums[index] + 1) {
            res = Math.max(res, 1 + helper(index + 1, nums[index] + 1, nums));
        }

        return res;
    }
}

// Approach 2: Memoization
class MaxConsecutiveMemoization {

    public int maxSelectedElements(int[] nums) {
        int[] arr = nums.clone();
        Arrays.sort(arr);
        int n = arr.length;
        int maxVal = arr[n - 1] + 1;
        Integer[][] dp = new Integer[n][maxVal + 1];

        return helper(0, 0, arr, dp);
    }

    private int helper(int index, int prev, int[] nums, Integer[][] dp) {
        if (index == nums.length) {
            return 0;
        }

        if (dp[index][prev] != null) {
            return dp[index][prev];
        }

        // 1. Skip current element
        int res = helper(index + 1, prev, nums, dp);

        // 2. Keep nums[index] as is
        if (prev == 0 || prev + 1 == nums[index]) {
            res = Math.max(res, 1 + helper(index + 1, nums[index], nums, dp));
        }

        // 3. Increment nums[index] -> nums[index] + 1
        if (prev == 0 || prev + 1 == nums[index] + 1) {
            res = Math.max(res, 1 + helper(index + 1, nums[index] + 1, nums, dp));
        }

        return dp[index][prev] = res;
    }
}

// Approach 3: Tabulation
class MaxConsecutiveTabulation {

    public int maxSelectedElements(int[] nums) {
        int[] arr = nums.clone();
        Arrays.sort(arr);
        int n = arr.length;
        int maxVal = arr[n - 1] + 1;
        int[][] dp = new int[n + 1][maxVal + 2];

        // Base cases are already 0 in Java default initialization

        for (int i = n - 1; i >= 0; i--) {
            for (int prev = 0; prev <= maxVal; prev++) {
                // 1. Skip current element
                dp[i][prev] = dp[i + 1][prev];

                // 2. Keep arr[i] as is
                if (prev == 0 || prev + 1 == arr[i]) {
                    dp[i][prev] = Math.max(dp[i][prev], 1 + dp[i + 1][arr[i]]);
                }

                // 3. Increment arr[i] -> arr[i] + 1
                if (prev == 0 || prev + 1 == arr[i] + 1) {
                    dp[i][prev] = Math.max(dp[i][prev], 1 + dp[i + 1][arr[i] + 1]);
                }
            }
        }

        return dp[0][0];
    }
}

// Approach 4: Space Optimization (Optimal)
class MaxConsecutiveOptimized {

    public int maxSelectedElements(int[] nums) {
        int[] arr = nums.clone();
        Arrays.sort(arr);
        int n = arr.length;
        int[] dp = new int[arr[n - 1] + 2];

        for (int i = n - 1; i >= 0; i--) {
            int x = arr[i];

            // Update prev = 0 (starting a new sequence with x or x + 1)
            dp[0] = Math.max(dp[0], 1 + dp[x]);
            dp[0] = Math.max(dp[0], 1 + dp[x + 1]);

            // Update prev = x - 1 (must happen before dp[x] is modified)
            dp[x - 1] = Math.max(dp[x - 1], 1 + dp[x]);

            // Update prev = x
            dp[x] = Math.max(dp[x], 1 + dp[x + 1]);
        }

        return dp[0];
    }
}

// Driver Class
public class Main {

    public static void main(String[] args) {

        int[] nums = {2, 1, 5, 1, 1};

        MaxConsecutiveRecursion rec = new MaxConsecutiveRecursion();
        System.out.println("Recursion Output: " + rec.maxSelectedElements(nums)); // 3

        MaxConsecutiveMemoization memo = new MaxConsecutiveMemoization();
        System.out.println("Memoization Output: " + memo.maxSelectedElements(nums)); // 3

        MaxConsecutiveTabulation tab = new MaxConsecutiveTabulation();
        System.out.println("Tabulation Output: " + tab.maxSelectedElements(nums)); // 3

        MaxConsecutiveOptimized opt = new MaxConsecutiveOptimized();
        System.out.println("Space Optimized Output: " + opt.maxSelectedElements(nums)); // 3
    }
}
