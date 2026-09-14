/*
Problem: Given two axis-aligned rectangles rec1 and rec2,
return true if they overlap, otherwise return false.

Each rectangle is represented as [x1, y1, x2, y2]:
- (x1, y1) is the bottom-left corner
- (x2, y2) is the top-right corner

Source: LeetCode Problem #836 → https://leetcode.com/problems/rectangle-overlap/

Approach: Optimized (Mathematical Check)
   - Idea:
     - Two rectangles do NOT overlap if one is completely to the left, right, above, or below the other.
     - Otherwise, they overlap.
   - Conditions for NO overlap:
       rec1.x2 <= rec2.x1  (rec1 is left of rec2)
       rec1.x1 >= rec2.x2  (rec1 is right of rec2)
       rec1.y2 <= rec2.y1  (rec1 is below rec2)
       rec1.y1 >= rec2.y2  (rec1 is above rec2)
   - Overlap exists if none of these conditions hold.
   - Time Complexity: O(1).
   - Space Complexity: O(1).
*/

class Solution {
    public boolean isRectangleOverlap(int[] rec1, int[] rec2) {
        // Check non-overlap conditions
        if (rec1[2] <= rec2[0] || rec1[0] >= rec2[2] ||
            rec1[3] <= rec2[1] || rec1[1] >= rec2[3]) {
            return false;
        }
        return true;
    }
}

// Driver class
public class Main {
    public static void main(String[] args) {
        Solution sol = new Solution();

        int[] rec1 = {0,0,2,2};
        int[] rec2 = {1,1,3,3};
        System.out.println("Overlap Result: " + sol.isRectangleOverlap(rec1, rec2));
        // true

        int[] rec3 = {0,0,1,1};
        int[] rec4 = {1,1,2,2};
        System.out.println("Overlap Result: " + sol.isRectangleOverlap(rec3, rec4));
        // false
    }
}
