package LRU_cache;

import java.util.HashMap;


/**
 * Design and implement a data structure for Least Recently Used (LRU) cache.
 * It should support the following operations: get and set.
 * <a href="https://leetcode.com/problems/lru-cache/">leetcode</a>
 */

//with HashMap and DoubleLinkedList

class LRUCache {

    private class Node {
        Node prev;
        Node next;
        Integer key;
        Integer value;

        private Node() { // used only for sentinel nodes head and tail

        }

        public Node(int key, int value) { // make sure the input does not have null key and value, the null be used as
            // 'not found' in map
            this.key = key;
            this.value = value;
            this.prev = null;
            this.next = null;
        }
    }

    private class Order {
        // double linked list
        private Node old_;
        private Node _new;

        public Order() {
            old_ = new Node(); // care
            _new = new Node(); // care
            old_.next = _new; // care
            _new.prev = old_; // care
        }

        private Node rm_oldest() {
            Node removed = old_.next;
            remove(removed);
            return removed;
        }

        private void addNew(Node current) {
            Node pre = _new.prev;
            Node next = _new;

            pre.next = current;
            current.next = next;

            next.prev = current;
            current.prev = pre;
        }

        private void remove(Node n) {
            Node pre = n.prev;
            Node next = n.next;

            pre.next = next;
            next.prev = pre;
        }

        private void access(Node n) {
            remove(n);
            addNew(n);
        }
    }

    private int capacity;
    private HashMap<Integer, Node> map;
    private Order order;

    public LRUCache(int capacity) {
        this.capacity = capacity;
        map = new HashMap();
        order = new Order();
    }

    // get(key) - Get the value (will always be positive) of the key if the key exists in the cache,
    // otherwise return -1.
    public Integer get(int key) {
        Node n = map.get(key);
        if (n == null) {
            return null; // leetcode expected it to be -1, thus the value would never be -1;
        }

        order.access(n);
        return n.value;
    }

    // Set or insert the value if the key is not already present.
    // When the cache reached its capacity, it should invalidate the least recently used item before inserting a new item.
    public void set(int key, int value) {
        // set
        Integer v = get(key);
        if (get(key) != null) { // leetcode expected it to be -1, thus the value would never be -1;
            map.get(key).value = value;
            return;
        }

        // insert
        if (map.size() == capacity) {
            map.remove(order.rm_oldest().key);
        }

        Node insert = new Node(key, value);
        map.put(key, insert);
        order.addNew(insert);
    }
}

public class Leetcode146LRUCache {
    public static void main(String[] args) {
        LRUCache cache = new LRUCache(5);
        cache.get(1);
        cache.set(3, 2);
    }
}
