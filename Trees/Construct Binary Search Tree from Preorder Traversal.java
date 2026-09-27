/*
Problem: Given an array preorder, construct a binary search tree (BST) 
from its preorder traversal. Return the root of the BST.

Source: LeetCode Problem #1008 → https://leetcode.com/problems/construct-binary-search-tree-from-preorder-traversal/

Approaches:
1. Brute Force (Insert Method)
   - Idea:
     - Start with root = preorder[0].
     - For each subsequent element, insert it into the BST using standard BST insertion.
   - Time Complexity: O(n^2) in worst case (skewed tree).
   - Space Complexity: O(h).

2. Optimal (Recursive with Bounds)
   - Idea:
     - Use preorder traversal order directly.
     - Maintain an index pointer and upper bound.
     - Recursively build left subtree with bound = root.val.
     - Recursively build right subtree with bound = parent bound.
     - This avoids repeated insertions.
   - Time Complexity: O(n).
   - Space Complexity: O(n) recursion stack.
*/

import java.util.*;

// Definition for a binary tree node.
class TreeNode {
    int val;
    TreeNode left, right;
    TreeNode(int x) { val = x; }
}

class BruteForce {
    public TreeNode bstFromPreorder(int[] preorder) {
        TreeNode root = null;
        for (int val : preorder) {
            root = insert(root, val);
        }
        return root;
    }

    private TreeNode insert(TreeNode root, int val) {
        if (root == null) return new TreeNode(val);
        if (val < root.val) root.left = insert(root.left, val);
        else root.right = insert(root.right, val);
        return root;
    }
}

class Optimal {
    int idx = 0;

    public TreeNode bstFromPreorder(int[] preorder) {
        return build(preorder, Integer.MAX_VALUE);
    }

    private TreeNode build(int[] preorder, int bound) {
        if (idx == preorder.length || preorder[idx] > bound) return null;
        TreeNode root = new TreeNode(preorder[idx++]);
        root.left = build(preorder, root.val);
        root.right = build(preorder, bound);
        return root;
    }
}

// Driver class
public class Main {
    public static void main(String[] args) {
        int[] preorder = {8,5,1,7,10,12};

        BruteForce brute = new BruteForce();
        Optimal opt = new Optimal();

        TreeNode rootBrute = brute.bstFromPreorder(preorder);
        System.out.print("Brute Force Preorder: ");
        printPreorder(rootBrute); // Expected: 8 5 1 7 10 12

        TreeNode rootOpt = opt.bstFromPreorder(preorder);
        System.out.print("\nOptimal Preorder: ");
        printPreorder(rootOpt);   // Expected: 8 5 1 7 10 12
    }

    // Utility: print preorder traversal
    private static void printPreorder(TreeNode root) {
        if (root == null) return;
        System.out.print(root.val + " ");
        printPreorder(root.left);
        printPreorder(root.right);
    }
}
