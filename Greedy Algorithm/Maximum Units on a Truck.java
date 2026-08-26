/*
Problem: You are given an array boxTypes, where boxTypes[i] = [numberOfBoxes, unitsPerBox].
You are also given an integer truckSize, which is the maximum number of boxes the truck can carry.
Return the maximum total number of units that can be put on the truck.

Source: LeetCode Problem #1710 → https://leetcode.com/problems/maximum-units-on-a-truck/

Approaches:
1. Brute Force (Sequential Picking)
   - Idea:
     - While truck has capacity, pick boxes one by one.
     - Always choose the box type with highest units per box available.
     - Continue until truck is full.
   - Time Complexity: O(n * truckSize).
   - Space Complexity: O(1).

2. Optimized (Greedy Sort)
   - Idea:
     - Sort boxTypes by unitsPerBox in descending order.
     - Fill truck with as many boxes as possible from highest units per box.
     - Continue until truck is full.
   - Time Complexity: O(n log n).
   - Space Complexity: O(1).
*/

import java.util.*;

// Approach 1: Brute Force
class MaximumUnitsBrute {
    public int maximumUnits(int[][] boxTypes, int truckSize) {
        int units = 0;
        while (truckSize > 0) {
            int maxUnits = -1, idx = -1;
            for (int i = 0; i < boxTypes.length; i++) {
                if (boxTypes[i][0] > 0 && boxTypes[i][1] > maxUnits) {
                    maxUnits = boxTypes[i][1];
                    idx = i;
                }
            }
            if (idx == -1) break; // no boxes left
            boxTypes[idx][0]--;   // take one box
            units += boxTypes[idx][1];
            truckSize--;
        }
        return units;
    }
}

// Approach 2: Optimized (Greedy Sort)
class MaximumUnitsOptimized {
    public int maximumUnits(int[][] boxTypes, int truckSize) {
        Arrays.sort(boxTypes, (a, b) -> b[1] - a[1]); // sort by units per box descending
        int units = 0;
        for (int[] box : boxTypes) {
            int count = Math.min(truckSize, box[0]);
            units += count * box[1];
            truckSize -= count;
            if (truckSize == 0) break;
        }
        return units;
    }
}

// Driver class
public class Main {
    public static void main(String[] args) {
        int[][] boxTypes1 = {{1,3},{2,2},{3,1}};
        int truckSize1 = 4;

        int[][] boxTypes2 = {{5,10},{2,5},{4,7},{3,9}};
        int truckSize2 = 10;

        // Test Brute Force
        MaximumUnitsBrute brute = new MaximumUnitsBrute();
        System.out.println("Brute Force Result (case1): " + brute.maximumUnits(boxTypes1, truckSize1)); // 8
        System.out.println("Brute Force Result (case2): " + brute.maximumUnits(boxTypes2, truckSize2)); // 91

        // Test Optimized
        MaximumUnitsOptimized opt = new MaximumUnitsOptimized();
        System.out.println("Optimized Result (case1): " + opt.maximumUnits(boxTypes1, truckSize1)); // 8
        System.out.println("Optimized Result (case2): " + opt.maximumUnits(boxTypes2, truckSize2)); // 91
    }
}
