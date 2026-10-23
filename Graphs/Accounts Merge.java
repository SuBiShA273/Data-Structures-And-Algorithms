/*
Problem: Given a list of accounts where each account consists of a name
and a list of emails, merge accounts belonging to the same person.

Two accounts belong to the same person if there is at least one common email.

Return the merged accounts in the format:
[name, sorted_emails...]

Source: LeetCode Problem #721 → https://leetcode.com/problems/accounts-merge/

Approaches:
1. Brute Force (Graph + DFS)
   - Idea:
     - Build a graph where emails are nodes.
     - Connect emails belonging to the same account.
     - Use DFS to find connected components.
     - Sort emails and add account holder's name.
   - Time Complexity: O(N * M log M)
   - Space Complexity: O(N * M)

2. Optimal (Disjoint Set Union / Union Find)
   - Idea:
     - Treat each account as a node.
     - Union accounts sharing an email.
     - Group emails by their ultimate parent.
     - Sort emails and construct answer.
   - Time Complexity: O(N * M * α(N))
   - Space Complexity: O(N * M)
*/

import java.util.*;

// --------------------------------------------------
// Approach 1 : Graph + DFS
// --------------------------------------------------
class BruteForce {

    public List<List<String>> accountsMerge(List<List<String>> accounts) {

        Map<String, Set<String>> graph = new HashMap<>();
        Map<String, String> emailToName = new HashMap<>();

        for (List<String> acc : accounts) {
            String name = acc.get(0);

            for (int i = 1; i < acc.size(); i++) {
                emailToName.put(acc.get(i), name);

                graph.putIfAbsent(acc.get(i), new HashSet<>());

                if (i == 1) continue;

                graph.get(acc.get(i)).add(acc.get(i - 1));
                graph.get(acc.get(i - 1)).add(acc.get(i));
            }
        }

        Set<String> visited = new HashSet<>();
        List<List<String>> result = new ArrayList<>();

        for (String email : graph.keySet()) {
            if (visited.contains(email)) continue;

            List<String> component = new ArrayList<>();
            dfs(email, graph, visited, component);

            Collections.sort(component);

            List<String> merged = new ArrayList<>();
            merged.add(emailToName.get(email));
            merged.addAll(component);

            result.add(merged);
        }

        return result;
    }

    private void dfs(String email,
                     Map<String, Set<String>> graph,
                     Set<String> visited,
                     List<String> component) {

        visited.add(email);
        component.add(email);

        for (String nei : graph.get(email)) {
            if (!visited.contains(nei)) {
                dfs(nei, graph, visited, component);
            }
        }
    }
}

// --------------------------------------------------
// Approach 2 : Union Find (Optimal)
// --------------------------------------------------
class Optimal {

    class DSU {
        int[] parent;

        DSU(int n) {
            parent = new int[n];
            for (int i = 0; i < n; i++) {
                parent[i] = i;
            }
        }

        int find(int x) {
            if (parent[x] == x) return x;
            return parent[x] = find(parent[x]);
        }

        void union(int u, int v) {
            int pu = find(u);
            int pv = find(v);
            if (pu != pv) {
                parent[pu] = pv;
            }
        }
    }

    public List<List<String>> accountsMerge(List<List<String>> accounts) {

        int n = accounts.size();
        DSU dsu = new DSU(n);

        Map<String, Integer> mailToAccount = new HashMap<>();

        for (int i = 0; i < n; i++) {
            List<String> acc = accounts.get(i);

            for (int j = 1; j < acc.size(); j++) {
                String mail = acc.get(j);

                if (!mailToAccount.containsKey(mail)) {
                    mailToAccount.put(mail, i);
                } else {
                    dsu.union(i, mailToAccount.get(mail));
                }
            }
        }

        Map<Integer, TreeSet<String>> mergedMails = new HashMap<>();

        for (String mail : mailToAccount.keySet()) {
            int parent = dsu.find(mailToAccount.get(mail));

            mergedMails.putIfAbsent(parent, new TreeSet<>());
            mergedMails.get(parent).add(mail);
        }

        List<List<String>> ans = new ArrayList<>();

        for (int parent : mergedMails.keySet()) {
            List<String> temp = new ArrayList<>();

            temp.add(accounts.get(parent).get(0));
            temp.addAll(mergedMails.get(parent));

            ans.add(temp);
        }

        return ans;
    }
}

// --------------------------------------------------
// Driver Class
// --------------------------------------------------
public class Main {

    public static void main(String[] args) {

        List<List<String>> accounts = Arrays.asList(
            Arrays.asList("John","johnsmith@mail.com","john_newyork@mail.com"),
            Arrays.asList("John","johnsmith@mail.com","john00@mail.com"),
            Arrays.asList("Mary","mary@mail.com"),
            Arrays.asList("John","johnnybravo@mail.com")
        );

        BruteForce brute = new BruteForce();
        Optimal opt = new Optimal();

        System.out.println("Brute Force Result:");
        System.out.println(brute.accountsMerge(accounts));

        System.out.println("\nOptimal Result:");
        System.out.println(opt.accountsMerge(accounts));
    }
}
