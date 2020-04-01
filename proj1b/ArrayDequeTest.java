import org.junit.Test;
import static org.junit.Assert.*;

/** Tests the ArrayDeque class. */
public class ArrayDequeTest {
    /** Tests the ArrayDeque.isEmpty method. */
    @Test
    public void testIsEmpty() {
        ArrayDeque<Double> ad = new ArrayDeque<>();
        assertTrue(ad.isEmpty());

        ad.addFirst(1.0);
        assertFalse(ad.isEmpty());

        ad.removeLast();
        assertTrue(ad.isEmpty());
    }

    /** Tests the ArrayDeque.addFirst method. */
    @Test
    public void testAddFirst() {
        ArrayDeque<Double> ad = new ArrayDeque<>();
        ad.addFirst(1.0);
        double actual = ad.get(0);
        assertEquals(1.0, actual, 0.01);

        ad.addFirst(2.0);
        actual = ad.get(0);
        assertEquals(2.0, actual, 0.01);

        actual = ad.get(1);
        assertEquals(1.0, actual, 0.01);
    }

    /** Tests the ArrayDeque.addLast method. */
    @Test
    public void testAddLast() {
        ArrayDeque<Double> ad = new ArrayDeque<>();
        ad.addLast(1.0);
        double actual = ad.get(0);
        assertEquals(1.0, actual, 0.01);

        ad.addLast(2.0);
        actual = ad.get(0);
        assertEquals(1.0, actual, 0.01);

        actual = ad.get(1);
        assertEquals(2.0, actual, 0.01);
    }

    //* Tests the ArrayDeque.get method. */
    @Test
    public void testGet() {
        ArrayDeque<Double> ad = new ArrayDeque<>();
        ad.addFirst(11.0);
        ad.addFirst(10.0);
        ad.addFirst(9.0);
        assertEquals(9.0, ad.get(0), 0.01);
        assertEquals(10.0, ad.get(1), 0.01);
        assertEquals(11.0, ad.get(2), 0.01);
    }

    //* Tests the ArrayDeque.size method. */
    @Test
    public void testSize() {
        ArrayDeque<Double> ad = new ArrayDeque<>();
        assertEquals(0, ad.size());

        ad.addFirst(1.0);
        assertEquals(1, ad.size());

        ad.addLast(2.0);
        assertEquals(2, ad.size());

        ad.addFirst(5.0);
        ad.removeLast();
        ad.removeLast();
        assertEquals(1, ad.size());

        ad.removeLast();
        assertEquals(0, ad.size());
    }

    //* Tests the ArrayDeque.removeFirst method. */
    @Test
    public void testRemoveFirst() {
        ArrayDeque<Double> ad = new ArrayDeque<>();
        ad.addFirst(3.0);
        ad.addFirst(2.0);
        ad.addFirst(1.0);
        double actual = ad.removeFirst();
        assertEquals(1.0, actual, 0.01);
        assertEquals(2.0, ad.get(0), 0.01);
        assertEquals(3.0, ad.get(1), 0.01);
    }


    //* Tests the ArrayDeque.removeLast method. */
    @Test
    public void testRemoveLast() {
        ArrayDeque<Double> ad = new ArrayDeque<>();
        ad.addFirst(3.0);
        ad.addFirst(2.0);
        ad.addFirst(1.0);
        double actual = ad.removeLast();
        assertEquals(3.0, actual, 0.01);
        assertEquals(1.0, ad.get(0), 0.01);
        assertEquals(2.0, ad.get(1), 0.01);
    }

    //* Tests initialization of ArrayDeque that creates a deep copy. */
    @Test
    public void testArrayDequeDeepCopy() {
        ArrayDeque<Double> ad1 = new ArrayDeque<>();
        ad1.addFirst(3.0);
        ad1.addFirst(2.0);
        ad1.addFirst(1.0);
        ArrayDeque<Double> ad2 = new ArrayDeque<>(ad1);
        assertEquals(ad1.get(0), ad2.get(0));
        assertEquals(ad1.get(1), ad2.get(1));
        assertEquals(ad1.get(2), ad2.get(2));
        assertNotEquals(ad1, ad2);
    }

    /** Tests the scenario where a ArrayDeque goes from empty, to some
     *  non-zero size back down to zero again, and then back to some non-zero
     *  size. */
    @Test
    public void testUpDownSize() {
        ArrayDeque<Integer> ad1 = new ArrayDeque<>();
        ad1.addFirst(3);
        ad1.addFirst(2);
        ad1.addFirst(1);
        ad1.removeLast();
        ad1.removeLast();
        ad1.removeLast();
        assertEquals(0, ad1.size());
        ad1.addFirst(10);
        ad1.addLast(11);
        ad1.addLast(12);
        assertEquals(3, ad1.size());
        int first = ad1.get(0);
        int second = ad1.get(1);
        int third = ad1.get(2);
        assertEquals(10, first);
        assertEquals(11, second);
        assertEquals(12, third);
    }

    //* TEST SCENARIOS THAT REQUIRE ARRAY TO WRAP AROUND. */

    /** Tests scenario where last item in Deque is stored in the front of the
     * array. */
    @Test
    public void testAddLastDuringWrapAround() {
        ArrayDeque<Integer> ad1 = new ArrayDeque<>();
        for (int i = 1; i < 7; i++) {
            ad1.addLast(i);
        }
        int actual3 = ad1.get(3);
        assertEquals(4, actual3);
        int actual4 = ad1.get(4);
        assertEquals(5, actual4);
        int actual5 = ad1.get(5);
        assertEquals(6, actual5);
    }

    /** Tests scenario where first item in the Deque is stored at the end of the
     * array. */
    @Test
    public void testAddFirstDuringWrapAround() {
        ArrayDeque<Integer> ad1 = new ArrayDeque<>();
        for (int i = 6; i > 0; i--) {
            ad1.addFirst(i);
        }
        int actual0 = ad1.get(0);
        assertEquals(1, actual0);
        int actual1 = ad1.get(1);
        assertEquals(2, actual1);
        int actual2 = ad1.get(2);
        assertEquals(3, actual2);
    }

    /** Tests scenario where items are removed from the front of the deque after
     * the array has wrapped around. */
    @Test
    public void testRemoveFirstDuringWrapAround() {
        ArrayDeque<Integer> ad1 = new ArrayDeque<>();
        for (int i = 6; i > 0; i--) {
            ad1.addFirst(i);
        }
        int actual = ad1.removeFirst();
        assertEquals(1, actual);
        actual = ad1.removeFirst();
        assertEquals(2, actual);
        actual = ad1.removeFirst();
        assertEquals(3, actual);
    }

    /** Tests scenario where items are removed from the back of the deque after
     * the array has wrapped around. */
    @Test
    public void testRemoveLastDuringWrapAround() {
        ArrayDeque<Integer> ad1 = new ArrayDeque<>();
        for (int i = 1; i < 7; i++) {
            ad1.addLast(i);
        }
        int actual = ad1.removeLast();
        assertEquals(6, actual);
        actual = ad1.removeLast();
        assertEquals(5, actual);
        actual = ad1.removeLast();
        assertEquals(4, actual);
    }

    /** Tests null cases for the get method. */
    @Test
    public void testGetNullCase() {
        ArrayDeque<Integer> ad1 = new ArrayDeque<>();
        assertNull(ad1.get(0));
        for (int i = 1; i < 7; i++) {
            ad1.addLast(i);
        }
        assertNull(ad1.get(7));
        assertNull(ad1.get(-1));
    }

    /** Tests the resize method using addLast and removeLast. */
    @Test
    public void testResizeLast() {
        ArrayDeque<Integer> ad1 = new ArrayDeque<>();
        for (int i = 0; i < 100; i++) {
            ad1.addLast(i);
            int actual = ad1.get(i);
            assertEquals(i, actual);
        }
        assertEquals(100, ad1.size());

        for (int i = 99; i > 49; i--) {
            int actual = ad1.removeLast();
            assertEquals(i, actual);
        }
        assertEquals(50, ad1.size());
    }

    /** Tests the resize method using addFirst and removeFirst. */
    @Test
    public void testResizeFirst() {
        ArrayDeque<Integer> ad1 = new ArrayDeque<>();
        for (int i = 0; i < 100; i++) {
            ad1.addFirst(i);
            int actual = ad1.get(0);
            assertEquals(i, actual);
        }
        assertEquals(100, ad1.size());

        for (int i = 99; i > 49; i--) {
            int actual = ad1.removeFirst();
            assertEquals(i, actual);
        }
        assertEquals(50, ad1.size());
    }

}
