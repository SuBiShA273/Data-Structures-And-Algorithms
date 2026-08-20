/*
Problem: You are given an integer array nums. 
Split nums into two arrays arr1 and arr2 using the following rule:
- Start with arr1 containing nums[0], arr2 containing nums[1].
- For each subsequent element nums[i]:
    - If last element of arr1 > last element of arr2 → put nums[i] into arr1.
    - Otherwise → put nums[i] into arr2.
Finally, return the merged array arr1 followed by arr2.

Source: LeetCode Problem #3069 → https://leetcode.com/problems/form-two-arrays-and-merge/

Approach: Simulation
   - Use two lists arr1 and arr2.
   - Initialize arr1 with nums[0], arr2 with nums[1].
   - Traverse from index 2 onward, compare last elements of arr1 and arr2.
   - Append nums[i] to whichever array satisfies the rule.
   - Merge arr1 and arr2 into the final result.
   - Time Complexity: O(n)
   - Space Complexity: O(n)
*/

import java.util.*;

class Solution {
    public int[] resultArray(int[] nums) {
        // Step 1: Create lists to simulate arr1 and arr2
        List<Integer> arr1 = new ArrayList<>();
        List<Integer> arr2 = new ArrayList<>();

        // Step 2: Initialize with first two elements
        arr1.add(nums[0]);
        arr2.add(nums[1]);

        // Step 3: Distribute remaining elements
        for (int i = 2; i < nums.length; i++) {
            int lastArr1 = arr1.get(arr1.size() - 1);
            int lastArr2 = arr2.get(arr2.size() - 1);

            if (lastArr1 > lastArr2) {
                arr1.add(nums[i]);
            } else {
                arr2.add(nums[i]);
            }
        }

        // Step 4: Merge arr1 and arr2
        int[] result = new int[nums.length];
        int index = 0;

        for (int num : arr1) {
            result[index++] = num;
        }
        for (int num : arr2) {
            result[index++] = num;
        }

        return result;
    }
}

// Driver class
public class Main {
    public static void main(String[] args) {
        Solution sol = new Solution();
        int[] nums = {2,1,3,3};
        int[] result = sol.resultArray(nums);

        System.out.println("Result Array: " + Arrays.toString(result));
        // Expected Output: [2,3,1,3]
    }
}
