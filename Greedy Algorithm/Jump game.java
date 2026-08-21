/*
Problem: You are given an array nums where nums[i] represents the maximum jump length at index i.
Return true if you can reach the last index, otherwise false.

Source: LeetCode Problem #55 → https://leetcode.com/problems/jump-game/

Approaches:
1. Brute Force (DFS/Recursion)
   - Idea: From each index, recursively try all possible jumps.
   - If any path reaches the last index, return true.
   - Time Complexity: O(n^2) worst case (exponential with recursion).
   - Space Complexity: O(n) recursion stack.

2. Optimized (Greedy)
   - Idea:
     - Track the farthest index reachable so far.
     - Iterate through nums, update farthest reach.
     - If at any point current index > farthest reach → cannot proceed.
     - At the end, check if farthest reach ≥ last index.
   - Time Complexity: O(n)
   - Space Complexity: O(1)
*/

import java.util.*;

// Approach 1: Brute Force (DFS)
class JumpGameBrute {
    public boolean canJump(int[] nums) {
        return dfs(nums, 0);
    }

    private boolean dfs(int[] nums, int pos) {
        if (pos >= nums.length - 1) return true;

        int maxJump = nums[pos];
        for (int step = 1; step <= maxJump; step++) {
            if (dfs(nums, pos + step)) return true;
        }
        return false;
    }
}

// Approach 2: Optimized (Greedy)
class JumpGameOptimized {
    public boolean canJump(int[] nums) {
        int farthest = 0;
        for (int i = 0; i < nums.length; i++) {
            if (i > farthest) return false; // stuck
            farthest = Math.max(farthest, i + nums[i]);
        }
        return true;
    }
}

// Driver class
public class Main {
    public static void main(String[] args) {
        int[] nums1 = {2,3,1,1,4};
        int[] nums2 = {3,2,1,0,4};

        // Test Brute Force
        JumpGameBrute brute = new JumpGameBrute();
        System.out.println("Brute Force Result (nums1): " + brute.canJump(nums1)); // true
        System.out.println("Brute Force Result (nums2): " + brute.canJump(nums2)); // false

        // Test Optimized
        JumpGameOptimized optimized = new JumpGameOptimized();
        System.out.println("Optimized Result (nums1): " + optimized.canJump(nums1)); // true
        System.out.println("Optimized Result (nums2): " + optimized.canJump(nums2)); // false
    }
}
