/*
Problem: Rearrange Array Elements by Sign

Given an array nums of even length containing equal numbers
of positive and negative integers.

Rearrange the array such that:
1. Every consecutive pair has opposite signs.
2. Relative order of positive numbers is preserved.
3. Relative order of negative numbers is preserved.
4. Array starts with a positive number.

Source: LeetCode Problem #2149
https://leetcode.com/problems/rearrange-array-elements-by-sign/

Approaches:

1. Brute Force
   - Idea:
     - Store positives and negatives separately.
     - Merge them alternately into a new array.

   - Time Complexity: O(n)
   - Space Complexity: O(n)

2. Better
   - Idea:
     - Use two arrays:
           positives[]
           negatives[]
     - Fill answer array alternatively.

   - Time Complexity: O(n)
   - Space Complexity: O(n)

3. Optimized
   - Idea:
     - Directly place positive numbers at even indices.
     - Directly place negative numbers at odd indices.
     - Preserve relative ordering automatically.

   - Time Complexity: O(n)
   - Space Complexity: O(n)

   - Most Interview Preferred ✅
*/

import java.util.*;

// Approach 1: Brute Force
class RearrangeArrayBrute {

    public int[] rearrangeArray(int[] nums) {

        List<Integer> positives = new ArrayList<>();
        List<Integer> negatives = new ArrayList<>();

        for (int num : nums) {

            if (num > 0) {
                positives.add(num);
            } else {
                negatives.add(num);
            }
        }

        int[] ans = new int[nums.length];

        int pos = 0;
        int neg = 0;

        for (int i = 0; i < nums.length; i++) {

            if (i % 2 == 0) {
                ans[i] = positives.get(pos++);
            } else {
                ans[i] = negatives.get(neg++);
            }
        }

        return ans;
    }
}

// Approach 2: Better
class RearrangeArrayBetter {

    public int[] rearrangeArray(int[] nums) {

        int n = nums.length;

        int[] positive = new int[n / 2];
        int[] negative = new int[n / 2];

        int p = 0;
        int ne = 0;

        for (int num : nums) {

            if (num > 0) {
                positive[p++] = num;
            } else {
                negative[ne++] = num;
            }
        }

        int[] ans = new int[n];

        p = 0;
        ne = 0;

        for (int i = 0; i < n; i++) {

            if (i % 2 == 0) {
                ans[i] = positive[p++];
            } else {
                ans[i] = negative[ne++];
            }
        }

        return ans;
    }
}

// Approach 3: Optimized
class RearrangeArrayOptimized {

    public int[] rearrangeArray(int[] nums) {

        int[] ans = new int[nums.length];

        int posIndex = 0;
        int negIndex = 1;

        for (int num : nums) {

            if (num > 0) {

                ans[posIndex] = num;
                posIndex += 2;

            } else {

                ans[negIndex] = num;
                negIndex += 2;
            }
        }

        return ans;
    }
}

// Driver Class
public class Main {

    public static void main(String[] args) {

        int[] nums = {3, 1, -2, -5, 2, -4};

        RearrangeArrayBrute brute = new RearrangeArrayBrute();
        System.out.println("Brute Force Output: " + Arrays.toString(brute.rearrangeArray(nums)));

        RearrangeArrayBetter better = new RearrangeArrayBetter();
        System.out.println("Better Output: " + Arrays.toString(better.rearrangeArray(nums)));

        RearrangeArrayOptimized optimized = new RearrangeArrayOptimized();
        System.out.println("Optimized Output: " + Arrays.toString(optimized.rearrangeArray(nums)));

        // Output:
        // [3, -2, 1, -5, 2, -4]
    }
}
