/*
Problem: You are given a string s that consists of lowercase English letters and parentheses.
Reverse the strings in each pair of matching parentheses, starting from the innermost one.
Return the final string without any parentheses.

Source: LeetCode Problem #1190 → https://leetcode.com/problems/reverse-substrings-between-each-pair-of-parentheses/

Approach: Optimized (Wormhole + Direction Switching)
   - Idea:
     - Precompute matching parentheses indices using a stack.
     - Store them in a "wormhole" array (each '(' points to its matching ')').
     - Traverse the string with a direction variable:
       - If encountering '(' or ')', jump to its matching index and reverse direction.
       - Otherwise, append character to result.
     - This simulates reversing substrings without explicitly rebuilding them.
   - Time Complexity: O(n).
   - Space Complexity: O(n).
*/

import java.util.*;

public class ReverseParentheses {

    // Approach: Wormhole + Direction Switching
    public static String reverseParenthesesOptimal(String s) {
        Stack<Integer> st = new Stack<>();
        int[] wormhole = new int[s.length()];

        // Step 1: Precompute matching parentheses
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '(') {
                st.push(i);
            } else if (s.charAt(i) == ')') {
                int ind = st.pop();
                wormhole[i] = ind;
                wormhole[ind] = i;
            }
        }

        // Step 2: Traverse with direction switching
        int dirn = 1;
        StringBuilder res = new StringBuilder();
        for (int i = 0; i >= 0 && i < s.length(); i += dirn) {
            char ch = s.charAt(i);
            if (ch == '(' || ch == ')') {
                i = wormhole[i];
                dirn = -dirn;
            } else {
                res.append(ch);
            }
        }
        return res.toString();
    }

    // Driver
    public static void main(String[] args) {
        String s1 = "(abcd)";
        System.out.println("Optimized Result 1: " + reverseParenthesesOptimal(s1)); // dcba

        String s2 = "(u(love)i)";
        System.out.println("Optimized Result 2: " + reverseParenthesesOptimal(s2)); // iloveu

        String s3 = "(ed(et(oc))el)";
        System.out.println("Optimized Result 3: " + reverseParenthesesOptimal(s3)); // leetcode

        String s4 = "a(bcdefghijkl(mno)p)q";
        System.out.println("Optimized Result 4: " + reverseParenthesesOptimal(s4)); // apmnolkjihgfedcbq
    }
}
