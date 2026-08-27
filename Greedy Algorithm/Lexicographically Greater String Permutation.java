/*
Problem: You are given two strings s and target.
Return the lexicographically smallest string that can be formed by permuting characters of s
such that the result is strictly greater than target.
If no such permutation exists, return "".

Source: LeetCode Problem #3720 → https://leetcode.com/problems/lexicographically-greater-string-permutation/

Approaches:
1. Brute Force (Generate All Permutations)
   - Idea:
     - Generate all permutations of s.
     - Compare each with target.
     - Track the lexicographically smallest valid permutation greater than target.
   - Time Complexity: O(n!).
   - Space Complexity: O(n).

2. Optimized (Greedy Swap + Counting)
   - Idea:
     - Count frequency of characters in s.
     - Traverse target from left to right:
       - At each position, try to place a character strictly greater than target[i].
       - If possible, fix that position and fill the rest with smallest available characters.
     - If no position works, return "".
   - Time Complexity: O(n * 26).
   - Space Complexity: O(26).
*/

import java.util.*;

// Approach 1: Brute Force (Backtracking Permutations)
class LexGreaterPermutationBrute {
    private String ans = "";
    public String lexGreaterPermutation(String s, String target) {
        char[] arr = s.toCharArray();
        Arrays.sort(arr); // start with smallest permutation
        backtrack(arr, new boolean[arr.length], new StringBuilder(), target);
        return ans;
    }

    private void backtrack(char[] arr, boolean[] used, StringBuilder sb, String target) {
        if (sb.length() == arr.length) {
            String candidate = sb.toString();
            if (candidate.compareTo(target) > 0) {
                if (ans.equals("") || candidate.compareTo(ans) < 0) {
                    ans = candidate;
                }
            }
            return;
        }
        for (int i = 0; i < arr.length; i++) {
            if (used[i]) continue;
            used[i] = true;
            sb.append(arr[i]);
            backtrack(arr, used, sb, target);
            sb.deleteCharAt(sb.length() - 1);
            used[i] = false;
        }
    }
}

// Approach 2: Optimized (Greedy Swap + Counting)
class LexGreaterPermutationOptimized {
    public String lexGreaterPermutation(String s, String target) {
        int n = s.length();
        int[] counts = new int[26];
        int[] currcount = new int[26];

        for (int i = 0; i < n; i++) {
            counts[s.charAt(i) - 'a']++;
            currcount[s.charAt(i) - 'a']++;
        }

        int idx = -1, charswap = -1;

        // Traverse target
        for (int i = 0; i < n; i++) {
            int targetidx = target.charAt(i) - 'a';
            for (int j = targetidx + 1; j < 26; j++) {
                if (currcount[j] > 0) {
                    idx = i;
                    charswap = j;
                    break;
                }
            }
            if (currcount[targetidx] > 0) {
                currcount[targetidx]--;
            } else {
                break;
            }
        }

        if (idx == -1) return "";

        StringBuilder res = new StringBuilder();
        for (int i = 0; i < idx; i++) {
            char c = target.charAt(i);
            res.append(c);
            counts[c - 'a']--;
        }

        res.append((char) ('a' + charswap));
        counts[charswap]--;

        for (int i = 0; i < 26; i++) {
            while (counts[i] > 0) {
                res.append((char) ('a' + i));
                counts[i]--;
            }
        }
        return res.toString();
    }
}

// Driver class
public class Main {
    public static void main(String[] args) {
        String s1 = "abc"; String target1 = "acb";
        String s2 = "bca"; String target2 = "bac";

        // Test Brute Force
        LexGreaterPermutationBrute brute = new LexGreaterPermutationBrute();
        System.out.println("Brute Force Result (s1,target1): " + brute.lexGreaterPermutation(s1, target1));
        System.out.println("Brute Force Result (s2,target2): " + brute.lexGreaterPermutation(s2, target2));

        // Test Optimized
        LexGreaterPermutationOptimized opt = new LexGreaterPermutationOptimized();
        System.out.println("Optimized Result (s1,target1): " + opt.lexGreaterPermutation(s1, target1));
        System.out.println("Optimized Result (s2,target2): " + opt.lexGreaterPermutation(s2, target2));
    }
}
