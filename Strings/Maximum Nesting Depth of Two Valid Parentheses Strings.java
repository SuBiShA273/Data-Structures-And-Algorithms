/*
Problem: A string seq is a valid parentheses string. 
You need to split seq into two disjoint subsequences A and B such that:
- Every character belongs to exactly one subsequence.
- Both A and B are valid parentheses strings.
Return an array answer where answer[i] = 0 if seq[i] is in A, and 1 if seq[i] is in B.
The goal is to minimize the maximum nesting depth between A and B.

Source: LeetCode Problem #1111 → https://leetcode.com/problems/maximum-nesting-depth-of-two-valid-parentheses-strings/

Approach: Depth Parity Assignment
   - Idea:
     - Track current depth while traversing seq.
     - Assign parentheses alternately based on depth parity (even/odd).
     - This balances nesting depth between A and B.
   - Time Complexity: O(n).
   - Space Complexity: O(n).
*/

import java.util.*;

class Solution {
    public int[] maxDepthAfterSplit(String seq) {
        int n = seq.length();
        int[] ans = new int[n];
        int depth = 0;
        for (int i = 0; i < n; i++) {
            if (seq.charAt(i) == '(') {
                depth++;
                ans[i] = depth % 2; // assign based on parity
            } else {
                ans[i] = depth % 2;
                depth--;
            }
        }
        return ans;
    }
}

// Driver class with testcases
public class Main {
    public static void main(String[] args) {
        Solution sol = new Solution();

        // Testcase 1
        String seq1 = "(()())";
        int[] res1 = sol.maxDepthAfterSplit(seq1);
        System.out.println("Testcase 1 Input: " + seq1);
        System.out.println("Testcase 1 Output: " + Arrays.toString(res1));
        // Expected: [1,0,0,0,0,1] or similar valid split

        // Testcase 2
        String seq2 = "()(())()";
        int[] res2 = sol.maxDepthAfterSplit(seq2);
        System.out.println("\nTestcase 2 Input: " + seq2);
        System.out.println("Testcase 2 Output: " + Arrays.toString(res2));
        // Expected: [0,0,0,1,1,0,0,0]

        // Testcase 3
        String seq3 = "((()))";
        int[] res3 = sol.maxDepthAfterSplit(seq3);
        System.out.println("\nTestcase 3 Input: " + seq3);
        System.out.println("Testcase 3 Output: " + Arrays.toString(res3));
        // Expected: [1,0,1,1,0,1] or similar valid split
    }
}
