/*
Problem: Given the roots of two binary trees p and q,
return true if they are the same tree, else false.
Two binary trees are considered the same if they are structurally identical
and the nodes have the same value.

Source: LeetCode Problem #100 → https://leetcode.com/problems/same-tree/

Approach 1: Brute Force (Recursive Comparison)
   - Idea:
     - If both nodes are null → true.
     - If one is null and the other not → false.
     - If values differ → false.
     - Recursively check left and right subtrees.
   - Time Complexity: O(n) [n = number of nodes].
   - Space Complexity: O(n) (recursion stack).
*/

// Definition for a binary tree node.
class TreeNode {
    int val;
    TreeNode left, right;
    TreeNode(int x) { val = x; }
}

class Solution {
    public boolean isSameTree(TreeNode p, TreeNode q) {
        if (p == null && q == null) return true;
        if (p == null || q == null) return false;
        if (p.val != q.val) return false;
        return isSameTree(p.left, q.left) && isSameTree(p.right, q.right);
    }
}

// Driver class
public class Main {
    public static void main(String[] args) {
        // Construct sample trees
        TreeNode p = new TreeNode(1);
        p.left = new TreeNode(2);
        p.right = new TreeNode(3);

        TreeNode q = new TreeNode(1);
        q.left = new TreeNode(2);
        q.right = new TreeNode(3);

        Solution sol = new Solution();
        System.out.println("Brute Force Result: " + sol.isSameTree(p, q)); // true
    }
}
