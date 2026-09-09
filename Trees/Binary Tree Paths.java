/*
Problem: Given the root of a binary tree, return all root-to-leaf paths
in any order.

Source: LeetCode Problem #257 → https://leetcode.com/problems/binary-tree-paths/

Approaches:
1. Brute Force (DFS with String Concatenation)
   - Idea:
     - Use recursion to explore all paths.
     - At each node, append current value to path string.
     - When leaf is reached, add path to result.
   - Time Complexity: O(n^2) [string concatenation at each step].
   - Space Complexity: O(n) (recursion stack).

2. Optimized (DFS with StringBuilder)
   - Idea:
     - Use DFS but build paths with StringBuilder to avoid repeated string copies.
     - Backtrack after exploring each branch.
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
class BinaryTreePathsBrute {
    public List<String> binaryTreePaths(TreeNode root) {
        List<String> result = new ArrayList<>();
        if (root != null) dfs(root, "", result);
        return result;
    }

    private void dfs(TreeNode node, String path, List<String> result) {
        if (node.left == null && node.right == null) {
            result.add(path + node.val);
            return;
        }
        if (node.left != null) dfs(node.left, path + node.val + "->", result);
        if (node.right != null) dfs(node.right, path + node.val + "->", result);
    }
}

// Approach 2: Optimized
class BinaryTreePathsOptimized {
    public List<String> binaryTreePaths(TreeNode root) {
        List<String> result = new ArrayList<>();
        if (root != null) dfs(root, new StringBuilder(), result);
        return result;
    }

    private void dfs(TreeNode node, StringBuilder path, List<String> result) {
        int len = path.length();
        if (len > 0) path.append("->");
        path.append(node.val);

        if (node.left == null && node.right == null) {
            result.add(path.toString());
        } else {
            if (node.left != null) dfs(node.left, path, result);
            if (node.right != null) dfs(node.right, path, result);
        }
        path.setLength(len); // backtrack
    }
}

// Driver class
public class Main {
    public static void main(String[] args) {
        // Construct sample tree: [1,2,3,null,5]
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        root.left.right = new TreeNode(5);

        // Test Brute Force
        BinaryTreePathsBrute brute = new BinaryTreePathsBrute();
        System.out.println("Brute Force Result: " + brute.binaryTreePaths(root));
        // ["1->2->5","1->3"]

        // Test Optimized
        BinaryTreePathsOptimized opt = new BinaryTreePathsOptimized();
        System.out.println("Optimized Result: " + opt.binaryTreePaths(root));
        // ["1->2->5","1->3"]
    }
}
