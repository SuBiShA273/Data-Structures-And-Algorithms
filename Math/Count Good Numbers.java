/*
Problem: A digit string is "good" if the digits at even indices are prime (2,3,5,7)
and the digits at odd indices are even (0,2,4,6,8).
Given an integer n, return the total number of good digit strings of length n.
Since the answer may be large, return it modulo 1e9+7.

Source: LeetCode Problem #1922 → https://leetcode.com/problems/count-good-numbers/

Approaches:
1. Brute Force (Combinatorial Counting)
   - Idea:
     - At even positions: 5 choices (prime digits).
     - At odd positions: 4 choices (even digits).
     - Total = 5^(ceil(n/2)) * 4^(floor(n/2)).
     - Compute directly with loops.
   - Time Complexity: O(n).
   - Space Complexity: O(1).

2. Optimal (Fast Exponentiation / Modular Power)
   - Idea:
     - Use modular exponentiation to compute powers efficiently.
     - Even positions count = (n+1)/2.
     - Odd positions count = n/2.
     - Result = (5^evenCount * 4^oddCount) % MOD.
   - Time Complexity: O(log n).
   - Space Complexity: O(1).
*/

public class CountGoodNumbers {
    static final long MOD = 1000000007;

    // Approach 1: Brute Force (Loop Power)
    public static long countGoodNumbersBrute(long n) {
        long evenCount = (n + 1) / 2; // positions with prime digits
        long oddCount = n / 2;        // positions with even digits

        long res = 1;
        for (int i = 0; i < evenCount; i++) res = (res * 5) % MOD;
        for (int i = 0; i < oddCount; i++) res = (res * 4) % MOD;
        return res;
    }

    // Approach 2: Optimal (Fast Modular Exponentiation)
    public static long countGoodNumbersOptimal(long n) {
        long evenCount = (n + 1) / 2;
        long oddCount = n / 2;

        long res = (modPow(5, evenCount, MOD) * modPow(4, oddCount, MOD)) % MOD;
        return res;
    }

    // Utility: Modular exponentiation
    private static long modPow(long base, long exp, long mod) {
        long result = 1;
        base %= mod;
        while (exp > 0) {
            if ((exp & 1) == 1) result = (result * base) % mod;
            base = (base * base) % mod;
            exp >>= 1;
        }
        return result;
    }

    // Driver
    public static void main(String[] args) {
        long n1 = 1;
        long n2 = 4;
        long n3 = 50;

        System.out.println("Brute Force Result (n=1): " + countGoodNumbersBrute(n1)); // Expected: 5
        System.out.println("Optimal Result (n=1): " + countGoodNumbersOptimal(n1));   // Expected: 5

        System.out.println("Brute Force Result (n=4): " + countGoodNumbersBrute(n2)); // Expected: 400
        System.out.println("Optimal Result (n=4): " + countGoodNumbersOptimal(n2));   // Expected: 400

        System.out.println("Brute Force Result (n=50): " + countGoodNumbersBrute(n3));
        System.out.println("Optimal Result (n=50): " + countGoodNumbersOptimal(n3));
    }
}
