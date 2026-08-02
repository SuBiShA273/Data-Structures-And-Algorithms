/*
Problem: Given an integer array nums, 
move all 0's to the end while maintaining the relative order of non-zero elements.

Source: LeetCode Problem #283 → https://leetcode.com/problems/move-zeroes/

Approaches:
1. Brute Force (Shift Method)
   - Idea: For each zero, shift elements left and push zero to the end.
   - Time Complexity: O(n^2)
   - Space Complexity: O(1)

2. Two-Pointer (Optimal)
   - Idea: Use a pointer to track position of non-zero elements, 
           swap or overwrite in-place.
   - Time Complexity: O(n)
   - Space Complexity: O(1)
*/

import java.util.*;

public class MoveZeroes {

    // Approach 1: Brute Force (Shift Method)
    public static void moveZeroesBrute(int[] nums) {
        int n = nums.length;
        for (int i = 0; i < n; i++) {
            if (nums[i] == 0) {
                for (int j = i + 1; j < n; j++) {
                    nums[j - 1] = nums[j];
                }
                nums[n - 1] = 0;
            }
        }
    }

    // Approach 2: Two-Pointer (Optimal)
    public static void moveZeroesOptimal(int[] nums) {
        int n = nums.length;
        int index = 0; // position for non-zero
        for (int i = 0; i < n; i++) {
            if (nums[i] != 0) {
                nums[index++] = nums[i];
            }
        }
        while (index < n) {
            nums[index++] = 0;
        }
    }

    // Utility function to print array
    public static void printArray(int[] nums) {
        System.out.println(Arrays.toString(nums));
    }

    public static void main(String[] args) {
        int[] nums1 = {0, 1, 0, 3, 12};
        int[] nums2 = {0, 1, 0, 3, 12};

        // Test Brute Force
        moveZeroesBrute(nums1);
        System.out.print("Brute Force Result: ");
        printArray(nums1);

        // Test Optimal
        moveZeroesOptimal(nums2);
        System.out.print("Optimal Result: ");
        printArray(nums2);
    }
}
