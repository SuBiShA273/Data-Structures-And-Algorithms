/*
Problem: Design a browser history system with the following operations:
- BrowserHistory(string homepage): Initializes with homepage.
- visit(string url): Visits url from current page, clears forward history.
- back(int steps): Move back up to steps, return current page.
- forward(int steps): Move forward up to steps, return current page.

Source: LeetCode Problem #1472 → https://leetcode.com/problems/design-browser-history/

Approaches:
1. Brute Force (ArrayList + Pointer)
   - Idea: Maintain a list of visited URLs.
   - Use an index pointer to track current page.
   - On visit: remove all forward pages and add new URL.
   - On back/forward: adjust pointer safely.
   - Time Complexity: O(steps) for back/forward, O(1) for visit.
   - Space Complexity: O(n).

2. Optimized (Doubly Linked List)
   - Idea: Each node stores a URL and links to prev/next.
   - On visit: create new node, link it, discard forward nodes.
   - On back/forward: move pointer along linked list.
   - Time Complexity: O(steps) for back/forward, O(1) for visit.
   - Space Complexity: O(n).
*/

import java.util.*;

// Approach 1: Brute Force (ArrayList + Pointer)
class BrowserHistoryBrute {
    private List<String> history;
    private int curr;

    public BrowserHistoryBrute(String homepage) {
        history = new ArrayList<>();
        history.add(homepage);
        curr = 0;
    }

    public void visit(String url) {
        // remove forward history
        while (history.size() > curr + 1) {
            history.remove(history.size() - 1);
        }
        history.add(url);
        curr++;
    }

    public String back(int steps) {
        curr = Math.max(0, curr - steps);
        return history.get(curr);
    }

    public String forward(int steps) {
        curr = Math.min(history.size() - 1, curr + steps);
        return history.get(curr);
    }
}

// Approach 2: Optimized (Doubly Linked List)
class BrowserHistoryOptimized {
    private class Node {
        String url;
        Node prev, next;
        Node(String url) { this.url = url; }
    }

    private Node curr;

    public BrowserHistoryOptimized(String homepage) {
        curr = new Node(homepage);
    }

    public void visit(String url) {
        Node newNode = new Node(url);
        curr.next = newNode;
        newNode.prev = curr;
        curr = newNode;
    }

    public String back(int steps) {
        while (steps > 0 && curr.prev != null) {
            curr = curr.prev;
            steps--;
        }
        return curr.url;
    }

    public String forward(int steps) {
        while (steps > 0 && curr.next != null) {
            curr = curr.next;
            steps--;
        }
        return curr.url;
    }
}

// Driver class
public class Main {
    public static void main(String[] args) {
        // Test Brute Force
        BrowserHistoryBrute brute = new BrowserHistoryBrute("leetcode.com");
        brute.visit("google.com");
        brute.visit("facebook.com");
        brute.visit("youtube.com");
        System.out.println("Brute Back(1): " + brute.back(1)); // facebook.com
        System.out.println("Brute Back(1): " + brute.back(1)); // google.com
        System.out.println("Brute Forward(1): " + brute.forward(1)); // facebook.com

        // Test Optimized
        BrowserHistoryOptimized opt = new BrowserHistoryOptimized("leetcode.com");
        opt.visit("google.com");
        opt.visit("facebook.com");
        opt.visit("youtube.com");
        System.out.println("Optimized Back(1): " + opt.back(1)); // facebook.com
        System.out.println("Optimized Back(1): " + opt.back(1)); // google.com
        System.out.println("Optimized Forward(1): " + opt.forward(1)); // facebook.com
    }
}
