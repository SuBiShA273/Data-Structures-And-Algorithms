/*
Problem: Given a circle represented by (xCenter, yCenter, radius)
and an axis-aligned rectangle represented by (x1, y1, x2, y2),
return true if the circle and rectangle overlap.

Definitions:
- Circle: center (xCenter, yCenter), radius r.
- Rectangle: bottom-left (x1, y1), top-right (x2, y2).
- Overlap means the circle and rectangle share at least one point.

Source: LeetCode Problem #1401 → https://leetcode.com/problems/circle-and-rectangle-overlapping/

Approach: Optimized (Geometric Distance Check)
   - Idea:
     - Find the closest point on the rectangle to the circle’s center.
     - Clamp circle center coordinates to rectangle boundaries:
       closestX = clamp(xCenter, x1, x2)
       closestY = clamp(yCenter, y1, y2)
     - Compute distance between circle center and this closest point.
     - If distance ≤ radius → overlap exists.
   - Time Complexity: O(1).
   - Space Complexity: O(1).
*/

class Solution {
    public boolean checkOverlap(int radius, int xCenter, int yCenter,
                                int x1, int y1, int x2, int y2) {
        // Clamp circle center to rectangle boundaries
        int closestX = Math.max(x1, Math.min(xCenter, x2));
        int closestY = Math.max(y1, Math.min(yCenter, y2));

        // Compute squared distance
        int dx = closestX - xCenter;
        int dy = closestY - yCenter;
        return dx * dx + dy * dy <= radius * radius;
    }
}

// Driver class
public class Main {
    public static void main(String[] args) {
        Solution sol = new Solution();

        // Example 1
        System.out.println("Overlap Result: " +
            sol.checkOverlap(1, 0, 0, 1, -1, 3, 1));
        // true

        // Example 2
        System.out.println("Overlap Result: " +
            sol.checkOverlap(1, 1, 1, 1, -3, 2, -1));
        // false

        // Example 3
        System.out.println("Overlap Result: " +
            sol.checkOverlap(2, 4, 4, 1, 1, 6, 6));
        // true
    }
}
