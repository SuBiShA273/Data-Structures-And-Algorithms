/*
Problem: Given the root of a binary tree, a target node, and an integer k,
return all the values of the nodes that are exactly k distance away from the target node.

Source: LeetCode Problem #863 → https://leetcode.com/problems/all-nodes-distance-k-in-binary-tree/

Approach: Optimized (DFS + BFS)
   - Idea:
     - Step 1: Build parent references using DFS.
     - Step 2: Perform BFS starting from target node.
     - Step 3: At each level, expand to left, right, and parent.
     - Step 4: When distance == k, collect all nodes at that level.
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
    public List<Integer> distanceK(TreeNode root, TreeNode target, int k) {
        Map<TreeNode, TreeNode> parent = new HashMap<>();
        buildParent(root, null, parent);

        List<Integer> result = new ArrayList<>();
        Set<TreeNode> visited = new HashSet<>();
        Queue<TreeNode> q = new LinkedList<>();
        q.offer(target);
        visited.add(target);

        int dist = 0;
        while (!q.isEmpty()) {
            int size = q.size();
            if (dist == k) {
                for (int i = 0; i < size; i++) {
                    result.add(q.poll().val);
                }
                return result;
            }
            for (int i = 0; i < size; i++) {
                TreeNode node = q.poll();
                if (node.left != null && visited.add(node.left)) q.offer(node.left);
                if (node.right != null && visited.add(node.right)) q.offer(node.right);
                if (parent.get(node) != null && visited.add(parent.get(node))) q.offer(parent.get(node));
            }
            dist++;
        }
        return result;
    }

    private void buildParent(TreeNode node, TreeNode par, Map<TreeNode, TreeNode> parent) {
        if (node == null) return;
        parent.put(node, par);
        buildParent(node.left, node, parent);
        buildParent(node.right, node, parent);
    }
}

// Driver class
public class Main {
    public static void main(String[] args) {
        /*
            Construct sample tree:
                   3
                  / \
                 5   1
                / \ / \
               6  2 0  8
                 / \
                7   4
        */
        TreeNode root = new TreeNode(3);
        root.left = new TreeNode(5);
        root.right = new TreeNode(1);
        root.left.left = new TreeNode(6);
        root.left.right = new TreeNode(2);
        root.right.left = new TreeNode(0);
        root.right.right = new TreeNode(8);
        root.left.right.left = new TreeNode(7);
        root.left.right.right = new TreeNode(4);

        TreeNode target = root.left; // Node 5
        Solution sol = new Solution();
        System.out.println("Nodes at distance 2: " + sol.distanceK(root, target, 2));
        // Expected: [7,4,1]
    }
}
