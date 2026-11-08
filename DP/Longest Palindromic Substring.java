/*
Problem: Longest Palindromic Substring

Given a string s, return the longest palindromic substring in s.

A palindrome reads the same forward and backward.

Source: LeetCode Problem #5
https://leetcode.com/problems/longest-palindromic-substring/

Approaches:

1. Recursion
   - Idea:
     - Generate all substrings recursively.
     - Check whether a substring is palindrome.
     - Keep track of the longest palindrome found.

   - Time Complexity: O(2^n) + palindrome checks
   - Space Complexity: O(n)

2. Memoization
   - Idea:
     - dp[i][j] indicates whether substring
       s[i...j] is a palindrome.
     - Store already computed results.
     - Avoid repeated palindrome checks.

   - Time Complexity: O(n²)
   - Space Complexity: O(n²)

3. Tabulation
   - Idea:
     - Build palindrome information from
       shorter substrings to longer substrings.
     - A substring is palindrome if:
           s[i] == s[j]
       and inner substring is palindrome.

   - Time Complexity: O(n²)
   - Space Complexity: O(n²)

4. Space Optimization (Expand Around Center)
   - Idea:
     - Every palindrome has a center.
     - Expand around each character
       (odd length palindrome).
     - Expand around each pair of characters
       (even length palindrome).
     - Track longest palindrome found.

   - Time Complexity: O(n²)
   - Space Complexity: O(1)

   - Most Interview Preferred Solution ✅
*/

import java.util.*;

// Approach 1: Recursion
class LongestPalindromeRecursion {

    public String longestPalindrome(String s) {

        String[] ans = {""};

        helper(s, 0, ans);

        return ans[0];
    }

    private void helper(
            String s,
            int start,
            String[] ans) {

        if (start == s.length()) {
            return;
        }

        for (int end = start;
             end < s.length();
             end++) {

            String sub =
                    s.substring(start, end + 1);

            if (isPalindrome(sub)
                && sub.length() > ans[0].length()) {

                ans[0] = sub;
            }
        }

        helper(s, start + 1, ans);
    }

    private boolean isPalindrome(
            String str) {

        int left = 0;
        int right = str.length() - 1;

        while (left < right) {

            if (str.charAt(left)
                != str.charAt(right)) {

                return false;
            }

            left++;
            right--;
        }

        return true;
    }
}

// Approach 2: Memoization
class LongestPalindromeMemoization {

    private Boolean[][] dp;

    public String longestPalindrome(String s) {

        int n = s.length();

        dp = new Boolean[n][n];

        String ans = "";

        for (int i = 0; i < n; i++) {

            for (int j = i; j < n; j++) {

                if (isPalindrome(s, i, j)
                    && (j - i + 1) > ans.length()) {

                    ans =
                            s.substring(i, j + 1);
                }
            }
        }

        return ans;
    }

    private boolean isPalindrome(
            String s,
            int left,
            int right) {

        if (left >= right) {
            return true;
        }

        if (dp[left][right] != null) {
            return dp[left][right];
        }

        return dp[left][right] =
                s.charAt(left)
                == s.charAt(right)
                &&
                isPalindrome(
                        s,
                        left + 1,
                        right - 1
                );
    }
}

// Approach 3: Tabulation
class LongestPalindromeTabulation {

    public String longestPalindrome(String s) {

        int n = s.length();

        boolean[][] dp =
                new boolean[n][n];

        int start = 0;
        int maxLen = 1;

        for (int i = 0; i < n; i++) {
            dp[i][i] = true;
        }

        for (int len = 2;
             len <= n;
             len++) {

            for (int i = 0;
                 i <= n - len;
                 i++) {

                int j = i + len - 1;

                if (s.charAt(i)
                    == s.charAt(j)) {

                    if (len == 2) {

                        dp[i][j] = true;

                    } else {

                        dp[i][j] =
                                dp[i + 1][j - 1];
                    }
                }

                if (dp[i][j]
                    && len > maxLen) {

                    start = i;
                    maxLen = len;
                }
            }
        }

        return s.substring(
                start,
                start + maxLen
        );
    }
}

// Approach 4: Space Optimized
class LongestPalindromeOptimized {

    public String longestPalindrome(String s) {

        if (s == null
            || s.length() < 2) {

            return s;
        }

        int start = 0;
        int end = 0;

        for (int i = 0;
             i < s.length();
             i++) {

            int len1 =
                    expand(s, i, i);

            int len2 =
                    expand(s, i, i + 1);

            int len =
                    Math.max(len1, len2);

            if (len > end - start) {

                start =
                        i - (len - 1) / 2;

                end =
                        i + len / 2;
            }
        }

        return s.substring(
                start,
                end + 1
        );
    }

    private int expand(
            String s,
            int left,
            int right) {

        while (left >= 0
                && right < s.length()
                && s.charAt(left)
                == s.charAt(right)) {

            left--;
            right++;
        }

        return right - left - 1;
    }
}

// Driver Class
public class Main {

    public static void main(String[] args) {

        String s = "babad";

        LongestPalindromeRecursion rec =
                new LongestPalindromeRecursion();

        System.out.println(
                "Recursion Output: "
                + rec.longestPalindrome(s)
        );

        LongestPalindromeMemoization memo =
                new LongestPalindromeMemoization();

        System.out.println(
                "Memoization Output: "
                + memo.longestPalindrome(s)
        );

        LongestPalindromeTabulation tab =
                new LongestPalindromeTabulation();

        System.out.println(
                "Tabulation Output: "
                + tab.longestPalindrome(s)
        );

        LongestPalindromeOptimized opt =
                new LongestPalindromeOptimized();

        System.out.println(
                "Space Optimized Output: "
                + opt.longestPalindrome(s)
        );
    }
}
