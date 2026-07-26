/*
Problem: Find the second largest digit in a string.
The string s consists of lowercase English letters and digits.
Return the second largest digit found in s. If it does not exist, return -1.

Source: LeetCode Problem #1796 → https://leetcode.com/problems/second-largest-digit-in-a-string/

Approaches:
1. Brute Force (Collect Digits, Sort)
   - Idea: Extract all digits, sort them, then pick the second largest.
   - Time Complexity: O(n log n)
   - Space Complexity: O(n)

2. Optimal (Track Largest & Second Largest)
   - Idea: Traverse once, track largest and second largest digit values.
   - Time Complexity: O(n)
   - Space Complexity: O(1)
*/

import java.util.*;

public class SecondLargestDigit {

    // Approach 1: Brute Force using Sorting
    public static int secondHighestBrute(String s) {
        List<Integer> digits = new ArrayList<>();
        for (char c : s.toCharArray()) {
            if (Character.isDigit(c)) {
                digits.add(c - '0');
            }
        }
        if (digits.size() < 2) return -1;
        Collections.sort(digits);
        int largest = digits.get(digits.size() - 1);
        // Find next distinct smaller digit
        for (int i = digits.size() - 2; i >= 0; i--) {
            if (digits.get(i) != largest) {
                return digits.get(i);
            }
        }
        return -1;
    }

    // Approach 2: Optimal (Track Largest & Second Largest)
    public static int secondHighestOptimal(String s) {
        int first = -1, second = -1;
        for (char c : s.toCharArray()) {
            if (Character.isDigit(c)) {
                int val = c - '0';
                if (val > first) {
                    second = first;
                    first = val;
                } else if (val < first && val > second) {
                    second = val;
                }
            }
        }
        return second;
    }

    public static void main(String[] args) {
        String s = "dfa12321afd";

        // Test Brute Force
        int res1 = secondHighestBrute(s);
        System.out.println("Brute Force Result: " + res1);

        // Test Optimal
        int res2 = secondHighestOptimal(s);
        System.out.println("Optimal Result: " + res2);
    }
}
