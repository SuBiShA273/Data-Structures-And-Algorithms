/*
Problem: House Robber

You are a professional robber planning to rob houses along a street.

Each house has a certain amount of money stashed.
The only constraint stopping you from robbing each of them is that
adjacent houses have security systems connected.

Return the maximum amount of money you can rob without alerting
the police.

Source: LeetCode Problem #198
https://leetcode.com/problems/house-robber/

Approaches:

1. Brute Force (Recursion)
   - Idea:
     - For every house, we have two choices:
         * Rob the current house and skip the next.
         * Skip the current house.
     - Recursively calculate the maximum profit.

   - Time Complexity: O(2^n)
   - Space Complexity: O(n)
     (Recursion stack)

2. Optimized (Dynamic Programming - Space Optimized)
   - Idea:
     - Let:
           prev1 = maximum profit till previous house
           prev2 = maximum profit till house before previous
     - For current house:
           take = nums[i] + prev2
           skip = prev1
     - Current profit:
           max(take, skip)

   - Time Complexity: O(n)
   - Space Complexity: O(1)
*/

import java.util.*;

// Approach 1: Brute Force (Recursion)
class HouseRobberBrute {

    public int rob(int[] nums) {
        return helper(nums, 0);
    }

    private int helper(int[] nums, int index) {

        if (index >= nums.length) {
            return 0;
        }

        int take = nums[index]
                + helper(nums, index + 2);

        int skip = helper(nums, index + 1);

        return Math.max(take, skip);
    }
}

// Approach 2: Optimized (DP - Space Optimized)
class HouseRobberOptimized {

    public int rob(int[] nums) {

        int prev1 = 0;
        int prev2 = 0;

        for (int money : nums) {

            int take = money + prev2;
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

        int[] nums = {2, 7, 9, 3, 1};

        HouseRobberBrute brute =
                new HouseRobberBrute();

        System.out.println(
                "Brute Force Output: "
                        + brute.rob(nums)
        ); // 12

        HouseRobberOptimized optimized =
                new HouseRobberOptimized();

        System.out.println(
                "Optimized Output: "
                        + optimized.rob(nums)
        ); // 12
    }
}
