/*
Problem: You are given an array nums where nums[i] represents the maximum jump length at index i.
Return the minimum number of jumps needed to reach the last index.

Source: LeetCode Problem #45 → https://leetcode.com/problems/jump-game-ii/

Approaches:
1. Brute Force (DFS/Recursion)
   - Idea: From each index, recursively try all possible jumps.
   - Track the minimum number of jumps needed to reach the end.
   - Time Complexity: Exponential (O(n^n) worst case).
   - Space Complexity: O(n) recursion stack.

2. Optimized (Greedy)
   - Idea:
     - Use two pointers (l, r) to represent the current jump range.
     - Within this range, compute the farthest index reachable.
     - Move to the next range and increment jump count.
     - Continue until reaching the last index.
   - Time Complexity: O(n)
   - Space Complexity: O(1)
*/

import java.util.*;

// Approach 1: Brute Force (DFS)
class JumpGameIIBrute {
    public int solve(int ind, int[] nums, int jumps) {
        if (ind >= nums.length - 1) return jumps;

        int mini = Integer.MAX_VALUE;
        for (int step = 1; step <= nums[ind]; step++) {
            mini = Math.min(mini, solve(ind + step, nums, jumps + 1));
        }
        return mini;
    }

    public int jump(int[] nums) {
        return solve(0, nums, 0);
    }
}

// Approach 2: Optimized (Greedy)
class JumpGameIIGreedy {
    public int jump(int[] nums) {
        int l = 0, r = 0, jumps = 0;
        while (r < nums.length - 1) {
            int farthest = 0;
            for (int i = l; i <= r; i++) {
                farthest = Math.max(farthest, i + nums[i]);
            }
            l = r + 1;
            r = farthest;
            jumps++;
        }
        return jumps;
    }
}

// Driver class
public class Main {
    public static void main(String[] args) {
        int[] nums1 = {2,3,1,1,4};
        int[] nums2 = {2,3,0,1,4};

        // Test Brute Force
        JumpGameIIBrute brute = new JumpGameIIBrute();
        System.out.println("Brute Force Result (nums1): " + brute.jump(nums1)); // 2
        System.out.println("Brute Force Result (nums2): " + brute.jump(nums2)); // 2

        // Test Optimized
        JumpGameIIGreedy greedy = new JumpGameIIGreedy();
        System.out.println("Greedy Result (nums1): " + greedy.jump(nums1)); // 2
        System.out.println("Greedy Result (nums2): " + greedy.jump(nums2)); // 2
    }
}
