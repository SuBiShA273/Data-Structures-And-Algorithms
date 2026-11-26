/*
Problem: Minimum Number of Removals to Make Mountain Array

You may recall that an array arr is a mountain array if and only if:
- arr.length >= 3
- There exists some index i (0-indexed) with 0 < i < arr.length - 1 such that:
  - arr[0] < arr[1] < ... < arr[i - 1] < arr[i]
  - arr[i] > arr[i + 1] > ... > arr[arr.length - 1]

Given an integer array nums, return the minimum number of elements to remove 
to make nums a mountain array.

Source: LeetCode Problem #1671
https://leetcode.com

Approaches:

1. Recursion
   - Idea:
     - For every valid peak index i (1 to n-2), find:
       - LIS ending at i (strictly increasing up to i)
       - LDS starting at i (strictly decreasing after i)
     - If lis > 1 and lds > 1, mountain length = lis + lds - 1.
     - Return n - maxMountainLength.

   - Time Complexity: O(n * 2^n)
   - Space Complexity: O(n)

2. Memoization
   - Idea:
     - Cache (currIndex, prevIndex) states in dp[n][n+1] for both LIS and LDS.
     - Avoid recomputing overlapping subproblems for each peak.

   - Time Complexity: O(n^2)
   - Space Complexity: O(n^2)

3. Tabulation
   - Idea:
     - lis[i] = length of longest increasing subsequence ending at i (left to right).
     - lds[i] = length of longest decreasing subsequence starting at i (right to left).
     - Combine both at every valid peak where lis[i] > 1 and lds[i] > 1.

   - Time Complexity: O(n^2)
   - Space Complexity: O(n)

4. Optimal (Binary Search / Patience Sorting)
   - Idea:
     - Maintain a tails list to compute lis[i] and lds[i] in O(n log n) time 
       using binary search instead of nested loops.

   - Time Complexity: O(n log n)
   - Space Complexity: O(n)
*/

import java.util.*;

// Approach 1: Recursion
class MountainRemovalsRecursion {

    public int minimumMountainRemovals(int[] nums) {
        int n = nums.length;
        int maxMountain = 0;

        for (int peak = 1; peak < n - 1; peak++) {
            int lis = getLIS(peak, -1, nums);
            int lds = getLDS(peak, -1, nums);

            if (lis > 1 && lds > 1) {
                maxMountain = Math.max(maxMountain, lis + lds - 1);
            }
        }

        return n - maxMountain;
    }

    // Longest strictly increasing subsequence ending at 'curr' (moving left)
    private int getLIS(int curr, int nextIdx, int[] nums) {
        if (curr < 0) {
            return 0;
        }

        int notTake = getLIS(curr - 1, nextIdx, nums);
        int take = 0;

        if (nextIdx == -1 || nums[curr] < nums[nextIdx]) {
            take = 1 + getLIS(curr - 1, curr, nums);
        }

        return Math.max(take, notTake);
    }

    // Longest strictly decreasing subsequence starting at 'curr' (moving right)
    private int getLDS(int curr, int prevIdx, int[] nums) {
        if (curr == nums.length) {
            return 0;
        }

        int notTake = getLDS(curr + 1, prevIdx, nums);
        int take = 0;

        if (prevIdx == -1 || nums[curr] < nums[prevIdx]) {
            take = 1 + getLDS(curr + 1, curr, nums);
        }

        return Math.max(take, notTake);
    }
}

// Approach 2: Memoization
class MountainRemovalsMemoization {

    public int minimumMountainRemovals(int[] nums) {
        int n = nums.length;
        Integer[][] dpLIS = new Integer[n][n + 1];
        Integer[][] dpLDS = new Integer[n][n + 1];

        int maxMountain = 0;

        for (int peak = 1; peak < n - 1; peak++) {
            // Force 'peak' to be included by starting from peak - 1 and peak + 1
            int lis = 1 + getLIS(peak - 1, peak, nums, dpLIS);
            int lds = 1 + getLDS(peak + 1, peak, nums, dpLDS);

            if (lis > 1 && lds > 1) {
                maxMountain = Math.max(maxMountain, lis + lds - 1);
            }
        }

        return n - maxMountain;
    }

    private int getLIS(int curr, int nextIdx, int[] nums, Integer[][] dp) {
        if (curr < 0) {
            return 0;
        }

        if (dp[curr][nextIdx] != null) {
            return dp[curr][nextIdx];
        }

        int notTake = getLIS(curr - 1, nextIdx, nums, dp);
        int take = 0;

        if (nums[curr] < nums[nextIdx]) {
            take = 1 + getLIS(curr - 1, curr, nums, dp);
        }

        return dp[curr][nextIdx] = Math.max(take, notTake);
    }

    private int getLDS(int curr, int prevIdx, int[] nums, Integer[][] dp) {
        if (curr == nums.length) {
            return 0;
        }

        if (dp[curr][prevIdx] != null) {
            return dp[curr][prevIdx];
        }

        int notTake = getLDS(curr + 1, prevIdx, nums, dp);
        int take = 0;

        if (nums[curr] < nums[prevIdx]) {
            take = 1 + getLDS(curr + 1, curr, nums, dp);
        }

        return dp[curr][prevIdx] = Math.max(take, notTake);
    }
}

// Approach 3: Tabulation
class MountainRemovalsTabulation {

    public int minimumMountainRemovals(int[] nums) {
        int n = nums.length;
        int[] lis = new int[n];
        int[] lds = new int[n];
        Arrays.fill(lis, 1);
        Arrays.fill(lds, 1);

        // LIS from left to right
        for (int i = 1; i < n; i++) {
            for (int j = 0; j < i; j++) {
                if (nums[i] > nums[j] && 1 + lis[j] > lis[i]) {
                    lis[i] = lis[j] + 1;
                }
            }
        }

        // LDS from right to left
        for (int i = n - 2; i >= 0; i--) {
            for (int j = n - 1; j > i; j--) {
                if (nums[i] > nums[j] && 1 + lds[j] > lds[i]) {
                    lds[i] = lds[j] + 1;
                }
            }
        }

        int maxMountain = 0;
        for (int i = 1; i < n - 1; i++) {
            if (lis[i] > 1 && lds[i] > 1) {
                maxMountain = Math.max(maxMountain, lis[i] + lds[i] - 1);
            }
        }

        return n - maxMountain;
    }
}

// Approach 4: Optimal (Binary Search / Patience Sorting)
class MountainRemovalsOptimized {

    public int minimumMountainRemovals(int[] nums) {
        int n = nums.length;
        int[] lis = new int[n];
        int[] lds = new int[n];

        // Compute LIS lengths in O(n log n)
        List<Integer> tails = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            int idx = Collections.binarySearch(tails, nums[i]);
            if (idx < 0) {
                idx = -(idx + 1);
            }
            if (idx == tails.size()) {
                tails.add(nums[i]);
            } else {
                tails.set(idx, nums[i]);
            }
            lis[i] = idx + 1;
        }

        // Compute LDS lengths in O(n log n)
        tails.clear();
        for (int i = n - 1; i >= 0; i--) {
            int idx = Collections.binarySearch(tails, nums[i]);
            if (idx < 0) {
                idx = -(idx + 1);
            }
            if (idx == tails.size()) {
                tails.add(nums[i]);
            } else {
                tails.set(idx, nums[i]);
            }
            lds[i] = idx + 1;
        }

        int maxMountain = 0;
        for (int i = 1; i < n - 1; i++) {
            if (lis[i] > 1 && lds[i] > 1) {
                maxMountain = Math.max(maxMountain, lis[i] + lds[i] - 1);
            }
        }

        return n - maxMountain;
    }
}

// Driver Class
public class Main {

    public static void main(String[] args) {

        int[] nums = {2, 1, 1, 5, 6, 2, 3, 1};

        MountainRemovalsRecursion rec = new MountainRemovalsRecursion();
        System.out.println("Recursion Output: " + rec.minimumMountainRemovals(nums)); // 3

        MountainRemovalsMemoization memo = new MountainRemovalsMemoization();
        System.out.println("Memoization Output: " + memo.minimumMountainRemovals(nums)); // 3

        MountainRemovalsTabulation tab = new MountainRemovalsTabulation();
        System.out.println("Tabulation Output: " + tab.minimumMountainRemovals(nums)); // 3

        MountainRemovalsOptimized opt = new MountainRemovalsOptimized();
        System.out.println("Binary Search Optimized Output: " + opt.minimumMountainRemovals(nums)); // 3
    }
}
