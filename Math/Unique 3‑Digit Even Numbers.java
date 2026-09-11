/*
Problem: Given an array of digits (0–9),
return the number of distinct 3-digit even numbers that can be formed
using the digits exactly as many times as they appear.

Rules:
- Each digit can be used only as many times as it appears in the array.
- Numbers cannot have leading zeros.
- The last digit must be even.

Source: LeetCode Problem #3483 → https://leetcode.com/problems/unique-3-digit-even-numbers/

Approaches:
1. Frequency Check (Optimized Enumeration)
   - Idea:
     - Precompute frequency of digits.
     - Iterate through all 3-digit even numbers (100–998).
     - For each candidate, check if digits exist in input with sufficient frequency.
   - Time Complexity: O(900 * n).
   - Space Complexity: O(10).

2. Permutation with Set (Brute Force)
   - Idea:
     - Generate all permutations of 3 digits.
     - Skip if leading digit is 0 or last digit is odd.
     - Store valid numbers in a set to ensure uniqueness.
   - Time Complexity: O(n^3).
   - Space Complexity: O(n^3).
*/

import java.util.*;

// Approach 1: Frequency Check
class UniqueEvenNumbersFreq {
    public int totalNumbers(int[] digits) {
        int cnt = 0;
        int[] freq = new int[10];
        for (int d : digits) freq[d]++;

        for (int num = 100; num <= 999; num += 2) { // only even numbers
            int o = num % 10;
            int t = (num / 10) % 10;
            int h = num / 100;

            freq[o]--; freq[t]--; freq[h]--;
            if (freq[o] >= 0 && freq[t] >= 0 && freq[h] >= 0) {
                cnt++;
            }
            freq[o]++; freq[t]++; freq[h]++; // restore
        }
        return cnt;
    }
}

// Approach 2: Permutation with Set
class UniqueEvenNumbersSet {
    public int totalNumbers(int[] digits) {
        int n = digits.length;
        HashSet<Integer> set = new HashSet<>();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                for (int k = 0; k < n; k++) {
                    if (i != j && j != k && k != i) {
                        if (digits[i] != 0 && digits[k] % 2 == 0) {
                            int num = digits[i] * 100 + digits[j] * 10 + digits[k];
                            set.add(num);
                        }
                    }
                }
            }
        }
        return set.size();
    }
}

// Driver class
public class Main {
    public static void main(String[] args) {
        int[] digits1 = {1,2,3,4};
        int[] digits2 = {0,2,2};
        int[] digits3 = {6,6,6};
        int[] digits4 = {1,3,5};

        UniqueEvenNumbersFreq freqSol = new UniqueEvenNumbersFreq();
        System.out.println("Frequency Result [1,2,3,4]: " + freqSol.totalNumbers(digits1)); // 12
        System.out.println("Frequency Result [0,2,2]: " + freqSol.totalNumbers(digits2)); // 2

        UniqueEvenNumbersSet setSol = new UniqueEvenNumbersSet();
        System.out.println("Set Result [6,6,6]: " + setSol.totalNumbers(digits3)); // 1
        System.out.println("Set Result [1,3,5]: " + setSol.totalNumbers(digits4)); // 0
    }
}
