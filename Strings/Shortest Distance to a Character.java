/*
Problem: Given a string s and a character c,
return an array of integers representing the shortest distance
from each character in s to the character c.

Source: LeetCode Problem #821 → https://leetcode.com/problems/shortest-distance-to-a-character/

Approaches:
1. Brute Force
   - Idea:
     - For each index i, scan left and right until we find c.
     - Take minimum distance.
   - Time Complexity: O(n^2).
   - Space Complexity: O(n).

2. Optimized (Two Passes)
   - Idea:
     - First pass left→right: record distance to nearest c on the left.
     - Second pass right→left: update with nearest c on the right.
     - Result is min of both directions.
   - Time Complexity: O(n).
   - Space Complexity: O(n).
*/

import java.util.*;

// Approach 1: Brute Force
class ShortestDistanceBrute {
    public int[] shortestToChar(String s, char c) {
        int n = s.length();
        int[] ans = new int[n];
        for (int i = 0; i < n; i++) {
            int dist = Integer.MAX_VALUE;
            for (int j = 0; j < n; j++) {
                if (s.charAt(j) == c) {
                    dist = Math.min(dist, Math.abs(i - j));
                }
            }
            ans[i] = dist;
        }
        return ans;
    }
}

// Approach 2: Optimized
class ShortestDistanceOptimized {
    public int[] shortestToChar(String s, char c) {
        int n = s.length();
        int[] ans = new int[n];
        int prev = -n; // sentinel far left
        // Left to right
        for (int i = 0; i < n; i++) {
            if (s.charAt(i) == c) prev = i;
            ans[i] = i - prev;
        }
        // Right to left
        prev = 2 * n; // sentinel far right
        for (int i = n - 1; i >= 0; i--) {
            if (s.charAt(i) == c) prev = i;
            ans[i] = Math.min(ans[i], prev - i);
        }
        return ans;
    }
}

// Driver class
public class Main {
    public static void main(String[] args) {
        String s = "loveleetcode";
        char c = 'e';

        // Test Brute Force
        ShortestDistanceBrute brute = new ShortestDistanceBrute();
        System.out.println("Brute Force Result: " + Arrays.toString(brute.shortestToChar(s, c)));
        // [3,2,1,0,1,0,0,1,2,2,1,0]

        // Test Optimized
        ShortestDistanceOptimized opt = new ShortestDistanceOptimized();
        System.out.println("Optimized Result: " + Arrays.toString(opt.shortestToChar(s, c)));
        // [3,2,1,0,1,0,0,1,2,2,1,0]
    }
}
