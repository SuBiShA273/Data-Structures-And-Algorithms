/*
Problem: Given a root node reference of a BST and a key,
delete the node with the given key in the BST. Return the root node reference.
The deletion must preserve the BST property.

Source: LeetCode Problem #450 → https://leetcode.com/problems/delete-node-in-a-bst/

Approaches:
1. Brute Force (Search + Rebuild)
   - Idea:
     - Search for the node with the given key.
     - If found:
       - If node has no children → return null.
       - If node has one child → return that child.
       - If node has two children → find inorder successor (smallest in right subtree),
         replace node’s value with successor’s value, then delete successor recursively.
   - Time Complexity: O(h), worst case O(n).
   - Space Complexity: O(h) recursion stack.

2. Optimal (Recursive with Inorder Successor/Predecessor)
   - Idea:
     - Traverse recursively using BST property.
     - When node to delete is found:
       - If no left child → return right.
       - If no right child → return left.
       - If both children → find inorder successor (min in right subtree),
         copy its value, and delete successor recursively.
     - This avoids rebuilding and is efficient.
   - Time Complexity: O(h).
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
    public TreeNode deleteNode(TreeNode root, int key) {
        if (root == null) return null;
        if (key < root.val) root.left = deleteNode(root.left, key);
        else if (key > root.val) root.right = deleteNode(root.right, key);
        else {
            // Node found
            if (root.left == null) return root.right;
            else if (root.right == null) return root.left;
            else {
                TreeNode successor = findMin(root.right);
                root.val = successor.val;
                root.right = deleteNode(root.right, successor.val);
            }
        }
        return root;
    }

    private TreeNode findMin(TreeNode node) {
        while (node.left != null) node = node.left;
        return node;
    }
}

class Optimal {
    public TreeNode deleteNode(TreeNode root, int key) {
        if (root == null) return null;
        if (key < root.val) {
            root.left = deleteNode(root.left, key);
        } else if (key > root.val) {
            root.right = deleteNode(root.right, key);
        } else {
            // Node found
            if (root.left == null) return root.right;
            if (root.right == null) return root.left;
            TreeNode successor = findMin(root.right);
            root.val = successor.val;
            root.right = deleteNode(root.right, successor.val);
        }
        return root;
    }

    private TreeNode findMin(TreeNode node) {
        while (node.left != null) node = node.left;
        return node;
    }
}

// Driver class
public class Main {
    public static void main(String[] args) {
        /*
            Construct sample BST:
                   5
                  / \
                 3   6
                / \    \
               2   4    7
        */
        TreeNode root = new TreeNode(5);
        root.left = new TreeNode(3);
        root.right = new TreeNode(6);
        root.left.left = new TreeNode(2);
        root.left.right = new TreeNode(4);
        root.right.right = new TreeNode(7);

        BruteForce brute = new BruteForce();
        Optimal opt = new Optimal();

        // Delete node with value 3
        TreeNode rootBrute = brute.deleteNode(root, 3);
        System.out.print("Brute Force Inorder after deleting 3: ");
        printInorder(rootBrute); // Expected inorder: 2 4 5 6 7

        // Rebuild tree for optimal test
        root = new TreeNode(5);
        root.left = new TreeNode(3);
        root.right = new TreeNode(6);
        root.left.left = new TreeNode(2);
        root.left.right = new TreeNode(4);
        root.right.right = new TreeNode(7);

        TreeNode rootOpt = opt.deleteNode(root, 3);
        System.out.print("\nOptimal Inorder after deleting 3: ");
        printInorder(rootOpt);   // Expected inorder: 2 4 5 6 7
    }

    // Utility: print inorder traversal
    private static void printInorder(TreeNode root) {
        if (root == null) return;
        printInorder(root.left);
        System.out.print(root.val + " ");
        printInorder(root.right);
    }
}
