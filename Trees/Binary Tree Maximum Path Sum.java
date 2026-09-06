/*
Problem: Given the root of a binary tree, return the maximum path sum.
A path is any sequence of nodes connected by edges, and may start and end at any node.
The path sum is the sum of node values along the path.

Source: LeetCode Problem #124 → https://leetcode.com/problems/binary-tree-maximum-path-sum/

Approaches:
1. Brute Force (All Paths)
   - Idea:
     - Generate all possible paths in the tree.
     - Compute sum for each path, track maximum.
     - Very inefficient for large trees.
   - Time Complexity: O(n^2) or worse (exploring all paths).
   - Space Complexity: O(n).

2. Optimized (DFS with Global Max)
   - Idea:
     - Use DFS recursion.
     - For each node:
       - Compute max gain from left and right subtrees (ignore negatives).
       - Update global max = max(global, leftGain + rightGain + node.val).
       - Return node.val + max(leftGain, rightGain) to parent.
   - Time Complexity: O(n).
   - Space Complexity: O(n) (recursion stack).
*/

import java.util.*;

// Definition for a binary tree node.
class TreeNode {
    int val;
    TreeNode left, right;
    TreeNode(int x) { val = x; }
}

// Approach 1: Brute Force (not practical, shown for completeness)
class MaxPathBrute {
    private int maxSum = Integer.MIN_VALUE;

    public int maxPathSum(TreeNode root) {
        List<Integer> path = new ArrayList<>();
        dfs(root, path);
        return maxSum;
    }

    private void dfs(TreeNode node, List<Integer> path) {
        if (node == null) return;
        path.add(node.val);
        // compute all subpath sums
        int sum = 0;
        for (int i = path.size() - 1; i >= 0; i--) {
            sum += path.get(i);
            maxSum = Math.max(maxSum, sum);
        }
        dfs(node.left, path);
        dfs(node.right, path);
        path.remove(path.size() - 1);
    }
}

// Approach 2: Optimized (DFS with Global Max)
class MaxPathOptimized {
    private int maxSum = Integer.MIN_VALUE;

    public int maxPathSum(TreeNode root) {
        dfs(root);
        return maxSum;
    }

    private int dfs(TreeNode node) {
        if (node == null) return 0;
        int leftGain = Math.max(dfs(node.left), 0);
        int rightGain = Math.max(dfs(node.right), 0);
        maxSum = Math.max(maxSum, leftGain + rightGain + node.val);
        return node.val + Math.max(leftGain, rightGain);
    }
}

// Driver class
public class Main {
    public static void main(String[] args) {
        // Construct sample tree: [1,2,3]
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);

        // Test Brute Force
        MaxPathBrute brute = new MaxPathBrute();
        System.out.println("Brute Force Result: " + brute.maxPathSum(root)); // 6

        // Test Optimized
        MaxPathOptimized opt = new MaxPathOptimized();
        System.out.println("Optimized Result: " + opt.maxPathSum(root)); // 6
    }
}
