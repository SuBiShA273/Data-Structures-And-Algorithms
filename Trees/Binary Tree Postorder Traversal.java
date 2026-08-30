/*
Problem: Given the root of a binary tree, return the postorder traversal of its nodes' values.
Postorder traversal order: Left → Right → Root.

Source: LeetCode Problem #145 → https://leetcode.com/problems/binary-tree-postorder-traversal/

Approach:
Recursive DFS
   - Idea:
     - Use recursion to visit left subtree, then right subtree, then root.
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
    public List<Integer> postorderTraversal(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        dfs(root, result);
        return result;
    }

    private void dfs(TreeNode node, List<Integer> result) {
        if (node == null) return;
        dfs(node.left, result);     // Left
        dfs(node.right, result);    // Right
        result.add(node.val);       // Root
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
        System.out.println("Recursive Postorder Traversal: " + sol.postorderTraversal(root)); // [3,2,1]
    }
}
