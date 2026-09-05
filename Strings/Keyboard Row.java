/*
Problem: Given an array of strings words, return the words that can be typed
using letters of only one row of the American keyboard.

Source: LeetCode Problem #500 → https://leetcode.com/problems/keyboard-row/

Approaches:
1. Brute Force
   - Idea:
     - For each word, check if all characters belong to the same row.
     - Rows: "qwertyuiop", "asdfghjkl", "zxcvbnm".
   - Time Complexity: O(n * m) [n = number of words, m = length of word].
   - Space Complexity: O(1).

2. Optimized (Row Mapping)
   - Idea:
     - Map each character to its row index.
     - For each word, check if all characters map to the same row.
   - Time Complexity: O(n * m).
   - Space Complexity: O(26).
*/

import java.util.*;

// Approach 1: Brute Force
class KeyboardRowBrute {
    private static final String row1 = "qwertyuiop";
    private static final String row2 = "asdfghjkl";
    private static final String row3 = "zxcvbnm";

    public String[] findWords(String[] words) {
        List<String> result = new ArrayList<>();
        for (String word : words) {
            String lower = word.toLowerCase();
            if (inOneRow(lower, row1) || inOneRow(lower, row2) || inOneRow(lower, row3)) {
                result.add(word);
            }
        }
        return result.toArray(new String[0]);
    }

    private boolean inOneRow(String word, String row) {
        for (char c : word.toCharArray()) {
            if (row.indexOf(c) == -1) return false;
        }
        return true;
    }
}

// Approach 2: Optimized (Row Mapping)
class KeyboardRowOptimized {
    public String[] findWords(String[] words) {
        int[] map = new int[26];
        for (char c : "qwertyuiop".toCharArray()) map[c - 'a'] = 1;
        for (char c : "asdfghjkl".toCharArray()) map[c - 'a'] = 2;
        for (char c : "zxcvbnm".toCharArray()) map[c - 'a'] = 3;

        List<String> result = new ArrayList<>();
        for (String word : words) {
            String lower = word.toLowerCase();
            int row = map[lower.charAt(0) - 'a'];
            boolean valid = true;
            for (char c : lower.toCharArray()) {
                if (map[c - 'a'] != row) {
                    valid = false;
                    break;
                }
            }
            if (valid) result.add(word);
        }
        return result.toArray(new String[0]);
    }
}

// Driver class
public class Main {
    public static void main(String[] args) {
        String[] words = {"Hello","Alaska","Dad","Peace"};

        // Test Brute Force
        KeyboardRowBrute brute = new KeyboardRowBrute();
        System.out.println("Brute Force Result: " + Arrays.toString(brute.findWords(words))); // [Alaska, Dad]

        // Test Optimized
        KeyboardRowOptimized opt = new KeyboardRowOptimized();
        System.out.println("Optimized Result: " + Arrays.toString(opt.findWords(words))); // [Alaska, Dad]
    }
}
