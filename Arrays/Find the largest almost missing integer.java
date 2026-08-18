/*
Problem: You are given an integer array nums and an integer k.
Return the largest integer that appears in exactly one subarray of length k.
If no such integer exists, return -1.

Source: LeetCode Problem #3471 → https://leetcode.com/problems/largest-integer-in-one-window/

Approaches:
1. Brute Force (Generate All Windows)
   - Idea:
     - For each starting index, generate the subarray of length k.
     - Track unique elements in that subarray.
     - Count how many distinct windows each element appears in.
     - Finally, return the maximum element that appears in exactly one window.
   - Time Complexity: O(n * k)
   - Space Complexity: O(n)

2. Optimized (Frequency + Edge Case Logic)
   - Idea:
     - If k == n → only one window, return max element.
     - If k == 1 → only elements with frequency 1 are valid, return max.
     - If 1 < k < n → only first or last element can appear in exactly one window.
     - Check those edge cases directly.
   - Time Complexity: O(n)
   - Space Complexity: O(n)
*/

import java.util.*;

// Approach 1: Brute Force
class LargestIntegerBrute {
    public int largestInteger(int[] nums, int k) {
        int n = nums.length;
        Map<Integer, Integer> subarrayCounts = new HashMap<>();

        // Generate all windows of size k
        for (int i = 0; i <= n - k; i++) {
            Set<Integer> uniqueInWindow = new HashSet<>();
            for (int j = i; j < i + k; j++) {
                uniqueInWindow.add(nums[j]);
            }
            for (int num : uniqueInWindow) {
                subarrayCounts.put(num, subarrayCounts.getOrDefault(num, 0) + 1);
            }
        }

        int maxVal = -1;
        for (int num : subarrayCounts.keySet()) {
            if (subarrayCounts.get(num) == 1) {
                maxVal = Math.max(maxVal, num);
            }
        }
        return maxVal;
    }
}

// Approach 2: Optimized
class LargestIntegerOptimized {
    public int largestInteger(int[] nums, int k) {
        int n = nums.length;

        // Case 1: Whole array is one window
        if (k == n) {
            int max = -1;
            for (int num : nums) max = Math.max(max, num);
            return max;
        }

        // Count global frequencies
        Map<Integer, Integer> globalCount = new HashMap<>();
        for (int num : nums) {
            globalCount.put(num, globalCount.getOrDefault(num, 0) + 1);
        }

        // Case 2: Window size = 1
        if (k == 1) {
            int max = -1;
            for (int num : globalCount.keySet()) {
                if (globalCount.get(num) == 1) {
                    max = Math.max(max, num);
                }
            }
            return max;
        }

        // Case 3: 1 < k < n → only first or last element can appear in exactly one window
        int ans = -1;
        if (globalCount.get(nums[0]) == 1) {
            ans = Math.max(ans, nums[0]);
        }
        if (globalCount.get(nums[n - 1]) == 1) {
            ans = Math.max(ans, nums[n - 1]);
        }

        return ans;
    }
}

// Driver class
public class Main {
    public static void main(String[] args) {
        int[] nums = {3, 9, 2, 1, 7};
        int k = 3;

        // Test Brute Force
        LargestIntegerBrute brute = new LargestIntegerBrute();
        System.out.println("Brute Force Result: " + brute.largestInteger(nums, k));

        // Test Optimized
        LargestIntegerOptimized optimized = new LargestIntegerOptimized();
        System.out.println("Optimized Result: " + optimized.largestInteger(nums, k));
    }
}
