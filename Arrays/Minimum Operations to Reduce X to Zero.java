/*
Problem: Given an array nums and an integer x,
return the minimum number of operations to reduce x to exactly 0.
In one operation, you can remove the leftmost or rightmost element from nums
and subtract its value from x. If it's not possible, return -1.

Source: LeetCode Problem #1658 → https://leetcode.com/problems/minimum-operations-to-reduce-x-to-zero/
*/

import java.util.*;

/* ---------------------------------------------------
   Brute Force Approach (Prefix + Suffix sums)
   - Idea:
     - Precompute prefix sums and suffix sums.
     - Try all combinations: remove i elements from left and j from right.
     - If sum == x, update minimum operations.
   - Time Complexity: O(n^2).
   - Space Complexity: O(n).
--------------------------------------------------- */
class BruteForce {
    public int minOperations(int[] nums, int x) {
        int n = nums.length;
        int[] prefix = new int[n + 1];
        int[] suffix = new int[n + 1];

        for (int i = 0; i < n; i++) prefix[i + 1] = prefix[i] + nums[i];
        for (int i = n - 1; i >= 0; i--) suffix[n - i] = suffix[n - i - 1] + nums[i];

        int ans = Integer.MAX_VALUE;
        for (int i = 0; i <= n; i++) {
            for (int j = 0; j <= n - i; j++) {
                if (prefix[i] + suffix[j] == x) {
                    ans = Math.min(ans, i + j);
                }
            }
        }
        return ans == Integer.MAX_VALUE ? -1 : ans;
    }
}

/* ---------------------------------------------------
   Optimized Approach (Longest Subarray Sum)
   - Idea:
     - Equivalent to finding the longest subarray with sum = total - x.
     - Use sliding window to find max length subarray with sum = total - x.
     - Answer = n - maxLength.
   - Time Complexity: O(n).
   - Space Complexity: O(1).
--------------------------------------------------- */
class Optimized {
    public int minOperations(int[] nums, int x) {
        int total = 0;
        for (int num : nums) total += num;

        int target = total - x;
        if (target < 0) return -1;
        if (target == 0) return nums.length;

        int l = 0, sum = 0, maxLen = -1;
        for (int r = 0; r < nums.length; r++) {
            sum += nums[r];
            while (l <= r && sum > target) {
                sum -= nums[l++];
            }
            if (sum == target) {
                maxLen = Math.max(maxLen, r - l + 1);
            }
        }
        return maxLen == -1 ? -1 : nums.length - maxLen;
    }
}

// Driver class
public class Main {
    public static void main(String[] args) {
        int[] nums1 = {1,1,4,2,3};
        int x1 = 5;

        BruteForce brute = new BruteForce();
        Optimized opt = new Optimized();

        System.out.println("Brute Force Result: " + brute.minOperations(nums1, x1)); // Expected: 2
        System.out.println("Optimized Result: " + opt.minOperations(nums1, x1));     // Expected: 2

        int[] nums2 = {5,6,7,8,9};
        int x2 = 4;
        System.out.println("Brute Force Result: " + brute.minOperations(nums2, x2)); // Expected: -1
        System.out.println("Optimized Result: " + opt.minOperations(nums2, x2));     // Expected: -1

        int[] nums3 = {3,2,20,1,1,3};
        int x3 = 10;
        System.out.println("Brute Force Result: " + brute.minOperations(nums3, x3)); // Expected: 5
        System.out.println("Optimized Result: " + opt.minOperations(nums3, x3));     // Expected: 5
    }
}
