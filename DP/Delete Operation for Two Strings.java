/*
Problem: Delete Operation for Two Strings

Given two strings word1 and word2.

Return the minimum number of deletions required
to make both strings equal.

In one operation, you can delete exactly one
character from either string.

Source: LeetCode Problem #583
https://leetcode.com/problems/delete-operation-for-two-strings/

Approaches:

1. Recursion
   - Idea:
     - If characters match, move both pointers.
     - Otherwise:
         Delete from word1
         Delete from word2
       Take minimum deletions.

   - Time Complexity: O(2^(m+n))
   - Space Complexity: O(m+n)

2. Memoization
   - Idea:
     - Store result of each state:
           dp[i][j]
     - Avoid repeated computation.

   - Time Complexity: O(m*n)
   - Space Complexity: O(m*n)

3. Tabulation
   - Idea:
     - dp[i][j] = minimum deletions needed
       for suffixes starting at i and j.
     - Build answer from bottom up.

   - Time Complexity: O(m*n)
   - Space Complexity: O(m*n)

4. Space Optimization
   - Idea:
     - Minimum Deletions =
           (len1 - LCS) + (len2 - LCS)
     - Find LCS using space-optimized DP.

   - Time Complexity: O(m*n)
   - Space Complexity: O(n)
*/

import java.util.*;

// Approach 1: Recursion
class DeleteOperationRecursion {

    public int minDistance(String word1, String word2) {
        return helper(0, 0, word1, word2);
    }

    private int helper(int i, int j, String word1, String word2) {

        if (i == word1.length()) {
            return word2.length() - j;
        }

        if (j == word2.length()) {
            return word1.length() - i;
        }

        if (word1.charAt(i) == word2.charAt(j)) {
            return helper(i + 1, j + 1, word1, word2);
        }

        return 1 + Math.min(helper(i + 1, j, word1, word2),
                            helper(i, j + 1, word1, word2));
    }
}

// Approach 2: Memoization
class DeleteOperationMemoization {

    public int minDistance(String word1, String word2) {

        Integer[][] dp = new Integer[word1.length()][word2.length()];

        return helper(0, 0, word1, word2, dp);
    }

    private int helper(int i, int j, String word1, String word2, Integer[][] dp) {

        if (i == word1.length()) {
            return word2.length() - j;
        }

        if (j == word2.length()) {
            return word1.length() - i;
        }

        if (dp[i][j] != null) {
            return dp[i][j];
        }

        if (word1.charAt(i) == word2.charAt(j)) {
            return dp[i][j] = helper(i + 1, j + 1, word1, word2, dp);
        }

        return dp[i][j] = 1 + Math.min(helper(i + 1, j, word1, word2, dp),
                                       helper(i, j + 1, word1, word2, dp));
    }
}

// Approach 3: Tabulation
class DeleteOperationTabulation {

    public int minDistance(String word1, String word2) {

        int m = word1.length();
        int n = word2.length();

        int[][] dp = new int[m + 1][n + 1];

        for (int i = 0; i <= m; i++) {
            dp[i][n] = m - i;
        }

        for (int j = 0; j <= n; j++) {
            dp[m][j] = n - j;
        }

        for (int i = m - 1; i >= 0; i--) {

            for (int j = n - 1; j >= 0; j--) {

                if (word1.charAt(i) == word2.charAt(j)) {
                    dp[i][j] = dp[i + 1][j + 1];
                } else {
                    dp[i][j] = 1 + Math.min(dp[i + 1][j], dp[i][j + 1]);
                }
            }
        }

        return dp[0][0];
    }
}

// Approach 4: Space Optimization (LCS)
class DeleteOperationOptimized {

    public int minDistance(String word1, String word2) {

        int m = word1.length();
        int n = word2.length();

        int[] prev = new int[n + 1];

        for (int i = 1; i <= m; i++) {

            int[] curr = new int[n + 1];

            for (int j = 1; j <= n; j++) {

                if (word1.charAt(i - 1) == word2.charAt(j - 1)) {
                    curr[j] = 1 + prev[j - 1];
                } else {
                    curr[j] = Math.max(prev[j], curr[j - 1]);
                }
            }

            prev = curr;
        }

        int lcs = prev[n];

        return (m - lcs) + (n - lcs);
    }
}

// Driver Class
public class Main {

    public static void main(String[] args) {

        String word1 = "sea";
        String word2 = "eat";

        DeleteOperationRecursion rec = new DeleteOperationRecursion();
        System.out.println("Recursion Output: " + rec.minDistance(word1, word2)); // 2

        DeleteOperationMemoization memo = new DeleteOperationMemoization();
        System.out.println("Memoization Output: " + memo.minDistance(word1, word2)); // 2

        DeleteOperationTabulation tab = new DeleteOperationTabulation();
        System.out.println("Tabulation Output: " + tab.minDistance(word1, word2)); // 2

        DeleteOperationOptimized opt = new DeleteOperationOptimized();
        System.out.println("Space Optimized Output: " + opt.minDistance(word1, word2)); // 2
    }
}
