/*
Problem: Given the root of a binary tree, return the length of the diameter.
Diameter = length of the longest path between any two nodes in the tree.
Length = number of edges on the path.

Source: LeetCode Problem #543 → https://leetcode.com/problems/diameter-of-binary-tree/

Approaches:
1. Brute Force (Height at Each Node)
   - Idea:
     - For each node, compute height of left and right subtrees.
     - Diameter through that node = leftHeight + rightHeight.
     - Recursively compute diameter for all nodes.
     - Return maximum.
   - Time Complexity: O(n^2) (height recomputed at each node).
   - Space Complexity: O(n) (recursion stack).

2. Optimized (DFS with Global Diameter)
   - Idea:
     - Use DFS that returns height of subtree.
     - At each node, update global diameter = max(diameter, leftHeight + rightHeight).
     - Return height = 1 + max(leftHeight, rightHeight).
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
class DiameterBrute {
    public int diameterOfBinaryTree(TreeNode root) {
        if (root == null) return 0;
        int leftHeight = height(root.left);
        int rightHeight = height(root.right);
        int diameterThroughRoot = leftHeight + rightHeight;
        int leftDiameter = diameterOfBinaryTree(root.left);
        int rightDiameter = diameterOfBinaryTree(root.right);
        return Math.max(diameterThroughRoot, Math.max(leftDiameter, rightDiameter));
    }

    private int height(TreeNode node) {
        if (node == null) return 0;
        return 1 + Math.max(height(node.left), height(node.right));
    }
}

// Approach 2: Optimized
class DiameterOptimized {
    private int diameter = 0;

    public int diameterOfBinaryTree(TreeNode root) {
        dfs(root);
        return diameter;
    }

    private int dfs(TreeNode node) {
        if (node == null) return 0;
        int left = dfs(node.left);
        int right = dfs(node.right);
        diameter = Math.max(diameter, left + right);
        return 1 + Math.max(left, right);
    }
}

// Driver class
public class Main {
    public static void main(String[] args) {
        // Construct sample tree: [1,2,3,4,5]
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        root.left.left = new TreeNode(4);
        root.left.right = new TreeNode(5);

        // Test Brute Force
        DiameterBrute brute = new DiameterBrute();
        System.out.println("Brute Force Result: " + brute.diameterOfBinaryTree(root)); // 3

        // Test Optimized
        DiameterOptimized opt = new DiameterOptimized();
        System.out.println("Optimized Result: " + opt.diameterOfBinaryTree(root)); // 3
    }
}
