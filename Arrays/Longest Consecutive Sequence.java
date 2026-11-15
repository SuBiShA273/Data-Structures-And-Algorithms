/*
Problem: Longest Consecutive Sequence

Given an unsorted array of integers nums.

Return the length of the longest consecutive elements sequence.

You must write an algorithm that runs in O(n) time.

Source: LeetCode Problem #128
https://leetcode.com/problems/longest-consecutive-sequence/

Approaches:

1. Brute Force
   - Idea:
     - For every element, keep checking whether
       the next consecutive number exists.
     - Use linear search every time.

   - Time Complexity: O(n²)
   - Space Complexity: O(1)

2. Better (Sorting)
   - Idea:
     - Sort the array.
     - Traverse the sorted array and count
       consecutive elements.
     - Ignore duplicates.

   - Time Complexity: O(n log n)
   - Space Complexity: O(1)

3. Optimized (HashSet)
   - Idea:
     - Store all numbers in a HashSet.
     - Start counting only when a number
       is the beginning of a sequence.
     - A number is the start if:
             num - 1
       does not exist in the HashSet.

   - Time Complexity: O(n)
   - Space Complexity: O(n)

   - Most Interview Preferred ✅
*/

import java.util.*;

// Approach 1: Brute Force
class LongestConsecutiveBrute {

    public int longestConsecutive(int[] nums) {

        int longest = 0;

        for (int num : nums) {

            int current = num;
            int count = 1;

            while (contains(nums, current + 1)) {
                current++;
                count++;
            }

            longest = Math.max(longest, count);
        }

        return longest;
    }

    private boolean contains(int[] nums, int target) {

        for (int num : nums) {

            if (num == target) {
                return true;
            }
        }

        return false;
    }
}

// Approach 2: Better (Sorting)
class LongestConsecutiveBetter {

    public int longestConsecutive(int[] nums) {

        if (nums.length == 0) {
            return 0;
        }

        Arrays.sort(nums);

        int longest = 1;
        int count = 1;

        for (int i = 1; i < nums.length; i++) {

            if (nums[i] == nums[i - 1]) {
                continue;
            }

            if (nums[i] == nums[i - 1] + 1) {

                count++;

            } else {

                count = 1;
            }

            longest = Math.max(longest, count);
        }

        return longest;
    }
}

// Approach 3: Optimized (HashSet)
class LongestConsecutiveOptimized {

    public int longestConsecutive(int[] nums) {

        HashSet<Integer> set = new HashSet<>();

        for (int num : nums) {
            set.add(num);
        }

        int longest = 0;

        for (int num : set) {

            if (!set.contains(num - 1)) {

                int current = num;
                int count = 1;

                while (set.contains(current + 1)) {

                    current++;
                    count++;
                }

                longest = Math.max(longest, count);
            }
        }

        return longest;
    }
}

// Driver Class
public class Main {

    public static void main(String[] args) {

        int[] nums = {100, 4, 200, 1, 3, 2};

        LongestConsecutiveBrute brute =
                new LongestConsecutiveBrute();

        System.out.println(
                "Brute Force Output: "
                + brute.longestConsecutive(nums)
        ); // 4

        LongestConsecutiveBetter better =
                new LongestConsecutiveBetter();

        System.out.println(
                "Better Output: "
                + better.longestConsecutive(nums)
        ); // 4

        LongestConsecutiveOptimized optimized =
                new LongestConsecutiveOptimized();

        System.out.println(
                "Optimized Output: "
                + optimized.longestConsecutive(nums)
        ); // 4
    }
}
