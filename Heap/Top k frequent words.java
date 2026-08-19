/*
Problem: Given an array of strings words and an integer k, return the k most frequent words.
The answer should be sorted by frequency from highest to lowest. 
Words with the same frequency should be sorted lexicographically.

Source: LeetCode Problem #692 → https://leetcode.com/problems/top-k-frequent-words/

Approaches:
1. Brute Force (Sorting)
   - Idea: Count frequency of each word using a HashMap.
   - Sort the words by frequency (descending) and lexicographically for ties.
   - Pick the top k words.
   - Time Complexity: O(n log n)
   - Space Complexity: O(n)

2. Optimized (Heap / Bucket Sort)
   - Idea:
     - Use a min-heap of size k to keep track of top k frequent words.
     - Custom comparator: higher frequency first, lexicographically smaller first.
     - Alternatively, bucket sort by frequency and sort each bucket lexicographically.
   - Time Complexity: O(n log k) or O(n) with bucket
   - Space Complexity: O(n)
*/

import java.util.*;

// Approach 1: Brute Force (Sorting)
class TopKWordsBrute {
    public List<String> topKFrequent(String[] words, int k) {
        Map<String, Integer> freq = new HashMap<>();
        for (String w : words) {
            freq.put(w, freq.getOrDefault(w, 0) + 1);
        }

        List<String> arr = new ArrayList<>(freq.keySet());
        Collections.sort(arr, (a, b) -> {
            int fa = freq.get(a), fb = freq.get(b);
            if (fa != fb) return fb - fa; // higher frequency first
            return a.compareTo(b);        // lexicographically smaller first
        });

        return arr.subList(0, k);
    }
}

// Approach 2: Optimized (Heap)
class TopKWordsHeap {
    public List<String> topKFrequent(String[] words, int k) {
        Map<String, Integer> freq = new HashMap<>();
        for (String w : words) {
            freq.put(w, freq.getOrDefault(w, 0) + 1);
        }

        // Min-heap with custom comparator
        PriorityQueue<String> heap = new PriorityQueue<>((a, b) -> {
            int fa = freq.get(a), fb = freq.get(b);
            if (fa == fb) return b.compareTo(a); // reverse lex order for tie
            return fa - fb; // lower frequency first
        });

        for (String word : freq.keySet()) {
            heap.offer(word);
            if (heap.size() > k) heap.poll();
        }

        // Extract from heap into result list
        List<String> res = new ArrayList<>();
        while (!heap.isEmpty()) {
            res.add(heap.poll());
        }
        Collections.reverse(res); // reverse to get highest freq first
        return res;
    }
}

// Driver class
public class Main {
    public static void main(String[] args) {
        String[] words = {"i","love","leetcode","i","love","coding"};
        int k = 2;

        // Test Brute Force
        TopKWordsBrute brute = new TopKWordsBrute();
        System.out.println("Brute Force Result: " + brute.topKFrequent(words, k));
        // Expected: [i, love]

        // Test Optimized Heap
        TopKWordsHeap optimized = new TopKWordsHeap();
        System.out.println("Optimized Result: " + optimized.topKFrequent(words, k));
        // Expected: [i, love]
    }
}
