/*
Problem: House Robber II

You are a professional robber planning to rob houses along a street.

All houses are arranged in a circle.

The first house is adjacent to the last house.

You cannot rob two adjacent houses.

Return the maximum amount of money you can rob without alerting
the police.

Source: LeetCode Problem #213
https://leetcode.com/problems/house-robber-ii/

Approaches:

1. Brute Force (Recursion)
   - Idea:
     - Since houses are in a circle, first and last house
       cannot be robbed together.
     - Try two cases:
         Case 1: Rob houses [0 ... n-2]
         Case 2: Rob houses [1 ... n-1]
     - Use recursion to solve each case.
     - Return the maximum result.

   - Time Complexity: O(2^n)
   - Space Complexity: O(n)
     (Recursion stack)

2. Optimized (DP - Space Optimized)
   - Idea:
     - Circular problem can be converted into:
         Case 1: Exclude last house
         Case 2: Exclude first house
     - Apply House Robber I on both ranges.
     - Return the maximum of the two results.

   - Time Complexity: O(n)
   - Space Complexity: O(1)
*/

import java.util.*;

// Approach 1: Brute Force (Recursion)
class HouseRobber2Brute {

    public int rob(int[] nums) {

        int n = nums.length;

        if (n == 1) {
            return nums[0];
        }

        return Math.max(
                helper(nums, 0, n - 2),
                helper(nums, 1, n - 1)
        );
    }

    private int helper(int[] nums, int index, int end) {

        if (index > end) {
            return 0;
        }

        int take = nums[index]
                + helper(nums, index + 2, end);

        int skip = helper(nums, index + 1, end);

        return Math.max(take, skip);
    }
}

// Approach 2: Optimized (DP - Space Optimized)
class HouseRobber2Optimized {

    public int rob(int[] nums) {

        int n = nums.length;

        if (n == 1) {
            return nums[0];
        }

        int excludeLast = robRange(nums, 0, n - 2);
        int excludeFirst = robRange(nums, 1, n - 1);

        return Math.max(excludeLast, excludeFirst);
    }

    private int robRange(int[] nums, int start, int end) {

        int prev1 = 0;
        int prev2 = 0;

        for (int i = start; i <= end; i++) {

            int take = nums[i] + prev2;
            int skip = prev1;

            int current = Math.max(take, skip);

            prev2 = prev1;
            prev1 = current;
        }

        return prev1;
    }
}

// Driver Class
public class Main {

    public static void main(String[] args) {

        int[] nums = {2, 3, 2};

        HouseRobber2Brute brute =
                new HouseRobber2Brute();

        System.out.println(
                "Brute Force Output: "
                        + brute.rob(nums)
        ); // 3

        HouseRobber2Optimized optimized =
                new HouseRobber2Optimized();

        System.out.println(
                "Optimized Output: "
                        + optimized.rob(nums)
        ); // 3
    }
}
