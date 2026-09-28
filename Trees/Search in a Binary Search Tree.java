/*
Problem: You are given the root of a binary search tree (BST) and an integer val.
Find the node in the BST where the node's value equals val and return the subtree rooted at that node.
If such a node does not exist, return null.

Source: LeetCode Problem #700 → https://leetcode.com/problems/search-in-a-binary-search-tree/

Approaches:
1. Brute Force (DFS Traversal)
   - Idea:
     - Traverse the entire tree recursively.
     - If current node is null → return null.
     - If current node value equals val → return node.
     - Otherwise, search both left and right subtrees.
   - Time Complexity: O(n).
   - Space Complexity: O(h).

2. Optimal (BST Property)
   - Idea:
     - Use BST property: left < root < right.
     - If val < root.val → search left subtree.
     - If val > root.val → search right subtree.
     - If val == root.val → return root.
   - Time Complexity: O(h).
   - Space Complexity: O(1) iterative or O(h) recursive.
*/

import java.util.*;

// Definition for a binary tree node.
class TreeNode {
    int val;
    TreeNode left, right;
    TreeNode(int x) { val = x; }
}

class BruteForce {
    public TreeNode searchBST(TreeNode root, int val) {
        if (root == null) return null;
        if (root.val == val) return root;
        TreeNode left = searchBST(root.left, val);
        if (left != null) return left;
        return searchBST(root.right, val);
    }
}

class Optimal {
    public TreeNode searchBST(TreeNode root, int val) {
        while (root != null) {
            if (val < root.val) root = root.left;
            else if (val > root.val) root = root.right;
            else return root;
        }
        return null;
    }
}

// Driver class
public class Main {
    public static void main(String[] args) {
        /*
            Construct sample BST:
                   4
                  / \
                 2   7
                / \
               1   3
        */
        TreeNode root = new TreeNode(4);
        root.left = new TreeNode(2);
        root.right = new TreeNode(7);
        root.left.left = new TreeNode(1);
        root.left.right = new TreeNode(3);

        BruteForce brute = new BruteForce();
        Optimal opt = new Optimal();

        TreeNode res1 = brute.searchBST(root, 2);
        System.out.println("Brute Force Result: " + (res1 != null ? res1.val : "null")); // Expected: 2

        TreeNode res2 = opt.searchBST(root, 2);
        System.out.println("Optimal Result: " + (res2 != null ? res2.val : "null"));     // Expected: 2

        TreeNode res3 = opt.searchBST(root, 5);
        System.out.println("Optimal Result (Not Found): " + (res3 != null ? res3.val : "null")); // Expected: null
    }
}
