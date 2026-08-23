/*
Problem: Given an array of intervals where intervals[i] = [start, end],
return the minimum number of intervals you need to remove to make the rest non-overlapping.

Source: LeetCode Problem #435 → https://leetcode.com/problems/non-overlapping-intervals/

Approaches:
1. Brute Force (Check All Pairs)
   - Idea: Compare every interval with every other interval.
   - Count overlaps and remove accordingly.
   - Time Complexity: O(n^2).
   - Space Complexity: O(1).

2. Optimized (Greedy)
   - Idea:
     - Sort intervals by end time.
     - Keep track of the last chosen interval’s end.
     - If current interval overlaps → remove it.
     - Otherwise, update last end.
   - Time Complexity: O(n log n) (due to sorting).
   - Space Complexity: O(1).
*/

import java.util.*;

// Approach 1: Brute Force
class NonOverlappingIntervalsBrute {
    public int eraseOverlapIntervals(int[][] intervals) {
        int n = intervals.length;
        int removals = 0;

        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                if (!(intervals[i][1] <= intervals[j][0] || intervals[j][1] <= intervals[i][0])) {
                    removals++;
                }
            }
        }
        return removals; // not efficient, just illustrative
    }
}

// Approach 2: Optimized (Greedy)
class NonOverlappingIntervalsOptimized {
    public int eraseOverlapIntervals(int[][] intervals) {
        Arrays.sort(intervals, (a, b) -> a[1] - b[1]); // sort by end time
        int count = 0;
        int prevEnd = intervals[0][1];

        for (int i = 1; i < intervals.length; i++) {
            if (intervals[i][0] < prevEnd) {
                count++; // overlap → remove current
            } else {
                prevEnd = intervals[i][1]; // update end
            }
        }
        return count;
    }
}

// Driver class
public class Main {
    public static void main(String[] args) {
        int[][] intervals1 = {{1,2},{2,3},{3,4},{1,3}};
        int[][] intervals2 = {{1,2},{1,2},{1,2}};

        // Test Brute Force
        NonOverlappingIntervalsBrute brute = new NonOverlappingIntervalsBrute();
        System.out.println("Brute Force Result (intervals1): " + brute.eraseOverlapIntervals(intervals1));
        System.out.println("Brute Force Result (intervals2): " + brute.eraseOverlapIntervals(intervals2));

        // Test Optimized
        NonOverlappingIntervalsOptimized opt = new NonOverlappingIntervalsOptimized();
        System.out.println("Optimized Result (intervals1): " + opt.eraseOverlapIntervals(intervals1)); // 1
        System.out.println("Optimized Result (intervals2): " + opt.eraseOverlapIntervals(intervals2)); // 2
    }
}
