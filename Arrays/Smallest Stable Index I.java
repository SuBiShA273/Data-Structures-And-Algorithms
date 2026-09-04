/*
Problem: You are given an integer array nums of length n and an integer k.
Define instability score at index i as:
   instability(i) = max(nums[0..i]) - min(nums[i..n-1])
An index i is stable if instability(i) ≤ k.
Return the smallest stable index. If none exists, return -1.

Source: LeetCode Problem #3903 → https://leetcode.com/problems/smallest-stable-index-i/

Approaches:
1. Brute Force (Nested Loops)
   - Idea:
     - For each index i:
       - Compute prefix max = max(nums[0..i]).
       - Compute suffix min = min(nums[i..n-1]).
       - Check if difference ≤ k.
     - Return first valid index.
   - Time Complexity: O(n^2).
   - Space Complexity: O(1).

2. Optimized (Prefix Max + Suffix Min)
   - Idea:
     - Precompute suffix minimums in one backward pass.
     - Sweep forward maintaining prefix maximum.
     - At each index, check prefixMax - suffixMin[i] ≤ k.
     - Return first valid index.
   - Time Complexity: O(n).
   - Space Complexity: O(n).
*/

import java.util.*;

// Approach 1: Brute Force
class StableIndexBrute {
    public int smallestStableIndex(int[] nums, int k) {
        int n = nums.length;
        for (int i = 0; i < n; i++) {
            int prefixMax = Integer.MIN_VALUE;
            for (int j = 0; j <= i; j++) {
                prefixMax = Math.max(prefixMax, nums[j]);
            }
            int suffixMin = Integer.MAX_VALUE;
            for (int j = i; j < n; j++) {
                suffixMin = Math.min(suffixMin, nums[j]);
            }
            if (prefixMax - suffixMin <= k) {
                return i;
            }
        }
        return -1;
    }
}

// Approach 2: Optimized (Prefix Max + Suffix Min)
class StableIndexOptimized {
    public int smallestStableIndex(int[] nums, int k) {
        int n = nums.length;
        int[] suffixMin = new int[n];
        
        // Build suffix minimums
        suffixMin[n - 1] = nums[n - 1];
        for (int i = n - 2; i >= 0; i--) {
            suffixMin[i] = Math.min(suffixMin[i + 1], nums[i]);
        }
        
        int prefixMax = Integer.MIN_VALUE;
        for (int i = 0; i < n; i++) {
            prefixMax = Math.max(prefixMax, nums[i]);
            if (prefixMax - suffixMin[i] <= k) {
                return i;
            }
        }
        return -1;
    }
}

// Driver class
public class Main {
    public static void main(String[] args) {
        int[] nums1 = {5,0,1,4}; int k1 = 3;
        int[] nums2 = {3,2,1};   int k2 = 1;
        int[] nums3 = {0};       int k3 = 0;

        // Test Brute Force
        StableIndexBrute brute = new StableIndexBrute();
        System.out.println("Brute Force Result (case1): " + brute.smallestStableIndex(nums1, k1)); // 3
        System.out.println("Brute Force Result (case2): " + brute.smallestStableIndex(nums2, k2)); // -1
        System.out.println("Brute Force Result (case3): " + brute.smallestStableIndex(nums3, k3)); // 0

        // Test Optimized
        StableIndexOptimized opt = new StableIndexOptimized();
        System.out.println("Optimized Result (case1): " + opt.smallestStableIndex(nums1, k1)); // 3
        System.out.println("Optimized Result (case2): " + opt.smallestStableIndex(nums2, k2)); // -1
        System.out.println("Optimized Result (case3): " + opt.smallestStableIndex(nums3, k3)); // 0
    }
}
