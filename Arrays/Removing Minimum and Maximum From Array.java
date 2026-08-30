/*
Problem: You are given a 0-indexed array of integers nums.
Return the minimum number of deletions required to remove both the minimum and maximum element from the array.

Source: LeetCode Problem #2091 → https://leetcode.com/problems/removing-minimum-and-maximum-from-array/

Optimized Approach (Index Calculation):
   - Find indices of min and max elements.
   - Compute three possible deletion counts:
       a) Remove both from left.
       b) Remove both from right.
       c) Remove one from left and one from right.
   - Return the minimum of these three.
   - Time Complexity: O(n).
   - Space Complexity: O(1).
*/

class Solution {
    public int minimumDeletions(int[] nums) {
        int n = nums.length;
        int minIdx = 0, maxIdx = 0;

        // Find indices of min and max
        for (int i = 0; i < n; i++) {
            if (nums[i] < nums[minIdx]) minIdx = i;
            if (nums[i] > nums[maxIdx]) maxIdx = i;
        }

        // Case 1: remove both from left
        int left = Math.max(minIdx, maxIdx) + 1;

        // Case 2: remove both from right
        int right = n - Math.min(minIdx, maxIdx);

        // Case 3: remove one from left and one from right
        int both = Math.min(minIdx, maxIdx) + 1 + (n - Math.max(minIdx, maxIdx));

        return Math.min(left, Math.min(right, both));
    }
}

// Driver class
public class Main {
    public static void main(String[] args) {
        Solution sol = new Solution();

        int[] nums1 = {2,10,7,5,4,1,8,6};
        int[] nums2 = {0,-4,19,1,8,-2,-3,5};
        int[] nums3 = {101};

        System.out.println("Optimized Result (nums1): " + sol.minimumDeletions(nums1)); // 5
        System.out.println("Optimized Result (nums2): " + sol.minimumDeletions(nums2)); // 3
        System.out.println("Optimized Result (nums3): " + sol.minimumDeletions(nums3)); // 1
    }
}
