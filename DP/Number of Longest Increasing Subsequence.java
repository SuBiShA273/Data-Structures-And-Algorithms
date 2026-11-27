/*
Problem: Number of Longest Increasing Subsequence

Given an integer array nums, return the number of longest increasing
subsequences.

Notice that the sequence has to be strictly increasing.

Source: LeetCode Problem #673
https://leetcode.com

Approaches:

1. Recursion
   - Idea:
     - At each index, we track two states: current index and previous index (prevIndex).
     - Each recursive call returns a pair: {maxLength, countOfWays}.
     - Explore both choices (take current element if strictly greater, or skip)
       and combine the lengths and counts accordingly.

   - Time Complexity: O(2^n)
   - Space Complexity: O(n) (recursion stack)

2. Memoization
   - Idea:
     - Store the {maxLength, countOfWays} pair in a 3D DP table:
       dp[index][prevIndex + 1][2].
     - Shift prevIndex by +1 to handle prevIndex = -1 safely.

   - Time Complexity: O(n^2)
   - Space Complexity: O(n^2) + O(n) -> O(n^2)

3. Tabulation (Standard 1D DP)
   - Idea:
     - Maintain two 1D arrays of size n: dp[i] (max LIS length ending at i)
       and cnt[i] (number of LIS ending at i), both initialized to 1.
     - For every pair j < i where nums[j] < nums[i], update dp[i] and cnt[i].
     - Sum cnt[i] for all indices where dp[i] equals the global maximum length.

   - Time Complexity: O(n^2)
   - Space Complexity: O(n)

4. Optimal (Segment Tree / Binary Indexed Tree)
   - Idea:
     - Coordinate-compress the array values so they map to ranks 1..n.
     - Query a Segment Tree (or Fenwick Tree) to fetch the {maxLength, count}
       for all elements strictly smaller than nums[i] in O(log n) time.
     - Update the tree at nums[i]'s rank with {maxLength + 1, count}.

   - Time Complexity: O(n log n)
   - Space Complexity: O(n)
*/

import java.util.*;

// Approach 1: Recursion
class NumberOfLISRecursion {

    public int findNumberOfLIS(int[] nums) {
        int[] res = helper(0, -1, nums);
        return res[1];
    }

    // Returns int[]{maxLength, count}
    private int[] helper(int index, int prevIndex, int[] nums) {

        if (index == nums.length) {
            return new int[]{0, 1};
        }

        // Option 1: Skip current element
        int[] skip = helper(index + 1, prevIndex, nums);
        int maxLen = skip[0];
        int count = skip[1];

        // Option 2: Take current element (if strictly increasing)
        if (prevIndex == -1 || nums[index] > nums[prevIndex]) {
            int[] take = helper(index + 1, index, nums);
            int takeLen = 1 + take[0];

            if (takeLen > maxLen) {
                maxLen = takeLen;
                count = take[1];
            } else if (takeLen == maxLen) {
                count += take[1];
            }
        }

        return new int[]{maxLen, count};
    }
}

// Approach 2: Memoization
class NumberOfLISMemoization {

    public int findNumberOfLIS(int[] nums) {

        int n = nums.length;
        // dp[index][prevIndex + 1][0 = length, 1 = count]
        Integer[][][] dp = new Integer[n][n + 1][2];

        int[] res = helper(0, -1, nums, dp);
        return res[1];
    }

    private int[] helper(int index, int prevIndex, int[] nums, Integer[][][] dp) {

        if (index == nums.length) {
            return new int[]{0, 1};
        }

        if (dp[index][prevIndex + 1][0] != null) {
            return new int[]{dp[index][prevIndex + 1][0], dp[index][prevIndex + 1][1]};
        }

        // Option 1: Skip current element
        int[] skip = helper(index + 1, prevIndex, nums, dp);
        int maxLen = skip[0];
        int count = skip[1];

        // Option 2: Take current element
        if (prevIndex == -1 || nums[index] > nums[prevIndex]) {
            int[] take = helper(index + 1, index, nums, dp);
            int takeLen = 1 + take[0];

            if (takeLen > maxLen) {
                maxLen = takeLen;
                count = take[1];
            } else if (takeLen == maxLen) {
                count += take[1];
            }
        }

        dp[index][prevIndex + 1][0] = maxLen;
        dp[index][prevIndex + 1][1] = count;

        return new int[]{maxLen, count};
    }
}

// Approach 3: Tabulation (Standard 1D DP)
class NumberOfLISTabulation {

    public int findNumberOfLIS(int[] nums) {

        int n = nums.length;
        int[] dp = new int[n];
        int[] cnt = new int[n];

        Arrays.fill(dp, 1);
        Arrays.fill(cnt, 1);

        int maxi = 1;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < i; j++) {
                if (nums[j] < nums[i]) {
                    if (1 + dp[j] > dp[i]) {
                        dp[i] = 1 + dp[j];
                        cnt[i] = cnt[j];
                    } else if (1 + dp[j] == dp[i]) {
                        cnt[i] += cnt[j];
                    }
                }
            }
            maxi = Math.max(maxi, dp[i]);
        }

        int totalWays = 0;
        for (int i = 0; i < n; i++) {
            if (dp[i] == maxi) {
                totalWays += cnt[i];
            }
        }

        return totalWays;
    }
}

// Approach 4: Optimal (Binary Indexed Tree / Fenwick Tree)
class NumberOfLISOptimal {

    public int findNumberOfLIS(int[] nums) {

        int n = nums.length;

        // Coordinate Compression
        int[] sorted = nums.clone();
        Arrays.sort(sorted);
        Map<Integer, Integer> rankMap = new HashMap<>();
        int rank = 1;
        for (int num : sorted) {
            if (!rankMap.containsKey(num)) {
                rankMap.put(num, rank++);
            }
        }

        int[] bitLen = new int[rank + 1];
        int[] bitCnt = new int[rank + 1];

        for (int num : nums) {
            int r = rankMap.get(num);

            // Query best {length, count} for all ranks < r
            int[] prev = query(r - 1, bitLen, bitCnt);
            int currLen = prev[0] + 1;
            int currCnt = Math.max(1, prev[1]);

            // Update BIT at rank r
            update(r, currLen, currCnt, bitLen, bitCnt);
        }

        int[] ans = query(rank - 1, bitLen, bitCnt);
        return ans[1];
    }

    private int[] query(int idx, int[] bitLen, int[] bitCnt) {
        int maxLen = 0;
        int totalCnt = 0;

        while (idx > 0) {
            if (bitLen[idx] > maxLen) {
                maxLen = bitLen[idx];
                totalCnt = bitCnt[idx];
            } else if (bitLen[idx] == maxLen) {
                totalCnt += bitCnt[idx];
            }
            idx -= (idx & -idx);
        }

        return new int[]{maxLen, totalCnt};
    }

    private void update(int idx, int len, int cnt, int[] bitLen, int[] bitCnt) {
        while (idx < bitLen.length) {
            if (len > bitLen[idx]) {
                bitLen[idx] = len;
                bitCnt[idx] = cnt;
            } else if (len == bitLen[idx]) {
                bitCnt[idx] += cnt;
            }
            idx += (idx & -idx);
        }
    }
}

// Driver Class
public class Main {

    public static void main(String[] args) {

        int[] nums = {1, 3, 5, 4, 7};

        NumberOfLISRecursion rec = new NumberOfLISRecursion();
        System.out.println("Recursion Output: " + rec.findNumberOfLIS(nums)); // 2

        NumberOfLISMemoization memo = new NumberOfLISMemoization();
        System.out.println("Memoization Output: " + memo.findNumberOfLIS(nums)); // 2

        NumberOfLISTabulation tab = new NumberOfLISTabulation();
        System.out.println("Tabulation Output: " + tab.findNumberOfLIS(nums)); // 2

        NumberOfLISOptimal opt = new NumberOfLISOptimal();
        System.out.println("Optimal Output: " + opt.findNumberOfLIS(nums)); // 2
    }
}
