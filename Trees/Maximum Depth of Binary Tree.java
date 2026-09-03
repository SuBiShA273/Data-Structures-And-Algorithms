/*
Problem: Given the root of a binary tree, return its maximum depth.
Maximum depth = number of nodes along the longest path from root down to the farthest leaf.

Source: LeetCode Problem #104 → https://leetcode.com/problems/maximum-depth-of-binary-tree/

Approach:
Recursive DFS
   - Idea:
     - Recursively compute depth of left and right subtrees.
     - Maximum depth = 1 + max(leftDepth, rightDepth).
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

// Recursive DFS Approach
class Solution {
    public int maxDepth(TreeNode root) {
        if (root == null) return 0;
        int left = maxDepth(root.left);
        int right = maxDepth(root.right);
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

        Solution sol = new Solution();
        System.out.println("Recursive Max Depth: " + sol.maxDepth(root)); // 3
    }
}
