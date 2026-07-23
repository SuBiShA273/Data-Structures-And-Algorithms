/*
Problem: Write a function that reverses a string.
The input string is given as an array of characters s.
You must do this by modifying the input array in-place with O(1) extra memory.

Source: LeetCode Problem #344 → https://leetcode.com/problems/reverse-string/

Approaches:
1. Brute Force (Extra Array)
   - Idea: Copy characters into a new array in reverse order, then overwrite.
   - Time Complexity: O(n)
   - Space Complexity: O(n)

2. Two-Pointer Technique (Optimal)
   - Idea: Use two pointers (start and end), swap characters until they meet.
   - Time Complexity: O(n)
   - Space Complexity: O(1)
*/

import java.util.*;

public class ReverseString {

    // Approach 1: Brute Force using Extra Array
    public static void reverseStringBrute(char[] s) {
        char[] temp = new char[s.length];
        for (int i = 0; i < s.length; i++) {
            temp[i] = s[s.length - 1 - i];
        }
        for (int i = 0; i < s.length; i++) {
            s[i] = temp[i];
        }
    }

    // Approach 2: Two-Pointer (Optimal)
    public static void reverseStringOptimal(char[] s) {
        int left = 0, right = s.length - 1;
        while (left < right) {
            char temp = s[left];
            s[left] = s[right];
            s[right] = temp;
            left++;
            right--;
        }
    }

    public static void main(String[] args) {
        char[] s = {'h','e','l','l','o'};

        // Test Brute Force
        char[] s1 = Arrays.copyOf(s, s.length);
        reverseStringBrute(s1);
        System.out.print("Brute Force Result: ");
        System.out.println(s1);

        // Test Optimal
        char[] s2 = Arrays.copyOf(s, s.length);
        reverseStringOptimal(s2);
        System.out.print("Optimal Result: ");
        System.out.println(s2);
    }
}
