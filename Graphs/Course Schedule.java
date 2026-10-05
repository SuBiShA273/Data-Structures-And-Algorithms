/*
Problem: There are a total of numCourses you have to take, labeled from 0 to numCourses - 1.
Some courses may have prerequisites, represented as a pair [a, b] meaning you must take course b before course a.
Return true if you can finish all courses, otherwise false.

Source: LeetCode Problem #207 → https://leetcode.com/problems/course-schedule/

Approach: DFS Cycle Detection
   - Idea:
     - Build adjacency list from prerequisites.
     - Use DFS to detect cycles:
       - States: 0 = unvisited, 1 = visiting, 2 = visited.
       - If we revisit a "visiting" node → cycle → cannot finish.
     - If no cycle exists, all courses can be finished.
   - Time Complexity: O(V + E).
   - Space Complexity: O(V + E).
*/

import java.util.*;

class Solution {
    public boolean canFinish(int numCourses, int[][] prerequisites) {
        List<List<Integer>> adj = new ArrayList<>();
        for (int i = 0; i < numCourses; i++) adj.add(new ArrayList<>());
        for (int[] pre : prerequisites) {
            adj.get(pre[1]).add(pre[0]);
        }

        int[] state = new int[numCourses]; // 0=unvisited, 1=visiting, 2=visited
        for (int i = 0; i < numCourses; i++) {
            if (state[i] == 0) {
                if (!dfs(adj, state, i)) return false;
            }
        }
        return true;
    }

    private boolean dfs(List<List<Integer>> adj, int[] state, int course) {
        state[course] = 1; // visiting
        for (int nei : adj.get(course)) {
            if (state[nei] == 1) return false; // cycle
            if (state[nei] == 0 && !dfs(adj, state, nei)) return false;
        }
        state[course] = 2; // visited
        return true;
    }
}

// Utility: print result
class Util {
    public static void printResult(String label, boolean result) {
        System.out.println(label + result);
    }
}

// Driver class with testcases
public class Main {
    public static void main(String[] args) {
        Solution sol = new Solution();

        // Testcase 1
        int numCourses1 = 2;
        int[][] prerequisites1 = {{1,0}};
        Util.printResult("DFS Result (Testcase 1): ", sol.canFinish(numCourses1, prerequisites1));
        // Expected: true (Course 0 → Course 1)

        // Testcase 2
        int numCourses2 = 2;
        int[][] prerequisites2 = {{1,0},{0,1}};
        Util.printResult("DFS Result (Testcase 2): ", sol.canFinish(numCourses2, prerequisites2));
        // Expected: false (Cycle between 0 and 1)

        // Testcase 3
        int numCourses3 = 4;
        int[][] prerequisites3 = {{1,0},{2,1},{3,2}};
        Util.printResult("DFS Result (Testcase 3): ", sol.canFinish(numCourses3, prerequisites3));
        // Expected: true (Linear chain 0→1→2→3)
    }
}
