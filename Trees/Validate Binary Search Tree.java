/*
Problem: Given the root of a binary tree, determine if it is a valid binary search tree (BST).
A valid BST is defined as:
- The left subtree of a node contains only nodes with keys less than the node's key.
- The right subtree of a node contains only nodes with keys greater than the node's key.
- Both the left and right subtrees must also be BSTs.

Source: LeetCode Problem #98 → https://leetcode.com/problems/validate-binary-search-tree/

Approaches:
1. Brute Force (Inorder Traversal + Array Check)
   - Idea:
     - Perform inorder traversal of the tree.
     - Store values in a list.
     - If the list is strictly increasing, the tree is a valid BST.
   - Time Complexity: O(n).
   - Space Complexity: O(n).

2. Optimal (Recursive with Bounds)
   - Idea:
     - Use recursion with min and max bounds.
     - For each node:
       - Ensure node.val > min and node.val < max.
       - Recurse left with updated max = node.val.
       - Recurse right with updated min = node.val.
     - This ensures BST validity without extra storage.
   - Time Complexity: O(n).
   - Space Complexity: O(h).
*/

import java.util.*;

// Definition for a binary tree node.
class TreeNode {
    int val;
    TreeNode left, right;
    TreeNode(int x) { val = x; }
}

class BruteForce {
    public boolean isValidBST(TreeNode root) {
        List<Integer> inorder = new ArrayList<>();
        inorderTraversal(root, inorder);
        for (int i = 1; i < inorder.size(); i++) {
            if (inorder.get(i) <= inorder.get(i - 1)) return false;
        }
        return true;
    }

    private void inorderTraversal(TreeNode root, List<Integer> inorder) {
        if (root == null) return;
        inorderTraversal(root.left, inorder);
        inorder.add(root.val);
        inorderTraversal(root.right, inorder);
    }
}

class Optimal {
    public boolean isValidBST(TreeNode root) {
        return validate(root, Long.MIN_VALUE, Long.MAX_VALUE);
    }

    private boolean validate(TreeNode node, long min, long max) {
        if (node == null) return true;
        if (node.val <= min || node.val >= max) return false;
        return validate(node.left, min, node.val) && validate(node.right, node.val, max);
    }
}

// Driver class
public class Main {
    public static void main(String[] args) {
        /*
            Construct sample tree:
                   2
                  / \
                 1   3
        */
        TreeNode root = new TreeNode(2);
        root.left = new TreeNode(1);
        root.right = new TreeNode(3);

        BruteForce brute = new BruteForce();
        Optimal opt = new Optimal();

        System.out.println("Brute Force Result: " + brute.isValidBST(root)); // Expected: true
        System.out.println("Optimal Result: " + opt.isValidBST(root));       // Expected: true

        /*
            Invalid BST example:
                   5
                  / \
                 1   4
                    / \
                   3   6
        */
        TreeNode root2 = new TreeNode(5);
        root2.left = new TreeNode(1);
        root2.right = new TreeNode(4);
        root2.right.left = new TreeNode(3);
        root2.right.right = new TreeNode(6);

        System.out.println("Brute Force Result (Invalid): " + brute.isValidBST(root2)); // Expected: false
        System.out.println("Optimal Result (Invalid): " + opt.isValidBST(root2));       // Expected: false
    }
}
