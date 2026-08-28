/*
Problem: Given a string s and an integer k, reverse the first k characters 
for every 2k characters counting from the start of the string.
If there are fewer than k characters left, reverse all of them.
If there are between k and 2k characters, reverse the first k characters 
and leave the others as original.

Source: LeetCode Problem #541 → https://leetcode.com/problems/reverse-string-ii/

Approaches:
1. Brute Force (Substring Manipulation)
   - Idea:
     - Split the string into chunks of size 2k.
     - For each chunk, reverse the first k characters manually.
     - Concatenate results.
   - Time Complexity: O(n).
   - Space Complexity: O(n).

2. Optimized (In-place Char Array Reversal)
   - Idea:
     - Convert string to char array.
     - For every 2k block, reverse the first k characters in place.
     - Use helper function to reverse efficiently.
   - Time Complexity: O(n).
   - Space Complexity: O(1).
*/

import java.util.*;

// Approach 1: Brute Force (Substring Manipulation)
class ReverseStringBrute {
    public String reverseStr(String s, int k) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < s.length(); i += 2 * k) {
            String part = s.substring(i, Math.min(i + k, s.length()));
            result.append(new StringBuilder(part).reverse());
            if (i + k < s.length()) {
                result.append(s.substring(i + k, Math.min(i + 2 * k, s.length())));
            }
        }
        return result.toString();
    }
}

// Approach 2: Optimized (In-place Char Array Reversal)
class ReverseStringOptimized {
    public String reverseStr(String s, int k) {
        char[] arr = s.toCharArray();
        for (int start = 0; start < arr.length; start += 2 * k) {
            int i = start, j = Math.min(start + k - 1, arr.length - 1);
            while (i < j) {
                char temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
                i++;
                j--;
            }
        }
        return new String(arr);
    }
}

// Driver class
public class Main {
    public static void main(String[] args) {
        String s1 = "abcdefg"; int k1 = 2;
        String s2 = "abcd";    int k2 = 2;

        // Test Brute Force
        ReverseStringBrute brute = new ReverseStringBrute();
        System.out.println("Brute Force Result (abcdefg,k=2): " + brute.reverseStr(s1, k1)); // "bacdfeg"
        System.out.println("Brute Force Result (abcd,k=2): " + brute.reverseStr(s2, k2));   // "bacd"

        // Test Optimized
        ReverseStringOptimized opt = new ReverseStringOptimized();
        System.out.println("Optimized Result (abcdefg,k=2): " + opt.reverseStr(s1, k1)); // "bacdfeg"
        System.out.println("Optimized Result (abcd,k=2): " + opt.reverseStr(s2, k2));   // "bacd"
    }
}
