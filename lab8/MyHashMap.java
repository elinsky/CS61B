import java.util.Iterator;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class MyHashMap<K, V> implements Map61B<K, V> {
    private int m; // hash table size
    private int size; // number of items in the hash table
    private Node[] st;
    private final double load_factor = 0.75;

    private class Node<kk, vv> {
        kk key;
        vv value;
        Node<kk, vv> next;

        public Node(kk key, vv value, Node<kk, vv> next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }
    }

    public MyHashMap(int m) {
        this.m = m;
        this.size = 0;
        st = new Node[m];
    }

    public MyHashMap() {
        this(16);
    }

    /** Removes all of the mappings from this map. */
    @Override
    public void clear() {
        this.m = 16;
        this.size = 0;
        this.st = new Node[16];
    }

    /** Returns true if this map contains a mapping for the specified key. */
    @Override
    public boolean containsKey(K key) {
        return get(key) != null;
    }

    /**
     * Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     */
    @Override
    public V get(K key) {
        int hash = hash(key);
        for (Node x = st[hash]; x != null; x = x.next) {
            if (key.equals(x.key)) {
                return (V) x.value;
            }
        }
        return null;
    }

    /** Returns the number of key-value mappings in this map. */
    @Override
    public int size() {
        return size;
    }

    private int hash(K x) {
        return (x.hashCode() & 0x7fffffff) % m;
    }

    /**
     * Associates the specified value with the specified key in this map.
     * If the map previously contained a mapping for the key,
     * the old value is replaced.
     */
    @Override
    public void put(Object key,Object value) {
        if (key == null) {
            throw new IllegalArgumentException();
        }
        if (size >= m * load_factor) {
            resize(m * 2);
        }
        int hash = hash((K) key);
        if (st[hash] == null) {
            st[hash] = new Node((K) key, (V) value, st[hash]);
            this.size += 1;
        }
        for (Node x = st[hash]; x != null; x = x.next) {
            if (key.equals(x.key)) {
                x.value = (V) value;
                return;
            }
        }
            st[hash] = new Node((K) key, (V) value, st[hash]);
            this.size += 1;
    }

    /** Returns a Set view of the keys contained in this map. */
    @Override
    public Set<K> keySet() {
        HashSet<K> set = new HashSet<K>();
        HashMapIterator iter = new HashMapIterator();
        while (iter.hasNext()) {
            set.add(iter.next());
        }
        return set;
    }

    /**
     * Removes the mapping for the specified key from this map if present.
     * Not required for Lab 8. If you don't implement this, throw an
     * UnsupportedOperationException.
     */
    @Override
    public Object remove(Object key) {
        // TODO
        return null;
    }

    /**
     * Removes the entry for the specified key only if it is currently mapped to
     * the specified value. Not required for Lab 8. If you don't implement this,
     * throw an UnsupportedOperationException.
     */
    @Override
    public Object remove(Object key, Object value) {
        // TODO
        return null;
    }

    @Override
    public Iterator<K> iterator() {
        return new HashMapIterator();
    }

    private class HashMapIterator implements Iterator<K> {
        private final ArrayList<K> queue;

        public HashMapIterator() {
            queue = new ArrayList<>();
            for (int i = 0; i < m; i++) {
                for (Node x = st[i]; x != null; x = x.next) {
                    queue.add((K) x.key);
                }
            }

        }

        public boolean hasNext() {
            return !queue.isEmpty();
        }

        public K next() {
            return queue.remove(queue.size() - 1);
        }
    }

    private void resize(int new_size) {
        MyHashMap<K, V> temp = new MyHashMap<>(new_size);
        HashMapIterator iter = new HashMapIterator();
        while (iter.hasNext()) {
            K next = iter.next();
            V next_val = get(next);
            temp.put(next, next_val);
        }
        this.m = temp.m;
        this.st = temp.st;
        this.size = temp.size;
    }
}
