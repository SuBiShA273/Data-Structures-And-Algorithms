/*
Problem: You are given n tasks, each represented by [enqueueTime, processingTime].
The CPU can process only one task at a time and will:
- Pick the task with the smallest processing time.
- If tie, pick the task with the smallest index.
Return the order in which the CPU processes the tasks.

Source: LeetCode Problem #1834 → https://leetcode.com/problems/single-threaded-cpu/

Approach: Greedy + Priority Queue
   - Step 1: Store tasks as [enqueueTime, processingTime, index].
   - Step 2: Sort tasks by enqueueTime.
   - Step 3: Use a min-heap ordered by (processingTime, index).
   - Step 4: Simulate CPU:
       - Add tasks to heap when their enqueueTime ≤ current time.
       - If heap empty, jump current time to next task’s enqueueTime.
       - Otherwise, process task from heap and update current time.
   - Step 5: Collect indices in result array.
   - Time Complexity: O(n log n)
   - Space Complexity: O(n)
*/

import java.util.*;

class Solution {
    public int[] getOrder(int[][] tasks) {
        int n = tasks.length;
        int[][] taskList = new int[n][3];

        // Step 1: Store tasks with index
        for (int i = 0; i < n; i++) {
            taskList[i][0] = tasks[i][0]; // enqueueTime
            taskList[i][1] = tasks[i][1]; // processingTime
            taskList[i][2] = i;           // index
        }

        // Step 2: Sort by enqueueTime
        Arrays.sort(taskList, (a, b) -> a[0] - b[0]);

        // Step 3: Min-heap ordered by processingTime, then index
        PriorityQueue<int[]> minHeap = new PriorityQueue<>((a, b) -> {
            if (a[1] != b[1]) return a[1] - b[1];
            return a[2] - b[2];
        });

        int[] result = new int[n];
        int resIdx = 0, taskIdx = 0;
        long currTime = 0;

        // Step 4: Simulate CPU
        while (resIdx < n) {
            // Add all tasks available at currTime
            while (taskIdx < n && taskList[taskIdx][0] <= currTime) {
                minHeap.offer(taskList[taskIdx]);
                taskIdx++;
            }

            if (minHeap.isEmpty()) {
                // Jump to next task’s enqueueTime
                currTime = taskList[taskIdx][0];
            } else {
                int[] currTask = minHeap.poll();
                result[resIdx++] = currTask[2]; // record index
                currTime += currTask[1];        // update time
            }
        }

        return result;
    }
}

// Driver class
public class Main {
    public static void main(String[] args) {
        Solution sol = new Solution();
        int[][] tasks = {{1,2},{2,4},{3,2},{4,1}};
        int[] order = sol.getOrder(tasks);

        System.out.println("Task Order: " + Arrays.toString(order));
        // Expected Output: [0,2,3,1]
    }
}
