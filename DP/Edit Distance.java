/*
Problem: Edit Distance

Given two strings word1 and word2, return the minimum number
of operations required to convert word1 to word2.

You have the following three operations permitted on a word:
- Insert a character
- Delete a character
- Replace a character

Constraints:
- 0 <= word1.length, word2.length <= 500
- word1 and word2 consist of lowercase English letters.

Example:
Input: word1 = "horse", word2 = "ros"
Output: 3
Explanation:
horse -> rorse (replace 'h' with 'r')
rorse -> rose  (remove 'r')
rose  -> ros   (remove 'e')

Source: LeetCode Problem #72
https://leetcode.com/problems/edit-distance/

Approaches:

1. Recursion
   - Idea:
     - Compare the last characters of the two prefixes (i, j).
     - If they match, move to (i-1, j-1) at no cost.
     - Otherwise take 1 + min of:
           delete  -> (i-1, j)
           insert  -> (i, j-1)
           replace -> (i-1, j-1)
     - Base cases: i == 0 -> j inserts, j == 0 -> i deletes.

   - Time Complexity: O(3^(m + n))
   - Space Complexity: O(m + n)

2. Memoization
   - Idea:
     - Same recursion, but cache the answer for each (i, j).
     - Only m * n distinct states exist.

   - Time Complexity: O(m * n)
   - Space Complexity: O(m * n)

3. Tabulation
   - Idea:
     - dp[i][j] = min operations to convert first i chars of word1
       into first j chars of word2.
     - dp[i][0] = i, dp[0][j] = j.
     - If word1[i-1] == word2[j-1]: dp[i][j] = dp[i-1][j-1]
       else: dp[i][j] = 1 + min(dp[i-1][j], dp[i][j-1], dp[i-1][j-1])

   - Time Complexity: O(m * n)
   - Space Complexity: O(m * n)

4. Space Optimization
   - Idea:
     - Row i depends only on row i-1 and the current row.
     - Keep just two 1D arrays (prev and curr).

   - Time Complexity: O(m * n)
   - Space Complexity: O(n)
*/

import java.util.*;

// Approach 1: Recursion
class EditDistanceRecursion {

    public int minDistance(String word1, String word2) {
        return helper(word1.length(), word2.length(), word1, word2);
    }

    private int helper(int i, int j, String word1, String word2) {

        if (i == 0) {
            return j;
        }

        if (j == 0) {
            return i;
        }

        if (word1.charAt(i - 1) == word2.charAt(j - 1)) {
            return helper(i - 1, j - 1, word1, word2);
        }

        int delete = helper(i - 1, j, word1, word2);
        int insert = helper(i, j - 1, word1, word2);
        int replace = helper(i - 1, j - 1, word1, word2);

        return 1 + Math.min(delete, Math.min(insert, replace));
    }
}

// Approach 2: Memoization
class EditDistanceMemoization {

    public int minDistance(String word1, String word2) {

        int m = word1.length();
        int n = word2.length();

        Integer[][] dp = new Integer[m + 1][n + 1];

        return helper(m, n, word1, word2, dp);
    }

    private int helper(int i, int j, String word1, String word2, Integer[][] dp) {

        if (i == 0) {
            return j;
        }

        if (j == 0) {
            return i;
        }

        if (dp[i][j] != null) {
            return dp[i][j];
        }

        if (word1.charAt(i - 1) == word2.charAt(j - 1)) {
            return dp[i][j] = helper(i - 1, j - 1, word1, word2, dp);
        }

        int delete = helper(i - 1, j, word1, word2, dp);
        int insert = helper(i, j - 1, word1, word2, dp);
        int replace = helper(i - 1, j - 1, word1, word2, dp);

        return dp[i][j] = 1 + Math.min(delete, Math.min(insert, replace));
    }
}

// Approach 3: Tabulation
class EditDistanceTabulation {

    public int minDistance(String word1, String word2) {

        int m = word1.length();
        int n = word2.length();

        int[][] dp = new int[m + 1][n + 1];

        for (int i = 0; i <= m; i++) {
            dp[i][0] = i;
        }

        for (int j = 0; j <= n; j++) {
            dp[0][j] = j;
        }

        for (int i = 1; i <= m; i++) {

            for (int j = 1; j <= n; j++) {

                if (word1.charAt(i - 1) == word2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    dp[i][j] = 1 + Math.min(dp[i - 1][j],
                                    Math.min(dp[i][j - 1],
                                             dp[i - 1][j - 1]));
                }
            }
        }

        return dp[m][n];
    }
}

// Approach 4: Space Optimization
class EditDistanceOptimized {

    public int minDistance(String word1, String word2) {

        int m = word1.length();
        int n = word2.length();

        int[] prev = new int[n + 1];
        int[] curr = new int[n + 1];

        for (int j = 0; j <= n; j++) {
            prev[j] = j;
        }

        for (int i = 1; i <= m; i++) {

            curr[0] = i;

            for (int j = 1; j <= n; j++) {

                if (word1.charAt(i - 1) == word2.charAt(j - 1)) {
                    curr[j] = prev[j - 1];
                } else {
                    curr[j] = 1 + Math.min(prev[j],
                                    Math.min(curr[j - 1],
                                             prev[j - 1]));
                }
            }

            int[] temp = prev;
            prev = curr;
            curr = temp;
        }

        return prev[n];
    }
}

// Driver Class
public class Main {

    public static void main(String[] args) {

        String word1 = "horse";
        String word2 = "ros";

        EditDistanceRecursion rec = new EditDistanceRecursion();
        System.out.println("Recursion Output: " + rec.minDistance(word1, word2)); // 3

        EditDistanceMemoization memo = new EditDistanceMemoization();
        System.out.println("Memoization Output: " + memo.minDistance(word1, word2)); // 3

        EditDistanceTabulation tab = new EditDistanceTabulation();
        System.out.println("Tabulation Output: " + tab.minDistance(word1, word2)); // 3

        EditDistanceOptimized opt = new EditDistanceOptimized();
        System.out.println("Space Optimized Output: " + opt.minDistance(word1, word2)); // 3
    }
}
