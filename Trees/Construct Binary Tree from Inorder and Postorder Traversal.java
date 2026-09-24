/*
Problem: Given two integer arrays inorder and postorder,
construct and return the binary tree.

Definitions:
- Inorder traversal: Left → Root → Right
- Postorder traversal: Left → Right → Root

Source: LeetCode Problem #106 → https://leetcode.com/problems/construct-binary-tree-from-inorder-and-postorder-traversal/

Approach: Optimized (DFS Recursive with Index Ranges)
   - Idea:
     - Postorder gives root order (last element is root).
     - Inorder gives left/right subtree boundaries.
     - Use a HashMap to store inorder indices for O(1) lookup.
     - Recursively build tree:
       - Take current root from postorder[postend].
       - Find its index in inorder.
       - Compute number of nodes in left subtree.
       - Build left subtree from inorder[instart..inroot-1] and postorder[poststart..poststart+numsLeft-1].
       - Build right subtree from inorder[inroot+1..inend] and postorder[poststart+numsLeft..postend-1].
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
    public TreeNode build(int[] inorder, int instart, int inend,
                          int[] postorder, int poststart, int postend,
                          Map<Integer,Integer> inmap) {
        if (poststart > postend || instart > inend) return null;

        TreeNode root = new TreeNode(postorder[postend]);
        int inroot = inmap.get(postorder[postend]);

        root.left = build(inorder, instart, inroot - 1,
                          postorder, poststart, poststart + inroot - instart - 1, inmap);
        root.right = build(inorder, inroot + 1, inend,
                           postorder, poststart + inroot - instart, postend - 1, inmap);
        return root;
    }

    public TreeNode buildTree(int[] inorder, int[] postorder) {
        int n = inorder.length;
        Map<Integer,Integer> inmap = new HashMap<>();
        for (int i = 0; i < n; i++) {
            inmap.put(inorder[i], i);
        }
        return build(inorder, 0, n - 1, postorder, 0, n - 1, inmap);
    }
}

// Driver class
public class Main {
    public static void main(String[] args) {
        Solution sol = new Solution();

        int[] inorder = {9,3,15,20,7};
        int[] postorder = {9,15,7,20,3};

        TreeNode root = sol.buildTree(inorder, postorder);
        System.out.println("Tree constructed successfully (root value): " + root.val);
        // Expected root value: 3
    }
}
