public class LinkedListDeque<T> implements Deque<T> {
    private class DLList<Stuff> {
        private class ItemNode {
            private ItemNode prev;
            private Stuff item;
            private ItemNode next;

            ItemNode(ItemNode p, Stuff i, ItemNode n) {
                prev = p;
                item = i;
                next = n;
            }

            public Stuff getRecursiveHelper(int index) {
                if (index == 0) {
                    return item;
                } else {
                    return next.getRecursiveHelper(index - 1);
                }
            }
        }

        private ItemNode sentinel;
        private int size;

        DLList() {
            sentinel = new ItemNode(null, null, null);
            sentinel.next = sentinel;
            sentinel.prev = sentinel;
            size = 0;
        }

        /** Add x to the front of the DLList. */
        public void addFirst(Stuff x) {
            sentinel.next = new ItemNode(sentinel, x, sentinel.next);
            sentinel.next.next.prev = sentinel.next;
            if (size == 0) {
                sentinel.prev = sentinel.next;
            }
            size += 1;
        }

        /** Returns the first item of the DLList. */
        public Stuff getFirst() {
            return sentinel.next.item;
        }

        //* Adds an item to the end of the list. */
        public void addLast(Stuff x) {
            sentinel.prev = new ItemNode(sentinel.prev, x, sentinel);
            sentinel.prev.prev.next = sentinel.prev;
            if (size == 0) {
                sentinel.next = sentinel.prev;
            }
            size += 1;
        }

        /** Returns the last item of the DLList. */
        public Stuff getLast() {
            return sentinel.prev.item;
        }

        /** Returns the size of the DLList. */
        public int getSize() {
            return size;
        }

        /** Removes and returns the last item of the DLList. If no such item
         * exists, returns null. */
        public Stuff removeLast() {
            Stuff last = sentinel.prev.item;
            sentinel.prev = sentinel.prev.prev;
            sentinel.prev.next = sentinel;
            size -= 1;
            return last;
        }

        /** Removes and returns the first item of the DLList. If no such item
         * exists, returns null. */
        public Stuff removeFirst() {
            Stuff first = sentinel.next.item;
            sentinel.next = sentinel.next.next;
            sentinel.next.prev = sentinel;
            size -= 1;
            return first;
        }

        /** Gets the item at the given index, where 0 is the front, 1 is the next
         * item, and so forth.  If no such item exists, returns null.  Must not
         * alter the deque! */
        public Stuff get(int index) {
            ItemNode ptr = sentinel.next;
            while (index > 0) {
                ptr = ptr.next;
                index -= 1;
            }
            return ptr.item;
        }

        /** Same as get, but uses recursion. */
        public Stuff getRecursive(int index) {
            index = Math.min(index, getSize());
            return sentinel.next.getRecursiveHelper(index);
        }
    }

    private DLList<T> queue;

    public LinkedListDeque() {
        queue = new DLList<>();
    }

    /** Creates a deep copy of other. */
    public LinkedListDeque(LinkedListDeque<T> other) {
        queue = new DLList<>();
        for (int i = 0; i < other.size(); i++) {
            this.addLast(other.get(i));
        }
    }

    /** Add an item of type T to the front of the deque. */
    public void addFirst(T item) {
        queue.addFirst(item);
    }

    /** Adds an item of type T to the back of the deque. */
    public void addLast(T item) {
        queue.addLast(item);
    }

    /** Returns the number of items in the deque. */
    public int size() {
        return queue.getSize();
    }

    /** Prints the items in the deque from first to last, separated by a space.
     * Once all the items have been printed, print out a new line. */
    public void printDeque() {
        for (int i = 0; i < size(); i++) {
            System.out.print(get(i) + " ");
        }
        System.out.println();
    }

    /** Removes and returns the item at the front of the deque.  If no such
     * item exists, returns null */
    public T removeFirst() {
        return queue.removeFirst();
    }

    /** Removes and returns the item at the back of the deque.  If no such item
     * exists, returns null. */
    public T removeLast() {
        return queue.removeLast();
    }

    /** Gets the item at the given index, where 0 is the front, 1 is the next
     * item, and so forth.  If no such item exists, returns null.  Must not
     * alter the deque! */
    public T get(int index) {
        return queue.get(index);
    }

    /** Same as get, but uses recursion. */
    public T getRecursive(int index) {
        return queue.getRecursive(index);
    }

    public static void main(String[] args) {
        LinkedListDeque<Integer> test1 = new LinkedListDeque<>();
        test1.addFirst(3);
        test1.addFirst(2);
        test1.addFirst(1);
    }
}
