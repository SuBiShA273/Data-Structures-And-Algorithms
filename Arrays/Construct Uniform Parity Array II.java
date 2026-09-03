/*
Problem: Construct Uniform Parity Array II
Return true if it's possible to construct nums2 with all odd or all even elements.

Source: LeetCode Problem #3876 → https://leetcode.com/problems/construct-uniform-parity-array-ii/

Optimal Approach:
   - If all elements are odd → true.
   - If all elements are even → true.
   - If mix → find smallest odd.
       - If any even < smallest odd → false.
       - Else → true.
   - Time Complexity: O(n).
   - Space Complexity: O(1).
*/

class Solution {
    public boolean uniformArray(int[] nums1) {
        int minOdd = Integer.MAX_VALUE;
        for (int x : nums1) {
            if (x % 2 == 1) {
                minOdd = Math.min(minOdd, x);
            }
        }
        for (int x : nums1) {
            if (x % 2 == 0 && minOdd != Integer.MAX_VALUE && x < minOdd) {
                return false;
            }
        }
        return true;
    }
}

// Driver class
public class Main {
    public static void main(String[] args) {
        Solution sol = new Solution();

        int[] nums1 = {1,4,7};   // true
        int[] nums2 = {2,3};     // false
        int[] nums3 = {4,6};     // true

        System.out.println(sol.uniformArray(nums1));
        System.out.println(sol.uniformArray(nums2));
        System.out.println(sol.uniformArray(nums3));
    }
}
