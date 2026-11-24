/*
Problem: Largest Divisible Subset

Given a set of distinct positive integers nums, return the largest subset
answer such that every pair (answer[i], answer[j]) of elements in this
subset satisfies:
- answer[i] % answer[j] == 0, or
- answer[j] % answer[i] == 0

If there are multiple solutions, return any of them.

Source: LeetCode Problem #368
https://leetcode.com

Approaches:

1. Recursion
   - Idea:
     - Sort the array first so divisibility becomes transitive (if b % a == 0
       and c % b == 0, then c % a == 0).
     - At each index, track the current index and the previous picked index
       (prevIndex). Explore both choices (pick if divisible, or skip) and
       return the longer list.

   - Time Complexity: O(2^n)
   - Space Complexity: O(n)

2. Memoization
   - Idea:
     - Store the longest valid subset list in a 2D DP table: dp[index][prevIndex + 1].
     - Shift prevIndex by +1 so -1 maps cleanly to index 0.
     - Avoid recalculating overlapping (index, prevIndex) states.

   - Time Complexity: O(n^2)
   - Space Complexity: O(n^2) + O(n) -> O(n^2)

3. Tabulation (1D DP + Path Reconstruction)
   - Idea:
     - Sort the array in ascending (or descending) order.
     - Use a 1D dp[] array where dp[i] stores the length of the longest
       divisible subset ending at index i.
     - Use a hash[] array to record the previous index in the optimal chain
       and reconstruct the subset from the maximum index.

   - Time Complexity: O(n^2)
   - Space Complexity: O(n)
*/

import java.util.*;

// Approach 1: Recursion
class DivisibleSubsetRecursion {

    public List<Integer> largestDivisibleSubset(int[] nums) {
        Arrays.sort(nums);
        return helper(0, -1, nums);
    }

    private List<Integer> helper(int index, int prevIndex, int[] nums) {

        if (index == nums.length) {
            return new ArrayList<>();
        }

        // Choice 1: Skip the current element
        List<Integer> skip = helper(index + 1, prevIndex, nums);

        // Choice 2: Pick the current element if valid
        List<Integer> pick = new ArrayList<>();
        if (prevIndex == -1 || nums[index] % nums[prevIndex] == 0) {
            pick.add(nums[index]);
            pick.addAll(helper(index + 1, index, nums));
        }

        return pick.size() > skip.size() ? pick : skip;
    }
}

// Approach 2: Memoization
class DivisibleSubsetMemoization {

    public List<Integer> largestDivisibleSubset(int[] nums) {

        int n = nums.length;
        Arrays.sort(nums);

        @SuppressWarnings("unchecked")
        List<Integer>[][] dp = new ArrayList[n][n + 1];

        return helper(0, -1, nums, dp);
    }

    private List<Integer> helper(int index, int prevIndex, int[] nums, List<Integer>[][] dp) {

        if (index == nums.length) {
            return new ArrayList<>();
        }

        if (dp[index][prevIndex + 1] != null) {
            return dp[index][prevIndex + 1];
        }

        // Choice 1: Skip current element
        List<Integer> skip = helper(index + 1, prevIndex, nums, dp);

        // Choice 2: Pick current element if divisible
        List<Integer> pick = new ArrayList<>();
        if (prevIndex == -1 || nums[index] % nums[prevIndex] == 0) {
            pick.add(nums[index]);
            pick.addAll(helper(index + 1, index, nums, dp));
        }

        return dp[index][prevIndex + 1] = (pick.size() > skip.size() ? pick : skip);
    }
}

// Approach 3: Tabulation (Optimal 1D DP with Hash Tracking)
class DivisibleSubsetTabulation {

    public List<Integer> largestDivisibleSubset(int[] nums) {

        int n = nums.length;
        Arrays.sort(nums);

        int[] dp = new int[n];
        int[] hash = new int[n];
        Arrays.fill(dp, 1);
        Arrays.fill(hash, -1);

        int maxi = 1;
        int lastIndex = 0;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < i; j++) {
                if (nums[i] % nums[j] == 0 && 1 + dp[j] > dp[i]) {
                    dp[i] = 1 + dp[j];
                    hash[i] = j;
                }
            }
            if (dp[i] > maxi) {
                maxi = dp[i];
                lastIndex = i;
            }
        }

        List<Integer> ans = new ArrayList<>();
        while (lastIndex != -1) {
            ans.add(nums[lastIndex]);
            lastIndex = hash[lastIndex];
        }

        Collections.reverse(ans);
        return ans;
    }
}

// Driver Class
public class Main {

    public static void main(String[] args) {

        int[] nums = {1, 2, 4, 8};

        DivisibleSubsetRecursion rec = new DivisibleSubsetRecursion();
        System.out.println("Recursion Output: " + rec.largestDivisibleSubset(nums.clone())); // [1, 2, 4, 8]

        DivisibleSubsetMemoization memo = new DivisibleSubsetMemoization();
        System.out.println("Memoization Output: " + memo.largestDivisibleSubset(nums.clone())); // [1, 2, 4, 8]

        DivisibleSubsetTabulation tab = new DivisibleSubsetTabulation();
        System.out.println("Tabulation Output: " + tab.largestDivisibleSubset(nums.clone())); // [1, 2, 4, 8]
    }
}
