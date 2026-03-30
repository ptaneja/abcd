public class Node {
    int key;
    int value;
    Node prev;
    Node next;

    public Node(int key, int value, Node prev, Node next) {
        this.key = key;
        this.value = value;
        this.prev = prev;
        this.next = next;
    }
}

class DLL {
    public Node head;
    public Node tail;

    public DLL() {
        this.head = new Node(-3, -3, null, null);
        this.tail = new Node(-3, -3, this.head, null);
        this.head.next = tail;
    }

    public void addFirst(Node first) {
        first.next = this.head.next;
        first.prev = this.head;
        this.head.next.prev = first;
        this.head.next = first;
    }

    public void remove(Node node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }

    public Node removeLast() {
        if (tail.prev == this.head) return null; // empty

        Node last = this.tail.prev;
        remove(last);
        return last;
    }
}

class LRUCache {
    Map<Integer, Node> cacheMap = new HashMap<>();
    DLL dll = new DLL();
    int capacity;

    public LRUCache(int capacity) {
        this.capacity = capacity;
    }
    
    public int get(int key) {
        if (!cacheMap.containsKey(key)) {
            return -1;
        }
        Node node = cacheMap.get(key);
        this.dll.remove(node);
        this.dll.addFirst(node);
        return node.value;
    }
    
    public void put(int key, int value) {
        if (cacheMap.containsKey(key)) {
            Node node = this.cacheMap.get(key);
            node.value = value;
            this.dll.remove(node);
            this.dll.addFirst(node);
        } else {
            if (cacheMap.size() == capacity) {
                Node last = this.dll.removeLast();
                cacheMap.remove(last.key);
            }
            Node newNode = new Node(key, value, null, null);
            this.dll.addFirst(newNode);
            cacheMap.put(key, newNode);
        }
    }
}

/**
 * Your LRUCache object will be instantiated and called as such:
 * LRUCache obj = new LRUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */