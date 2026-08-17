/*
Problem: The MedianFinder class supports adding numbers to a data stream 
and finding the median of all elements so far.

Source: LeetCode Problem #295 → https://leetcode.com/problems/find-median-from-data-stream/

Approaches:
1. Brute Force (Sorting)
   - Idea: Store all numbers in a list.
   - On each query, sort the list and compute median.
   - Time Complexity: O(n log n) per median query
   - Space Complexity: O(n)

2. Optimized (Two Heaps)
   - Idea: Use a max-heap for the smaller half and a min-heap for the larger half.
   - Balance heaps so that median can be found in O(1).
   - Time Complexity: O(log n) per insertion, O(1) per median query
   - Space Complexity: O(n)
*/

import java.util.*;

// Approach 1: Brute Force (Sorting)
class MedianFinderBrute {
    private List<Integer> nums;

    // Constructor
    public MedianFinderBrute() {
        nums = new ArrayList<>();
    }

    // Add number
    public void addNum(int num) {
        nums.add(num);
    }

    // Find median
    public double findMedian() {
        Collections.sort(nums);
        int n = nums.size();

        if (n % 2 == 1) {
            return nums.get(n / 2);
        }
        return (nums.get(n / 2 - 1) + nums.get(n / 2)) / 2.0;
    }
}

// Approach 2: Optimized (Two Heaps)
class MedianFinderHeap {
    private PriorityQueue<Integer> maxHeap; // smaller half
    private PriorityQueue<Integer> minHeap; // larger half

    // Constructor
    public MedianFinderHeap() {
        maxHeap = new PriorityQueue<>(Collections.reverseOrder());
        minHeap = new PriorityQueue<>();
    }

    // Add number
    public void addNum(int num) {
        // Step 1: Add to maxHeap
        maxHeap.offer(num);

        // Step 2: Balance by moving largest of maxHeap to minHeap
        minHeap.offer(maxHeap.poll());

        // Step 3: Ensure maxHeap has equal or one more element than minHeap
        if (minHeap.size() > maxHeap.size()) {
            maxHeap.offer(minHeap.poll());
        }
    }

    // Find median
    public double findMedian() {
        if (maxHeap.size() == minHeap.size()) {
            return (maxHeap.peek() + minHeap.peek()) / 2.0;
        }
        return maxHeap.peek();
    }
}

// Driver class
public class Main {
    public static void main(String[] args) {
        // Test Brute Force
        MedianFinderBrute brute = new MedianFinderBrute();
        brute.addNum(1);
        brute.addNum(2);
        brute.addNum(3);
        System.out.println("Brute Force Median: " + brute.findMedian()); // Output: 2.0

        // Test Optimized Heap
        MedianFinderHeap heap = new MedianFinderHeap();
        heap.addNum(1);
        heap.addNum(2);
        System.out.println("Heap Median: " + heap.findMedian()); // Output: 1.5
        heap.addNum(3);
        System.out.println("Heap Median: " + heap.findMedian()); // Output: 2.0
    }
}
