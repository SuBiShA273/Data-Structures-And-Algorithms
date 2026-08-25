/*
Problem: There are n children standing in a line, each with a rating value.
You must give each child at least one candy.
Children with a higher rating than their immediate neighbors must get more candies.
Return the minimum number of candies you must give.

Source: LeetCode Problem #135 → https://leetcode.com/problems/candy/

Approaches:
1. Brute Force (Iterative Adjustment)
   - Idea:
     - Start by giving each child 1 candy.
     - Repeatedly scan the array:
       - If a child has higher rating than neighbor but not more candies, increase.
     - Continue until stable.
   - Time Complexity: O(n^2).
   - Space Complexity: O(n).

2. Optimized (Greedy Two-Pass)
   - Idea:
     - First pass (left → right): ensure each child has more candies than left neighbor if rating is higher.
     - Second pass (right → left): ensure each child has more candies than right neighbor if rating is higher.
     - Sum up candies.
   - Time Complexity: O(n).
   - Space Complexity: O(n).

3. Optimized (Greedy Slope Method)
   - Idea:
     - Traverse ratings once.
     - Track increasing slope (peak) and decreasing slope (valley).
     - Add candies accordingly.
     - Subtract overlap at peak to avoid double counting.
   - Time Complexity: O(n).
   - Space Complexity: O(1).
*/

import java.util.*;

// Approach 1: Brute Force
class CandyBrute {
    public int candy(int[] ratings) {
        int n = ratings.length;
        int[] candies = new int[n];
        Arrays.fill(candies, 1);

        boolean changed = true;
        while (changed) {
            changed = false;
            for (int i = 0; i < n; i++) {
                if (i > 0 && ratings[i] > ratings[i - 1] && candies[i] <= candies[i - 1]) {
                    candies[i] = candies[i - 1] + 1;
                    changed = true;
                }
                if (i < n - 1 && ratings[i] > ratings[i + 1] && candies[i] <= candies[i + 1]) {
                    candies[i] = candies[i + 1] + 1;
                    changed = true;
                }
            }
        }
        int sum = 0;
        for (int c : candies) sum += c;
        return sum;
    }
}

// Approach 2: Greedy Two-Pass
class CandyTwoPass {
    public int candy(int[] ratings) {
        int n = ratings.length;
        int[] candies = new int[n];
        Arrays.fill(candies, 1);

        // Left to right
        for (int i = 1; i < n; i++) {
            if (ratings[i] > ratings[i - 1]) {
                candies[i] = candies[i - 1] + 1;
            }
        }

        // Right to left
        for (int i = n - 2; i >= 0; i--) {
            if (ratings[i] > ratings[i + 1]) {
                candies[i] = Math.max(candies[i], candies[i + 1] + 1);
            }
        }

        int sum = 0;
        for (int c : candies) sum += c;
        return sum;
    }
}

// Approach 3: Greedy Slope Method
class CandySlope {
    public int candy(int[] ratings) {
        int n = ratings.length;
        int candies = n; // each child gets at least 1
        int i = 1;

        while (i < n) {
            if (ratings[i] == ratings[i - 1]) {
                i++;
                continue;
            }

            // Increasing slope
            int peak = 0;
            while (i < n && ratings[i] > ratings[i - 1]) {
                peak++;
                candies += peak;
                i++;
            }

            // Decreasing slope
            int valley = 0;
            while (i < n && ratings[i] < ratings[i - 1]) {
                valley++;
                candies += valley;
                i++;
            }

            // Remove overlap at peak
            candies -= Math.min(peak, valley);
        }
        return candies;
    }
}

// Driver class
public class Main {
    public static void main(String[] args) {
        int[] ratings1 = {1,0,2};
        int[] ratings2 = {1,2,2};
        int[] ratings3 = {1,3,6,8,9,5,3};

        // Test Brute Force
        CandyBrute brute = new CandyBrute();
        System.out.println("Brute Force Result (ratings1): " + brute.candy(ratings1)); // 5
        System.out.println("Brute Force Result (ratings2): " + brute.candy(ratings2)); // 4

        // Test Two-Pass
        CandyTwoPass twoPass = new CandyTwoPass();
        System.out.println("Two-Pass Result (ratings1): " + twoPass.candy(ratings1)); // 5
        System.out.println("Two-Pass Result (ratings2): " + twoPass.candy(ratings2)); // 4

        // Test Slope Method
        CandySlope slope = new CandySlope();
        System.out.println("Slope Result (ratings3): " + slope.candy(ratings3)); // Example output
    }
}
