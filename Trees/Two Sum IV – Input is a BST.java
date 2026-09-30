/*
Problem: Given the root of a Binary Search Tree and an integer k,
return true if there exist two elements in the BST such that their sum is equal to k.

Source: LeetCode Problem #653 → https://leetcode.com/problems/two-sum-iv-input-is-a-bst/

Approaches:
1. Brute Force (Inorder Traversal + Two-Pointer)
   - Idea:
     - Perform inorder traversal to get sorted list of values.
     - Use two-pointer technique to check if any pair sums to k.
   - Time Complexity: O(n).
   - Space Complexity: O(n).

2. Optimal (Two BST Iterators)
   - Idea:
     - Use two iterators:
       - One for smallest values (inorder forward).
       - One for largest values (inorder reverse).
     - Compare values like two-pointer approach.
     - Move left iterator forward if sum < k, right iterator backward if sum > k.
   - Time Complexity: O(n).
   - Space Complexity: O(h).
*/

import java.util.*;

// Definition for a binary tree node.
class TreeNode {
    int val;
    TreeNode left, right;
    TreeNode(int x) { val = x; }
}

// Approach 1: Brute Force
class BruteForce {
    public boolean findTarget(TreeNode root, int k) {
        List<Integer> inorder = new ArrayList<>();
        inorderTraversal(root, inorder);
        int i = 0, j = inorder.size() - 1;
        while (i < j) {
            int sum = inorder.get(i) + inorder.get(j);
            if (sum == k) return true;
            else if (sum < k) i++;
            else j--;
        }
        return false;
    }

    private void inorderTraversal(TreeNode root, List<Integer> inorder) {
        if (root == null) return;
        inorderTraversal(root.left, inorder);
        inorder.add(root.val);
        inorderTraversal(root.right, inorder);
    }
}

// Approach 2: Optimal (BST Iterator)
class BSTIterator {
    Stack<TreeNode> st = new Stack<>();
    boolean reverse;

    BSTIterator(TreeNode root, boolean reverse) {
        this.reverse = reverse;
        pushAll(root);
    }

    public int next() {
        TreeNode node = st.pop();
        if (!reverse) pushAll(node.right);
        else pushAll(node.left);
        return node.val;
    }

    public boolean hasNext() {
        return !st.isEmpty();
    }

    private void pushAll(TreeNode node) {
        while (node != null) {
            st.push(node);
            node = reverse ? node.right : node.left;
        }
    }
}

class Optimal {
    public boolean findTarget(TreeNode root, int k) {
        if (root == null) return false;
        BSTIterator l = new BSTIterator(root, false); // smallest
        BSTIterator r = new BSTIterator(root, true);  // largest
        int i = l.next(), j = r.next();
        while (i < j) {
            if (i + j == k) return true;
            else if (i + j < k) i = l.next();
            else j = r.next();
        }
        return false;
    }
}

// Driver class
public class Main {
    public static void main(String[] args) {
        /*
            Construct sample BST:
                   5
                  / \
                 3   6
                / \    \
               2   4    7
        */
        TreeNode root = new TreeNode(5);
        root.left = new TreeNode(3);
        root.right = new TreeNode(6);
        root.left.left = new TreeNode(2);
        root.left.right = new TreeNode(4);
        root.right.right = new TreeNode(7);

        BruteForce brute = new BruteForce();
        Optimal opt = new Optimal();

        int k = 9;
        System.out.println("Brute Force Result (k=9): " + brute.findTarget(root, k)); // Expected: true
        System.out.println("Optimal Result (k=9): " + opt.findTarget(root, k));       // Expected: true

        k = 28;
        System.out.println("Brute Force Result (k=28): " + brute.findTarget(root, k)); // Expected: false
        System.out.println("Optimal Result (k=28): " + opt.findTarget(root, k));       // Expected: false
    }
}
