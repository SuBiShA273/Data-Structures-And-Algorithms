/*
Problem: You are given a string s that contains bracket pairs "(key)".
You are also given a list of key-value pairs knowledge, where each key is mapped to a value.
Replace all occurrences of "(key)" in s with the corresponding value.
If the key does not exist in knowledge, replace with "?".
Return the resulting string.

Source: LeetCode Problem #1807 → https://leetcode.com/problems/evaluate-the-bracket-pairs-of-a-string/
*/

import java.util.*;

/* ---------------------------------------------------
   Brute Force Approach
   - Idea:
     - Iterate through the string character by character.
     - When encountering '(', extract the key until ')'.
     - Search the key in the knowledge list linearly.
     - Replace with value or "?".
   - Time Complexity: O(n * k), where k = number of key-value pairs.
   - Space Complexity: O(n).
--------------------------------------------------- */
class BruteForce {
    public String evaluate(String s, List<List<String>> knowledge) {
        StringBuilder res = new StringBuilder();
        int i = 0;
        while (i < s.length()) {
            if (s.charAt(i) == '(') {
                int j = i + 1;
                while (s.charAt(j) != ')') j++;
                String key = s.substring(i + 1, j);
                String val = "?";
                for (List<String> pair : knowledge) {
                    if (pair.get(0).equals(key)) {
                        val = pair.get(1);
                        break;
                    }
                }
                res.append(val);
                i = j + 1;
            } else {
                res.append(s.charAt(i));
                i++;
            }
        }
        return res.toString();
    }
}

/* ---------------------------------------------------
   Optimized Approach (HashMap Lookup)
   - Idea:
     - Store knowledge pairs in a HashMap for O(1) lookup.
     - Iterate through the string.
     - When encountering '(', extract key until ')'.
     - Replace with value from map or "?".
   - Time Complexity: O(n).
   - Space Complexity: O(k).
--------------------------------------------------- */
class Optimized {
    public String evaluate(String s, List<List<String>> knowledge) {
        Map<String, String> map = new HashMap<>();
        for (List<String> pair : knowledge) {
            map.put(pair.get(0), pair.get(1));
        }

        StringBuilder res = new StringBuilder();
        int i = 0;
        while (i < s.length()) {
            if (s.charAt(i) == '(') {
                int j = i + 1;
                while (s.charAt(j) != ')') j++;
                String key = s.substring(i + 1, j);
                res.append(map.getOrDefault(key, "?"));
                i = j + 1;
            } else {
                res.append(s.charAt(i));
                i++;
            }
        }
        return res.toString();
    }
}

// Driver class
public class Main {
    public static void main(String[] args) {
        BruteForce brute = new BruteForce();
        Optimized opt = new Optimized();

        List<List<String>> knowledge1 = Arrays.asList(
            Arrays.asList("name", "bob"),
            Arrays.asList("age", "two")
        );
        String s1 = "(name)is(age)yearsold";
        System.out.println("Brute Force Result: " + brute.evaluate(s1, knowledge1)); // bobistwoyearsold
        System.out.println("Optimized Result: " + opt.evaluate(s1, knowledge1));     // bobistwoyearsold

        List<List<String>> knowledge2 = Arrays.asList(
            Arrays.asList("a", "b")
        );
        String s2 = "hi(name)";
        System.out.println("Brute Force Result: " + brute.evaluate(s2, knowledge2)); // hi?
        System.out.println("Optimized Result: " + opt.evaluate(s2, knowledge2));     // hi?
    }
}
