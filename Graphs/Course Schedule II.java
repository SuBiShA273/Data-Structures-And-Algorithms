/*
Problem: There are a total of numCourses you have to take, labeled from 0 to numCourses - 1.
Some courses may have prerequisites, represented as a pair [a, b] meaning you must take course b before course a.
Return the ordering of courses you should take to finish all courses.
If there are many valid answers, return any of them.
If it is impossible to finish all courses, return an empty array.

Source: LeetCode Problem #210 → https://leetcode.com/problems/course-schedule-ii/

Approach: DFS Topological Sort
   - Idea:
     - Build adjacency list from prerequisites.
     - Use DFS to detect cycles and generate topological order.
     - States: 0 = unvisited, 1 = visiting, 2 = visited.
     - If cycle detected → return empty array.
     - Otherwise, reverse the DFS postorder list to get valid course order.
   - Time Complexity: O(V + E).
   - Space Complexity: O(V + E).
*/

import java.util.*;

class Solution {
    public int[] findOrder(int numCourses, int[][] prerequisites) {
        List<List<Integer>> adj = new ArrayList<>();
        for (int i = 0; i < numCourses; i++) adj.add(new ArrayList<>());
        for (int[] pre : prerequisites) {
            adj.get(pre[1]).add(pre[0]);
        }

        int[] state = new int[numCourses]; // 0=unvisited, 1=visiting, 2=visited
        List<Integer> order = new ArrayList<>();

        for (int i = 0; i < numCourses; i++) {
            if (state[i] == 0) {
                if (!dfs(adj, state, order, i)) return new int[0];
            }
        }

        Collections.reverse(order);
        return order.stream().mapToInt(Integer::intValue).toArray();
    }

    private boolean dfs(List<List<Integer>> adj, int[] state, List<Integer> order, int course) {
        state[course] = 1; // visiting
        for (int nei : adj.get(course)) {
            if (state[nei] == 1) return false; // cycle
            if (state[nei] == 0 && !dfs(adj, state, order, nei)) return false;
        }
        state[course] = 2; // visited
        order.add(course);
        return true;
    }
}

// Utility: print result
class Util {
    public static void printArray(String label, int[] arr) {
        System.out.print(label);
        System.out.println(Arrays.toString(arr));
    }
}

// Driver class with testcases
public class Main {
    public static void main(String[] args) {
        Solution sol = new Solution();

        // Testcase 1
        int numCourses1 = 2;
        int[][] prerequisites1 = {{1,0}};
        Util.printArray("DFS Result (Testcase 1): ", sol.findOrder(numCourses1, prerequisites1));
        // Expected: [0,1]

        // Testcase 2
        int numCourses2 = 4;
        int[][] prerequisites2 = {{1,0},{2,0},{3,1},{3,2}};
        Util.printArray("DFS Result (Testcase 2): ", sol.findOrder(numCourses2, prerequisites2));
        // Expected: [0,2,1,3] or [0,1,2,3]

        // Testcase 3
        int numCourses3 = 2;
        int[][] prerequisites3 = {{1,0},{0,1}};
        Util.printArray("DFS Result (Testcase 3): ", sol.findOrder(numCourses3, prerequisites3));
        // Expected: [] (cycle detected)
    }
}
