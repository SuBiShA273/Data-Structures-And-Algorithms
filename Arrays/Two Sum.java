/*
Problem: Two Sum

Given an array of integers nums and an integer target.

Return the indices of the two numbers such that they add up
to target.

You may assume that exactly one solution exists,
and you may not use the same element twice.

Source: LeetCode Problem #1
https://leetcode.com/problems/two-sum/

Approaches:

1. Brute Force
   - Idea:
     - Check every possible pair.
     - If nums[i] + nums[j] == target,
       return {i, j}.

   - Time Complexity: O(n²)
   - Space Complexity: O(1)

2. Better (HashMap)
   - Idea:
     - For each element, calculate:
           complement = target - nums[i]
     - Check if the complement already exists in HashMap.
     - If yes, return both indices.
     - Otherwise store current number and its index.

   - Time Complexity: O(n)
   - Space Complexity: O(n)

3. Optimized (One Pass HashMap)
   - Idea:
     - Same as Better approach.
     - Perform lookup and insertion in a single traversal.
     - Most commonly accepted interview solution.

   - Time Complexity: O(n)
   - Space Complexity: O(n)
*/

import java.util.*;

// Approach 1: Brute Force
class TwoSumBrute {

    public int[] twoSum(int[] nums, int target) {

        for (int i = 0; i < nums.length; i++) {

            for (int j = i + 1; j < nums.length; j++) {

                if (nums[i] + nums[j] == target) {

                    return new int[]{
                            i,
                            j
                    };
                }
            }
        }

        return new int[]{-1, -1};
    }
}

// Approach 2: Better (HashMap)
class TwoSumBetter {

    public int[] twoSum(int[] nums, int target) {

        HashMap<Integer, Integer> map =
                new HashMap<>();

        for (int i = 0; i < nums.length; i++) {

            map.put(nums[i], i);
        }

        for (int i = 0; i < nums.length; i++) {

            int complement =
                    target - nums[i];

            if (map.containsKey(complement)
                    && map.get(complement) != i) {

                return new int[]{
                        i,
                        map.get(complement)
                };
            }
        }

        return new int[]{-1, -1};
    }
}

// Approach 3: Optimized (One Pass HashMap)
class TwoSumOptimized {

    public int[] twoSum(int[] nums, int target) {

        HashMap<Integer, Integer> map =
                new HashMap<>();

        for (int i = 0; i < nums.length; i++) {

            int complement =
                    target - nums[i];

            if (map.containsKey(complement)) {

                return new int[]{
                        map.get(complement),
                        i
                };
            }

            map.put(nums[i], i);
        }

        return new int[]{-1, -1};
    }
}

// Driver Class
public class Main {

    public static void main(String[] args) {

        int[] nums = {2, 7, 11, 15};
        int target = 9;

        TwoSumBrute brute =
                new TwoSumBrute();

        System.out.println(
                Arrays.toString(
                        brute.twoSum(nums, target)
                )
        ); // [0, 1]

        TwoSumBetter better =
                new TwoSumBetter();

        System.out.println(
                Arrays.toString(
                        better.twoSum(nums, target)
                )
        ); // [0, 1]

        TwoSumOptimized optimized =
                new TwoSumOptimized();

        System.out.println(
                Arrays.toString(
                        optimized.twoSum(nums, target)
                )
        ); // [0, 1]
    }
}
