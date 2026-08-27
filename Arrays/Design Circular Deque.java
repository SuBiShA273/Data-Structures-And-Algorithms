/*
Problem: Design your implementation of the circular double-ended queue (deque).
Implement the MyCircularDeque class:
- MyCircularDeque(k): Initializes the deque with a maximum size of k.
- insertFront(): Adds an item at the front. Return true if successful.
- insertLast(): Adds an item at the rear. Return true if successful.
- deleteFront(): Deletes an item from the front. Return true if successful.
- deleteLast(): Deletes an item from the rear. Return true if successful.
- getFront(): Gets the front item. Return -1 if empty.
- getRear(): Gets the last item. Return -1 if empty.
- isEmpty(): Checks if deque is empty.
- isFull(): Checks if deque is full.

Source: LeetCode Problem #641 → https://leetcode.com/problems/design-circular-deque/

Approaches:
1. Brute Force (ArrayList Simulation)
   - Idea:
     - Use ArrayList to simulate deque operations.
     - Insert/delete at front or rear using list methods.
     - Check size for isEmpty/isFull.
   - Time Complexity: O(n) for front operations (due to shifting).
   - Space Complexity: O(k).

2. Optimized (Fixed-Size Array + Pointers)
   - Idea:
     - Use circular array of size k.
     - Maintain head and tail pointers with modulo arithmetic.
     - Insert/delete in O(1).
   - Time Complexity: O(1) for all operations.
   - Space Complexity: O(k).
*/

import java.util.*;

// Approach 1: Brute Force (ArrayList Simulation)
class MyCircularDequeBrute {
    private List<Integer> deque;
    private int capacity;

    public MyCircularDequeBrute(int k) {
        deque = new ArrayList<>();
        capacity = k;
    }

    public boolean insertFront(int value) {
        if (isFull()) return false;
        deque.add(0, value);
        return true;
    }

    public boolean insertLast(int value) {
        if (isFull()) return false;
        deque.add(value);
        return true;
    }

    public boolean deleteFront() {
        if (isEmpty()) return false;
        deque.remove(0);
        return true;
    }

    public boolean deleteLast() {
        if (isEmpty()) return false;
        deque.remove(deque.size() - 1);
        return true;
    }

    public int getFront() {
        return isEmpty() ? -1 : deque.get(0);
    }

    public int getRear() {
        return isEmpty() ? -1 : deque.get(deque.size() - 1);
    }

    public boolean isEmpty() {
        return deque.isEmpty();
    }

    public boolean isFull() {
        return deque.size() == capacity;
    }
}

// Approach 2: Optimized (Fixed-Size Array + Pointers)
class MyCircularDequeOptimized {
    private int[] arr;
    private int front, rear, size, capacity;

    public MyCircularDequeOptimized(int k) {
        arr = new int[k];
        capacity = k;
        front = 0;
        rear = -1;
        size = 0;
    }

    public boolean insertFront(int value) {
        if (isFull()) return false;
        front = (front - 1 + capacity) % capacity;
        arr[front] = value;
        size++;
        if (size == 1) rear = front;
        return true;
    }

    public boolean insertLast(int value) {
        if (isFull()) return false;
        rear = (rear + 1) % capacity;
        arr[rear] = value;
        size++;
        if (size == 1) front = rear;
        return true;
    }

    public boolean deleteFront() {
        if (isEmpty()) return false;
        front = (front + 1) % capacity;
        size--;
        return true;
    }

    public boolean deleteLast() {
        if (isEmpty()) return false;
        rear = (rear - 1 + capacity) % capacity;
        size--;
        return true;
    }

    public int getFront() {
        return isEmpty() ? -1 : arr[front];
    }

    public int getRear() {
        return isEmpty() ? -1 : arr[rear];
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean isFull() {
        return size == capacity;
    }
}

// Driver class
public class Main {
    public static void main(String[] args) {
        // Test Brute Force
        MyCircularDequeBrute brute = new MyCircularDequeBrute(3);
        System.out.println("Brute insertLast(1): " + brute.insertLast(1)); // true
        System.out.println("Brute insertLast(2): " + brute.insertLast(2)); // true
        System.out.println("Brute insertFront(3): " + brute.insertFront(3)); // true
        System.out.println("Brute getRear(): " + brute.getRear()); // 2

        // Test Optimized
        MyCircularDequeOptimized opt = new MyCircularDequeOptimized(3);
        System.out.println("Opt insertLast(1): " + opt.insertLast(1)); // true
        System.out.println("Opt insertLast(2): " + opt.insertLast(2)); // true
        System.out.println("Opt insertFront(3): " + opt.insertFront(3)); // true
        System.out.println("Opt getRear(): " + opt.getRear()); // 2
    }
}
