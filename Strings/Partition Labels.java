/*
Problem: You are given a string s. We want to partition the string into as many parts as possible
so that each letter appears in at most one part. Return a list of integers representing the size of these parts.

Source: LeetCode Problem #763 → https://leetcode.com/problems/partition-labels/

Approaches:
1. Brute Force (Greedy Check with HashSet)
   - Idea:
     - For each partition, keep track of characters seen.
     - Extend partition until all characters in it do not appear later.
     - Inefficient because we repeatedly scan ahead.
   - Time Complexity: O(n^2).
   - Space Complexity: O(n).

2. Optimal (Greedy with Last Occurrence Map)
   - Idea:
     - Precompute last index of each character.
     - Traverse string, track furthest last index seen so far.
     - When current index == furthest last index, cut partition.
     - Efficient single pass.
   - Time Complexity: O(n).
   - Space Complexity: O(26) ≈ O(1).
*/

import java.util.*;

class BruteForce {
    public List<Integer> partitionLabels(String s) {
        List<Integer> res = new ArrayList<>();
        int i = 0;
        while (i < s.length()) {
            Set<Character> set = new HashSet<>();
            int j = i;
            int end = i;
            while (j < s.length()) {
                set.add(s.charAt(j));
                end = j;
                boolean valid = true;
                for (char c : set) {
                    if (s.lastIndexOf(c) > end) {
                        valid = false;
                        break;
                    }
                }
                if (valid) break;
                j++;
            }
            res.add(end - i + 1);
            i = end + 1;
        }
        return res;
    }
}

class Optimal {
    public List<Integer> partitionLabels(String s) {
        List<Integer> res = new ArrayList<>();
        int[] last = new int[26];
        for (int i = 0; i < s.length(); i++) {
            last[s.charAt(i) - 'a'] = i;
        }
        int start = 0, end = 0;
        for (int i = 0; i < s.length(); i++) {
            end = Math.max(end, last[s.charAt(i) - 'a']);
            if (i == end) {
                res.add(end - start + 1);
                start = i + 1;
            }
        }
        return res;
    }
}

// Driver class with testcases
public class Main {
    public static void main(String[] args) {
        BruteForce brute = new BruteForce();
        Optimal opt = new Optimal();

        // Testcase 1
        String s1 = "ababcbacadefegdehijhklij";
        System.out.println("Brute Force Result (Testcase 1): " + brute.partitionLabels(s1)); // Expected: [9,7,8]
        System.out.println("Optimal Result (Testcase 1): " + opt.partitionLabels(s1));       // Expected: [9,7,8]

        // Testcase 2
        String s2 = "eccbbbbdec";
        System.out.println("\nBrute Force Result (Testcase 2): " + brute.partitionLabels(s2)); // Expected: [10]
        System.out.println("Optimal Result (Testcase 2): " + opt.partitionLabels(s2));         // Expected: [10]

        // Testcase 3
        String s3 = "caedbdedda";
        System.out.println("\nBrute Force Result (Testcase 3): " + brute.partitionLabels(s3)); // Expected: [1,9]
        System.out.println("Optimal Result (Testcase 3): " + opt.partitionLabels(s3));         // Expected: [1,9]
    }
}
