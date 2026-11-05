/*
Problem: Single Number

Given a non-empty array of integers nums.

Every element appears exactly twice except for one element
which appears only once.

Return that single element.

Source: LeetCode Problem #136
https://leetcode.com/problems/single-number/

Approaches:

1. Brute Force
   - Idea:
     - For every element, count its occurrences.
     - If count == 1, return that element.

   - Time Complexity: O(n²)
   - Space Complexity: O(1)

2. Better (HashMap Frequency Count)
   - Idea:
     - Store frequency of each element in a HashMap.
     - Traverse the map and return the element
       whose frequency is 1.

   - Time Complexity: O(n)
   - Space Complexity: O(n)

3. Optimized (Bit Manipulation - XOR)
   - Idea:
     - XOR of same numbers becomes 0.
     - XOR of 0 with a number gives the number.
     - Therefore:
           a ^ a = 0
           a ^ 0 = a
     - XOR all elements.
     - Duplicate numbers cancel out.
     - Remaining value is the answer.

   - Time Complexity: O(n)
   - Space Complexity: O(1)
*/

import java.util.*;

// Approach 1: Brute Force
class SingleNumberBrute {

    public int singleNumber(int[] nums) {

        for (int i = 0; i < nums.length; i++) {

            int count = 0;

            for (int j = 0; j < nums.length; j++) {

                if (nums[i] == nums[j]) {
                    count++;
                }
            }

            if (count == 1) {
                return nums[i];
            }
        }

        return -1;
    }
}

// Approach 2: Better (HashMap)
class SingleNumberBetter {

    public int singleNumber(int[] nums) {

        HashMap<Integer, Integer> map =
                new HashMap<>();

        for (int num : nums) {

            map.put(
                    num,
                    map.getOrDefault(num, 0) + 1
            );
        }

        for (int key : map.keySet()) {

            if (map.get(key) == 1) {
                return key;
            }
        }

        return -1;
    }
}

// Approach 3: Optimized (XOR)
class SingleNumberOptimized {

    public int singleNumber(int[] nums) {

        int xor = 0;

        for (int num : nums) {
            xor ^= num;
        }

        return xor;
    }
}

// Driver Class
public class Main {

    public static void main(String[] args) {

        int[] nums = {4, 1, 2, 1, 2};

        SingleNumberBrute brute =
                new SingleNumberBrute();

        System.out.println(
                "Brute Force Output: "
                + brute.singleNumber(nums)
        ); // 4

        SingleNumberBetter better =
                new SingleNumberBetter();

        System.out.println(
                "Better Output: "
                + better.singleNumber(nums)
        ); // 4

        SingleNumberOptimized optimized =
                new SingleNumberOptimized();

        System.out.println(
                "Optimized Output: "
                + optimized.singleNumber(nums)
        ); // 4
    }
}
