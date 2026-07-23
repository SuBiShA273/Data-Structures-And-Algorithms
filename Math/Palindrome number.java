/*
Problem: Given an integer x, return true if x is a palindrome, and false otherwise.

Source: LeetCode Problem #9 → https://leetcode.com/problems/palindrome-number/

Approaches:
1. String Conversion (Simple)
   - Idea: Convert integer to string, check if it reads the same forward and backward.
   - Time Complexity: O(n) where n = number of digits
   - Space Complexity: O(n)

2. Mathematical Reversal (Optimal)
   - Idea: Reverse half of the number mathematically without converting to string.
   - Time Complexity: O(log10(n)) → number of digits
   - Space Complexity: O(1)
*/

public class PalindromeNumber {

    // Approach 1: String Conversion
    public static boolean isPalindromeString(int x) {
        if (x < 0) return false; // negative numbers are not palindromes
        String s = Integer.toString(x);
        String rev = new StringBuilder(s).reverse().toString();
        return s.equals(rev);
    }

    // Approach 2: Mathematical Reversal (Optimal)
    public static boolean isPalindromeMath(int x) {
        if (x < 0 || (x % 10 == 0 && x != 0)) return false;
        int reversedHalf = 0;
        while (x > reversedHalf) {
            int digit = x % 10;
            reversedHalf = reversedHalf * 10 + digit;
            x /= 10;
        }
        // For even length: x == reversedHalf
        // For odd length: x == reversedHalf/10
        return (x == reversedHalf || x == reversedHalf / 10);
    }

    public static void main(String[] args) {
        int num = 121;

        // Test String Conversion
        System.out.println("String Conversion Result: " + isPalindromeString(num));

        // Test Mathematical Reversal
        System.out.println("Mathematical Reversal Result: " + isPalindromeMath(num));
    }
}
