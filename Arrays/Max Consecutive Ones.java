/*
Problem: Max Consecutive Ones

Given a binary array nums, return the maximum number
of consecutive 1's in the array.

Source: LeetCode Problem #485
https://leetcode.com/problems/max-consecutive-ones/

Approaches:

1. Brute Force
   - Idea:
     - For every index containing 1,
       start counting consecutive 1's.
     - Update maximum length found.

   - Time Complexity: O(n²)
   - Space Complexity: O(1)

2. Optimized (Single Traversal)
   - Idea:
     - Traverse the array once.
     - Maintain current streak of consecutive 1's.
     - Reset streak when 0 is encountered.
     - Keep track of maximum streak.

   - Time Complexity: O(n)
   - Space Complexity: O(1)
*/

import java.util.*;

// Approach 1: Brute Force
class MaxConsecutiveOnesBrute {

    public int findMaxConsecutiveOnes(int[] nums) {

        int maxCount = 0;

        for (int i = 0; i < nums.length; i++) {

            if (nums[i] == 1) {

                int count = 0;

                for (int j = i;
                     j < nums.length && nums[j] == 1;
                     j++) {

                    count++;
                }

                maxCount =
                        Math.max(maxCount, count);
            }
        }

        return maxCount;
    }
}

// Approach 2: Optimized
class MaxConsecutiveOnesOptimized {

    public int findMaxConsecutiveOnes(int[] nums) {

        int maxCount = 0;
        int currentCount = 0;

        for (int num : nums) {

            if (num == 1) {

                currentCount++;

                maxCount = Math.max(
                        maxCount,
                        currentCount
                );

            } else {

                currentCount = 0;
            }
        }

        return maxCount;
    }
}

// Driver Class
public class Main {

    public static void main(String[] args) {

        int[] nums = {1, 1, 0, 1, 1, 1};

        MaxConsecutiveOnesBrute brute =
                new MaxConsecutiveOnesBrute();

        System.out.println(
                "Brute Force Output: "
                + brute.findMaxConsecutiveOnes(nums)
        ); // 3

        MaxConsecutiveOnesOptimized optimized =
                new MaxConsecutiveOnesOptimized();

        System.out.println(
                "Optimized Output: "
                + optimized.findMaxConsecutiveOnes(nums)
        ); // 3
    }
}
