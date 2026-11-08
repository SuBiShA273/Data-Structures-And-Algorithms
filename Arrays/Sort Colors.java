/*
Problem: Sort Colors

Given an array nums with n objects colored red, white, or blue.

Use:
0 -> Red
1 -> White
2 -> Blue

Sort the array in-place so that objects of the same color
are adjacent and appear in the order:
0s -> 1s -> 2s

You must solve the problem without using the library sort function.

Source: LeetCode Problem #75
https://leetcode.com/problems/sort-colors/

Approaches:

1. Brute Force (Sorting)
   - Idea:
     - Use any sorting algorithm like Bubble Sort.
     - After sorting, array automatically becomes:
           0s -> 1s -> 2s

   - Time Complexity: O(n²)
   - Space Complexity: O(1)

2. Better (Counting Sort)
   - Idea:
     - Count the number of:
           0s
           1s
           2s
     - Overwrite the array accordingly.

   - Time Complexity: O(n)
   - Space Complexity: O(1)

3. Optimized (Dutch National Flag Algorithm)
   - Idea:
     - Maintain three pointers:
           low
           mid
           high
     - Regions:
           [0...low-1]     -> 0s
           [low...mid-1]   -> 1s
           [high+1...n-1]  -> 2s
     - Process array in one pass.

   - Time Complexity: O(n)
   - Space Complexity: O(1)

   - Most Interview Preferred ✅
*/

import java.util.*;

// Approach 1: Brute Force (Bubble Sort)
class SortColorsBrute {

    public void sortColors(int[] nums) {

        int n = nums.length;

        for (int i = 0; i < n - 1; i++) {

            for (int j = 0; j < n - i - 1; j++) {

                if (nums[j] > nums[j + 1]) {

                    int temp = nums[j];
                    nums[j] = nums[j + 1];
                    nums[j + 1] = temp;
                }
            }
        }
    }
}

// Approach 2: Better (Counting Sort)
class SortColorsBetter {

    public void sortColors(int[] nums) {

        int count0 = 0;
        int count1 = 0;
        int count2 = 0;

        for (int num : nums) {

            if (num == 0) {
                count0++;
            } else if (num == 1) {
                count1++;
            } else {
                count2++;
            }
        }

        int index = 0;

        while (count0-- > 0) {
            nums[index++] = 0;
        }

        while (count1-- > 0) {
            nums[index++] = 1;
        }

        while (count2-- > 0) {
            nums[index++] = 2;
        }
    }
}

// Approach 3: Optimized (Dutch National Flag)
class SortColorsOptimized {

    public void sortColors(int[] nums) {

        int low = 0;
        int mid = 0;
        int high = nums.length - 1;

        while (mid <= high) {

            if (nums[mid] == 0) {

                swap(nums, low, mid);
                low++;
                mid++;

            } else if (nums[mid] == 1) {

                mid++;

            } else {

                swap(nums, mid, high);
                high--;
            }
        }
    }

    private void swap(
            int[] nums,
            int i,
            int j) {

        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}

// Driver Class
public class Main {

    public static void main(String[] args) {

        int[] nums = {2, 0, 2, 1, 1, 0};

        SortColorsOptimized obj =
                new SortColorsOptimized();

        obj.sortColors(nums);

        System.out.println(
                Arrays.toString(nums)
        );

        // Output:
        // [0, 0, 1, 1, 2, 2]
    }
}
