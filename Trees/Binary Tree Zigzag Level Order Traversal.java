/*
Problem: Given the root of a binary tree, return the zigzag level order traversal
of its nodes' values. (i.e., left to right, then right to left for the next level, and so on).

Source: LeetCode Problem #103 → https://leetcode.com/problems/binary-tree-zigzag-level-order-traversal/

Approach: Optimized (BFS with Direction Flag)
   - Idea:
     - Use a queue for BFS.
     - Maintain a boolean flag for direction.
     - Insert nodes into list accordingly (left-to-right or right-to-left).
     - Flip flag after each level.
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

class Solution {
    public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        if (root == null) return result;

        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        boolean leftToRight = true;

        while (!queue.isEmpty()) {
            int size = queue.size();
            LinkedList<Integer> level = new LinkedList<>();
            for (int i = 0; i < size; i++) {
                TreeNode node = queue.poll();
                if (leftToRight) level.addLast(node.val);
                else level.addFirst(node.val);
                if (node.left != null) queue.offer(node.left);
                if (node.right != null) queue.offer(node.right);
            }
            result.add(level);
            leftToRight = !leftToRight;
        }
        return result;
    }
}

// Driver class
public class Main {
    public static void main(String[] args) {
        // Construct sample tree: [3,9,20,null,null,15,7]
        TreeNode root = new TreeNode(3);
        root.left = new TreeNode(9);
        root.right = new TreeNode(20);
        root.right.left = new TreeNode(15);
        root.right.right = new TreeNode(7);

        Solution sol = new Solution();
        System.out.println("Optimized Result: " + sol.zigzagLevelOrder(root));
        // [[3],[20,9],[15,7]]
    }
}
