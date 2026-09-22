/*
Problem: Given the root of a complete binary tree,
return the number of nodes in the tree.

Source: LeetCode Problem #222 → https://leetcode.com/problems/count-complete-tree-nodes/

Approach: Optimized (Height Comparison)
   - Idea:
     - For a complete binary tree:
       - If left height == right height → tree is perfect, node count = 2^h - 1.
       - Else → recursively count left + right + 1 (root).
     - Use helper functions to compute leftmost and rightmost heights.
   - Time Complexity: O(log^2 n).
   - Space Complexity: O(log n).
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
    public int getheightl(TreeNode root) {
        int cnt = 0;
        while (root != null) {
            cnt++;
            root = root.left;
        }
        return cnt;
    }

    public int getheightr(TreeNode root) {
        int cnt = 0;
        while (root != null) {
            cnt++;
            root = root.right;
        }
        return cnt;
    }

    public int countNodes(TreeNode root) {
        if (root == null) return 0;
        int l = getheightl(root.left);
        int r = getheightr(root.right);
        if (l == r) {
            return (2 << l) - 1; // perfect tree node count
        }
        return 1 + countNodes(root.left) + countNodes(root.right);
    }
}

// Driver class with testcases
public class Main {
    public static void main(String[] args) {
        Solution sol = new Solution();

        /*
            Testcase 1:
                   1
                  / \
                 2   3
                / \  /
               4  5 6
            Expected: 6
        */
        TreeNode root1 = new TreeNode(1);
        root1.left = new TreeNode(2);
        root1.right = new TreeNode(3);
        root1.left.left = new TreeNode(4);
        root1.left.right = new TreeNode(5);
        root1.right.left = new TreeNode(6);
        System.out.println("Testcase 1 Result: " + sol.countNodes(root1)); // 6

        /*
            Testcase 2:
                   1
                  / \
                 2   3
                / \  / \
               4  5 6  7
            Expected: 7
        */
        TreeNode root2 = new TreeNode(1);
        root2.left = new TreeNode(2);
        root2.right = new TreeNode(3);
        root2.left.left = new TreeNode(4);
        root2.left.right = new TreeNode(5);
        root2.right.left = new TreeNode(6);
        root2.right.right = new TreeNode(7);
        System.out.println("Testcase 2 Result: " + sol.countNodes(root2)); // 7

        /*
            Testcase 3:
            Empty tree
            Expected: 0
        */
        TreeNode root3 = null;
        System.out.println("Testcase 3 Result: " + sol.countNodes(root3)); // 0
    }
}
