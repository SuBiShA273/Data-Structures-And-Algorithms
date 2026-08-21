/*
Problem: A complex number can be represented as a string in the form "a+bi".
Given two complex numbers num1 and num2, return their product as a string.

Source: LeetCode Problem #537 → https://leetcode.com/problems/complex-number-multiplication/

Approaches:
1. Brute Force (Manual Parsing with Split)
   - Idea: Split the string by '+' and 'i' to extract real and imaginary parts.
   - Apply multiplication formula: (a+bi)(c+di) = (ac - bd) + (ad + bc)i
   - Time Complexity: O(1)
   - Space Complexity: O(1)

2. Optimized (Direct Index Parsing)
   - Idea: Use indexOf('+') to locate the split point.
   - Parse substrings directly into integers.
   - Apply multiplication formula.
   - Time Complexity: O(1)
   - Space Complexity: O(1)
*/

import java.util.*;

// Approach 1: Brute Force (Split)
class ComplexMultiplyBrute {
    public String complexNumberMultiply(String num1, String num2) {
        String[] parts1 = num1.split("\\+");
        String[] parts2 = num2.split("\\+");

        int a = Integer.parseInt(parts1[0]);
        int b = Integer.parseInt(parts1[1].replace("i", ""));
        int c = Integer.parseInt(parts2[0]);
        int d = Integer.parseInt(parts2[1].replace("i", ""));

        int real = a * c - b * d;
        int imag = a * d + b * c;

        return real + "+" + imag + "i";
    }
}

// Approach 2: Optimized (Index Parsing)
class ComplexMultiplyOptimized {
    public String complexNumberMultiply(String num1, String num2) {
        int plus1 = num1.indexOf('+');
        int plus2 = num2.indexOf('+');

        int a = Integer.parseInt(num1.substring(0, plus1));
        int b = Integer.parseInt(num1.substring(plus1 + 1, num1.length() - 1));
        int c = Integer.parseInt(num2.substring(0, plus2));
        int d = Integer.parseInt(num2.substring(plus2 + 1, num2.length() - 1));

        int real = a * c - b * d;
        int imag = a * d + b * c;

        return real + "+" + imag + "i";
    }
}

// Driver class
public class Main {
    public static void main(String[] args) {
        String num1 = "1+1i";
        String num2 = "1+1i";

        // Test Brute Force
        ComplexMultiplyBrute brute = new ComplexMultiplyBrute();
        System.out.println("Brute Force Result: " + brute.complexNumberMultiply(num1, num2));
        // Expected: "0+2i"

        // Test Optimized
        ComplexMultiplyOptimized optimized = new ComplexMultiplyOptimized();
        System.out.println("Optimized Result: " + optimized.complexNumberMultiply(num1, num2));
        // Expected: "0+2i"
    }
}
