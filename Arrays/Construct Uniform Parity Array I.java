/*
Problem: Construct Uniform Parity Array I
Return true if it's possible to construct nums2 with all odd or all even elements.

Source: LeetCode Problem #3875 → https://leetcode.com/problems/construct-uniform-parity-array-i/

Optimal Approach:
   - If all elements are odd → true.
   - If all elements are even → true.
   - If mix of odd and even → still true (differences allow construction).
   - Hence, always return true.
   - Time Complexity: O(1).
   - Space Complexity: O(1).
*/

class Solution {
    public boolean canConstruct(int[] nums1) {
        return true; // Always possible
    }
}

// Driver class
public class Main {
    public static void main(String[] args) {
        Solution sol = new Solution();

        int[] nums1 = {2,3};
        int[] nums2 = {4,6};
        int[] nums3 = {1,5,9};

        System.out.println(sol.canConstruct(nums1)); // true
        System.out.println(sol.canConstruct(nums2)); // true
        System.out.println(sol.canConstruct(nums3)); // true
    }
}
