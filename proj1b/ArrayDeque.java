public class ArrayDeque<T> implements Deque<T> {
    private T[] items;
    private int size;
    private int nextFirst;
    private int nextLast;

    public ArrayDeque() {
        items = (T []) new Object[8];
        size = 0;
        nextFirst = 3;
        nextLast = 4;
    }

    /** Creates a deep copy of other. */
    public ArrayDeque(ArrayDeque<T> other) {
        items = (T []) new Object[other.items.length];
        System.arraycopy(other.items, 0, items, 0, items.length);
        size = other.size;
        nextFirst = other.nextFirst;
        nextLast = other.nextLast;
    }

    /** Add an item of type T to the front of the deque. */
    public void addFirst(T item) {
        if (size == items.length) {
            resize();
        }
        items[nextFirst] = item;
        nextFirst = Math.floorMod(nextFirst - 1, items.length);
        size += 1;
    }

    /** Adds an item of type T to the back of the deque. */
    public void addLast(T item) {
        if (size == items.length) {
            resize();
        }
        items[nextLast] = item;
        nextLast = Math.floorMod(nextLast + 1, items.length);
        size += 1;
    }

    /** Returns the number of items in the deque. */
    public int size() {
        return size; }

    /** Prints the items in the deque from first to last, separated by a space.
     * Once all the items have been printed, print out a new line. */
    public void printDeque() {
        for (int i = 0; i < size; i++) {
            System.out.print(get(i));
        }
        System.out.println();
    }

    /** Removes and returns the item at the front of the deque.  If no such
     * item exists, returns null */
    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        if (items.length > 16 && (double) size / (double) items.length < 0.50) {
            resize();
        }
        T result = items[Math.floorMod(nextFirst + 1, items.length)];
        items[Math.floorMod(nextFirst + 1, items.length)] = null;
        nextFirst = Math.floorMod(nextFirst + 1, items.length);
        size -= 1;
        return result;
    }

    /** Removes and returns the item at the back of the deque.  If no such item
     * exists, returns null. */
    public T removeLast() {
        if (size == 0) {
            return null;
        }
        if (items.length > 16 && (double) size / (double) items.length < 0.50) {
            resize();
        }
        T result = items[Math.floorMod(nextLast - 1, items.length)];
        items[Math.floorMod(nextLast - 1, items.length)] = null;
        nextLast = Math.floorMod(nextLast - 1, items.length);
        size -= 1;
        return result;
    }

    /** Gets the item at the given index, where 0 is the front, 1 is the next
     * item, and so forth.  If no such item exists, returns null.  Must not
     * alter the deque! */
    public T get(int index) {
        if (index < 0 || index >= size) {
            return null;
        } else {
            return items[Math.floorMod(nextFirst + 1 + index, items.length)];
        }
    }

    /** Resizes the underlying array. */
    private void resize() {
        T[] temp = (T []) new Object[(int) (size * 1.5)];
        for (int i = 0; i < size; i++) {
            temp[i] = get(i);
        }
        items = temp;
        nextFirst = items.length - 1;
        nextLast = size;
    }

    public static void main(String[] args) {

    }
}
