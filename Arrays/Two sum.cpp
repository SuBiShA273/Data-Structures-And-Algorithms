/*
Problem: Given an array of integers nums and an integer target, 
return the indices of the two numbers such that they add up to target.

Source: LeetCode Problem #1 → https://leetcode.com/problems/two-sum/

Approaches:
1. Extreme Brute Force
   - Idea: Check every possible pair (i, j).
   - Time Complexity: O(n^2)
   - Space Complexity: O(1)

2. Hashing (Optimal)
   - Idea: Use a hash map to store values and indices, 
           check if target - nums[i] exists in the map.
   - Time Complexity: O(n)
   - Space Complexity: O(n)
*/

#include <bits/stdc++.h>
using namespace std;

// Approach 1: Extreme Brute Force
vector<int> twoSumBrute(vector<int>& nums, int target) {
    int n = nums.size();
    for (int i = 0; i < n; i++) {
        for (int j = i + 1; j < n; j++) {
            if (nums[i] + nums[j] == target) {
                return {i, j};
            }
        }
    }
    return {};
}

// Approach 2: Hashing (Optimal)
vector<int> twoSumHash(vector<int>& nums, int target) {
    unordered_map<int, int> mp; // value -> index
    for (int i = 0; i < nums.size(); i++) {
        int complement = target - nums[i];
        if (mp.find(complement) != mp.end()) {
            return {mp[complement], i};
        }
        mp[nums[i]] = i;
    }
    return {};
}

int main() {
    vector<int> nums = {2, 7, 11, 15};
    int target = 9;

    // Test Brute Force
    vector<int> res1 = twoSumBrute(nums, target);
    cout << "Brute Force Result: ";
    if (!res1.empty()) cout << res1[0] << ", " << res1[1] << endl;
    else cout << "No solution" << endl;

    // Test Hashing
    vector<int> res2 = twoSumHash(nums, target);
    cout << "Hashing Result: ";
    if (!res2.empty()) cout << res2[0] << ", " << res2[1] << endl;
    else cout << "No solution" << endl;

    return 0;
}
