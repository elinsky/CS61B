import java.util.Iterator;
import java.util.Set;

public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {
    Node<K, V> root;

    class Node<K, V> {
        public K key;
        public V value;
        public Node<K, V> left, right;
        public int size;

        public Node(K key, V value, int size) {
            this.key = key;
            this.value = value;
            this.size = size;
        }
    }
    /** Removes all of the mappings from this map. */
    @Override
    public void clear(){
        root = null;
    }

    /* Returns true if this map contains a mapping for the specified key. */
    @Override
    public boolean containsKey(K key) {
        V value = get(key);
        return value != null;
    }

    /* Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     */
    @Override
    public V get(K key) {
        return get_helper(key, root);
    }

    private V get_helper(K key, Node<K, V> node) {
        if (node == null) {
            return null;
        }
        int cmp = key.compareTo(node.key);
        if (cmp == 0) {
            return node.value;
        } else if (key.compareTo(node.key) < 0) {
            return get_helper(key, node.left);
        } else {
            return get_helper(key, node.right);
        }
    }

    /* Returns the number of key-value mappings in this map. */
    @Override
    public int size() {
        if (root != null) {
            return root.size;
        } else {
            return 0;
        }
    }

    private int size(Node<K, V> node) {
        if (node == null) {
            return 0;
        } else {
            return node.size;
        }
    }

    /* Associates the specified value with the specified key in this map. */
    @Override
    public void put(K key, V value) {
        root = put_helper(key, value, root);
    }

    private Node<K, V> put_helper(K key, V value, Node<K, V> node) {
        if (node == null) {
            return new Node<K, V>(key, value, 1);
        }
        int cmp = key.compareTo(node.key);
        if (cmp == 0) {
            node.value = value;
            return node;
        } else if (key.compareTo(node.key) < 0) {
            node.left = put_helper(key, value, node.left);
        } else {
            node.right = put_helper(key, value, node.right);
        }
        node.size = size(node.left) + size(node.right) + 1;
        return node;
    }

    public void printInOrder() {
        print_helper(root);
    }

    private void print_helper(Node<K, V> node) {
        if (size(node) == 1) {
            System.out.print(node.value + " ");
        } else if (node.size > 1) {
            print_helper(node.left);
            print_helper(node);
            print_helper(node.right);
        }
    }

    /* Returns a Set view of the keys contained in this map. */
    @Override
    public Set<K> keySet() {
        throw new UnsupportedOperationException();
    }

    /* Removes the mapping for the specified key from this map if present.
     * Not required for Lab 8. If you don't implement this, throw an
     * UnsupportedOperationException. */
    @Override
    public V remove(K key) {
        throw new UnsupportedOperationException();
    }

    /* Removes the entry for the specified key only if it is currently mapped to
     * the specified value. Not required for Lab 8. If you don't implement this,
     * throw an UnsupportedOperationException.*/
    @Override
    public V remove(K key, V value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator<K> iterator() {
        throw new UnsupportedOperationException();
    }

}

