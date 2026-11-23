/*
Problem: Pascal's Triangle

Given an integer numRows, return the first numRows
of Pascal's triangle.

In Pascal's triangle, each number is the sum of the
two numbers directly above it.

Source: LeetCode Problem #118
https://leetcode.com

Approaches:

1. Brute Force (Using Factorial nCr Formula)
   - Idea:
     - For every row r (0 to numRows - 1) and column c (0 to r):
         Element = r! / (c! * (r - c)!)
     - Compute factorials directly for each cell.
     - Note: Prone to integer/long overflow for larger rows (r > 20).

   - Time Complexity: O(n³)
   - Space Complexity: O(1) (excluding output list)

2. Better (Dynamic Programming / Previous Row Sum)
   - Idea:
     - First and last element of every row is always 1.
     - Every inner element at index j is the sum of two elements
       from the previous row: prevRow[j - 1] + prevRow[j].
     - Build row by row using the previous row.

   - Time Complexity: O(n²)
   - Space Complexity: O(1) (excluding output list)

   - Very Intuitive & Clean ✅

3. Optimized (Running Combination Formula / No Factorials)
   - Idea:
     - Each row starts with ans = 1.
     - Compute next element in O(1) time without factorials:
         ans = ans * (r - j + 1) / j
     - Generates each row independently without needing previous rows.

   - Time Complexity: O(n²)
   - Space Complexity: O(1) (excluding output list)

   - Most Interview Preferred (Also solves LC #119 in O(n) time) ✅
*/

import java.util.*;

// Approach 1: Brute Force (Factorial nCr)
class PascalsTriangleBrute {

    private long factorial(int n) {
        long fact = 1;
        for (int i = 2; i <= n; i++) {
            fact *= i;
        }
        return fact;
    }

    private int nCr(int n, int r) {
        return (int) (factorial(n) / (factorial(r) * factorial(n - r)));
    }

    public List<List<Integer>> generate(int numRows) {

        List<List<Integer>> res = new ArrayList<>();

        for (int r = 0; r < numRows; r++) {
            List<Integer> row = new ArrayList<>();
            for (int c = 0; c <= r; c++) {
                row.add(nCr(r, c));
            }
            res.add(row);
        }

        return res;
    }
}

// Approach 2: Better (Previous Row Addition)
class PascalsTriangleBetter {

    public List<List<Integer>> generate(int numRows) {

        List<List<Integer>> res = new ArrayList<>();

        for (int r = 0; r < numRows; r++) {
            List<Integer> row = new ArrayList<>();

            for (int j = 0; j <= r; j++) {
                // First and last elements are always 1
                if (j == 0 || j == r) {
                    row.add(1);
                } else {
                    List<Integer> prevRow = res.get(r - 1);
                    row.add(prevRow.get(j - 1) + prevRow.get(j));
                }
            }

            res.add(row);
        }

        return res;
    }
}

// Approach 3: Optimized (Running Combination Formula)
class PascalsTriangleOptimized {

    public List<List<Integer>> generate(int numRows) {

        List<List<Integer>> res = new ArrayList<>();

        for (int r = 0; r < numRows; r++) {
            List<Integer> row = new ArrayList<>();
            long ans = 1;
            row.add((int) ans);

            for (int j = 1; j <= r; j++) {
                ans = ans * (r - j + 1);
                ans = ans / j;
                row.add((int) ans);
            }

            res.add(row);
        }

        return res;
    }
}

// Driver Class
public class Main {

    public static void main(String[] args) {

        int numRows = 5;

        PascalsTriangleBrute brute = new PascalsTriangleBrute();
        System.out.println("Brute Force Output: " + brute.generate(numRows));

        PascalsTriangleBetter better = new PascalsTriangleBetter();
        System.out.println("Better Output: " + better.generate(numRows));

        PascalsTriangleOptimized optimized = new PascalsTriangleOptimized();
        System.out.println("Optimized Output: " + optimized.generate(numRows));
    }
}
