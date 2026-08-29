/*
Problem: Given the root of a binary tree, return the preorder traversal of its nodes' values.
Preorder traversal order: Root → Left → Right.

Source: LeetCode Problem #144 → https://leetcode.com/problems/binary-tree-preorder-traversal/

Approach:
Recursive DFS
   - Idea:
     - Use recursion to visit root, then left subtree, then right subtree.
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
    public List<Integer> preorderTraversal(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        dfs(root, result);
        return result;
    }

    private void dfs(TreeNode node, List<Integer> result) {
        if (node == null) return;
        result.add(node.val);       // Root
        dfs(node.left, result);     // Left
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
        System.out.println("Recursive Preorder Traversal: " + sol.preorderTraversal(root)); // [1,2,3]
    }
}
