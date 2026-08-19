/*
Problem: At a lemonade stand, each lemonade costs $5. Customers pay with $5, $10, or $20 bills.
You must provide correct change to each customer. Return true if you can provide change to all customers, false otherwise.

Source: LeetCode Problem #860 → https://leetcode.com/problems/lemonade-change/

Approach: Greedy
   - Track counts of $5 and $10 bills (no need to track $20).
   - For each customer:
     - If bill = $5 → just collect it.
     - If bill = $10 → must give one $5 as change.
     - If bill = $20 → prefer giving $10 + $5 (greedy: use larger bill first),
                       otherwise give three $5 bills.
     - If not possible → return false.
   - Time Complexity: O(n)
   - Space Complexity: O(1)
*/

class LemonadeChangeGreedy {
    public boolean lemonadeChange(int[] bills) {
        int five = 0, ten = 0;

        for (int bill : bills) {
            if (bill == 5) {
                five++;
            } else if (bill == 10) {
                if (five == 0) return false;
                five--;
                ten++;
            } else { // bill == 20
                if (ten > 0 && five > 0) {
                    ten--;
                    five--;
                } else if (five >= 3) {
                    five -= 3;
                } else {
                    return false;
                }
            }
        }
        return true;
    }
}

// Driver class
public class Main {
    public static void main(String[] args) {
        LemonadeChangeGreedy greedy = new LemonadeChangeGreedy();

        int[] bills1 = {5,5,5,10,20};
        int[] bills2 = {5,5,10,10,20};

        System.out.println("Result (bills1): " + greedy.lemonadeChange(bills1)); // true
        System.out.println("Result (bills2): " + greedy.lemonadeChange(bills2)); // false
    }
}
