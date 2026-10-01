/*
Problem: Two nodes of a BST are swapped by mistake.
Recover the tree without changing its structure.

Source: LeetCode Problem #99 → https://leetcode.com/problems/recover-binary-search-tree/

Approaches:
1. Brute Force (Inorder Traversal + Sort)
   - Idea:
     - Perform inorder traversal to collect all values.
     - Sort the values.
     - Reassign values back to nodes in inorder order.
     - Tree structure remains same, values corrected.
   - Time Complexity: O(n log n).
   - Space Complexity: O(n).

2. Optimal (Inorder Traversal + Detect Violations)
   - Idea:
     - Inorder traversal should be strictly increasing.
     - Track previous node.
     - Find two nodes where order breaks (prev > curr).
     - Swap their values directly.
   - Time Complexity: O(n).
   - Space Complexity: O(h) recursion stack (or O(1) with Morris traversal).
*/

import java.util.*;

// Definition for a binary tree node.
class TreeNode {
    int val;
    TreeNode left, right;
    TreeNode(int x) { val = x; }
}

// Brute Force Approach
class BruteForce {
    public void recoverTree(TreeNode root) {
        List<Integer> vals = new ArrayList<>();
        inorder(root, vals);
        Collections.sort(vals);
        Iterator<Integer> it = vals.iterator();
        reassign(root, it);
    }

    private void inorder(TreeNode root, List<Integer> vals) {
        if (root == null) return;
        inorder(root.left, vals);
        vals.add(root.val);
        inorder(root.right, vals);
    }

    private void reassign(TreeNode root, Iterator<Integer> it) {
        if (root == null) return;
        reassign(root.left, it);
        root.val = it.next();
        reassign(root.right, it);
    }
}

// Optimal Approach
class Optimal {
    private TreeNode first, second, prev;

    public void recoverTree(TreeNode root) {
        first = second = prev = null;
        inorder(root);
        // Swap values
        int temp = first.val;
        first.val = second.val;
        second.val = temp;
    }

    private void inorder(TreeNode root) {
        if (root == null) return;
        inorder(root.left);
        if (prev != null && prev.val > root.val) {
            if (first == null) first = prev;
            second = root;
        }
        prev = root;
        inorder(root.right);
    }
}

// Utility: print inorder traversal
class Util {
    public static void printInorder(TreeNode root) {
        if (root == null) return;
        printInorder(root.left);
        System.out.print(root.val + " ");
        printInorder(root.right);
    }
}

// Driver class with testcases
public class Main {
    public static void main(String[] args) {
        // Testcase 1
        TreeNode root1 = new TreeNode(1);
        root1.left = new TreeNode(3);
        root1.left.right = new TreeNode(2);

        System.out.print("Testcase 1 Before: ");
        Util.printInorder(root1); // Wrong inorder: 3 2 1

        BruteForce brute = new BruteForce();
        brute.recoverTree(root1);
        System.out.print("\nTestcase 1 After (Brute): ");
        Util.printInorder(root1); // Correct inorder: 1 2 3

        // Reset tree for optimal
        root1 = new TreeNode(1);
        root1.left = new TreeNode(3);
        root1.left.right = new TreeNode(2);

        Optimal opt = new Optimal();
        opt.recoverTree(root1);
        System.out.print("\nTestcase 1 After (Optimal): ");
        Util.printInorder(root1); // Correct inorder: 1 2 3

        // Testcase 2
        TreeNode root2 = new TreeNode(3);
        root2.left = new TreeNode(1);
        root2.right = new TreeNode(4);
        root2.right.left = new TreeNode(2);

        System.out.print("\n\nTestcase 2 Before: ");
        Util.printInorder(root2); // Wrong inorder: 1 3 2 4

        brute.recoverTree(root2);
        System.out.print("\nTestcase 2 After (Brute): ");
        Util.printInorder(root2); // Correct inorder: 1 2 3 4

        // Reset tree for optimal
        root2 = new TreeNode(3);
        root2.left = new TreeNode(1);
        root2.right = new TreeNode(4);
        root2.right.left = new TreeNode(2);

        opt.recoverTree(root2);
        System.out.print("\nTestcase 2 After (Optimal): ");
        Util.printInorder(root2); // Correct inorder: 1 2 3 4
    }
}
