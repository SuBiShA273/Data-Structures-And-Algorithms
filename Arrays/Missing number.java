/*
Problem: Given an array nums containing n distinct numbers in the range [0, n],
return the only number in the range that is missing from the array.

Source: LeetCode Problem #268 → https://leetcode.com/problems/missing-number/

Approaches:
1. Sum Formula (Math)
   - Idea: Expected sum of 0..n is n*(n+1)/2.
   - Subtract actual array sum to find missing number.
   - Time Complexity: O(n)
   - Space Complexity: O(1)

2. XOR Trick (Bit Manipulation)
   - Idea: XOR all numbers from 0..n and XOR with all elements in array.
   - Since duplicates cancel out, the missing number remains.
   - Time Complexity: O(n)
   - Space Complexity: O(1)
*/

public class MissingNumber {

    // Approach 1: Sum Formula (Math)
    public static int missingNumberSum(int[] nums) {
        int n = nums.length;
        int expectedSum = n * (n + 1) / 2;
        int actualSum = 0;
        for (int num : nums) {
            actualSum += num;
        }
        return expectedSum - actualSum;
    }

    // Approach 2: XOR Trick (Bit Manipulation)
    public static int missingNumberXOR(int[] nums) {
        int n = nums.length;
        int xorAll = 0;
        int xorNums = 0;

        // XOR from 0 to n
        for (int i = 0; i <= n; i++) {
            xorAll ^= i;
        }

        // XOR all elements in nums
        for (int num : nums) {
            xorNums ^= num;
        }

        // Missing number is xorAll ^ xorNums
        return xorAll ^ xorNums;
    }

    public static void main(String[] args) {
        int[] nums = {3, 0, 1};

        // Test Sum Formula
        System.out.println("Sum Formula Result: " + missingNumberSum(nums));

        // Test XOR Trick
        System.out.println("XOR Trick Result: " + missingNumberXOR(nums));
    }
}
