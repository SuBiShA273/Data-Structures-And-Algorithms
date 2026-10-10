/*
Problem: Write an algorithm to determine if a number n is "happy".
A happy number is defined by repeatedly replacing the number with the sum of the squares of its digits
until it becomes 1 (happy) or loops endlessly (not happy).

Source: LeetCode Problem #202 → https://leetcode.com/problems/happy-number/

Approaches:
1. Brute Force (DFS Recursion + HashSet)
   - Idea:
     - Recursively compute sum of squares of digits.
     - Track visited numbers in a set.
     - If we reach 1 → happy.
     - If we revisit a number → cycle → not happy.
   - Time Complexity: O(log n) per iteration, may repeat.
   - Space Complexity: O(n) for visited set.

2. Optimal (Floyd’s Cycle Detection)
   - Idea:
     - Use two pointers (slow, fast).
     - Slow moves one step (sum of squares once).
     - Fast moves two steps (sum of squares twice).
     - If they meet at 1 → happy.
     - If they meet elsewhere → cycle → not happy.
   - Time Complexity: O(log n).
   - Space Complexity: O(1).
*/

import java.util.*;

class BruteForce {
    public boolean isHappy(int n) {
        Set<Integer> seen = new HashSet<>();
        return dfs(n, seen);
    }

    private boolean dfs(int n, Set<Integer> seen) {
        if (n == 1) return true;
        if (seen.contains(n)) return false;
        seen.add(n);
        int next = sumOfSquares(n);
        return dfs(next, seen);
    }

    private int sumOfSquares(int n) {
        int sum = 0;
        while (n > 0) {
            int d = n % 10;
            sum += d * d;
            n /= 10;
        }
        return sum;
    }
}

class Optimal {
    public boolean isHappy(int n) {
        int slow = n, fast = next(n);
        while (fast != 1 && slow != fast) {
            slow = next(slow);
            fast = next(next(fast));
        }
        return fast == 1;
    }

    private int next(int n) {
        int sum = 0;
        while (n > 0) {
            int d = n % 10;
            sum += d * d;
            n /= 10;
        }
        return sum;
    }
}

// Driver class with testcases
public class Main {
    public static void main(String[] args) {
        BruteForce brute = new BruteForce();
        Optimal opt = new Optimal();

        // Testcase 1
        int n1 = 19;
        System.out.println("Brute Force Result (Testcase 1): " + brute.isHappy(n1)); // Expected: true
        System.out.println("Optimal Result (Testcase 1): " + opt.isHappy(n1));       // Expected: true

        // Testcase 2
        int n2 = 2;
        System.out.println("\nBrute Force Result (Testcase 2): " + brute.isHappy(n2)); // Expected: false
        System.out.println("Optimal Result (Testcase 2): " + opt.isHappy(n2));         // Expected: false

        // Testcase 3
        int n3 = 7;
        System.out.println("\nBrute Force Result (Testcase 3): " + brute.isHappy(n3)); // Expected: true
        System.out.println("Optimal Result (Testcase 3): " + opt.isHappy(n3));         // Expected: true
    }
}
