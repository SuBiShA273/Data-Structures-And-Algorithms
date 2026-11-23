/*
Problem: Longest Increasing Subsequence (LIS)

Given an integer array nums, return the length of the longest strictly increasing subsequence.

Source: LeetCode Problem #300
https://leetcode.com

Approaches:

1. Recursion
   - Idea:
     - At each index, we track two states: current index (index) and the index of the
       previously picked element (prevIndex).
     - Explore both choices: skip the current element, or pick it (if nums[index] > nums[prevIndex]),
       and take the maximum length.

   - Time Complexity: O(2^n)
   - Space Complexity: O(n)

2. Memoization
   - Idea:
     - Store results in a 2D DP table: dp[index][prevIndex + 1].
     - Shift prevIndex by +1 so that -1 maps to index 0.
     - Avoid recalculating overlapping subproblems.

   - Time Complexity: O(n * n) -> O(n^2)
   - Space Complexity: O(n * n) + O(n) -> O(n^2)

3. Tabulation (2D DP)
   - Idea:
     - Build the 2D DP table bottom-up from index n - 1 down to 0, and prevIndex from index - 1 down to -1.
     - Use +1 coordinate shift for prevIndex in the DP table.
     - Base case: when index == n, LIS length is 0.

   - Time Complexity: O(n^2)
   - Space Complexity: O(n^2)

4. Space Optimization (1D DP - Two Rows)
   - Idea:
     - Current index's states depend only on the next index's states (ahead).
     - Replace the 2D table with two 1D arrays (ahead and curr) of size n + 1.

   - Time Complexity: O(n^2)
   - Space Complexity: O(2 * (n + 1)) -> O(n)

5. Optimal (Binary Search / Patience Sorting)
   - Idea:
     - Maintain a list 'tails' where tails.get(i) stores the smallest possible tail element
       of an increasing subsequence of length i + 1.
     - Use binary search (lower bound) to either extend 'tails' or replace an existing element
       to keep future extension opportunities optimal.

   - Time Complexity: O(n log n)
   - Space Complexity: O(n)
*/

import java.util.*;

// Approach 1: Recursion
class LISRecursion {

    public int lengthOfLIS(int[] nums) {
        return helper(0, -1, nums);
    }

    private int helper(int index, int prevIndex, int[] nums) {

        if (index == nums.length) {
            return 0;
        }

        // Option 1: Skip current element
        int notTake = 0 + helper(index + 1, prevIndex, nums);

        // Option 2: Pick current element (if strictly increasing)
        int take = 0;
        if (prevIndex == -1 || nums[index] > nums[prevIndex]) {
            take = 1 + helper(index + 1, index, nums);
        }

        return Math.max(take, notTake);
    }
}

// Approach 2: Memoization
class LISMemoization {

    public int lengthOfLIS(int[] nums) {

        int n = nums.length;
        Integer[][] dp = new Integer[n][n + 1];

        return helper(0, -1, nums, dp);
    }

    private int helper(int index, int prevIndex, int[] nums, Integer[][] dp) {

        if (index == nums.length) {
            return 0;
        }

        if (dp[index][prevIndex + 1] != null) {
            return dp[index][prevIndex + 1];
        }

        int notTake = 0 + helper(index + 1, prevIndex, nums, dp);

        int take = 0;
        if (prevIndex == -1 || nums[index] > nums[prevIndex]) {
            take = 1 + helper(index + 1, index, nums, dp);
        }

        return dp[index][prevIndex + 1] = Math.max(take, notTake);
    }
}

// Approach 3: Tabulation (2D DP)
class LISTabulation {

    public int lengthOfLIS(int[] nums) {

        int n = nums.length;
        int[][] dp = new int[n + 1][n + 1];

        // Base cases are already 0 in Java default initialization

        for (int index = n - 1; index >= 0; index--) {
            for (int prevIndex = index - 1; prevIndex >= -1; prevIndex--) {

                int notTake = 0 + dp[index + 1][prevIndex + 1];

                int take = 0;
                if (prevIndex == -1 || nums[index] > nums[prevIndex]) {
                    take = 1 + dp[index + 1][index + 1];
                }

                dp[index][prevIndex + 1] = Math.max(take, notTake);
            }
        }

        return dp[0][0];
    }
}

// Approach 4: Space Optimization (Two 1D Arrays)
class LISOptimized {

    public int lengthOfLIS(int[] nums) {

        int n = nums.length;
        int[] ahead = new int[n + 1];
        int[] curr = new int[n + 1];

        for (int index = n - 1; index >= 0; index--) {
            for (int prevIndex = index - 1; prevIndex >= -1; prevIndex--) {

                int notTake = 0 + ahead[prevIndex + 1];

                int take = 0;
                if (prevIndex == -1 || nums[index] > nums[prevIndex]) {
                    take = 1 + ahead[index + 1];
                }

                curr[prevIndex + 1] = Math.max(take, notTake);
            }
            // Copy curr states into ahead for the next iteration
            System.arraycopy(curr, 0, ahead, 0, n + 1);
        }

        return ahead[0];
    }
}

// Approach 5: Optimal (Binary Search / Patience Sorting)
class LISBinarySearch {

    public int lengthOfLIS(int[] nums) {

        List<Integer> tails = new ArrayList<>();

        for (int num : nums) {
            int idx = Collections.binarySearch(tails, num);

            if (idx < 0) {
                idx = -(idx + 1);
            }

            if (idx == tails.size()) {
                tails.add(num);
            } else {
                tails.set(idx, num);
            }
        }

        return tails.size();
    }
}

// Driver Class
public class Main {

    public static void main(String[] args) {

        int[] nums = {10, 9, 2, 5, 3, 7, 101, 18};

        LISRecursion rec = new LISRecursion();
        System.out.println("Recursion Output: " + rec.lengthOfLIS(nums)); // 4

        LISMemoization memo = new LISMemoization();
        System.out.println("Memoization Output: " + memo.lengthOfLIS(nums)); // 4

        LISTabulation tab = new LISTabulation();
        System.out.println("Tabulation Output: " + tab.lengthOfLIS(nums)); // 4

        LISOptimized opt = new LISOptimized();
        System.out.println("Space Optimized Output: " + opt.lengthOfLIS(nums)); // 4

        LISBinarySearch bs = new LISBinarySearch();
        System.out.println("Binary Search Output: " + bs.lengthOfLIS(nums)); // 4
    }
}
