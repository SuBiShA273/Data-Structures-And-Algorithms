/*
Problem: Pascal's Triangle II

Given an integer rowIndex, return the rowIndexth (0-indexed)
row of the Pascal's triangle.

In Pascal's triangle, each number is the sum of the two
numbers directly above it.

Source: LeetCode Problem #119
https://leetcode.com

Approaches:

1. Brute Force (Recursion)
   - Idea:
     - Every element at (row, col) is the sum of (row - 1, col - 1)
       and (row - 1, col).
     - Base case: first and last elements of any row are 1.
     - Compute each element of rowIndex independently using recursion.

   - Time Complexity: O(2^rowIndex)
   - Space Complexity: O(rowIndex) (recursion stack)

2. Better (Dynamic Programming / Full Triangle)
   - Idea:
     - Build Pascal's triangle row by row from row 0 up to rowIndex.
     - Store previous row and compute current row elements.
     - Return the last constructed row.

   - Time Complexity: O(rowIndex²)
   - Space Complexity: O(rowIndex) (storing current and previous row)

3. Optimized (In-Place 1D List Update)
   - Idea:
     - Maintain a single list of size rowIndex + 1 initialized with 1s.
     - Update the list from right to left (j = i - 1 down to 1)
       so we don't overwrite values needed for the current row:
         row[j] = row[j] + row[j - 1]

   - Time Complexity: O(rowIndex²)
   - Space Complexity: O(rowIndex) (single list only)

4. Optimal Math (Combinatorics / nCr Formula)
   - Idea:
     - The j-th element of rowIndex is nCr(rowIndex, j).
     - Derive each term from the previous term in O(1) time:
         curr = prev * (rowIndex - j + 1) / j
     - Use long to prevent integer overflow during multiplication.

   - Time Complexity: O(rowIndex)
   - Space Complexity: O(1) auxiliary space (excluding output list)

   - Most Interview Preferred ✅
*/

import java.util.*;

// Approach 1: Brute Force (Recursion)
class PascalsTriangle2Brute {

    public List<Integer> getRow(int rowIndex) {

        List<Integer> row = new ArrayList<>();

        for (int col = 0; col <= rowIndex; col++) {
            row.add(getElement(rowIndex, col));
        }

        return row;
    }

    private int getElement(int row, int col) {

        if (col == 0 || col == row) {
            return 1;
        }

        return getElement(row - 1, col - 1) + getElement(row - 1, col);
    }
}

// Approach 2: Better (Row-by-Row DP)
class PascalsTriangle2Better {

    public List<Integer> getRow(int rowIndex) {

        List<Integer> prev = new ArrayList<>();
        prev.add(1);

        for (int i = 1; i <= rowIndex; i++) {

            List<Integer> curr = new ArrayList<>();
            curr.add(1);

            for (int j = 1; j < i; j++) {
                curr.add(prev.get(j - 1) + prev.get(j));
            }

            curr.add(1);
            prev = curr;
        }

        return prev;
    }
}

// Approach 3: Optimized (In-Place 1D Update from Right to Left)
class PascalsTriangle2Optimized {

    public List<Integer> getRow(int rowIndex) {

        List<Integer> row = new ArrayList<>();

        for (int i = 0; i <= rowIndex; i++) {
            row.add(1);

            // Traverse backwards to avoid overwriting needed values
            for (int j = i - 1; j >= 1; j--) {
                row.set(j, row.get(j) + row.get(j - 1));
            }
        }

        return row;
    }
}

// Approach 4: Optimal Math (Combinatorics nCr)
class PascalsTriangle2Optimal {

    public List<Integer> getRow(int rowIndex) {

        List<Integer> row = new ArrayList<>();
        long val = 1;
        row.add((int) val);

        for (int j = 1; j <= rowIndex; j++) {
            val = val * (rowIndex - j + 1) / j;
            row.add((int) val);
        }

        return row;
    }
}

// Driver Class
public class Main {

    public static void main(String[] args) {

        int rowIndex = 4;

        PascalsTriangle2Brute brute = new PascalsTriangle2Brute();
        System.out.println("Brute Force Output: " + brute.getRow(rowIndex)); // [1, 4, 6, 4, 1]

        PascalsTriangle2Better better = new PascalsTriangle2Better();
        System.out.println("Better Output: " + better.getRow(rowIndex)); // [1, 4, 6, 4, 1]

        PascalsTriangle2Optimized optimized = new PascalsTriangle2Optimized();
        System.out.println("Optimized Output: " + optimized.getRow(rowIndex)); // [1, 4, 6, 4, 1]

        PascalsTriangle2Optimal optimal = new PascalsTriangle2Optimal();
        System.out.println("Optimal Math Output: " + optimal.getRow(rowIndex)); // [1, 4, 6, 4, 1]
    }
}
