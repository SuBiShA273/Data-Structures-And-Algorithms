/*
Problem: Given the root of a binary tree, return the inorder traversal of its nodes' values.
Inorder traversal order: Left → Root → Right.

Source: LeetCode Problem #94 → https://leetcode.com/problems/binary-tree-inorder-traversal/

Approach:
Recursive DFS
   - Idea:
     - Use recursion to visit left subtree, then root, then right subtree.
     - Collect values in a list.
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
    public List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        dfs(root, result);
        return result;
    }

    private void dfs(TreeNode node, List<Integer> result) {
        if (node == null) return;
        dfs(node.left, result);     // Left
        result.add(node.val);       // Root
        dfs(node.right, result);    // Right
    }
}

// Driver class
public class Main {
    public static void main(String[] args) {
        // Construct sample tree: [1,null,2,3]
        TreeNode root = new TreeNode(1);
        root.right = new TreeNode(2);
        root.right.left = new TreeNode(3);

        Solution sol = new Solution();
        System.out.println("Recursive Inorder Traversal: " + sol.inorderTraversal(root)); // [1,3,2]
    }
}
