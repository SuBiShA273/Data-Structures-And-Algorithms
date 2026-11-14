/*
Problem: Shortest Common Supersequence

Given two strings str1 and str2.

Return the shortest string that has both str1 and str2
as subsequences.

If multiple answers exist, return any one of them.

Source: LeetCode Problem #1092
https://leetcode.com/problems/shortest-common-supersequence/

Approaches:

1. Recursion
   - Idea:
     - If characters match, include once.
     - Otherwise:
         Take character from str1
         OR
         Take character from str2
     - Return shorter result.

   - Time Complexity: Exponential
   - Space Complexity: O(m + n)

2. Memoization
   - Idea:
     - Store answer for each state:
           dp[i][j]
     - Avoid recomputation.

   - Time Complexity: O(m*n)
   - Space Complexity: O(m*n)

3. Tabulation
   - Idea:
     - First compute LCS table.
     - Backtrack using LCS table.
     - Build shortest common supersequence.

   - Time Complexity: O(m*n)
   - Space Complexity: O(m*n)

4. Space Optimization
   - Idea:
     - Not Applicable.
     - To reconstruct the SCS string,
       complete DP table is required.

   - Time Complexity: O(m*n)
   - Space Complexity: O(m*n)
*/

import java.util.*;

// Approach 1: Recursion
class SCSRecursion {

    public String shortestCommonSupersequence(String str1, String str2) {
        return helper(0, 0, str1, str2);
    }

    private String helper(int i, int j, String str1, String str2) {

        if (i == str1.length()) {
            return str2.substring(j);
        }

        if (j == str2.length()) {
            return str1.substring(i);
        }

        if (str1.charAt(i) == str2.charAt(j)) {
            return str1.charAt(i) + helper(i + 1, j + 1, str1, str2);
        }

        String choice1 = str1.charAt(i) + helper(i + 1, j, str1, str2);
        String choice2 = str2.charAt(j) + helper(i, j + 1, str1, str2);

        return choice1.length() <= choice2.length() ? choice1 : choice2;
    }
}

// Approach 2: Memoization
class SCSMemoization {

    public String shortestCommonSupersequence(String str1, String str2) {

        String[][] dp = new String[str1.length()][str2.length()];

        return helper(0, 0, str1, str2, dp);
    }

    private String helper(int i, int j, String str1, String str2, String[][] dp) {

        if (i == str1.length()) {
            return str2.substring(j);
        }

        if (j == str2.length()) {
            return str1.substring(i);
        }

        if (dp[i][j] != null) {
            return dp[i][j];
        }

        if (str1.charAt(i) == str2.charAt(j)) {
            return dp[i][j] = str1.charAt(i) + helper(i + 1, j + 1, str1, str2, dp);
        }

        String choice1 = str1.charAt(i) + helper(i + 1, j, str1, str2, dp);
        String choice2 = str2.charAt(j) + helper(i, j + 1, str1, str2, dp);

        return dp[i][j] = choice1.length() <= choice2.length() ? choice1 : choice2;
    }
}

// Approach 3: Tabulation (Interview Preferred)
class SCSTabulation {

    public String shortestCommonSupersequence(String str1, String str2) {

        int m = str1.length();
        int n = str2.length();

        int[][] dp = new int[m + 1][n + 1];

        for (int i = 1; i <= m; i++) {

            for (int j = 1; j <= n; j++) {

                if (str1.charAt(i - 1) == str2.charAt(j - 1)) {
                    dp[i][j] = 1 + dp[i - 1][j - 1];
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }

        StringBuilder ans = new StringBuilder();

        int i = m;
        int j = n;

        while (i > 0 && j > 0) {

            if (str1.charAt(i - 1) == str2.charAt(j - 1)) {

                ans.append(str1.charAt(i - 1));
                i--;
                j--;

            } else if (dp[i - 1][j] > dp[i][j - 1]) {

                ans.append(str1.charAt(i - 1));
                i--;

            } else {

                ans.append(str2.charAt(j - 1));
                j--;
            }
        }

        while (i > 0) {
            ans.append(str1.charAt(i - 1));
            i--;
        }

        while (j > 0) {
            ans.append(str2.charAt(j - 1));
            j--;
        }

        return ans.reverse().toString();
    }
}

// Driver Class
public class Main {

    public static void main(String[] args) {

        String str1 = "abac";
        String str2 = "cab";

        SCSRecursion rec = new SCSRecursion();
        System.out.println("Recursion Output: " + rec.shortestCommonSupersequence(str1, str2));

        SCSMemoization memo = new SCSMemoization();
        System.out.println("Memoization Output: " + memo.shortestCommonSupersequence(str1, str2));

        SCSTabulation tab = new SCSTabulation();
        System.out.println("Tabulation Output: " + tab.shortestCommonSupersequence(str1, str2));

        // Output:
        // cabac
    }
}
