/*
Problem: Implement an iterator over a binary search tree (BST).
Your iterator should be initialized with the root node of the BST.
- next(): return the next smallest number.
- hasNext(): return whether there is a next smallest number.

Source: LeetCode Problem #173 → https://leetcode.com/problems/binary-search-tree-iterator/

Approach: Stack-based Controlled Inorder Traversal (Optimal)
   - Idea:
     - Use a stack to simulate inorder traversal.
     - Push all left nodes initially.
     - For next(), pop from stack, then push the right subtree’s left path.
     - This ensures O(h) space and O(1) amortized time per operation.
   - Time Complexity: O(1) amortized per operation.
   - Space Complexity: O(h).
*/

import java.util.*;

// Definition for a binary tree node.
class TreeNode {
    int val;
    TreeNode left, right;
    TreeNode(int x) { val = x; }
}

class BSTIterator {
    private Stack<TreeNode> stack;

    public BSTIterator(TreeNode root) {
        stack = new Stack<>();
        pushLeft(root);
    }

    private void pushLeft(TreeNode node) {
        while (node != null) {
            stack.push(node);
            node = node.left;
        }
    }

    public int next() {
        TreeNode node = stack.pop();
        if (node.right != null) {
            pushLeft(node.right);
        }
        return node.val;
    }

    public boolean hasNext() {
        return !stack.isEmpty();
    }
}

// Driver class
public class Main {
    public static void main(String[] args) {
        /*
            Construct sample BST:
                   7
                  / \
                 3   15
                    /  \
                   9    20
        */
        TreeNode root = new TreeNode(7);
        root.left = new TreeNode(3);
        root.right = new TreeNode(15);
        root.right.left = new TreeNode(9);
        root.right.right = new TreeNode(20);

        BSTIterator iterator = new BSTIterator(root);

        System.out.print("BST Iterator Output: ");
        while (iterator.hasNext()) {
            System.out.print(iterator.next() + " "); // Expected: 3 7 9 15 20
        }
    }
}
