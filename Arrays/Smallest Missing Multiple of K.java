/*
Problem: You are given an integer array nums and an integer k.
Return the smallest positive multiple of k that is not present in nums.

Source: LeetCode Problem #3718 → https://leetcode.com/problems/smallest-missing-multiple-of-k/

Approaches:
1. Brute Force (Sequential Search)
   - Idea:
     - Start from k, check each multiple sequentially.
     - For each multiple, scan the array to see if it exists.
     - Return the first multiple not found.
   - Time Complexity: O(n * m) where m = number of multiples checked.
   - Space Complexity: O(1).

2. Optimized (HashSet Lookup)
   - Idea:
     - Store all elements of nums in a HashSet for O(1) lookup.
     - Iterate multiples of k starting from k.
     - Return the first multiple not in the set.
   - Time Complexity: O(n).
   - Space Complexity: O(n).
*/

import java.util.*;

// Approach 1: Brute Force
class SmallestMissingMultipleBrute {
    public int smallestMissingMultiple(int[] nums, int k) {
        for (int i = 1; ; i++) {
            int multiple = k * i;
            boolean found = false;
            for (int num : nums) {
                if (num == multiple) {
                    found = true;
                    break;
                }
            }
            if (!found) return multiple;
        }
    }
}

// Approach 2: Optimized (HashSet Lookup)
class SmallestMissingMultipleOptimized {
    public int smallestMissingMultiple(int[] nums, int k) {
        Set<Integer> set = new HashSet<>();
        for (int num : nums) set.add(num);

        for (int i = 1; ; i++) {
            int multiple = k * i;
            if (!set.contains(multiple)) return multiple;
        }
    }
}

// Driver class
public class Main {
    public static void main(String[] args) {
        int[] nums1 = {8,2,3,4,6};
        int k1 = 2;

        int[] nums2 = {1,4,7,10,15};
        int k2 = 5;

        // Test Brute Force
        SmallestMissingMultipleBrute brute = new SmallestMissingMultipleBrute();
        System.out.println("Brute Force Result (nums1, k=2): " + brute.smallestMissingMultiple(nums1, k1)); // 10
        System.out.println("Brute Force Result (nums2, k=5): " + brute.smallestMissingMultiple(nums2, k2)); // 5

        // Test Optimized
        SmallestMissingMultipleOptimized opt = new SmallestMissingMultipleOptimized();
        System.out.println("Optimized Result (nums1, k=2): " + opt.smallestMissingMultiple(nums1, k1)); // 10
        System.out.println("Optimized Result (nums2, k=5): " + opt.smallestMissingMultiple(nums2, k2)); // 5
    }
}
