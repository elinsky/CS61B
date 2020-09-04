package bearmaps.hw4.project2ab;

import java.util.HashMap;
import java.util.NoSuchElementException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class ArrayHeapMinPQ<T> implements ExtrinsicMinPQ<T> {
    final double MAXCAPACITY = 0.75;
    final double MINCAPACITY = 0.25;
    private final HashMap<T, Integer> itemLocation;
    int size;
    int root = 1;
    private Node<T>[] elements;
    private int next; // Next open space to insert into

    public ArrayHeapMinPQ() {
        this.size = 0;
        this.next = root;
        this.elements = new ArrayHeapMinPQ.Node[16];
        this.itemLocation = new HashMap<>();
    }

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        ArrayHeapMinPQ<String> pq = new ArrayHeapMinPQ<>();
        for (int i = 1; i < 1000000; i++) {
            pq.add("hi" + i, i);
        }
        long end = System.currentTimeMillis();
        System.out.println("Total time elapsed: " + (end - start)/1000.0 +  " seconds.");
    }

    @Override
    public void add(T item, double priority) {
        if (item == null) {
            throw new IllegalArgumentException("Cannot add a null item");
        }
        if (contains(item)) {
            throw new IllegalArgumentException("Item is already in the priority queue");
        }
        Node toInsert = new Node(item, priority);
        elements[next] = toInsert;
        itemLocation.put(item, next);
        swim(next);
        next++;
        size++;
        checkResize();
    }

    @Override
    public boolean contains(T item) {
        return itemLocation.containsKey(item);
    }

    @Override
    public T getSmallest() {
        if (size() == 0) {
            throw new NoSuchElementException("The priority queue is empty");
        }
        return elements[root].item;
    }

    @Override
    public T removeSmallest() {
        if (size() == 0) {
            throw new NoSuchElementException("The priority queue is empty");
        }
        T smallest = elements[root].item;
        elements[root] = elements[next - 1];
        elements[next - 1] = null;
        itemLocation.remove(smallest);
        next--;
        size--;
        sink(root);
        checkResize();
        return smallest;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void changePriority(T item, double priority) {
        if (item == null) {
            throw new IllegalArgumentException("Cannot change priority of null object");
        }
        int position = find(item);
        elements[position].priority = priority;
        sink(position);
        swim(position);
    }

    private int find(T item) {
        try {
            return itemLocation.get(item);
        } catch (Exception e) {
            throw new NoSuchElementException("The priority queue does not contain this item");
        }
    }

    private void sink(int node) {
        int swapCandidate = node * 2;
        if (swapCandidate > size()) {
            return;
        }
        try {
            if (less(swapCandidate + 1, swapCandidate)) {
                swapCandidate++;
            }
        } catch (Exception ignored) {
        }
        if (less(swapCandidate, node)) {
            swap(swapCandidate, node);
            sink(swapCandidate);
        }
    }

    private void swim(int node) {
        if (node == 1) {
            return;
        }
        int parent = node / 2;
        if (less(node, parent)) {
            swap(parent, node);
            swim(parent);
        }
    }

    private void swap(int from, int to) {
        Node<T> temp = elements[from];
        elements[from] = elements[to];
        itemLocation.put(elements[from].item, from);
        elements[to] = temp;
        itemLocation.put(elements[to].item, to);
    }

    private void checkResize() {
        if (size >= elements.length * MAXCAPACITY) {
            resize(true);
        } else if (size > 16 && size < elements.length * MINCAPACITY) {
            resize(false);
        }

    }

    private boolean less(int i, int j) {
        if (elements[i] == null || elements[j] == null) {
            throw new NullPointerException();
        }
        return elements[i].compareTo(elements[j]) < 0;
    }

    private void resize(boolean makeBigger) {
        int newSize = 0;
        if (makeBigger) {
            // Increase size 50%.  Go from 75% full to 50% full.
            newSize = (int) (elements.length * 1.5);
        } else {
            // Reduce size 50%.  Go from 25% full to 50% full.
            newSize = (int) (elements.length * 0.5);
        }
        Node<T>[] temp = new ArrayHeapMinPQ.Node[newSize];
        System.arraycopy(elements, 0, temp, 0, size() + 1);
        elements = temp;
    }

    private class Node<T> implements Comparable<Node> {
        T item;
        double priority;

        public Node(T item, double priority) {
            this.item = item;
            this.priority = priority;
        }

        @Override
        public int compareTo(Node that) {
            if (that == null) {
                throw new NullPointerException();
            }
            if (this.priority > that.priority) {
                return 1;
            } else if (this.priority < that.priority) {
                return -1;
            }
            return 0;
        }
    }
}
