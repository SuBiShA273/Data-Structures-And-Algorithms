/*
Problem: Given a signed 32-bit integer x, return x with its digits reversed.
If reversing x causes the value to go outside the signed 32-bit integer range [-2^31, 2^31 - 1], return 0.

Source: LeetCode Problem #7 → https://leetcode.com/problems/reverse-integer/

Approaches:
1. String Conversion (Simple)
   - Idea: Convert integer to string, reverse it, then parse back.
   - Handle overflow using try-catch or bounds check.
   - Time Complexity: O(n) where n = number of digits
   - Space Complexity: O(n)

2. Mathematical Reversal (Optimal)
   - Idea: Use modulus and division to extract digits and rebuild reversed number.
   - Check overflow before multiplying/adding.
   - Time Complexity: O(log10(n)) → number of digits
   - Space Complexity: O(1)
*/

public class ReverseInteger {

    // Approach 1: String Conversion
    public static int reverseString(int x) {
        try {
            String s = Integer.toString(x);
            boolean negative = s.charAt(0) == '-';
            String rev = new StringBuilder(negative ? s.substring(1) : s).reverse().toString();
            int result = Integer.parseInt(negative ? "-" + rev : rev);
            return result;
        } catch (NumberFormatException e) {
            return 0; // overflow case
        }
    }

    // Approach 2: Mathematical Reversal (Optimal)
    public static int reverseMath(int x) {
        int result = 0;
        while (x != 0) {
            int digit = x % 10;
            x /= 10;
            // Check overflow before updating result
            if (result > Integer.MAX_VALUE / 10 || result < Integer.MIN_VALUE / 10) {
                return 0;
            }
            result = result * 10 + digit;
        }
        return result;
    }

    public static void main(String[] args) {
        int num = 123;

        // Test String Conversion
        System.out.println("String Conversion Result: " + reverseString(num));

        // Test Mathematical Reversal
        System.out.println("Mathematical Reversal Result: " + reverseMath(num));
    }
}
