/*
Problem: You are given an array of non-overlapping intervals sorted by start time,
and a new interval. Insert the new interval into intervals such that the result
remains sorted and non-overlapping. Merge if necessary.

Source: LeetCode Problem #57 → https://leetcode.com/problems/insert-interval/

Optimized Approach (Greedy Merge in One Pass):
   - Iterate through intervals.
   - Add intervals that end before newInterval starts.
   - Merge overlapping intervals with newInterval.
   - Add remaining intervals after newInterval.
   - Time Complexity: O(n)
   - Space Complexity: O(n)
*/

import java.util.*;

class Solution {
    public int[][] insert(int[][] intervals, int[] newInterval) {
        List<int[]> result = new ArrayList<>();
        int i = 0, n = intervals.length;

        // Add intervals before newInterval
        while (i < n && intervals[i][1] < newInterval[0]) {
            result.add(intervals[i]);
            i++;
        }

        // Merge overlapping intervals
        while (i < n && intervals[i][0] <= newInterval[1]) {
            newInterval[0] = Math.min(newInterval[0], intervals[i][0]);
            newInterval[1] = Math.max(newInterval[1], intervals[i][1]);
            i++;
        }
        result.add(newInterval);

        // Add remaining intervals
        while (i < n) {
            result.add(intervals[i]);
            i++;
        }

        return result.toArray(new int[result.size()][]);
    }
}

// Driver class
public class Main {
    public static void main(String[] args) {
        Solution sol = new Solution();

        int[][] intervals1 = {{1,3},{6,9}};
        int[] newInterval1 = {2,5};
        System.out.println("Optimized Result 1: " + Arrays.deepToString(sol.insert(intervals1, newInterval1)));
        // Output: [[1,5],[6,9]]

        int[][] intervals2 = {{1,2},{3,5},{6,7},{8,10},{12,16}};
        int[] newInterval2 = {4,8};
        System.out.println("Optimized Result 2: " + Arrays.deepToString(sol.insert(intervals2, newInterval2)));
        // Output: [[1,2],[3,10],[12,16]]
    }
}
