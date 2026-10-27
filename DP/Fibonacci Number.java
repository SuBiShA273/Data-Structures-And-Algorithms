/*
Problem: Fibonacci Number

The Fibonacci numbers are defined as:

F(0) = 0
F(1) = 1

F(n) = F(n - 1) + F(n - 2), for n > 1

Given n, return F(n).

Source: LeetCode Problem #509
https://leetcode.com/problems/fibonacci-number/

Approaches:

1. Brute Force (Recursion)
   - Idea:
     - Directly follow the Fibonacci definition.
     - Recursively calculate:
           fib(n) = fib(n - 1) + fib(n - 2)
     - Recomputes the same subproblems many times.

   - Time Complexity: O(2^n)
   - Space Complexity: O(n)
     (Recursion stack)

2. Optimized (Iterative DP - Space Optimized)
   - Idea:
     - Store only the previous two Fibonacci numbers.
     - Compute the current Fibonacci value iteratively.
     - Avoid recursion and extra DP array.

   - Time Complexity: O(n)
   - Space Complexity: O(1)
*/

import java.util.*;

// Approach 1: Brute Force (Recursion)
class FibonacciBrute {

    public int fib(int n) {

        if (n <= 1) {
            return n;
        }

        return fib(n - 1) + fib(n - 2);
    }
}

// Approach 2: Optimized (Iterative DP)
class FibonacciOptimized {

    public int fib(int n) {

        if (n <= 1) {
            return n;
        }

        int prev1 = 1;
        int prev2 = 0;

        for (int i = 2; i <= n; i++) {

            int cur = prev1 + prev2;

            prev2 = prev1;
            prev1 = cur;
        }

        return prev1;
    }
}

// Driver Class
public class Main {

    public static void main(String[] args) {

        FibonacciBrute brute = new FibonacciBrute();

        System.out.println(
                "Brute Force Output: "
                        + brute.fib(5)
        ); // 5

        FibonacciOptimized optimized =
                new FibonacciOptimized();

        System.out.println(
                "Optimized Output: "
                        + optimized.fib(5)
        ); // 5
    }
}
