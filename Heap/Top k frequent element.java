/*
Problem: Given an integer array nums and an integer k, return the k most frequent elements.
You may return the answer in any order.

Source: LeetCode Problem #347 → https://leetcode.com/problems/top-k-frequent-elements/

Approaches:
1. Brute Force (Sorting by Frequency)
   - Idea: Count frequency of each element using a HashMap.
   - Sort the keys by frequency (and optionally by value).
   - Pick the top k elements.
   - Time Complexity: O(n log n)
   - Space Complexity: O(n)

2. Optimized (Bucket Sort)
   - Idea:
     - Count frequency of each element.
     - Place elements into buckets indexed by frequency.
     - Traverse buckets from high to low to collect top k.
   - Time Complexity: O(n)
   - Space Complexity: O(n)
*/

import java.util.*;

// Approach 1: Brute Force (Sorting by Frequency)
class TopKFrequentBrute {
    public int[] topKFrequent(int[] nums, int k) {
        Map<Integer, Integer> freq = new HashMap<>();
        for (int num : nums) {
            freq.put(num, freq.getOrDefault(num, 0) + 1);
        }

        // Sort keys by frequency (descending)
        List<Integer> arr = new ArrayList<>(freq.keySet());
        Collections.sort(arr, (a, b) -> freq.get(b) - freq.get(a));

        int[] res = new int[k];
        for (int i = 0; i < k; i++) {
            res[i] = arr.get(i);
        }
        return res;
    }
}

// Approach 2: Optimized (Bucket Sort)
class TopKFrequentOptimized {
    public int[] topKFrequent(int[] nums, int k) {
        Map<Integer, Integer> freq = new HashMap<>();
        for (int n : nums) {
            freq.put(n, freq.getOrDefault(n, 0) + 1);
        }

        // Buckets: index = frequency
        List<Integer>[] buckets = new List[nums.length + 1];
        for (int n : freq.keySet()) {
            int f = freq.get(n);
            if (buckets[f] == null) {
                buckets[f] = new ArrayList<>();
            }
            buckets[f].add(n);
        }

        int[] res = new int[k];
        int idx = 0;

        // Traverse buckets from high frequency to low
        for (int i = buckets.length - 1; i >= 0 && idx < k; i--) {
            if (buckets[i] != null) {
                for (int n : buckets[i]) {
                    res[idx++] = n;
                    if (idx == k) return res;
                }
            }
        }
        return res;
    }
}

// Driver class
public class Main {
    public static void main(String[] args) {
        int[] nums = {1,1,1,2,2,3};
        int k = 2;

        // Test Brute Force
        TopKFrequentBrute brute = new TopKFrequentBrute();
        System.out.println("Brute Force Result: " + Arrays.toString(brute.topKFrequent(nums, k)));
        // Expected: [1,2]

        // Test Optimized
        TopKFrequentOptimized optimized = new TopKFrequentOptimized();
        System.out.println("Optimized Result: " + Arrays.toString(optimized.topKFrequent(nums, k)));
        // Expected: [1,2]
    }
}
