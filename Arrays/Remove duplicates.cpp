/*
Problem: Given a sorted array nums, 
remove the duplicates in-place such that each unique element appears only once.
Return the new length of the array after removing duplicates.

Source: LeetCode Problem #26 → https://leetcode.com/problems/remove-duplicates-from-sorted-array/

Approaches:
1. Brute Force (Extra Space)
   - Idea: Use a set to store unique elements, then overwrite nums.
   - Time Complexity: O(n log n) [due to set insertion]
   - Space Complexity: O(n)

2. Two-Pointer Technique (Optimal)
   - Idea: Since array is sorted, use two pointers:
           one for placing unique elements, one for scanning.
   - Time Complexity: O(n)
   - Space Complexity: O(1)
*/

#include <bits/stdc++.h>
using namespace std;

// Approach 1: Brute Force using Set
int removeDuplicatesBrute(vector<int>& nums) {
    set<int> st(nums.begin(), nums.end());
    int index = 0;
    for (int val : st) {
        nums[index++] = val;
    }
    return index; // new length
}

// Approach 2: Two-Pointer (Optimal)
int removeDuplicatesOptimal(vector<int>& nums) {
    if (nums.empty()) return 0;
    int i = 0; // slow pointer
    for (int j = 1; j < nums.size(); j++) {
        if (nums[j] != nums[i]) {
            i++;
            nums[i] = nums[j];
        }
    }
    return i + 1; // new length
}

int main() {
    vector<int> nums = {0,0,1,1,1,2,2,3,3,4};

    // Test Brute Force
    int len1 = removeDuplicatesBrute(nums);
    cout << "Brute Force Result Length: " << len1 << endl;
    cout << "Array after removal: ";
    for (int i = 0; i < len1; i++) cout << nums[i] << " ";
    cout << endl;

    // Reset nums for optimal test
    nums = {0,0,1,1,1,2,2,3,3,4};

    // Test Optimal
    int len2 = removeDuplicatesOptimal(nums);
    cout << "Optimal Result Length: " << len2 << endl;
    cout << "Array after removal: ";
    for (int i = 0; i < len2; i++) cout << nums[i] << " ";
    cout << endl;

    return 0;
}
