/*
Problem: You are given a binary string s and a positive integer k.
A substring of s is beautiful if the number of '1's in it is exactly k.
Let len be the length of the shortest beautiful substring.
Return the lexicographically smallest beautiful substring of s with length equal to len.
If s doesn't contain a beautiful substring, return an empty string.

Source: LeetCode Problem #2904 → https://leetcode.com/problems/shortest-and-lexicographically-smallest-beautiful-string/

Approaches:
1. Brute Force (Enumerate All Substrings)
   - Idea:
     - Generate all substrings.
     - Count number of '1's in each substring.
     - Track shortest length substrings with exactly k ones.
     - Among them, return lexicographically smallest.
   - Time Complexity: O(n^3).
   - Space Complexity: O(n).

2. Optimized (Sliding Window / Two Pointers)
   - Idea:
     - Use two pointers to maintain a window.
     - Expand right pointer until we have k ones.
     - Shrink left pointer to minimize length while keeping k ones.
     - Track shortest substring and update lexicographically smallest.
   - Time Complexity: O(n).
   - Space Complexity: O(1).
*/

import java.util.*;

// Approach 1: Brute Force
class ShortestBeautifulSubstringBrute {
    public String shortestBeautifulSubstring(String s, int k) {
        int n = s.length();
        String ans = "";
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j <= n; j++) {
                String sub = s.substring(i, j);
                int count = 0;
                for (char c : sub.toCharArray()) {
                    if (c == '1') count++;
                }
                if (count == k) {
                    if (ans.equals("") || sub.length() < ans.length() ||
                        (sub.length() == ans.length() && sub.compareTo(ans) < 0)) {
                        ans = sub;
                    }
                }
            }
        }
        return ans;
    }
}

// Approach 2: Optimized (Sliding Window)
class ShortestBeautifulSubstringOptimized {
    public String shortestBeautifulSubstring(String s, int k) {
        int l = 0;          // left pointer
        int one = 0;        // count of '1's in current window
        String res = "";    // result substring

        for (int r = 0; r < s.length(); r++) {
            if (s.charAt(r) == '1')
                one++;

            // Shrink left pointer if too many '1's or leading zeros
            while (one > k || (l <= r && s.charAt(l) == '0')) {
                if (s.charAt(l) == '1')
                    one--;
                l++;
            }

            // If window has exactly k ones, check candidate substring
            if (one == k) {
                String curr = s.substring(l, r + 1);
                if (res.isEmpty() || curr.length() < res.length() ||
                    (curr.length() == res.length() && curr.compareTo(res) < 0)) {
                    res = curr;
                }
            }
        }
        return res;
    }
}

// Driver class
public class Main {
    public static void main(String[] args) {
        String s1 = "100011001"; int k1 = 3;
        String s2 = "1011"; int k2 = 2;
        String s3 = "000"; int k3 = 1;

        // Test Brute Force
        ShortestBeautifulSubstringBrute brute = new ShortestBeautifulSubstringBrute();
        System.out.println("Brute Force Result (s1,k=3): " + brute.shortestBeautifulSubstring(s1, k1)); // "11001"
        System.out.println("Brute Force Result (s2,k=2): " + brute.shortestBeautifulSubstring(s2, k2)); // "11"
        System.out.println("Brute Force Result (s3,k=1): " + brute.shortestBeautifulSubstring(s3, k3)); // ""

        // Test Optimized
        ShortestBeautifulSubstringOptimized opt = new ShortestBeautifulSubstringOptimized();
        System.out.println("Optimized Result (s1,k=3): " + opt.shortestBeautifulSubstring(s1, k1)); // "11001"
        System.out.println("Optimized Result (s2,k=2): " + opt.shortestBeautifulSubstring(s2, k2)); // "11"
        System.out.println("Optimized Result (s3,k=1): " + opt.shortestBeautifulSubstring(s3, k3)); // ""
    }
}
