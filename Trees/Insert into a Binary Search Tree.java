/*
Problem: Given the root of a binary search tree (BST) and a value to insert,
return the root of the BST after insertion. It is guaranteed that the new value
does not exist in the original BST.

Source: LeetCode Problem #701 → https://leetcode.com/problems/insert-into-a-binary-search-tree/

Approaches:
1. Brute Force (Recursive Insert)
   - Idea:
     - Traverse the tree recursively.
     - If root is null → create new node.
     - If val < root.val → insert into left subtree.
     - If val > root.val → insert into right subtree.
   - Time Complexity: O(h), worst case O(n).
   - Space Complexity: O(h) recursion stack.

2. Optimal (Iterative Insert)
   - Idea:
     - Traverse the tree iteratively until finding the correct null position.
     - Insert new node directly without recursion.
   - Time Complexity: O(h), worst case O(n).
   - Space Complexity: O(1).
*/

import java.util.*;

// Definition for a binary tree node.
class TreeNode {
    int val;
    TreeNode left, right;
    TreeNode(int x) { val = x; }
}

class BruteForce {
    public TreeNode insertIntoBST(TreeNode root, int val) {
        if (root == null) return new TreeNode(val);
        if (val < root.val) root.left = insertIntoBST(root.left, val);
        else root.right = insertIntoBST(root.right, val);
        return root;
    }
}

class Optimal {
    public TreeNode insertIntoBST(TreeNode root, int val) {
        if (root == null) return new TreeNode(val);
        TreeNode curr = root;
        while (true) {
            if (val < curr.val) {
                if (curr.left == null) {
                    curr.left = new TreeNode(val);
                    break;
                } else {
                    curr = curr.left;
                }
            } else {
                if (curr.right == null) {
                    curr.right = new TreeNode(val);
                    break;
                } else {
                    curr = curr.right;
                }
            }
        }
        return root;
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

        // Insert value 5
        TreeNode rootBrute = brute.insertIntoBST(root, 5);
        System.out.print("Brute Force Inorder: ");
        printInorder(rootBrute); // Expected inorder: 1 2 3 4 5 7

        // Rebuild tree for optimal test
        root = new TreeNode(4);
        root.left = new TreeNode(2);
        root.right = new TreeNode(7);
        root.left.left = new TreeNode(1);
        root.left.right = new TreeNode(3);

        TreeNode rootOpt = opt.insertIntoBST(root, 5);
        System.out.print("\nOptimal Inorder: ");
        printInorder(rootOpt);   // Expected inorder: 1 2 3 4 5 7
    }

    // Utility: print inorder traversal
    private static void printInorder(TreeNode root) {
        if (root == null) return;
        printInorder(root.left);
        System.out.print(root.val + " ");
        printInorder(root.right);
    }
}
