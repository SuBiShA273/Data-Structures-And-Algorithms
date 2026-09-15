/*
Problem: Given the root of a binary tree,
return the maximum width of the tree.
The width of one level is defined as the length between the leftmost
and rightmost non-null nodes, considering null nodes in between.

Source: LeetCode Problem #662 → https://leetcode.com/problems/maximum-width-of-binary-tree/

Approach: Optimized (BFS with Index Tracking)
   - Idea:
     - Perform BFS level by level.
     - Assign indices to nodes as if the tree were a complete binary tree:
       - root index = 0
       - left child index = 2 * parentIndex + 1
       - right child index = 2 * parentIndex + 2
     - At each level, width = (lastIndex - firstIndex + 1).
     - Track maximum width across all levels.
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
    public int widthOfBinaryTree(TreeNode root) {
        Queue<Pair> q = new LinkedList<>();
        q.add(new Pair(root, 0));
        int ans = 0;

        while (!q.isEmpty()) {
            int size = q.size();
            int f = 0, l = 0;
            int mmin = q.peek().ind; // normalize indices per level

            for (int i = 0; i < size; i++) {
                int ind = q.peek().ind;
                TreeNode node = q.peek().node;
                q.poll();

                ind = ind - mmin; // prevent overflow
                if (i == 0) f = ind;
                if (i == size - 1) l = ind;

                if (node.left != null) q.add(new Pair(node.left, 2 * ind + 1));
                if (node.right != null) q.add(new Pair(node.right, 2 * ind + 2));
            }
            ans = Math.max(ans, l - f + 1);
        }
        return ans;
    }

    static class Pair {
        TreeNode node;
        int ind;
        Pair(TreeNode n, int i) {
            node = n;
            ind = i;
        }
    }
}

// Driver class
public class Main {
    public static void main(String[] args) {
        /*
            Construct sample tree:
                   1
                  / \
                 3   2
                /     \
               5       9
              /         \
             6           7
        */
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(3);
        root.right = new TreeNode(2);
        root.left.left = new TreeNode(5);
        root.right.right = new TreeNode(9);
        root.left.left.left = new TreeNode(6);
        root.right.right.right = new TreeNode(7);

        Solution sol = new Solution();
        System.out.println("Optimized Result: " + sol.widthOfBinaryTree(root));
        // Expected: 8
    }
}
