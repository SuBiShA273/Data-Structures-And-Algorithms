/*
Problem: Given the root of a binary tree and an integer targetSum,
return true if the tree has a root-to-leaf path such that
adding up all the values along the path equals targetSum.

Source: LeetCode Problem #112 → https://leetcode.com/problems/path-sum/

Approaches:
1. Brute Force (DFS Recursion)
   - Idea:
     - Subtract current node value from targetSum.
     - If leaf node and remaining sum == 0 → return true.
     - Otherwise, recurse left and right.
   - Time Complexity: O(n).
   - Space Complexity: O(h) recursion stack (h = tree height).

2. Optimal (BFS Iterative)
   - Idea:
     - Use a queue storing (node, currentSum).
     - Traverse level by level.
     - If leaf node and currentSum == targetSum → return true.
     - Otherwise, continue BFS.
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
    public boolean hasPathSum(TreeNode root, int targetSum) {
        if (root == null) return false;
        if (root.left == null && root.right == null) return targetSum == root.val;
        return hasPathSum(root.left, targetSum - root.val) ||
               hasPathSum(root.right, targetSum - root.val);
    }
}

class Optimal {
    public boolean hasPathSum(TreeNode root, int targetSum) {
        if (root == null) return false;
        Queue<TreeNode> qNode = new LinkedList<>();
        Queue<Integer> qSum = new LinkedList<>();
        qNode.offer(root);
        qSum.offer(root.val);

        while (!qNode.isEmpty()) {
            TreeNode node = qNode.poll();
            int currSum = qSum.poll();
            if (node.left == null && node.right == null && currSum == targetSum) {
                return true;
            }
            if (node.left != null) {
                qNode.offer(node.left);
                qSum.offer(currSum + node.left.val);
            }
            if (node.right != null) {
                qNode.offer(node.right);
                qSum.offer(currSum + node.right.val);
            }
        }
        return false;
    }
}

// Utility: build sample trees
class Util {
    public static TreeNode buildTree1() {
        TreeNode root = new TreeNode(5);
        root.left = new TreeNode(4);
        root.right = new TreeNode(8);
        root.left.left = new TreeNode(11);
        root.left.left.left = new TreeNode(7);
        root.left.left.right = new TreeNode(2);
        root.right.left = new TreeNode(13);
        root.right.right = new TreeNode(4);
        root.right.right.right = new TreeNode(1);
        return root;
    }

    public static TreeNode buildTree2() {
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
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
        int target1 = 22;
        System.out.println("Brute Force Result (Testcase 1): " + brute.hasPathSum(root1, target1)); // Expected: true
        System.out.println("Optimal Result (Testcase 1): " + opt.hasPathSum(root1, target1));       // Expected: true

        // Testcase 2
        TreeNode root2 = Util.buildTree2();
        int target2 = 5;
        System.out.println("\nBrute Force Result (Testcase 2): " + brute.hasPathSum(root2, target2)); // Expected: false
        System.out.println("Optimal Result (Testcase 2): " + opt.hasPathSum(root2, target2));         // Expected: false

        // Testcase 3
        TreeNode root3 = null;
        int target3 = 0;
        System.out.println("\nBrute Force Result (Testcase 3): " + brute.hasPathSum(root3, target3)); // Expected: false
        System.out.println("Optimal Result (Testcase 3): " + opt.hasPathSum(root3, target3));         // Expected: false
    }
}
