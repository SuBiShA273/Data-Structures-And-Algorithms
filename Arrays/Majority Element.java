/*
Problem: Majority Element

Given an array nums of size n.

Return the majority element.

The majority element is the element that appears
more than ⌊n / 2⌋ times.

You may assume that the majority element always exists.

Source: LeetCode Problem #169
https://leetcode.com/problems/majority-element/

Approaches:

1. Brute Force
   - Idea:
     - For every element, count its occurrences.
     - If count > n/2, return that element.

   - Time Complexity: O(n²)
   - Space Complexity: O(1)

2. Better (HashMap)
   - Idea:
     - Store frequency of each element.
     - Return the element whose frequency
       exceeds n/2.

   - Time Complexity: O(n)
   - Space Complexity: O(n)

3. Optimized (Moore's Voting Algorithm)
   - Idea:
     - Maintain a candidate and count.
     - If count becomes 0, choose current element
       as the new candidate.
     - Increase count when candidate matches.
     - Decrease count otherwise.
     - Final candidate is the majority element.

   - Time Complexity: O(n)
   - Space Complexity: O(1)

   - Most Interview Preferred ✅
*/

import java.util.*;

// Approach 1: Brute Force
class MajorityElementBrute {

    public int majorityElement(int[] nums) {

        int n = nums.length;

        for (int i = 0; i < n; i++) {

            int count = 0;

            for (int j = 0; j < n; j++) {

                if (nums[i] == nums[j]) {
                    count++;
                }
            }

            if (count > n / 2) {
                return nums[i];
            }
        }

        return -1;
    }
}

// Approach 2: Better (HashMap)
class MajorityElementBetter {

    public int majorityElement(int[] nums) {

        HashMap<Integer, Integer> map =
                new HashMap<>();

        for (int num : nums) {

            map.put(
                    num,
                    map.getOrDefault(num, 0) + 1
            );
        }

        int n = nums.length;

        for (int key : map.keySet()) {

            if (map.get(key) > n / 2) {
                return key;
            }
        }

        return -1;
    }
}

// Approach 3: Optimized (Moore's Voting Algorithm)
class MajorityElementOptimized {

    public int majorityElement(int[] nums) {

        int candidate = 0;
        int count = 0;

        for (int num : nums) {

            if (count == 0) {
                candidate = num;
            }

            if (num == candidate) {
                count++;
            } else {
                count--;
            }
        }

        return candidate;
    }
}

// Driver Class
public class Main {

    public static void main(String[] args) {

        int[] nums = {2, 2, 1, 1, 1, 2, 2};

        MajorityElementBrute brute =
                new MajorityElementBrute();

        System.out.println(
                "Brute Force Output: "
                + brute.majorityElement(nums)
        ); // 2

        MajorityElementBetter better =
                new MajorityElementBetter();

        System.out.println(
                "Better Output: "
                + better.majorityElement(nums)
        ); // 2

        MajorityElementOptimized optimized =
                new MajorityElementOptimized();

        System.out.println(
                "Optimized Output: "
                + optimized.majorityElement(nums)
        ); // 2
    }
}
