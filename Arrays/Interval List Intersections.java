/*
Problem: You are given two lists of closed intervals, firstList and secondList,
where each interval is [start, end] and the lists are sorted and disjoint.
Return the intersection of these two interval lists.

Source: LeetCode Problem #986 → https://leetcode.com/problems/interval-list-intersections/

Approaches:
1. Brute Force (Check All Pairs)
   - Idea:
     - For each interval in firstList, compare with every interval in secondList.
     - If they overlap, compute intersection and add to result.
   - Time Complexity: O(m * n).
   - Space Complexity: O(1).

2. Optimized (Two Pointers)
   - Idea:
     - Use two pointers i and j for firstList and secondList.
     - At each step, check overlap between intervals.
     - If overlap exists, add intersection.
     - Move pointer of interval that ends first.
   - Time Complexity: O(m + n).
   - Space Complexity: O(1).
*/

import java.util.*;

// Approach 1: Brute Force
class IntervalIntersectionBrute {
    public int[][] intervalIntersection(int[][] firstList, int[][] secondList) {
        List<int[]> result = new ArrayList<>();
        for (int[] a : firstList) {
            for (int[] b : secondList) {
                int start = Math.max(a[0], b[0]);
                int end = Math.min(a[1], b[1]);
                if (start <= end) {
                    result.add(new int[]{start, end});
                }
            }
        }
        return result.toArray(new int[result.size()][]);
    }
}

// Approach 2: Optimized (Two Pointers)
class IntervalIntersectionOptimized {
    public int[][] intervalIntersection(int[][] firstList, int[][] secondList) {
        List<int[]> result = new ArrayList<>();
        int i = 0, j = 0;

        while (i < firstList.length && j < secondList.length) {
            int start = Math.max(firstList[i][0], secondList[j][0]);
            int end = Math.min(firstList[i][1], secondList[j][1]);

            if (start <= end) {
                result.add(new int[]{start, end});
            }

            if (firstList[i][1] < secondList[j][1]) {
                i++;
            } else {
                j++;
            }
        }
        return result.toArray(new int[result.size()][]);
    }
}

// Driver class
public class Main {
    public static void main(String[] args) {
        int[][] firstList = {{0,2},{5,10},{13,23},{24,25}};
        int[][] secondList = {{1,5},{8,12},{15,24},{25,26}};

        // Test Brute Force
        IntervalIntersectionBrute brute = new IntervalIntersectionBrute();
        System.out.println("Brute Force Result:");
        for (int[] interval : brute.intervalIntersection(firstList, secondList)) {
            System.out.println(Arrays.toString(interval));
        }

        // Test Optimized
        IntervalIntersectionOptimized opt = new IntervalIntersectionOptimized();
        System.out.println("Optimized Result:");
        for (int[] interval : opt.intervalIntersection(firstList, secondList)) {
            System.out.println(Arrays.toString(interval));
        }
    }
}
