/*
Problem: Given the root of a binary tree,
return the values of the nodes you can see from the right side,
ordered from top to bottom.

Source: LeetCode Problem #199 → https://leetcode.com/problems/binary-tree-right-side-view/

Approach: Optimized (DFS Root→Right→Left)
   - Idea:
     - Perform DFS with traversal order Root → Right → Left.
     - At each depth, the first node encountered is the rightmost node.
     - Store values in result list when visiting a new depth for the first time.
   - Time Complexity: O(n).
   - Space Complexity: O(h) (recursion stack, h = tree height).
*/

import java.util.*;

// Definition for a binary tree node.
class TreeNode {
    int val;
    TreeNode left, right;
    TreeNode(int x) { val = x; }
}

// Optimized Approach (DFS Root→Right→Left)
class RightSideViewDFS {
    public List<Integer> rightSideView(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        dfs(root, 0, result);
        return result;
    }

    private void dfs(TreeNode node, int depth, List<Integer> result) {
        if (node == null) return;
        // First time visiting this depth → rightmost node
        if (depth == result.size()) {
            result.add(node.val);
        }
        // Traverse right first, then left
        dfs(node.right, depth + 1, result);
        dfs(node.left, depth + 1, result);
    }
}

// Driver class
public class Main {
    public static void main(String[] args) {
        // Construct sample tree: [1,2,3,null,5,null,4]
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        root.left.right = new TreeNode(5);
        root.right.right = new TreeNode(4);

        RightSideViewDFS opt = new RightSideViewDFS();
        System.out.println("Optimized DFS Result: " + opt.rightSideView(root));
        // [1,3,4]
    }
}
