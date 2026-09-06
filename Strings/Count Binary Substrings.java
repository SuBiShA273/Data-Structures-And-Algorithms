/*
Problem: Given a binary string s, return the number of non-empty substrings
that have the same number of consecutive 0's and 1's, and all 0's and 1's
in these substrings are grouped consecutively.

Source: LeetCode Problem #696 → https://leetcode.com/problems/count-binary-substrings/

Approach: Optimized (Two Counters)
   - Idea:
     - Track lengths of consecutive groups of characters.
     - Maintain prevc (previous group count) and curc (current group count).
     - When character changes, add min(prevc, curc) to answer.
     - At the end, add min(prevc, curc) one last time.
   - Time Complexity: O(n).
   - Space Complexity: O(1).
*/

class Solution {
    public int countBinarySubstrings(String s) {
        int prevc = 0, curc = 1;
        int ans = 0;
        for (int i = 1; i < s.length(); i++) {
            if (s.charAt(i - 1) == s.charAt(i)) {
                curc++;
            } else {
                ans += Math.min(prevc, curc);
                prevc = curc;
                curc = 1;
            }
        }
        return ans + Math.min(prevc, curc);
    }
}

// Driver class
public class Main {
    public static void main(String[] args) {
        Solution sol = new Solution();

        String s1 = "00110011"; // Expected 6
        String s2 = "10101";    // Expected 4

        System.out.println("Optimized Result (00110011): " + sol.countBinarySubstrings(s1));
        System.out.println("Optimized Result (10101): " + sol.countBinarySubstrings(s2));
    }
}
