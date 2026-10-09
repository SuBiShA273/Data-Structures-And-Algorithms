/*
Problem: Given the root of a binary tree, invert the tree and return its root.
Inversion means swapping every left child with its right child.

Source: LeetCode Problem #226 → https://leetcode.com/problems/invert-binary-tree/

Approach: DFS Recursion
   - Idea:
     - Recursively swap left and right children.
     - Apply recursion to both subtrees.
   - Time Complexity: O(n).
   - Space Complexity: O(h) recursion stack (h = tree height).
*/

import java.util.*;

// Definition for a binary tree node.
class TreeNode {
    int val;
    TreeNode left, right;
    TreeNode(int x) { val = x; }
}

class Solution {
    public TreeNode invertTree(TreeNode root) {
        if (root == null) return null;
        // Swap children
        TreeNode temp = root.left;
        root.left = invertTree(root.right);
        root.right = invertTree(temp);
        return root;
    }
}

// Utility: print tree in level order
class Util {
    public static void printLevelOrder(TreeNode root) {
        if (root == null) {
            System.out.println("[]");
            return;
        }
        Queue<TreeNode> q = new LinkedList<>();
        q.offer(root);
        List<String> res = new ArrayList<>();
        while (!q.isEmpty()) {
            TreeNode node = q.poll();
            if (node == null) {
                res.add("null");
                continue;
            }
            res.add(String.valueOf(node.val));
            q.offer(node.left);
            q.offer(node.right);
        }
        System.out.println(res);
    }

    public static TreeNode buildTree() {
        TreeNode root = new TreeNode(4);
        root.left = new TreeNode(2);
        root.right = new TreeNode(7);
        root.left.left = new TreeNode(1);
        root.left.right = new TreeNode(3);
        root.right.left = new TreeNode(6);
        root.right.right = new TreeNode(9);
        return root;
    }
}

// Driver class with testcases
public class Main {
    public static void main(String[] args) {
        Solution sol = new Solution();

        // Testcase 1
        TreeNode root1 = Util.buildTree();
        System.out.println("Original Tree (Level Order):");
        Util.printLevelOrder(root1);

        TreeNode res1 = sol.invertTree(root1);
        System.out.println("DFS Inverted Tree:");
        Util.printLevelOrder(res1);
    }
}
