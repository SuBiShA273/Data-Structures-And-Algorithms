/*
Problem: Given the root of a binary tree,
return the vertical order traversal of its nodes' values.
Nodes are reported column by column, from left to right.
Within each column, nodes are ordered by row (top to bottom),
and if two nodes are in the same row and column, order by value.

Source: LeetCode Problem #987 → https://leetcode.com/problems/vertical-order-traversal-of-a-binary-tree/

Approach: Optimized (BFS + TreeMap)
   - Idea:
     - Use BFS with queue storing (node, row, col).
     - Store nodes in TreeMap<col, TreeMap<row, PriorityQueue<val>>>.
     - This ensures correct ordering automatically.
   - Time Complexity: O(n log n).
   - Space Complexity: O(n).
*/

import java.util.*;

// Definition for a binary tree node.
class TreeNode {
    int val;
    TreeNode left, right;
    TreeNode(int x) { val = x; }
}

// Helper class to store node with coordinates
class NodeInfo {
    TreeNode node;
    int row, col;
    NodeInfo(TreeNode node, int row, int col) {
        this.node = node;
        this.row = row;
        this.col = col;
    }
}

// Optimized Approach
class VerticalTraversalOptimized {
    public List<List<Integer>> verticalTraversal(TreeNode root) {
        TreeMap<Integer, TreeMap<Integer, PriorityQueue<Integer>>> map = new TreeMap<>();
        Queue<NodeInfo> q = new LinkedList<>();
        q.offer(new NodeInfo(root, 0, 0));

        while (!q.isEmpty()) {
            NodeInfo info = q.poll();
            TreeNode node = info.node;
            int row = info.row;
            int col = info.col;

            map.putIfAbsent(col, new TreeMap<>());
            map.get(col).putIfAbsent(row, new PriorityQueue<>());
            map.get(col).get(row).offer(node.val);

            if (node.left != null) q.offer(new NodeInfo(node.left, row + 1, col - 1));
            if (node.right != null) q.offer(new NodeInfo(node.right, row + 1, col + 1));
        }

        List<List<Integer>> result = new ArrayList<>();
        for (TreeMap<Integer, PriorityQueue<Integer>> rows : map.values()) {
            List<Integer> colList = new ArrayList<>();
            for (PriorityQueue<Integer> pq : rows.values()) {
                while (!pq.isEmpty()) colList.add(pq.poll());
            }
            result.add(colList);
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

        VerticalTraversalOptimized opt = new VerticalTraversalOptimized();
        System.out.println("Optimized Result: " + opt.verticalTraversal(root));
        // [[9],[3,15],[20],[7]]
    }
}
