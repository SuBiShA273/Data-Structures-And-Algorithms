/*
Problem: Longest String Chain

You are given an array of words where each word consists of lowercase English letters.
wordA is a predecessor of wordB if and only if we can insert exactly one letter
anywhere in wordA without changing the order of the other characters to make it equal to wordB.

Return the length of the longest possible word chain with words chosen from the given list of words.

Source: LeetCode Problem #1048
https://leetcode.com

Approaches:

1. Recursion
   - Idea:
     - Sort words by length in ascending order.
     - At each index curr and previous chosen index prev:
       - Not Take: f(curr + 1, prev)
       - Take (if prev == -1 or isPredecessor(words[prev], words[curr])): 1 + f(curr + 1, curr)

   - Time Complexity: O(2^n * L)
   - Space Complexity: O(n) (Recursion stack)

2. Memoization
   - Idea:
     - Store results in dp[n][n + 1] (using prev + 1 to handle prev = -1).
     - Avoid recalculating overlapping (curr, prev) states.

   - Time Complexity: O(n^2 * L)
   - Space Complexity: O(n^2) + O(n)

3. Tabulation (1D LIS Pattern)
   - Idea:
     - dp[i] = longest string chain ending at index i.
     - For every j < i, if isPredecessor(words[j], words[i]), update dp[i] = max(dp[i], 1 + dp[j]).

   - Time Complexity: O(n^2 * L)
   - Space Complexity: O(n)

4. Optimal Approach (Sorting + HashMap DP)
   - Idea:
     - Instead of comparing all pairs of words, delete 1 character at a time from the current word.
     - Look up the resulting predecessor in a HashMap in O(1) average time.

   - Time Complexity: O(n log n + n * L^2)
   - Space Complexity: O(n * L)
*/

import java.util.*;

// Helper Utility for Two-Pointer Predecessor Check
class StringChainHelper {
    public static boolean isPredecessor(String shorter, String longer) {
        if (longer.length() != shorter.length() + 1) {
            return false;
        }
        int i = 0, j = 0;
        while (i < shorter.length() && j < longer.length()) {
            if (shorter.charAt(i) == longer.charAt(j)) {
                i++;
                j++;
            } else {
                j++;
            }
        }
        return i == shorter.length();
    }
}

// Approach 1: Recursion
class LongestStringChainRecursion {

    public int longestStrChain(String[] words) {
        Arrays.sort(words, (a, b) -> a.length() - b.length());
        return helper(0, -1, words);
    }

    private int helper(int curr, int prev, String[] words) {
        if (curr == words.length) {
            return 0;
        }

        int notTake = helper(curr + 1, prev, words);
        int take = 0;

        if (prev == -1 || StringChainHelper.isPredecessor(words[prev], words[curr])) {
            take = 1 + helper(curr + 1, curr, words);
        }

        return Math.max(take, notTake);
    }
}

// Approach 2: Memoization
class LongestStringChainMemoization {

    public int longestStrChain(String[] words) {
        int n = words.length;
        Arrays.sort(words, (a, b) -> a.length() - b.length());
        Integer[][] dp = new Integer[n][n + 1];

        return helper(0, -1, words, dp);
    }

    private int helper(int curr, int prev, String[] words, Integer[][] dp) {
        if (curr == words.length) {
            return 0;
        }

        if (dp[curr][prev + 1] != null) {
            return dp[curr][prev + 1];
        }

        int notTake = helper(curr + 1, prev, words, dp);
        int take = 0;

        if (prev == -1 || StringChainHelper.isPredecessor(words[prev], words[curr])) {
            take = 1 + helper(curr + 1, curr, words, dp);
        }

        return dp[curr][prev + 1] = Math.max(take, notTake);
    }
}

// Approach 3: Tabulation (1D DP)
class LongestStringChainTabulation {

    public int longestStrChain(String[] words) {
        int n = words.length;
        Arrays.sort(words, (a, b) -> a.length() - b.length());

        int[] dp = new int[n];
        Arrays.fill(dp, 1);
        int maxi = 1;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < i; j++) {
                if (StringChainHelper.isPredecessor(words[j], words[i]) && 1 + dp[j] > dp[i]) {
                    dp[i] = 1 + dp[j];
                }
            }
            maxi = Math.max(maxi, dp[i]);
        }

        return maxi;
    }
}

// Approach 4: Optimal (HashMap DP)
class LongestStringChainOptimal {

    public int longestStrChain(String[] words) {
        Arrays.sort(words, (a, b) -> a.length() - b.length());
        Map<String, Integer> dp = new HashMap<>();
        int maxi = 1;

        for (String word : words) {
            int best = 1;
            for (int i = 0; i < word.length(); i++) {
                String prev = word.substring(0, i) + word.substring(i + 1);
                best = Math.max(best, dp.getOrDefault(prev, 0) + 1);
            }
            dp.put(word, best);
            maxi = Math.max(maxi, best);
        }

        return maxi;
    }
}

// Driver Class
public class Main {

    public static void main(String[] args) {

        String[] words = {"a", "b", "ba", "bca", "bda", "bdca"};

        LongestStringChainRecursion rec = new LongestStringChainRecursion();
        System.out.println("Recursion Output: " + rec.longestStrChain(words.clone())); // 4

        LongestStringChainMemoization memo = new LongestStringChainMemoization();
        System.out.println("Memoization Output: " + memo.longestStrChain(words.clone())); // 4

        LongestStringChainTabulation tab = new LongestStringChainTabulation();
        System.out.println("Tabulation Output: " + tab.longestStrChain(words.clone())); // 4

        LongestStringChainOptimal opt = new LongestStringChainOptimal();
        System.out.println("Optimal Output: " + opt.longestStrChain(words.clone())); // 4
    }
}
