/*
Problem: Given two strings s and t of equal length, return the minimum number of steps 
to make t an anagram of s. In one step, you can replace any character in t with another character.

Source: LeetCode Problem #1347 → https://leetcode.com/problems/minimum-number-of-steps-to-make-two-strings-anagram/

Approaches:
1. Brute Force (Sorting + Comparison)
   - Idea: Sort both strings and compare character by character.
   - Count mismatches as steps needed.
   - Time Complexity: O(n log n)
   - Space Complexity: O(n)

2. Optimized (Frequency Count)
   - Idea:
     - Count frequency of each character in both strings.
     - The difference in counts gives the number of replacements needed.
   - Time Complexity: O(n)
   - Space Complexity: O(1) (fixed alphabet size = 26)
*/

import java.util.*;

// Approach 1: Brute Force (Sorting + Comparison)
class MinStepsBrute {
    public int minSteps(String s, String t) {
        char[] sArr = s.toCharArray();
        char[] tArr = t.toCharArray();

        Arrays.sort(sArr);
        Arrays.sort(tArr);

        int steps = 0;
        for (int i = 0; i < sArr.length; i++) {
            if (sArr[i] != tArr[i]) {
                steps++;
            }
        }
        return steps;
    }
}

// Approach 2: Optimized (Frequency Count)
class MinStepsOptimized {
    public int minSteps(String s, String t) {
        int[] freq = new int[26];

        // Count frequency difference
        for (char c : s.toCharArray()) {
            freq[c - 'a']++;
        }
        for (char c : t.toCharArray()) {
            freq[c - 'a']--;
        }

        int steps = 0;
        for (int count : freq) {
            if (count > 0) {
                steps += count; // only positive counts matter
            }
        }
        return steps;
    }
}

// Driver class
public class Main {
    public static void main(String[] args) {
        String s = "bab";
        String t = "aba";

        // Test Brute Force
        MinStepsBrute brute = new MinStepsBrute();
        System.out.println("Brute Force Result: " + brute.minSteps(s, t));

        // Test Optimized
        MinStepsOptimized optimized = new MinStepsOptimized();
        System.out.println("Optimized Result: " + optimized.minSteps(s, t));
        // Expected Output: 1
    }
}
