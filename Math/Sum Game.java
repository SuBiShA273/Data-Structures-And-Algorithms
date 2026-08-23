/*
Problem: Alice and Bob play a game with a string num of even length.
- num consists of digits and '?' characters.
- Alice replaces '?' in the first half, Bob replaces '?' in the second half.
- Alice wins if she can make the sum of digits in the first half ≠ sum of digits in the second half.
- Otherwise, Bob wins.

Source: LeetCode Problem #1927 → https://leetcode.com/problems/sum-game/

Optimized Approach (Mathematical Reasoning):
   - Compute sum of known digits in both halves.
   - Count number of '?' in each half.
   - If total '?' is odd → Alice wins (she has last move).
   - If total '?' is even → compare difference in sums with possible max adjustment.
   - Time Complexity: O(n)
   - Space Complexity: O(1)
*/

class Solution {
    public boolean sumGame(String num) {
        int n = num.length();
        int half = n / 2;
        int sum1 = 0, sum2 = 0, q1 = 0, q2 = 0;

        // First half
        for (int i = 0; i < half; i++) {
            if (num.charAt(i) == '?') q1++;
            else sum1 += num.charAt(i) - '0';
        }

        // Second half
        for (int i = half; i < n; i++) {
            if (num.charAt(i) == '?') q2++;
            else sum2 += num.charAt(i) - '0';
        }

        // If total '?' is odd → Alice wins
        if ((q1 + q2) % 2 == 1) return true;

        // Otherwise, check if sums can be equalized
        int diff = sum1 - sum2;
        int adjust = (q2 - q1) * 9 / 2;
        return diff != adjust;
    }
}

// Driver class
public class Main {
    public static void main(String[] args) {
        Solution sol = new Solution();
        System.out.println(sol.sumGame("25??")); // true
        System.out.println(sol.sumGame("?329")); // false
    }
}
