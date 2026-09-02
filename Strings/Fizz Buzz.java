/*
Problem: Given an integer n, return a string array answer (1-indexed) where:
- answer[i] == "FizzBuzz" if i is divisible by 3 and 5.
- answer[i] == "Fizz" if i is divisible by 3.
- answer[i] == "Buzz" if i is divisible by 5.
- answer[i] == i (as a string) if none of the above conditions are true.

Source: LeetCode Problem #412 → https://leetcode.com/problems/fizz-buzz/

Approaches:
1. Brute Force (Direct Conditions)
   - Idea:
     - Loop from 1 to n.
     - Check divisibility by 3 and 5 separately.
     - Append correct string to result.
   - Time Complexity: O(n).
   - Space Complexity: O(n).

2. Optimized (String Building)
   - Idea:
     - Loop from 1 to n.
     - Build string dynamically:
       - Append "Fizz" if divisible by 3.
       - Append "Buzz" if divisible by 5.
       - If string is empty, append number.
     - More flexible and avoids multiple condition checks.
   - Time Complexity: O(n).
   - Space Complexity: O(n).
*/

import java.util.*;

// Approach 1: Brute Force
class FizzBuzzBrute {
    public List<String> fizzBuzz(int n) {
        List<String> result = new ArrayList<>();
        for (int i = 1; i <= n; i++) {
            if (i % 3 == 0 && i % 5 == 0) result.add("FizzBuzz");
            else if (i % 3 == 0) result.add("Fizz");
            else if (i % 5 == 0) result.add("Buzz");
            else result.add(String.valueOf(i));
        }
        return result;
    }
}

// Approach 2: Optimized (String Building)
class FizzBuzzOptimized {
    public List<String> fizzBuzz(int n) {
        List<String> result = new ArrayList<>();
        for (int i = 1; i <= n; i++) {
            StringBuilder sb = new StringBuilder();
            if (i % 3 == 0) sb.append("Fizz");
            if (i % 5 == 0) sb.append("Buzz");
            if (sb.length() == 0) sb.append(i);
            result.add(sb.toString());
        }
        return result;
    }
}

// Driver class
public class Main {
    public static void main(String[] args) {
        int n = 15;

        // Test Brute Force
        FizzBuzzBrute brute = new FizzBuzzBrute();
        System.out.println("Brute Force Result: " + brute.fizzBuzz(n));

        // Test Optimized
        FizzBuzzOptimized opt = new FizzBuzzOptimized();
        System.out.println("Optimized Result: " + opt.fizzBuzz(n));
    }
}
