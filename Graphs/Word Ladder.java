/*
Problem: Given two words, beginWord and endWord, and a dictionary wordList,
return the length of the shortest transformation sequence from beginWord to endWord,
such that:
  - Only one letter can be changed at a time.
  - Each transformed word must exist in wordList.
If no such sequence exists, return 0.

Source: LeetCode Problem #127 → https://leetcode.com/problems/word-ladder/

Approaches:
1. Brute Force (DFS Backtracking)
   - Idea:
     - Try all possible transformations recursively.
     - Track visited words to avoid cycles.
     - Very slow (exponential).
   - Time Complexity: O(N!).
   - Space Complexity: O(N).

2. Optimal (BFS)
   - Idea:
     - Treat words as nodes, edges exist if they differ by one letter.
     - BFS ensures shortest path.
     - Precompute neighbors using pattern matching (e.g., h*t → hot, hit).
   - Time Complexity: O(N * L) where N = number of words, L = word length.
   - Space Complexity: O(N * L).
*/

import java.util.*;

class BruteForce {
    private int minLen;
    public int ladderLength(String beginWord, String endWord, List<String> wordList) {
        Set<String> dict = new HashSet<>(wordList);
        minLen = Integer.MAX_VALUE;
        dfs(beginWord, endWord, dict, new HashSet<>(), 1);
        return minLen == Integer.MAX_VALUE ? 0 : minLen;
    }

    private void dfs(String word, String endWord, Set<String> dict, Set<String> visited, int length) {
        if (word.equals(endWord)) {
            minLen = Math.min(minLen, length);
            return;
        }
        for (String nei : dict) {
            if (!visited.contains(nei) && isNeighbor(word, nei)) {
                visited.add(nei);
                dfs(nei, endWord, dict, visited, length+1);
                visited.remove(nei);
            }
        }
    }

    private boolean isNeighbor(String a, String b) {
        if (a.length() != b.length()) return false;
        int diff = 0;
        for (int i = 0; i < a.length(); i++) {
            if (a.charAt(i) != b.charAt(i)) diff++;
            if (diff > 1) return false;
        }
        return diff == 1;
    }
}

class Optimal {
    public int ladderLength(String beginWord, String endWord, List<String> wordList) {
        Set<String> dict = new HashSet<>(wordList);
        if (!dict.contains(endWord)) return 0;

        Queue<String> q = new LinkedList<>();
        q.offer(beginWord);
        int level = 1;

        while (!q.isEmpty()) {
            int size = q.size();
            for (int s = 0; s < size; s++) {
                String word = q.poll();
                if (word.equals(endWord)) return level;
                char[] arr = word.toCharArray();
                for (int i = 0; i < arr.length; i++) {
                    char old = arr[i];
                    for (char c = 'a'; c <= 'z'; c++) {
                        arr[i] = c;
                        String next = new String(arr);
                        if (dict.contains(next)) {
                            q.offer(next);
                            dict.remove(next);
                        }
                    }
                    arr[i] = old;
                }
            }
            level++;
        }
        return 0;
    }
}

// Driver class with testcases
public class Main {
    public static void main(String[] args) {
        BruteForce brute = new BruteForce();
        Optimal opt = new Optimal();

        // Testcase 1
        String begin1 = "hit", end1 = "cog";
        List<String> wordList1 = Arrays.asList("hot","dot","dog","lot","log","cog");
        System.out.println("Brute Force Result (Testcase 1): " + brute.ladderLength(begin1, end1, wordList1)); // Expected: 5
        System.out.println("Optimal Result (Testcase 1): " + opt.ladderLength(begin1, end1, wordList1));       // Expected: 5

        // Testcase 2
        String begin2 = "hit", end2 = "cog";
        List<String> wordList2 = Arrays.asList("hot","dot","dog","lot","log");
        System.out.println("\nBrute Force Result (Testcase 2): " + brute.ladderLength(begin2, end2, wordList2)); // Expected: 0
        System.out.println("Optimal Result (Testcase 2): " + opt.ladderLength(begin2, end2, wordList2));         // Expected: 0

        // Testcase 3
        String begin3 = "a", end3 = "c";
        List<String> wordList3 = Arrays.asList("a","b","c");
        System.out.println("\nBrute Force Result (Testcase 3): " + brute.ladderLength(begin3, end3, wordList3)); // Expected: 2
        System.out.println("Optimal Result (Testcase 3): " + opt.ladderLength(begin3, end3, wordList3));         // Expected: 2
    }
}
