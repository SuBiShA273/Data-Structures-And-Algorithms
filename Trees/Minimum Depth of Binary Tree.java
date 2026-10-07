/*
Problem: Given a binary tree, find its minimum depth.
The minimum depth is the number of nodes along the shortest path
from the root node down to the nearest leaf node.

Source: LeetCode Problem #111 → https://leetcode.com/problems/minimum-depth-of-binary-tree/

Approaches:
1. Brute Force (DFS Recursion)
   - Idea:
     - Recursively compute min depth of left and right subtrees.
     - If one child is null, return depth of the other + 1.
     - Otherwise, return min(left, right) + 1.
   - Time Complexity: O(n).
   - Space Complexity: O(h) recursion stack (h = tree height).

2. Optimal (BFS Level-Order)
   - Idea:
     - Perform BFS from root.
     - First time we encounter a leaf node, return current depth.
     - Ensures shortest path found early.
   - Time Complexity: O(n).
   - Space Complexity: O(n).
*/

import java.util.*;

// Definition for a binary tree node.
class TreeNode {
    int val;
    TreeNode left, right;
    TreeNode(int x) { val = x; }
}

class BruteForce {
    public int minDepth(TreeNode root) {
        if (root == null) return 0;
        if (root.left == null && root.right == null) return 1;
        if (root.left == null) return 1 + minDepth(root.right);
        if (root.right == null) return 1 + minDepth(root.left);
        return 1 + Math.min(minDepth(root.left), minDepth(root.right));
    }
}

class Optimal {
    public int minDepth(TreeNode root) {
        if (root == null) return 0;
        Queue<TreeNode> q = new LinkedList<>();
        q.offer(root);
        int depth = 1;
        while (!q.isEmpty()) {
            int size = q.size();
            for (int i = 0; i < size; i++) {
                TreeNode node = q.poll();
                if (node.left == null && node.right == null) return depth;
                if (node.left != null) q.offer(node.left);
                if (node.right != null) q.offer(node.right);
            }
            depth++;
        }
        return depth;
    }
}

// Utility: build sample trees
class Util {
    public static TreeNode buildTree1() {
        TreeNode root = new TreeNode(3);
        root.left = new TreeNode(9);
        root.right = new TreeNode(20);
        root.right.left = new TreeNode(15);
        root.right.right = new TreeNode(7);
        return root;
    }

    public static TreeNode buildTree2() {
        TreeNode root = new TreeNode(2);
        root.right = new TreeNode(3);
        root.right.right = new TreeNode(4);
        root.right.right.right = new TreeNode(5);
        root.right.right.right.right = new TreeNode(6);
        return root;
    }
}

// Driver class with testcases
public class Main {
    public static void main(String[] args) {
        BruteForce brute = new BruteForce();
        Optimal opt = new Optimal();

        // Testcase 1
        TreeNode root1 = Util.buildTree1();
        System.out.println("Brute Force Result (Testcase 1): " + brute.minDepth(root1)); // Expected: 2
        System.out.println("Optimal Result (Testcase 1): " + opt.minDepth(root1));       // Expected: 2

        // Testcase 2
        TreeNode root2 = Util.buildTree2();
        System.out.println("\nBrute Force Result (Testcase 2): " + brute.minDepth(root2)); // Expected: 5
        System.out.println("Optimal Result (Testcase 2): " + opt.minDepth(root2));         // Expected: 5

        // Testcase 3
        TreeNode root3 = null;
        System.out.println("\nBrute Force Result (Testcase 3): " + brute.minDepth(root3)); // Expected: 0
        System.out.println("Optimal Result (Testcase 3): " + opt.minDepth(root3));         // Expected: 0
    }
}
