/*
Problem: Given the root of a binary search tree (BST) and an integer k,
return the kth smallest value (1-indexed) of all the values of the nodes in the tree.

Source: LeetCode Problem #230 → https://leetcode.com/problems/kth-smallest-element-in-a-bst/

Approaches:
1. Brute Force (Inorder Traversal + List)
   - Idea:
     - Perform full inorder traversal of BST (which gives sorted order).
     - Store all values in a list.
     - Return list[k-1].
   - Time Complexity: O(n).
   - Space Complexity: O(n).

2. Optimal (Inorder Traversal with Counter)
   - Idea:
     - Inorder traversal yields sorted order.
     - Maintain a counter while traversing.
     - Stop traversal once kth element is reached.
     - Avoid storing all values.
   - Time Complexity: O(h + k) (h = height).
   - Space Complexity: O(h) recursion stack.
*/

import java.util.*;

// Definition for a binary tree node.
class TreeNode {
    int val;
    TreeNode left, right;
    TreeNode(int x) { val = x; }
}

class BruteForce {
    public int kthSmallest(TreeNode root, int k) {
        List<Integer> inorder = new ArrayList<>();
        inorderTraversal(root, inorder);
        return inorder.get(k - 1);
    }

    private void inorderTraversal(TreeNode root, List<Integer> inorder) {
        if (root == null) return;
        inorderTraversal(root.left, inorder);
        inorder.add(root.val);
        inorderTraversal(root.right, inorder);
    }
}

class Optimal {
    private int count = 0;
    private int result = -1;

    public int kthSmallest(TreeNode root, int k) {
        inorder(root, k);
        return result;
    }

    private void inorder(TreeNode root, int k) {
        if (root == null) return;
        inorder(root.left, k);
        count++;
        if (count == k) {
            result = root.val;
            return;
        }
        inorder(root.right, k);
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
                / \
               2   4
              /
             1
        */
        TreeNode root = new TreeNode(5);
        root.left = new TreeNode(3);
        root.right = new TreeNode(6);
        root.left.left = new TreeNode(2);
        root.left.right = new TreeNode(4);
        root.left.left.left = new TreeNode(1);

        BruteForce brute = new BruteForce();
        Optimal opt = new Optimal();

        int k = 3;
        System.out.println("Brute Force Result (k=3): " + brute.kthSmallest(root, k)); // Expected: 3
        System.out.println("Optimal Result (k=3): " + opt.kthSmallest(root, k));       // Expected: 3
    }
}
