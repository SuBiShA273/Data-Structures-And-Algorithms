/*
Problem: You are given a positive integer n.
- Compute digitSum = sum of digits of n.
- Compute digitProduct = product of digits of n.
- Check if n is divisible by (digitSum + digitProduct).
Return true if divisible, otherwise false.

Source: LeetCode Problem #3622 → https://leetcode.com/problems/check-if-number-is-divisible-by-sum-and-product-of-digits/

Approaches:
1. Brute Force (String Parsing)
   - Idea: Convert n to string, iterate over characters.
   - Extract digits, compute sum and product.
   - Check divisibility.
   - Time Complexity: O(d) where d = number of digits.
   - Space Complexity: O(1).

2. Optimized (Math / Modulo Extraction)
   - Idea: Use modulo and division to extract digits directly.
   - Avoid string conversion overhead.
   - Compute sum and product in one pass.
   - Time Complexity: O(d).
   - Space Complexity: O(1).
*/

import java.util.*;

// Approach 1: Brute Force (String Parsing)
class DivisibleBrute {
    public boolean isDivisible(int n) {
        String s = String.valueOf(n);
        int sum = 0, product = 1;
        for (char c : s.toCharArray()) {
            int digit = c - '0';
            sum += digit;
            product *= digit;
        }
        return n % (sum + product) == 0;
    }
}

// Approach 2: Optimized (Math / Modulo Extraction)
class DivisibleOptimized {
    public boolean isDivisible(int n) {
        int original = n;
        int sum = 0, product = 1;
        while (n > 0) {
            int digit = n % 10;
            sum += digit;
            product *= digit;
            n /= 10;
        }
        return original % (sum + product) == 0;
    }
}

// Driver class
public class Main {
    public static void main(String[] args) {
        int n1 = 99;
        int n2 = 23;

        // Test Brute Force
        DivisibleBrute brute = new DivisibleBrute();
        System.out.println("Brute Force Result (99): " + brute.isDivisible(n1)); // true
        System.out.println("Brute Force Result (23): " + brute.isDivisible(n2)); // false

        // Test Optimized
        DivisibleOptimized optimized = new DivisibleOptimized();
        System.out.println("Optimized Result (99): " + optimized.isDivisible(n1)); // true
        System.out.println("Optimized Result (23): " + optimized.isDivisible(n2)); // false
    }
}
