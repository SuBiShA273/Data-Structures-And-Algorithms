/*
Problem: Design a class to find the Kth largest element in a stream.
Implement the KthLargest class with:
- KthLargest(int k, int[] nums): Initializes the object with the integer k and the stream of integers nums.
- int add(int val): Appends the integer val to the stream and returns the element representing the kth largest element.

Source: LeetCode Problem #703 → https://leetcode.com/problems/kth-largest-element-in-a-stream/

Approaches:
1. Brute Force (Sorting)
   - Idea: Store all elements in a list.
   - On each insertion, sort the list in descending order.
   - Return the kth largest element.
   - Time Complexity: O(n log n) per insertion
   - Space Complexity: O(n)

2. Min-Heap (Optimized)
   - Idea: Maintain a min-heap of size k.
   - The root of the heap is always the kth largest element.
   - Time Complexity: O(log k) per insertion
   - Space Complexity: O(k)
*/

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// Approach 1: Brute Force (Sorting)
class KthLargestBruteForce {
    private int k;
    private List<Integer> stream;

    // Constructor
    public KthLargestBruteForce(int k, int[] nums) {
        this.k = k;
        this.stream = new ArrayList<>();
        for (int num : nums) {
            stream.add(num);
        }
    }

    // Add new value and return kth largest
    public int add(int val) {
        stream.add(val);
        Collections.sort(stream, Collections.reverseOrder()); // sort descending
        return stream.get(k - 1);
    }
}

import java.util.PriorityQueue;

// Approach 2: Min-Heap (Optimized)
class KthLargestHeap {
    private PriorityQueue<Integer> minHeap;
    private int k;

    // Constructor
    public KthLargestHeap(int k, int[] nums) {
        this.k = k;
        minHeap = new PriorityQueue<>();

        // Add initial numbers
        for (int num : nums) {
            minHeap.offer(num);
            if (minHeap.size() > k) {
                minHeap.poll(); // remove smallest
            }
        }
    }

    // Add new value and return kth largest
    public int add(int val) {
        minHeap.offer(val);
        if (minHeap.size() > k) {
            minHeap.poll();
        }
        return minHeap.peek();
    }
}

// Driver class
public class Main {
    public static void main(String[] args) {
        int[] nums = {4, 5, 8, 2};

        // Test Brute Force Approach
        KthLargestBruteForce kthBrute = new KthLargestBruteForce(3, nums);
        System.out.println("Brute Force Approach:");
        System.out.println(kthBrute.add(3));   // Output: 4
        System.out.println(kthBrute.add(5));   // Output: 5
        System.out.println(kthBrute.add(10));  // Output: 5
        System.out.println(kthBrute.add(9));   // Output: 8
        System.out.println(kthBrute.add(4));   // Output: 8

        // Test Min-Heap Approach
        KthLargestHeap kthHeap = new KthLargestHeap(3, nums);
        System.out.println("\nHeap Approach:");
        System.out.println(kthHeap.add(3));   // Output: 4
        System.out.println(kthHeap.add(5));   // Output: 5
        System.out.println(kthHeap.add(10));  // Output: 5
        System.out.println(kthHeap.add(9));   // Output: 8
        System.out.println(kthHeap.add(4));   // Output: 8
    }
}
