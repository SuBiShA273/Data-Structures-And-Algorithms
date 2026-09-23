/*
Problem: Given a binary search tree (BST), find the lowest common ancestor (LCA)
of two given nodes p and q.

Definition:
- The LCA of two nodes p and q is the lowest node in the tree
  that has both p and q as descendants (where a node can be a descendant of itself).

Source: LeetCode Problem #235 → https://leetcode.com/problems/lowest-common-ancestor-of-a-binary-search-tree/
*/

import java.util.*;

// Definition for a binary tree node.
class TreeNode {
    int val;
    TreeNode left, right;
    TreeNode(int x) { val = x; }
}

/* ---------------------------------------------------
   Brute Force Approach (DFS)
   - Idea:
     - Traverse the tree recursively.
     - If current node is null → return null.
     - If current node equals p or q → return current node.
     - Recursively search left and right subtrees.
     - If both left and right return non-null → current node is LCA.
     - Otherwise, return whichever side is non-null.
   - Time Complexity: O(n).
   - Space Complexity: O(h).
--------------------------------------------------- */
class LCA_Brute {
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null || root == p || root == q) return root;

        TreeNode left = lowestCommonAncestor(root.left, p, q);
        TreeNode right = lowestCommonAncestor(root.right, p, q);

        if (left != null && right != null) return root;
        return (left != null) ? left : right;
    }
}

/* ---------------------------------------------------
   Optimized Approach (BST Property)
   - Idea:
     - Use BST property: left < root < right.
     - If both p and q are smaller than root → LCA lies in left subtree.
     - If both p and q are greater than root → LCA lies in right subtree.
     - Otherwise, root is the LCA.
   - Time Complexity: O(h).
   - Space Complexity: O(1).
--------------------------------------------------- */
class LCA_Optimized {
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        while (root != null) {
            if (p.val < root.val && q.val < root.val) {
                root = root.left;
            } else if (p.val > root.val && q.val > root.val) {
                root = root.right;
            } else {
                return root; // split point → LCA
            }
        }
        return null;
    }
}

// Driver class
public class Main {
    public static void main(String[] args) {
        /*
            Construct sample BST:
                   6
                  / \
                 2   8
                / \ / \
               0  4 7  9
                 / \
                3   5
        */
        TreeNode root = new TreeNode(6);
        root.left = new TreeNode(2);
        root.right = new TreeNode(8);
        root.left.left = new TreeNode(0);
        root.left.right = new TreeNode(4);
        root.right.left = new TreeNode(7);
        root.right.right = new TreeNode(9);
        root.left.right.left = new TreeNode(3);
        root.left.right.right = new TreeNode(5);

        TreeNode p = root.left;        // Node 2
        TreeNode q = root.left.right;  // Node 4

        LCA_Brute brute = new LCA_Brute();
        TreeNode lcaBrute = brute.lowestCommonAncestor(root, p, q);
        System.out.println("Brute Force Result: " + lcaBrute.val); // Expected: 2

        LCA_Optimized opt = new LCA_Optimized();
        TreeNode lcaOpt = opt.lowestCommonAncestor(root, p, q);
        System.out.println("Optimized Result: " + lcaOpt.val); // Expected: 2
    }
}
