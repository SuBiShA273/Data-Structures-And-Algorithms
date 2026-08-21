/*
Problem: Given two arrays g (children’s greed factors) and s (cookie sizes),
assign cookies to children such that the maximum number of children are content.
Each child i is content if s[j] >= g[i] for some cookie j.
Return the maximum number of content children.

Source: LeetCode Problem #455 → https://leetcode.com/problems/assign-cookies/

Approaches:
1. Brute Force (Nested Loops)
   - Idea: For each child, try to find a cookie that satisfies their greed.
   - Mark cookie as used once assigned.
   - Time Complexity: O(m * n) where m = children, n = cookies.
   - Space Complexity: O(n) for tracking used cookies.

2. Optimized (Greedy + Sorting)
   - Idea:
     - Sort greed array and cookie array.
     - Use two pointers: one for children, one for cookies.
     - Assign smallest possible cookie that satisfies current child.
     - Move pointers accordingly.
   - Time Complexity: O(n log n + m log m)
   - Space Complexity: O(1)
*/

import java.util.*;

// Approach 1: Brute Force
class AssignCookiesBrute {
    public int findContentChildren(int[] g, int[] s) {
        boolean[] used = new boolean[s.length];
        int count = 0;

        for (int i = 0; i < g.length; i++) {
            for (int j = 0; j < s.length; j++) {
                if (!used[j] && s[j] >= g[i]) {
                    used[j] = true;
                    count++;
                    break;
                }
            }
        }
        return count;
    }
}

// Approach 2: Optimized (Greedy)
class AssignCookiesOptimized {
    public int findContentChildren(int[] g, int[] s) {
        Arrays.sort(g);
        Arrays.sort(s);

        int i = 0, j = 0;
        while (i < g.length && j < s.length) {
            if (s[j] >= g[i]) {
                i++; // child satisfied
            }
            j++; // move to next cookie
        }
        return i;
    }
}

// Driver class
public class Main {
    public static void main(String[] args) {
        int[] g = {1,2,3};
        int[] s = {1,1};

        // Test Brute Force
        AssignCookiesBrute brute = new AssignCookiesBrute();
        System.out.println("Brute Force Result: " + brute.findContentChildren(g, s)); // 1

        // Test Optimized
        AssignCookiesOptimized optimized = new AssignCookiesOptimized();
        System.out.println("Optimized Result: " + optimized.findContentChildren(g, s)); // 1
    }
}
