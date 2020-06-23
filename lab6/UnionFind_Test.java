import org.junit.Test;

import static org.junit.Assert.*;

public class UnionFind_Test {

    /* Test bottom edge of the array for size_of. */
    @Test
    public void test_sizeOf() {
        UnionFind u = new UnionFind(10);
        int expected = 1;
        int actual = u.sizeOf(0);
        assertEquals(expected, actual);
    }

    /* Test top edge of the array for size_of */
    @Test
    public void test_sizeOf2() {
        UnionFind u = new UnionFind(10);
        int expected = 1;
        int actual = u.sizeOf(9);
        assertEquals(expected, actual);
    }

    /* Test that sizeOf can't return a value greater than the array size. */
    @Test
    public void test_sizeOf3() {
        UnionFind u = new UnionFind(3);
        u.union(0, 1);
        u.union(1, 2);
        u.union(0, 2); // Should be a no-op.  Shouldn't increment size.
        int expected = 3;
        int actual = u.sizeOf(0);
        assertEquals(expected, actual);
    }

    /* Test middle of the road case for size_of. */
    @Test
    public void test_sizeOf4() {
        UnionFind u = new UnionFind(3);
        u.union(0, 1);
        int expected = 2;
        int actual = u.sizeOf(1);
        assertEquals(expected, actual);
    }

    /* Test that validate throws an exception when Index is above below bound. */
    @Test(expected = IndexOutOfBoundsException.class)
    public void test_validate() {
        UnionFind u = new UnionFind(10);
        u.sizeOf(-1);
    }

    /* Test that validate throws an exception when Index is above the upper bound. */
    @Test(expected = IndexOutOfBoundsException.class)
    public void test_validate2() {
        UnionFind u = new UnionFind(10);
        u.sizeOf(10);
    }

    /* Test that parent returns parent in simple 2 node case. */
    @Test
    public void test_parent() {
        UnionFind u = new UnionFind(5);
        u.union(0, 1);
        int expected = 1;
        int actual = u.parent(0);
        assertEquals(expected, actual);
    }

    /* Test that parent returns negative size of the tree when v1 is the root. */
    @Test
    public void test_parent2() {
        UnionFind u = new UnionFind(5);
        u.union(0, 1);
        int expected = -2;
        int actual = u.parent(1);
        assertEquals(expected, actual);
    }

    /* Test that parent returns the parent when called with a grandchild. */
    @Test
    public void test_parent3() {
        UnionFind u = new UnionFind(5);
        u.union(1, 0);
        u.union(3, 2);
        u.union(2, 0);
        int expected = 2;
        int actual = u.parent(3);
        assertEquals(expected, actual);
    }

    /* Test that parent of 1 node tree returns -1. */
    @Test
    public void test_parent4() {
        UnionFind u = new UnionFind(5);
        int expected = -1;
        int actual = u.parent(0);
        assertEquals(expected, actual);
    }

    /* Test connected for simplest case where connected. */
    @Test
    public void test_connected() {
        UnionFind u = new UnionFind(5);
        u.union(0, 1);
        boolean actual = u.connected(0, 1);
        assertTrue(actual);
    }

    /* Test connected for simplest case where not connected. */
    @Test
    public void test_connected2() {
        UnionFind u = new UnionFind(5);
        boolean actual = u.connected(0, 1);
        assertFalse(actual);
    }

    /* Test connected where v1 is grandchild and v2 is root. */
    @Test
    public void test_connected3() {
        UnionFind u = new UnionFind(5);
        u.union(1, 0);
        u.union(3, 2);
        u.union(2, 0);
        boolean actual = u.connected(3, 0);
        assertTrue(actual);
    }

    /* Test connected where v1 and v2 are both children on different sides of the tree. */
    @Test
    public void test_connected4() {
        UnionFind u = new UnionFind(5);
        u.union(1, 0);
        u.union(3, 2);
        u.union(2, 0);
        boolean actual = u.connected(1, 3);
        assertTrue(actual);
    }

    /* Test union where v1 is out of bounds. */
    @Test(expected = IndexOutOfBoundsException.class)
    public void test_union() {
        UnionFind u = new UnionFind(5);
        u.union(-1, 0);
    }

    /* Test union where v2 is out of bounds. */
    @Test(expected = IndexOutOfBoundsException.class)
    public void test_union2() {
        UnionFind u = new UnionFind(5);
        u.union(4, 5);
    }

    /* Test find where vertex is the root. */
    @Test
    public void test_find() {
        UnionFind u = new UnionFind(5);
        u.union(1, 0);
        int expected = 0;
        int actual = u.find(0);
        assertEquals(expected, actual);
    }

    /* Test find where vertex is a child. */
    @Test
    public void test_find2() {
        UnionFind u = new UnionFind(5);
        u.union(1, 0);
        u.union(3, 2);
        u.union(2, 0);
        int expected = 0;
        int actual = u.find(3);
        assertEquals(expected, actual);
    }
}
