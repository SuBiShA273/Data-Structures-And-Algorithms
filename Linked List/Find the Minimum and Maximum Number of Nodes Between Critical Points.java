/*
Problem: A critical point in a linked list is a node that is either a local maxima or local minima.
- Local maxima: node.val > prev.val && node.val > next.val
- Local minima: node.val < prev.val && node.val < next.val
You are given the head of a linked list.
Return an array of length 2 containing:
   - The minimum distance between any two critical points.
   - The maximum distance between any two critical points.
If there are fewer than two critical points, return [-1,-1].

Source: LeetCode Problem #2058 → https://leetcode.com/problems/find-the-minimum-and-maximum-number-of-nodes-between-critical-points/

Approaches:
1. Brute Force (Store All Critical Points)
   - Idea:
     - Traverse the list, record indices of all critical points.
     - Compute pairwise distances for min and max.
   - Time Complexity: O(n^2).
   - Space Complexity: O(n).

2. Optimized (Single Pass Tracking)
   - Idea:
     - Traverse once, record first, last, and previous critical point index.
     - Update min distance on the fly.
     - Max distance = last - first.
   - Time Complexity: O(n).
   - Space Complexity: O(1).
*/

import java.util.*;

// Definition for singly-linked list.
class ListNode {
    int val;
    ListNode next;
    ListNode(int x) { val = x; }
}

// Approach 1: Brute Force
class CriticalPointsBrute {
    public int[] nodesBetweenCriticalPoints(ListNode head) {
        List<Integer> indices = new ArrayList<>();
        int idx = 1;
        ListNode prev = head, curr = head.next;

        while (curr != null && curr.next != null) {
            if ((curr.val > prev.val && curr.val > curr.next.val) ||
                (curr.val < prev.val && curr.val < curr.next.val)) {
                indices.add(idx);
            }
            prev = curr;
            curr = curr.next;
            idx++;
        }

        if (indices.size() < 2) return new int[]{-1,-1};

        int minDist = Integer.MAX_VALUE;
        for (int i = 1; i < indices.size(); i++) {
            minDist = Math.min(minDist, indices.get(i) - indices.get(i-1));
        }
        int maxDist = indices.get(indices.size()-1) - indices.get(0);

        return new int[]{minDist, maxDist};
    }
}

// Approach 2: Optimized (Single Pass)
class CriticalPointsOptimized {
    public int[] nodesBetweenCriticalPoints(ListNode head) {
        int first = -1, last = -1, prevIdx = -1;
        int minDist = Integer.MAX_VALUE;
        int idx = 1;

        ListNode prev = head, curr = head.next;
        while (curr != null && curr.next != null) {
            if ((curr.val > prev.val && curr.val > curr.next.val) ||
                (curr.val < prev.val && curr.val < curr.next.val)) {
                if (first == -1) first = idx;
                if (prevIdx != -1) {
                    minDist = Math.min(minDist, idx - prevIdx);
                }
                prevIdx = idx;
                last = idx;
            }
            prev = curr;
            curr = curr.next;
            idx++;
        }

        if (first == -1 || last == first) return new int[]{-1,-1};
        return new int[]{minDist, last - first};
    }
}

// Driver class
public class Main {
    public static void main(String[] args) {
        // Construct sample linked list: [3,1,5,3,7,9,2]
        ListNode head = new ListNode(3);
        head.next = new ListNode(1);
        head.next.next = new ListNode(5);
        head.next.next.next = new ListNode(3);
        head.next.next.next.next = new ListNode(7);
        head.next.next.next.next.next = new ListNode(9);
        head.next.next.next.next.next.next = new ListNode(2);

        // Test Brute Force
        CriticalPointsBrute brute = new CriticalPointsBrute();
        System.out.println("Brute Force Result: " + Arrays.toString(brute.nodesBetweenCriticalPoints(head)));

        // Test Optimized
        CriticalPointsOptimized opt = new CriticalPointsOptimized();
        System.out.println("Optimized Result: " + Arrays.toString(opt.nodesBetweenCriticalPoints(head)));
    }
}
