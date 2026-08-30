/*
Problem: Given a sorted array nums, 
remove the duplicates in-place such that each unique element appears only once.
Return the new length of the array after removing duplicates.

Source: LeetCode Problem #26 → https://leetcode.com/problems/remove-duplicates-from-sorted-array/

Approaches:
1. Brute Force (Extra Space)
   - Idea: Use a set to store unique elements, then overwrite nums.
   - Time Complexity: O(n log n) [due to set insertion]
   - Space Complexity: O(n)

2. Two-Pointer Technique (Optimal)
   - Idea: Since array is sorted, use two pointers:
           one for placing unique elements, one for scanning.
   - Time Complexity: O(n)
   - Space Complexity: O(1)
*/

import java.util.*;

// Approach 1: Brute Force using Set
class RemoveDuplicatesBrute {
    public int removeDuplicates(int[] nums) {
        Set<Integer> set = new LinkedHashSet<>();
        for (int num : nums) {
            set.add(num);
        }
        int index = 0;
        for (int val : set) {
            nums[index++] = val;
        }
        return index; // new length
    }
}

// Approach 2: Two-Pointer (Optimal)
class RemoveDuplicatesOptimal {
    public int removeDuplicates(int[] nums) {
        if (nums.length == 0) return 0;
        int i = 0; // slow pointer
        for (int j = 1; j < nums.length; j++) {
            if (nums[j] != nums[i]) {
                i++;
                nums[i] = nums[j];
            }
        }
        return i + 1; // new length
    }
}

// Driver class
public class Main {
    public static void main(String[] args) {
        int[] nums1 = {0,0,1,1,1,2,2,3,3,4};

        // Test Brute Force
        RemoveDuplicatesBrute brute = new RemoveDuplicatesBrute();
        int len1 = brute.removeDuplicates(nums1);
        System.out.println("Brute Force Result Length: " + len1);
        System.out.print("Array after removal: ");
        for (int i = 0; i < len1; i++) System.out.print(nums1[i] + " ");
        System.out.println();

        // Reset nums for optimal test
        int[] nums2 = {0,0,1,1,1,2,2,3,3,4};

        // Test Optimal
        RemoveDuplicatesOptimal opt = new RemoveDuplicatesOptimal();
        int len2 = opt.removeDuplicates(nums2);
        System.out.println("Optimal Result Length: " + len2);
        System.out.print("Array after removal: ");
        for (int i = 0; i < len2; i++) System.out.print(nums2[i] + " ");
        System.out.println();
    }
}
