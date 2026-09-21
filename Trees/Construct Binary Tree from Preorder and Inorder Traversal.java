/*
Problem: Given two integer arrays preorder and inorder,
construct and return the binary tree.

Definitions:
- Preorder traversal: Root → Left → Right
- Inorder traversal: Left → Root → Right

Source: LeetCode Problem #105 → https://leetcode.com/problems/construct-binary-tree-from-preorder-and-inorder-traversal/

Approach: Optimized (DFS Recursive with Index Ranges)
   - Idea:
     - Preorder gives root order.
     - Inorder gives left/right subtree boundaries.
     - Use a HashMap to store inorder indices for O(1) lookup.
     - Recursively build tree:
       - Take current root from preorder[prestart].
       - Find its index in inorder.
       - Compute number of nodes in left subtree.
       - Build left subtree from preorder[prestart+1..prestart+numsLeft] and inorder[instart..inroot-1].
       - Build right subtree from preorder[prestart+numsLeft+1..preend] and inorder[inroot+1..inend].
   - Time Complexity: O(n).
   - Space Complexity: O(n).
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
    public TreeNode build(int[] preorder, int prestart, int preend,
                          int[] inorder, int instart, int inend,
                          Map<Integer,Integer> inmap) {
        if (prestart > preend || instart > inend) return null;

        TreeNode root = new TreeNode(preorder[prestart]);
        int inroot = inmap.get(preorder[prestart]);
        int numsLeft = inroot - instart;

        root.left = build(preorder, prestart + 1, prestart + numsLeft,
                          inorder, instart, inroot - 1, inmap);
        root.right = build(preorder, prestart + numsLeft + 1, preend,
                           inorder, inroot + 1, inend, inmap);
        return root;
    }

    public TreeNode buildTree(int[] preorder, int[] inorder) {
        Map<Integer,Integer> inmap = new HashMap<>();
        for (int i = 0; i < inorder.length; i++) {
            inmap.put(inorder[i], i);
        }
        int n = preorder.length;
        return build(preorder, 0, n - 1, inorder, 0, n - 1, inmap);
    }
}

// Driver class
public class Main {
    public static void main(String[] args) {
        Solution sol = new Solution();

        int[] preorder = {3,9,20,15,7};
        int[] inorder = {9,3,15,20,7};

        TreeNode root = sol.buildTree(preorder, inorder);
        System.out.println("Tree constructed successfully (root value): " + root.val);
        // Expected root value: 3
    }
}
