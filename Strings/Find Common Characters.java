/*
Problem: Given a string array words, return a list of all characters that show up in all strings within words (including duplicates).
You may return the answer in any order.

Source: LeetCode Problem #1002 → https://leetcode.com/problems/find-common-characters/

Approaches:
1. Brute Force (Check Each Character)
   - Idea:
     - For each character in the first word, check if it appears in all other words.
     - Track counts to handle duplicates.
   - Time Complexity: O(n * m^2) [n = number of words, m = length of word].
   - Space Complexity: O(1).

2. Optimized (Frequency Counting)
   - Idea:
     - Count frequency of each character in every word.
     - Take the minimum frequency across all words.
     - Add that many occurrences of the character to result.
   - Time Complexity: O(n * m).
   - Space Complexity: O(26) = O(1).
*/

import java.util.*;

// Approach 1: Brute Force
class CommonCharsBrute {
    public List<String> commonChars(String[] words) {
        List<String> result = new ArrayList<>();
        String first = words[0];
        boolean[] used = new boolean[first.length()];

        for (int i = 0; i < first.length(); i++) {
            char c = first.charAt(i);
            if (used[i]) continue;

            int count = 1; // count in first word
            boolean valid = true;

            for (int j = 1; j < words.length; j++) {
                int idx = words[j].indexOf(c);
                if (idx == -1) {
                    valid = false;
                    break;
                } else {
                    words[j] = words[j].substring(0, idx) + "#" + words[j].substring(idx+1);
                }
            }

            if (valid) {
                result.add(String.valueOf(c));
            }
            used[i] = true;
        }
        return result;
    }
}

// Approach 2: Optimized (Frequency Counting)
class CommonCharsOptimized {
    public List<String> commonChars(String[] words) {
        int[] minFreq = new int[26];
        Arrays.fill(minFreq, Integer.MAX_VALUE);

        for (String word : words) {
            int[] freq = new int[26];
            for (char c : word.toCharArray()) {
                freq[c - 'a']++;
            }
            for (int i = 0; i < 26; i++) {
                minFreq[i] = Math.min(minFreq[i], freq[i]);
            }
        }

        List<String> result = new ArrayList<>();
        for (int i = 0; i < 26; i++) {
            for (int j = 0; j < minFreq[i]; j++) {
                result.add(String.valueOf((char)(i + 'a')));
            }
        }
        return result;
    }
}

// Driver class
public class Main {
    public static void main(String[] args) {
        String[] words1 = {"bella","label","roller"};
        String[] words2 = {"cool","lock","cook"};

        // Test Brute Force
        CommonCharsBrute brute = new CommonCharsBrute();
        System.out.println("Brute Force Result (case1): " + brute.commonChars(words1)); // [e, l, l]
        System.out.println("Brute Force Result (case2): " + brute.commonChars(words2)); // [c, o]

        // Test Optimized
        CommonCharsOptimized opt = new CommonCharsOptimized();
        System.out.println("Optimized Result (case1): " + opt.commonChars(words1)); // [e, l, l]
        System.out.println("Optimized Result (case2): " + opt.commonChars(words2)); // [c, o]
    }
}
