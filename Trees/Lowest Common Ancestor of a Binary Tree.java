/*
Problem: Given a binary tree, find the lowest common ancestor (LCA)
of two given nodes p and q.

Definition:
- The LCA of two nodes p and q is the lowest node in the tree
  that has both p and q as descendants (where a node can be a descendant of itself).

Source: LeetCode Problem #236 → https://leetcode.com/problems/lowest-common-ancestor-of-a-binary-tree/

Approach: Optimized (DFS Recursive)
   - Idea:
     - Traverse the tree recursively.
     - If the current node is null → return null.
     - If the current node equals p or q → return current node.
     - Recursively search left and right subtrees.
     - If both left and right return non-null → current node is LCA.
     - Otherwise, return whichever side is non-null.
   - Time Complexity: O(n).
   - Space Complexity: O(h) (recursion stack, h = tree height).
*/

import java.util.*;

// Definition for a binary tree node.
class TreeNode {
    int val;
    TreeNode left, right;
    TreeNode(int x) { val = x; }
}

// Optimized Approach (DFS Recursive)
class LowestCommonAncestorOptimized {
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null || root == p || root == q) return root;

        TreeNode left = lowestCommonAncestor(root.left, p, q);
        TreeNode right = lowestCommonAncestor(root.right, p, q);

        if (left != null && right != null) return root;
        return (left != null) ? left : right;
    }
}

// Driver class
public class Main {
    public static void main(String[] args) {
        /*
            Construct sample tree:
                   3
                  / \
                 5   1
                / \ / \
               6  2 0  8
                 / \
                7   4
        */
        TreeNode root = new TreeNode(3);
        root.left = new TreeNode(5);
        root.right = new TreeNode(1);
        root.left.left = new TreeNode(6);
        root.left.right = new TreeNode(2);
        root.right.left = new TreeNode(0);
        root.right.right = new TreeNode(8);
        root.left.right.left = new TreeNode(7);
        root.left.right.right = new TreeNode(4);

        TreeNode p = root.left;        // Node 5
        TreeNode q = root.left.right;  // Node 2

        LowestCommonAncestorOptimized opt = new LowestCommonAncestorOptimized();
        TreeNode lca = opt.lowestCommonAncestor(root, p, q);
        System.out.println("Optimized Result: " + lca.val);
        // Expected: 5
    }
}
