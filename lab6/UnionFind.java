import java.util.Arrays;

public class UnionFind {

    private final int[] elements;

    /* Creates a UnionFind data structure holding n vertices. Initially, all
       vertices are in disjoint sets. */
    public UnionFind(int n) {
        this.elements = new int[n];
        Arrays.fill(elements, -1);
    }

    /* Throws an exception if v1 is not a valid index. */
    private void validate(int vertex) {
        int n = elements.length;
        if (vertex < 0 | vertex >= n) {
            throw new IndexOutOfBoundsException();
        }
    }

    /* Returns the size of the set v1 belongs to. */
    public int sizeOf(int v1) {
        validate(v1);
        int root = find(v1);
        return -1 * parent(root);
    }

    /* Returns the parent of v1. If v1 is the root of a tree, returns the
       negative size of the tree for which v1 is the root. */
    public int parent(int v1) {
        validate(v1);
        return elements[v1];
    }

    /* Returns true if nodes v1 and v2 are connected. */
    public boolean connected(int v1, int v2) {
        validate(v1);
        validate(v2);
        return find(v1) == find(v2);
    }

    /* Connects two elements v1 and v2 together. v1 and v2 can be any valid 
       elements, and a union-by-size heuristic is used. If the sizes of the sets
       are equal, tie break by connecting v1's root to v2's root. Unioning a 
       vertex with itself or vertices that are already connected should not 
       change the sets but may alter the internal structure of the data. */
    public void union(int v1, int v2) {
        validate(v1);
        validate(v2);
        if (connected(v1, v2)) {
            return;
        }
        int v1_size = sizeOf(v1);
        int v2_size = sizeOf(v2);
        if (v1_size > v2_size) {
            elements[find(v2)] = find(v1);
            elements[find(v1)] = -1 * (v1_size + v2_size);
        } else {
            elements[find(v1)] = find(v2);
            elements[find(v2)] = -1 * (v1_size + v2_size);
        }
    }

    /* Returns the root of the set V belongs to. */
    public int find(int vertex) {
        validate(vertex);
        while (elements[vertex] >= 0 ) {
            vertex = elements[vertex];
        }
        return vertex;
    }
}
