import java.util.List;

public class MyTrieSet implements TrieSet61B {
    private final static int R = 256;
    private Node root = new Node();

    private static class Node {
        private Node[] next = new Node[R];
        private boolean present = false;
    }

    /** Clears all items out of Trie */
    @Override
    public void clear() {
        root = new Node();
    }

    /** Returns true if the Trie contains KEY, false otherwise */
    @Override
    public boolean contains(String key) {
        Node found = contains(root, key, 0);
        return found != null && found.present;
    }

    private Node contains(Node x, String key, int d) {
        if (x == null) {
            return null;
        } else if (d == key.length()) {
            return x;
        } else {
            return contains(x.next[key.charAt(d)], key, d + 1);
        }
    }

    /** Inserts string KEY into Trie */
    @Override
    public void add(String key) {
        Node added = add(root, key, 0);
        added.present = true;
    }

    private Node add(Node x, String key, int d) {
        Node next = x.next[key.charAt(d)];
        if (d == key.length() - 1) {
            if (next == null) {
                x.next[key.charAt(d)] = new Node();
                return x.next[key.charAt(d)];
            }
            return next;
        } else if (next != null) {
            return add(x.next[key.charAt(d)], key, d + 1);
        }
        else {
            x.next[key.charAt(d)] = new Node();
            return add(x.next[key.charAt(d)], key, d + 1);
        }
    }

    /** Returns a list of all words that start with PREFIX */
    @Override
    public List<String> keysWithPrefix(String prefix) {
        // TODO
        return null;
    }

    /** Returns the longest prefix of KEY that exists in the Trie
     * Not required for Lab 9. If you don't implement this, throw an
     * UnsupportedOperationException.
     */
    @Override
    public String longestPrefixOf(String key) {
        // TODO
        return null;
    }
}
