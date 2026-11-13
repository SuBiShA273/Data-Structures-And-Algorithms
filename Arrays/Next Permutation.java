/*
Problem: Next Permutation

A permutation of an array of integers is an arrangement
of its members into a sequence or linear order.

Given an array nums, find the next lexicographically
greater permutation.

If such arrangement is not possible,
rearrange it as the lowest possible order
(sorted in ascending order).

The replacement must be in-place.

Source: LeetCode Problem #31
https://leetcode.com/problems/next-permutation/

Approaches:

1. Brute Force
   - Idea:
     - Generate all permutations.
     - Sort them lexicographically.
     - Find current permutation.
     - Return the next one.

   - Time Complexity: O(n! * n)
   - Space Complexity: O(n!)

2. Better
   - Idea:
     - Find first decreasing element from the right.
     - Find the smallest greater element on the right.
     - Swap them.
     - Sort the remaining suffix.

   - Time Complexity: O(n log n)
   - Space Complexity: O(1)

3. Optimized
   - Idea:
     - Traverse from right and find first index i such that:
            nums[i] < nums[i + 1]
     - Find the first greater element from the right.
     - Swap them.
     - Reverse the suffix.

   - Time Complexity: O(n)
   - Space Complexity: O(1)

   - Most Interview Preferred ✅
*/

import java.util.*;

// Approach 1: Brute Force (Conceptual)
class NextPermutationBrute {

    public void nextPermutation(int[] nums) {

        Arrays.sort(nums);
    }
}

// Approach 2: Better
class NextPermutationBetter {

    public void nextPermutation(int[] nums) {

        int n = nums.length;

        int pivot = -1;

        for (int i = n - 2; i >= 0; i--) {

            if (nums[i] < nums[i + 1]) {
                pivot = i;
                break;
            }
        }

        if (pivot == -1) {
            Arrays.sort(nums);
            return;
        }

        int swapIndex = pivot + 1;

        for (int i = pivot + 1; i < n; i++) {

            if (nums[i] > nums[pivot]) {
                swapIndex = i;
            }
        }

        swap(nums, pivot, swapIndex);

        Arrays.sort(nums, pivot + 1, n);
    }

    private void swap(int[] nums, int i, int j) {

        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}

// Approach 3: Optimized
class NextPermutationOptimized {

    public void nextPermutation(int[] nums) {

        int n = nums.length;

        int index = -1;

        for (int i = n - 2; i >= 0; i--) {

            if (nums[i] < nums[i + 1]) {
                index = i;
                break;
            }
        }

        if (index == -1) {

            reverse(nums, 0, n - 1);
            return;
        }

        for (int i = n - 1; i > index; i--) {

            if (nums[i] > nums[index]) {

                swap(nums, i, index);
                break;
            }
        }

        reverse(nums, index + 1, n - 1);
    }

    private void swap(int[] nums, int i, int j) {

        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

    private void reverse(int[] nums, int left, int right) {

        while (left < right) {

            swap(nums, left, right);

            left++;
            right--;
        }
    }
}

// Driver Class
public class Main {

    public static void main(String[] args) {

        int[] nums = {1, 2, 3};

        NextPermutationOptimized obj =
                new NextPermutationOptimized();

        obj.nextPermutation(nums);

        System.out.println(Arrays.toString(nums));

        // Output:
        // [1, 3, 2]
    }
}
