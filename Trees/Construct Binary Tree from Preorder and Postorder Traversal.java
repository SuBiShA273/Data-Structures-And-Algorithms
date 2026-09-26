/*
Problem: Given two integer arrays preorder and postorder,
construct and return the binary tree. It is guaranteed that
the input represents a valid binary tree.

Definitions:
- Preorder traversal: Root → Left → Right
- Postorder traversal: Left → Right → Root

Source: LeetCode Problem #889 → https://leetcode.com/problems/construct-binary-tree-from-preorder-and-postorder-traversal/

Approach: Optimized (DFS Recursive with Index Ranges)
   - Idea:
     - Preorder gives root order (first element is root).
     - Postorder gives subtree boundaries.
     - Use a HashMap to store postorder indices for O(1) lookup.
     - Recursively build tree:
       - Root = preorder[prestart].
       - Next element in preorder is left child root.
       - Find left child in postorder to determine left subtree size.
       - Build left subtree from preorder[prestart+1..prestart+numsLeft] and postorder[poststart..leftTreeEnd].
       - Build right subtree from preorder[prestart+numsLeft+1..preend] and postorder[leftTreeEnd+1..postend-1].
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
    public TreeNode construct(int[] preorder, int prestart, int preend,
                              int[] postorder, int poststart, int postend,
                              Map<Integer,Integer> postmap) {
        if (prestart > preend || poststart > postend) return null;

        TreeNode root = new TreeNode(preorder[prestart]);
        if (prestart == preend) return root;

        int leftRootVal = preorder[prestart + 1];
        int leftTreeEnd = postmap.get(leftRootVal);
        int numsLeft = leftTreeEnd - poststart + 1;

        root.left = construct(preorder, prestart + 1, prestart + numsLeft,
                              postorder, poststart, leftTreeEnd, postmap);
        root.right = construct(preorder, prestart + numsLeft + 1, preend,
                               postorder, leftTreeEnd + 1, postend - 1, postmap);
        return root;
    }

    public TreeNode constructFromPrePost(int[] preorder, int[] postorder) {
        Map<Integer,Integer> postmap = new HashMap<>();
        int n = postorder.length;
        for (int i = 0; i < n; i++) {
            postmap.put(postorder[i], i);
        }
        return construct(preorder, 0, n - 1, postorder, 0, n - 1, postmap);
    }
}

// Driver class
public class Main {
    public static void main(String[] args) {
        Solution sol = new Solution();

        int[] preorder = {1,2,4,5,3,6,7};
        int[] postorder = {4,5,2,6,7,3,1};

        TreeNode root = sol.constructFromPrePost(preorder, postorder);
        System.out.println("Tree constructed successfully (root value): " + root.val);
        // Expected root value: 1
    }
}
