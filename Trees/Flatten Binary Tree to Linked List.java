/*
Problem: Given the root of a binary tree, flatten the tree into a "linked list":
- The linked list should use the right child pointers.
- The left child pointers should be set to null.
- The order should follow preorder traversal.

Source: LeetCode Problem #114 → https://leetcode.com/problems/flatten-binary-tree-to-linked-list/

Approach: Optimized (Reverse Preorder Recursion)
   - Idea:
     - Traverse the tree in reverse preorder (right → left → root).
     - Maintain a global pointer `prev` to track the previously visited node.
     - At each node:
       - Recursively flatten right subtree.
       - Recursively flatten left subtree.
       - Set node.right = prev, node.left = null.
       - Update prev = node.
     - This ensures nodes are connected in preorder sequence.
   - Time Complexity: O(n).
   - Space Complexity: O(h) recursion stack.
*/

import java.util.*;

// Definition for a binary tree node.
class TreeNode {
    int val;
    TreeNode left, right;
    TreeNode() {}
    TreeNode(int val) { this.val = val; }
    TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }
}

class Solution {
    TreeNode prev = null;

    public void flatten(TreeNode root) {
        if (root == null) return;
        flatten(root.right);
        flatten(root.left);
        root.right = prev;
        root.left = null;
        prev = root;
    }
}

// Driver class
public class Main {
    public static void main(String[] args) {
        /*
            Construct sample tree:
                   1
                  / \
                 2   5
                / \   \
               3   4   6
        */
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(5);
        root.left.left = new TreeNode(3);
        root.left.right = new TreeNode(4);
        root.right.right = new TreeNode(6);

        Solution sol = new Solution();
        sol.flatten(root);

        System.out.print("Flattened List: ");
        printList(root); // Expected: 1 -> 2 -> 3 -> 4 -> 5 -> 6
    }

    private static void printList(TreeNode root) {
        while (root != null) {
            System.out.print(root.val + " ");
            root = root.right;
        }
        System.out.println();
    }
}
