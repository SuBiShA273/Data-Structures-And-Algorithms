/*
Problem: Given an array of integers nums and an integer target,
return the indices of the two numbers such that they add up to target.

Source: LeetCode Problem #1 → https://leetcode.com/problems/two-sum/

Approaches:
1. Extreme Brute Force
   - Idea: Check every possible pair (i, j).
   - Time Complexity: O(n^2)
   - Space Complexity: O(1)

2. Hashing (Optimal)
   - Idea: Use a hash map to store values and indices,
           check if target - nums[i] exists in the map.
   - Time Complexity: O(n)
   - Space Complexity: O(n)
*/

import java.util.*;

class TwoSum {
    // Approach 1: Extreme Brute Force
    public int[] twoSumBrute(int[] nums, int target) {
        int n = nums.length;
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                if (nums[i] + nums[j] == target) {
                    return new int[]{i, j};
                }
            }
        }
        return new int[]{}; // No solution
    }

    // Approach 2: Hashing (Optimal)
    public int[] twoSumHash(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<>(); // value -> index
        for (int i = 0; i < nums.length; i++) {
            int complement = target - nums[i];
            if (map.containsKey(complement)) {
                return new int[]{map.get(complement), i};
            }
            map.put(nums[i], i);
        }
        return new int[]{}; // No solution
    }
}

// Driver class
public class Main {
    public static void main(String[] args) {
        TwoSum solver = new TwoSum();
        int[] nums = {2, 7, 11, 15};
        int target = 9;

        // Test Brute Force
        int[] res1 = solver.twoSumBrute(nums, target);
        System.out.print("Brute Force Result: ");
        if (res1.length > 0) System.out.println(res1[0] + ", " + res1[1]);
        else System.out.println("No solution");

        // Test Hashing
        int[] res2 = solver.twoSumHash(nums, target);
        System.out.print("Hashing Result: ");
        if (res2.length > 0) System.out.println(res2[0] + ", " + res2[1]);
        else System.out.println("No solution");
    }
}
