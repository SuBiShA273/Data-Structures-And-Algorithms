/*
Problem: Design an algorithm to serialize and deserialize a binary tree.
Serialization converts a tree into a string, and deserialization converts
the string back into the original tree structure.

Source: LeetCode Problem #297 → https://leetcode.com/problems/serialize-and-deserialize-binary-tree/

Approach: BFS (Level Order Traversal)
   - Idea:
     - Serialize:
       - Use a queue for level order traversal.
       - Append node values to string, use "null" for missing children.
     - Deserialize:
       - Split string by commas.
       - Use a queue to rebuild tree level by level.
       - Assign left and right children accordingly.
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

public class Codec {

    // Encodes a tree to a single string.
    public String serialize(TreeNode root) {
        if (root == null) return "";
        Queue<TreeNode> q = new LinkedList<>();
        q.add(root);
        StringBuilder res = new StringBuilder();
        while (!q.isEmpty()) {
            TreeNode node = q.poll();
            if (node == null) {
                res.append("null,");
                continue;
            }
            res.append(node.val).append(",");
            q.add(node.left);
            q.add(node.right);
        }
        return res.toString();
    }

    // Decodes your encoded data to tree.
    public TreeNode deserialize(String data) {
        if (data.equals("")) return null;
        String[] values = data.split(",");
        Queue<TreeNode> q = new LinkedList<>();
        TreeNode root = new TreeNode(Integer.parseInt(values[0]));
        q.add(root);
        int i = 1;
        while (!q.isEmpty() && i < values.length) {
            TreeNode node = q.poll();
            if (!values[i].equals("null")) {
                TreeNode left = new TreeNode(Integer.parseInt(values[i]));
                node.left = left;
                q.add(left);
            }
            i++;
            if (i < values.length && !values[i].equals("null")) {
                TreeNode right = new TreeNode(Integer.parseInt(values[i]));
                node.right = right;
                q.add(right);
            }
            i++;
        }
        return root;
    }
}

// Driver class
public class Main {
    public static void main(String[] args) {
        Codec ser = new Codec();
        Codec deser = new Codec();

        /*
            Construct sample tree:
                   1
                  / \
                 2   3
                    / \
                   4   5
        */
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        root.right.left = new TreeNode(4);
        root.right.right = new TreeNode(5);

        String serialized = ser.serialize(root);
        System.out.println("Serialized: " + serialized);

        TreeNode ans = deser.deserialize(serialized);
        System.out.println("Deserialized Root Value: " + ans.val);
        // Expected: 1
    }
}
