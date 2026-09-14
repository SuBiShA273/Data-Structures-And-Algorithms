/*
Problem: Given a pattern and a string s,
return true if s follows the same pattern.

Rules:
- Each character in pattern maps to a word in s.
- The mapping must be bijective (one-to-one and onto).
- Example: pattern = "abba", s = "dog cat cat dog" → true.

Source: LeetCode Problem #290 → https://leetcode.com/problems/word-pattern/

Approach: Optimized (HashMap Bijection Check)
   - Idea:
     - Split string s into words.
     - If lengths differ, return false.
     - Use two maps:
       - charToWord: pattern character → word
       - wordToChar: word → pattern character
     - For each position:
       - If mapping exists, check consistency.
       - If not, create new mapping.
     - Return true if all checks pass.
   - Time Complexity: O(n) (n = number of words).
   - Space Complexity: O(n).
*/

import java.util.*;

class WordPatternOptimized {
    public boolean wordPattern(String pattern, String s) {
        String[] words = s.split(" ");
        if (pattern.length() != words.length) return false;

        Map<Character, String> charToWord = new HashMap<>();
        Map<String, Character> wordToChar = new HashMap<>();

        for (int i = 0; i < pattern.length(); i++) {
            char c = pattern.charAt(i);
            String w = words[i];

            if (charToWord.containsKey(c)) {
                if (!charToWord.get(c).equals(w)) return false;
            } else {
                charToWord.put(c, w);
            }

            if (wordToChar.containsKey(w)) {
                if (wordToChar.get(w) != c) return false;
            } else {
                wordToChar.put(w, c);
            }
        }
        return true;
    }
}

// Driver class
public class Main {
    public static void main(String[] args) {
        WordPatternOptimized opt = new WordPatternOptimized();

        String pattern1 = "abba";
        String s1 = "dog cat cat dog";
        System.out.println("Result 1: " + opt.wordPattern(pattern1, s1)); // true

        String pattern2 = "abba";
        String s2 = "dog cat cat fish";
        System.out.println("Result 2: " + opt.wordPattern(pattern2, s2)); // false

        String pattern3 = "aaaa";
        String s3 = "dog cat cat dog";
        System.out.println("Result 3: " + opt.wordPattern(pattern3, s3)); // false

        String pattern4 = "abba";
        String s4 = "dog dog dog dog";
        System.out.println("Result 4: " + opt.wordPattern(pattern4, s4)); // false
    }
}
