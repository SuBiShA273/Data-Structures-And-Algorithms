/*
Problem: Word Break

Given a string s and a dictionary of strings wordDict, return true if s can be
segmented into a space-separated sequence of one or more dictionary words.

Note that the same word in the dictionary may be reused multiple times in the
segmentation.

Source: LeetCode Problem #139
https://leetcode.com

Approaches:

1. Recursion
   - Idea:
     - Starting from `index`, try every possible end position `j` from `index + 1` to `n`.
     - If `s.substring(index, j)` exists in `wordSet` and the remaining suffix `j`
       can be broken, return true.

   - Time Complexity: O(2^n)
   - Space Complexity: O(n + m * L) (recursion stack + HashSet)

2. Memoization
   - Idea:
     - Store computed results for each starting index in a 1D `Boolean[] memo` array.
     - Avoid recalculating overlapping suffixes.

   - Time Complexity: O(n^3 + m * L)
   - Space Complexity: O(n + m * L)

3. Tabulation
   - Idea:
     - Build a 1D boolean DP table bottom-up from index `n - 1` down to `0`.
     - Base case: `dp[n] = true` (an empty suffix is always valid).
     - Check `dp[j]` before slicing `s.substring(i, j)` to avoid unnecessary allocations.

   - Time Complexity: O(n^3 + m * L)
   - Space Complexity: O(n + m * L)

4. Length-Bounded Tabulation (Optimal)
   - Idea:
     - Find `maxLen` (the length of the longest word in `wordDict`).
     - Bound the inner loop `j` by `Math.min(n, i + maxLen)` and `break` immediately
       once `dp[i]` becomes true.

   - Time Complexity: O(n * L^2 + m * L)
   - Space Complexity: O(n + m * L)
*/

import java.util.*;

// Approach 1: Recursion
class WordBreakRecursion {

    public boolean wordBreak(String s, List<String> wordDict) {
        Set<String> wordSet = new HashSet<>(wordDict);
        return helper(0, s, wordSet);
    }

    private boolean helper(int index, String s, Set<String> wordSet) {
        if (index == s.length()) {
            return true;
        }

        for (int j = index + 1; j <= s.length(); j++) {
            if (wordSet.contains(s.substring(index, j)) && helper(j, s, wordSet)) {
                return true;
            }
        }

        return false;
    }
}

// Approach 2: Memoization
class WordBreakMemoization {

    public boolean wordBreak(String s, List<String> wordDict) {
        Set<String> wordSet = new HashSet<>(wordDict);
        Boolean[] memo = new Boolean[s.length()];
        return helper(0, s, wordSet, memo);
    }

    private boolean helper(int index, String s, Set<String> wordSet, Boolean[] memo) {
        if (index == s.length()) {
            return true;
        }

        if (memo[index] != null) {
            return memo[index];
        }

        for (int j = index + 1; j <= s.length(); j++) {
            if (wordSet.contains(s.substring(index, j)) && helper(j, s, wordSet, memo)) {
                return memo[index] = true;
            }
        }

        return memo[index] = false;
    }
}

// Approach 3: Tabulation
class WordBreakTabulation {

    public boolean wordBreak(String s, List<String> wordDict) {
        int n = s.length();
        Set<String> wordSet = new HashSet<>(wordDict);

        boolean[] dp = new boolean[n + 1];
        dp[n] = true;

        for (int i = n - 1; i >= 0; i--) {
            for (int j = i + 1; j <= n; j++) {
                if (dp[j] && wordSet.contains(s.substring(i, j))) {
                    dp[i] = true;
                    break;
                }
            }
        }

        return dp[0];
    }
}

// Approach 4: Length-Bounded Tabulation (Optimal)
class WordBreakOptimized {

    public boolean wordBreak(String s, List<String> wordDict) {
        int n = s.length();
        Set<String> wordSet = new HashSet<>(wordDict);

        int maxLen = 0;
        for (String word : wordDict) {
            maxLen = Math.max(maxLen, word.length());
        }

        boolean[] dp = new boolean[n + 1];
        dp[n] = true;

        for (int i = n - 1; i >= 0; i--) {
            int limit = Math.min(n, i + maxLen);
            for (int j = i + 1; j <= limit; j++) {
                if (dp[j] && wordSet.contains(s.substring(i, j))) {
                    dp[i] = true;
                    break;
                }
            }
        }

        return dp[0];
    }
}

// Driver Class
public class Main {

    public static void main(String[] args) {

        String s = "catsandog";
        List<String> wordDict = Arrays.asList("cats", "dog", "sand", "and", "cat");

        WordBreakRecursion rec = new WordBreakRecursion();
        System.out.println("Recursion Output: " + rec.wordBreak(s, wordDict)); // false

        WordBreakMemoization memo = new WordBreakMemoization();
        System.out.println("Memoization Output: " + memo.wordBreak(s, wordDict)); // false

        WordBreakTabulation tab = new WordBreakTabulation();
        System.out.println("Tabulation Output: " + tab.wordBreak(s, wordDict)); // false

        WordBreakOptimized opt = new WordBreakOptimized();
        System.out.println("Optimal Tabulation Output: " + opt.wordBreak(s, wordDict)); // false
    }
}
