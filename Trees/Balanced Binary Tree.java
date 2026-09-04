/*
Problem: Given a binary tree, determine if it is height-balanced.
A height-balanced binary tree is defined as:
   For every node, the difference between the heights of left and right subtrees ≤ 1.

Source: LeetCode Problem #110 → https://leetcode.com/problems/balanced-binary-tree/

Approaches:
1. Brute Force (DFS Height Check)
   - Idea:
     - For each node, compute height of left and right subtrees.
     - Check if difference ≤ 1 and recurse for children.
   - Time Complexity: O(n^2) (height recomputed at each node).
   - Space Complexity: O(n) (recursion stack).

2. Optimized (DFS with Early Stop)
   - Idea:
     - Use DFS that returns height if subtree is balanced, else -1.
     - If any subtree returns -1, propagate failure immediately.
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

// Approach 1: Brute Force
class BalancedTreeBrute {
    public boolean isBalanced(TreeNode root) {
        if (root == null) return true;
        int leftHeight = height(root.left);
        int rightHeight = height(root.right);
        if (Math.abs(leftHeight - rightHeight) > 1) return false;
        return isBalanced(root.left) && isBalanced(root.right);
    }

    private int height(TreeNode node) {
        if (node == null) return 0;
        return 1 + Math.max(height(node.left), height(node.right));
    }
}

// Approach 2: Optimized
class BalancedTreeOptimized {
    public boolean isBalanced(TreeNode root) {
        return dfsHeight(root) != -1;
    }

    private int dfsHeight(TreeNode node) {
        if (node == null) return 0;
        int left = dfsHeight(node.left);
        if (left == -1) return -1;
        int right = dfsHeight(node.right);
        if (right == -1) return -1;
        if (Math.abs(left - right) > 1) return -1;
        return 1 + Math.max(left, right);
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
        BalancedTreeBrute brute = new BalancedTreeBrute();
        System.out.println("Brute Force Result: " + brute.isBalanced(root)); // true

        // Test Optimized
        BalancedTreeOptimized opt = new BalancedTreeOptimized();
        System.out.println("Optimized Result: " + opt.isBalanced(root)); // true
    }
}
