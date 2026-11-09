/*
Problem: Minimum Insertion Steps to Make a String Palindrome

Given a string s.

Return the minimum number of insertions needed
to make s a palindrome.

Source: LeetCode Problem #1312
https://leetcode.com/problems/minimum-insertion-steps-to-make-a-string-palindrome/

Approaches:

1. Recursion
   - Idea:
     - Compare characters at both ends.
     - If they match, move inward.
     - Otherwise, insert a character on either side.
     - Take the minimum of both possibilities.

   - Time Complexity: O(2^n)
   - Space Complexity: O(n)

2. Memoization
   - Idea:
     - Store results for every state:
           dp[left][right]
     - Avoid recomputing overlapping subproblems.

   - Time Complexity: O(n²)
   - Space Complexity: O(n²)

3. Tabulation
   - Idea:
     - Build answers for smaller substrings first.
     - If characters match:
           dp[i][j] = dp[i+1][j-1]
     - Otherwise:
           dp[i][j] = 1 + min(dp[i+1][j], dp[i][j-1])

   - Time Complexity: O(n²)
   - Space Complexity: O(n²)

4. Space Optimization
   - Idea:
     - Minimum Insertions = n - LPS
     - LPS (Longest Palindromic Subsequence)
       = LCS(s, reverse(s))
     - Use space-optimized LCS.

   - Time Complexity: O(n²)
   - Space Complexity: O(n)
*/

import java.util.*;

// Approach 1: Recursion
class MinInsertionsRecursion {

    public int minInsertions(String s) {
        return helper(0, s.length() - 1, s);
    }

    private int helper(int left, int right, String s) {

        if (left >= right) {
            return 0;
        }

        if (s.charAt(left) == s.charAt(right)) {
            return helper(left + 1, right - 1, s);
        }

        return 1 + Math.min(
  
