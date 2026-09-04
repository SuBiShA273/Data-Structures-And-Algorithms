/*
Problem: There is a robot starting at the origin (0,0).
It can move 'R' (right), 'L' (left), 'U' (up), 'D' (down).
Given a string moves, return true if the robot returns to the origin after all moves, else false.

Source: LeetCode Problem #657 → https://leetcode.com/problems/robot-return-to-origin/

Approach 1: 
   - Idea:
     - Start at (0,0).
     - For each move, update coordinates.
     - At the end, check if (x,y) == (0,0).
   - Time Complexity: O(n).
   - Space Complexity: O(1).
*/

class Solution {
    public boolean judgeCircle(String moves) {
        int x = 0, y = 0;
        for (char c : moves.toCharArray()) {
            if (c == 'R') x++;
            else if (c == 'L') x--;
            else if (c == 'U') y++;
            else if (c == 'D') y--;
        }
        return x == 0 && y == 0;
    }
}

// Driver class
public class Main {
    public static void main(String[] args) {
        Solution sol = new Solution();

        String moves1 = "UD";
        String moves2 = "LL";

        System.out.println("Brute Force Result (UD): " + sol.judgeCircle(moves1)); // true
        System.out.println("Brute Force Result (LL): " + sol.judgeCircle(moves2)); // false
    }
}
