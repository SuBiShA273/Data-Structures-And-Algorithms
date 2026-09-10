/*
Problem: Given the root of a binary tree,
return the number of nodes where the node's value is equal to
the average of values in its subtree (including itself).

Source: LeetCode Problem #2265 → https://leetcode.com/problems/count-nodes-equal-to-average-of-subtree/

Approaches:
1. Brute Force
   - Idea:
     - For each node, traverse its entire subtree to compute sum and count.
     - Check if node.val == sum/count.
     - Repeat for all nodes.
   - Time Complexity: O(n^2) (subtree recomputation).
   - Space Complexity: O(n).

2. Optimized (DFS with Sum + Count)
   - Idea:
     - Use DFS that returns both sum and count for each subtree.
     - At each node, compute sum and count from children.
     - Check condition once and update global answer.
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

// Approach 1: Brute Force
class AverageSubtreeBrute {
    private int count = 0;

    public int averageOfSubtree(TreeNode root) {
        dfs(root);
        return count;
    }

    private void dfs(TreeNode node) {
        if (node == null) return;
        int[] res = compute(node);
        int sum = res[0], cnt = res[1];
        if (node.val == sum / cnt) count++;
        dfs(node.left);
        dfs(node.right);
    }

    private int[] compute(TreeNode node) {
        if (node == null) return new int[]{0,0};
        int[] left = compute(node.left);
        int[] right = compute(node.right);
        int sum = node.val + left[0] + right[0];
        int cnt = 1 + left[1] + right[1];
        return new int[]{sum,cnt};
    }
}

// Approach 2: Optimized
class AverageSubtreeOptimized {
    private int count = 0;

    public int averageOfSubtree(TreeNode root) {
        dfs(root);
        return count;
    }

    private int[] dfs(TreeNode node) {
        if (node == null) return new int[]{0,0};
        int[] left = dfs(node.left);
        int[] right = dfs(node.right);
        int sum = node.val + left[0] + right[0];
        int cnt = 1 + left[1] + right[1];
        if (node.val == sum / cnt) count++;
        return new int[]{sum,cnt};
    }
}

// Driver class
public class Main {
    public static void main(String[] args) {
        // Construct sample tree: [4,8,5,0,1,null,6]
        TreeNode root = new TreeNode(4);
        root.left = new TreeNode(8);
        root.right = new TreeNode(5);
        root.left.left = new TreeNode(0);
        root.left.right = new TreeNode(1);
        root.right.right = new TreeNode(6);

        // Test Brute Force
        AverageSubtreeBrute brute = new AverageSubtreeBrute();
        System.out.println("Brute Force Result: " + brute.averageOfSubtree(root)); // Expected 5

        // Test Optimized
        AverageSubtreeOptimized opt = new AverageSubtreeOptimized();
        System.out.println("Optimized Result: " + opt.averageOfSubtree(root)); // Expected 5
    }
}
