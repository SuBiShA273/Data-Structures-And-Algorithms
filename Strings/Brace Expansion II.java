/*
Problem: Given a string expression representing brace expansion,
return all words formed after expanding the expression in lexicographic order.

Rules:
- Expressions contain braces '{ }', commas ',', and lowercase letters.
- Braces denote sets of options.
- Commas separate options.
- Concatenation is implicit.

Source: LeetCode Problem #1096 → https://leetcode.com/problems/brace-expansion-ii/

Approach: DFS + Recursion
   - Idea:
     - Use recursion to expand braces.
     - Find the first closing brace '}'.
     - Match with its corresponding opening brace '{'.
     - Split the inner content by commas to get options.
     - For each option, recursively expand prefix + option + suffix.
     - Use TreeSet to maintain lexicographic order and uniqueness.
   - Time Complexity: Exponential in worst case (due to expansion).
   - Space Complexity: O(n + k) where n = length of expression, k = number of expansions.
*/

import java.util.*;

class Solution {
    public List<String> braceExpansionII(String expression) {
        TreeSet<String> ans = new TreeSet<>();
        dfs(expression, ans);
        return new ArrayList<>(ans);
    }

    public void dfs(String expr, TreeSet<String> ans) {
        int firstclose = expr.indexOf("}");
        if (firstclose == -1) {
            ans.add(expr);
            return;
        }
        int matchopen = expr.lastIndexOf("{", firstclose);
        String prefix = expr.substring(0, matchopen);
        String suffix = expr.substring(firstclose + 1);
        String inner = expr.substring(matchopen + 1, firstclose);
        String[] options = inner.split(",");
        for (String c : options) {
            dfs(prefix + c + suffix, ans);
        }
    }
}

// Driver class
public class Main {
    public static void main(String[] args) {
        Solution sol = new Solution();

        // Testcase 1
        String expr1 = "{a,b}{c,{d,e}}";
        System.out.println("Result 1: " + sol.braceExpansionII(expr1));
        // Expected: [ac, ad, ae, bc, bd, be]

        // Testcase 2
        String expr2 = "{{a,z},a{b,c},{ab,z}}";
        System.out.println("Result 2: " + sol.braceExpansionII(expr2));
        // Expected: [a, ab, ac, z]

        // Testcase 3
        String expr3 = "{a,b}{c,d}";
        System.out.println("Result 3: " + sol.braceExpansionII(expr3));
        // Expected: [ac, ad, bc, bd]
    }
}
