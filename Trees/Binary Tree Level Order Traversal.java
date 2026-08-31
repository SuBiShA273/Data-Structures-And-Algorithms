/*
Problem: Given the root of a binary tree, return the level order traversal of its nodes' values.
(Level order = nodes at each depth from left to right).

Source: LeetCode Problem #102 → https://leetcode.com/problems/binary-tree-level-order-traversal/

Approaches:
1. Brute Force (DFS with Level Tracking)
   - Idea:
     - Use recursion (DFS).
     - Pass current depth as parameter.
     - Add node values into corresponding level list.
   - Time Complexity: O(n).
   - Space Complexity: O(n) (recursion stack + result).

2. Optimized (BFS using Queue)
   - Idea:
     - Use a queue to process nodes level by level.
     - For each level, process all nodes in the queue.
     - Add children to queue for next level.
   - Time Complexity: O(n).
   - Space Complexity: O(n).
*/

import java.util.*;

// Definition for a binary tree node.
class TreeNode {
    int val;
    TreeNode left, right;
    TreeNode(int x) { val = x; }
}

// Approach 1: Brute Force (DFS with Level Tracking)
class LevelOrderBrute {
    public List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        dfs(root, 0, result);
        return result;
    }

    private void dfs(TreeNode node, int level, List<List<Integer>> result) {
        if (node == null) return;
        if (result.size() == level) {
            result.add(new ArrayList<>());
        }
        result.get(level).add(node.val);
        dfs(node.left, level + 1, result);
        dfs(node.right, level + 1, result);
    }
}

// Approach 2: Optimized (BFS using Queue)
class LevelOrderOptimized {
    public List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        if (root == null) return result;

        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            int size = queue.size();
            List<Integer> level = new ArrayList<>();

            for (int i = 0; i < size; i++) {
                TreeNode node = queue.poll();
                level.add(node.val);
                if (node.left != null) queue.offer(node.left);
                if (node.right != null) queue.offer(node.right);
            }
            result.add(level);
        }
        return result;
    }
}

// Driver class
public class Main {
    public static void main(String[] args) {
        // Construct sample tree: [3,9,20,null,null,15,7]
        TreeNode root = new TreeNode(3);
        root.left = new TreeNode(9);
        root.right = new TreeNode(20);
        root.right.left = new TreeNode(15);
        root.right.right = new TreeNode(7);

        // Test Brute Force
        LevelOrderBrute brute = new LevelOrderBrute();
        System.out.println("Brute Force Result: " + brute.levelOrder(root)); // [[3],[9,20],[15,7]]

        // Test Optimized
        LevelOrderOptimized opt = new LevelOrderOptimized();
        System.out.println("Optimized Result: " + opt.levelOrder(root)); // [[3],[9,20],[15,7]]
    }
}
