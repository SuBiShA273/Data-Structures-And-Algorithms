/*
Problem: Longest Common Subsequence

Given two strings text1 and text2.

Return the length of their Longest Common Subsequence (LCS).

A subsequence is a sequence that can be derived from another
sequence by deleting some or no characters without changing
the order of the remaining characters.

Source: LeetCode Problem #1143
https://leetcode.com/problems/longest-common-subsequence/

Approaches:

1. Recursion
   - Idea:
     - Compare characters at current indices.
     - If equal:
           1 + LCS(next indices)
     - Otherwise:
           max(skip character from text1,
               skip character from text2)

   - Time Complexity: O(2^(m+n))
   - Space Complexity: O(m+n)

2. Memoization (Top-Down DP)
   - Idea:
     - Store results in dp[i][j].
     - Avoid recomputing overlapping subproblems.

   - Time Complexity: O(m * n)
   - Space Complexity: O(m * n)

3. Tabulation (Bottom-Up DP)
   - Idea:
     - dp[i][j] = LCS length using first i characters
       of text1 and first j characters of text2.
     - Build the table iteratively.

   - Time Complexity: O(m * n)
   - Space Complexity: O(m * n)

4. Space Optimization
   - Idea:
     - Current row only depends on previous row.
     - Use two 1D arrays instead of a 2D matrix.

   - Time Complexity: O(m * n)
   - Space Complexity: O(n)
*/

import java.util.*;

// Approach 1: Recursion
class LCSRecursion {

    public int longestCommonSubsequence(
            String text1,
            String text2) {

        return helper(0, 0, text1, text2);
    }

    private int helper(
            int i,
            int j,
            String text1,
            String text2) {

        if (i == text1.length() ||
            j == text2.length()) {
            return 0;
        }

        if (text1.charAt(i) ==
            text2.charAt(j)) {

            return 1 +
                    helper(
                            i + 1,
                            j + 1,
                            text1,
                            text2
                    );
        }

        return Math.max(
                helper(
                        i + 1,
                        j,
                        text1,
                        text2
                ),
                helper(
                        i,
                        j + 1,
                        text1,
                        text2
                )
        );
    }
}

// Approach 2: Memoization
class LCSMemoization {

    public int longestCommonSubsequence(
            String text1,
            String text2) {

        Integer[][] dp =
                new Integer[text1.length()]
                             [text2.length()];

        return helper(
                0,
                0,
                text1,
                text2,
                dp
        );
    }

    private int helper(
            int i,
            int j,
            String text1,
            String text2,
            Integer[][] dp) {

        if (i == text1.length() ||
            j == text2.length()) {
            return 0;
        }

        if (dp[i][j] != null) {
            return dp[i][j];
        }

        if (text1.charAt(i) ==
            text2.charAt(j)) {

            return dp[i][j] =
                    1 +
                    helper(
                            i + 1,
                            j + 1,
                            text1,
                            text2,
                            dp
                    );
        }

        return dp[i][j] =
                Math.max(
                        helper(
                                i + 1,
                                j,
                                text1,
                                text2,
                                dp
                        ),
                        helper(
                                i,
                                j + 1,
                                text1,
                                text2,
                                dp
                        )
                );
    }
}

// Approach 3: Tabulation
class LCSTabulation {

    public int longestCommonSubsequence(
            String text1,
            String text2) {

        int m = text1.length();
        int n = text2.length();

        int[][] dp =
                new int[m + 1][n + 1];

        for (int i = 1; i <= m; i++) {

            for (int j = 1; j <= n; j++) {

                if (text1.charAt(i - 1) ==
                    text2.charAt(j - 1)) {

                    dp[i][j] =
                            1 + dp[i - 1][j - 1];

                } else {

                    dp[i][j] =
                            Math.max(
                                    dp[i - 1][j],
                                    dp[i][j - 1]
                            );
                }
            }
        }

        return dp[m][n];
    }
}

// Approach 4: Space Optimization
class LCSOptimized {

    public int longestCommonSubsequence(
            String text1,
            String text2) {

        int m = text1.length();
        int n = text2.length();

        int[] prev = new int[n + 1];

        for (int i = 1; i <= m; i++) {

            int[] curr = new int[n + 1];

            for (int j = 1; j <= n; j++) {

                if (text1.charAt(i - 1) ==
                    text2.charAt(j - 1)) {

                    curr[j] =
                            1 + prev[j - 1];

                } else {

                    curr[j] =
                            Math.max(
                                    prev[j],
                                    curr[j - 1]
                            );
                }
            }

            prev = curr;
        }

        return prev[n];
    }
}

// Driver Class
public class Main {

    public static void main(String[] args) {

        String text1 = "abcde";
        String text2 = "ace";

        LCSRecursion rec =
                new LCSRecursion();

        System.out.println(
                "Recursion Output: "
                + rec.longestCommonSubsequence(
                        text1,
                        text2)
        ); // 3

        LCSMemoization memo =
                new LCSMemoization();

        System.out.println(
                "Memoization Output: "
                + memo.longestCommonSubsequence(
                        text1,
                        text2)
        ); // 3

        LCSTabulation tab =
                new LCSTabulation();

        System.out.println(
                "Tabulation Output: "
                + tab.longestCommonSubsequence(
                        text1,
                        text2)
        ); // 3

        LCSOptimized opt =
                new LCSOptimized();

        System.out.println(
                "Space Optimized Output: "
                + opt.longestCommonSubsequence(
                        text1,
                        text2)
        ); // 3
    }
}
