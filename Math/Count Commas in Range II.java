/*
Problem: You are given an integer n.
Return the total number of commas used when writing all integers from [1, n] (inclusive)
in standard number formatting.

In standard formatting:
- A comma is inserted after every three digits from the right.
- Numbers with fewer than 4 digits contain no commas.

Source: Custom Problem (Count Commas in Range II)

Approach: Optimized (Mathematical Ranges)
   - Idea:
     - Commas appear only for numbers >= 1,000.
     - For each threshold (1,000, 1,000,000, 1,000,000,000, ...),
       count how many numbers from that threshold up to n have at least that many commas.
     - Accumulate counts.
   - Time Complexity: O(log n).
   - Space Complexity: O(1).
*/

class Solution {
    public long countCommas(long n) {
        if (n < 1000) return 0;
        long x = 1000;
        long cnt = 0;
        while (x <= n) {
            cnt += n - x + 1;
            x *= 1000;
        }
        return cnt;
    }
}

// Driver class
public class Main {
    public static void main(String[] args) {
        Solution sol = new Solution();

        long n1 = 1002;   // Expected 3
        long n2 = 998;    // Expected 0
        long n3 = 1_000_000; // Expected 1 comma for each number from 1000–999999 + 2 commas for 1,000,000

        System.out.println("Optimized Result (1002): " + sol.countCommas(n1));
        System.out.println("Optimized Result (998): " + sol.countCommas(n2));
        System.out.println("Optimized Result (1,000,000): " + sol.countCommas(n3));
    }
}
