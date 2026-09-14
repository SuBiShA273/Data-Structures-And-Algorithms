/*
Problem: Given the root of a binary tree,
return true if the tree is symmetric (mirror of itself), otherwise return false.

Source: LeetCode Problem #101 → https://leetcode.com/problems/symmetric-tree/

Approach: Optimized (DFS Recursive Mirror Check)
   - Idea:
     - A tree is symmetric if its left and right subtrees are mirrors.
     - Recursively check:
       - Both nodes are null → symmetric.
       - One null and the other not → not symmetric.
       - Values must match, and left.left mirrors right.right, left.right mirrors right.left.
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
class SymmetricTreeOptimized {
    public boolean isSymmetric(TreeNode root) {
        if (root == null) return true;
        return isMirror(root.left, root.right);
    }

    private boolean isMirror(TreeNode t1, TreeNode t2) {
        if (t1 == null && t2 == null) return true;
        if (t1 == null || t2 == null) return false;
        return (t1.val == t2.val) &&
               isMirror(t1.left, t2.right) &&
               isMirror(t1.right, t2.left);
    }
}

// Driver class
public class Main {
    public static void main(String[] args) {
        // Construct sample tree: [1,2,2,3,4,4,3]
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(2);
        root.left.left = new TreeNode(3);
        root.left.right = new TreeNode(4);
        root.right.left = new TreeNode(4);
        root.right.right = new TreeNode(3);

        SymmetricTreeOptimized opt = new SymmetricTreeOptimized();
        System.out.println("Optimized Result: " + opt.isSymmetric(root));
        // true
    }
}
