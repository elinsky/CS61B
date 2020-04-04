import org.junit.Test;
import static org.junit.Assert.*;

/** Tests the LinkedListDeque class. */
public class LinkedListDequeTests {
    /** Tests the LinkedListDeque.isEmpty method. */
    @Test
    public void testIsEmpty() {
        LinkedListDeque<Double> lld = new LinkedListDeque<>();
        assertTrue(lld.isEmpty());

        lld.addFirst(1.0);
        assertFalse(lld.isEmpty());

        lld.removeLast();
        assertTrue(lld.isEmpty());
    }

    /** Tests the LinkedListDeque.addFirst method. */
    @Test
    public void testAddFirst() {
        LinkedListDeque<Double> lld = new LinkedListDeque<>();
        lld.addFirst(1.0);
        double actual = lld.get(0);
        assertEquals(1.0, actual, 0.01);

        lld.addFirst(2.0);
        actual = lld.get(0);
        assertEquals(2.0, actual, 0.01);

        actual = lld.get(1);
        assertEquals(1.0, actual, 0.01);
    }

    /** Tests the LinkedListDeque.addLast method. */
    @Test
    public void testAddLast() {
        LinkedListDeque<Double> lld = new LinkedListDeque<>();
        lld.addLast(1.0);
        double actual = lld.get(0);
        assertEquals(1.0, actual, 0.01);

        lld.addLast(2.0);
        actual = lld.get(0);
        assertEquals(1.0, actual, 0.01);

        actual = lld.get(1);
        assertEquals(2.0, actual, 0.01);
    }

    //* Tests the LinkedListDeque.get method. */
    @Test
    public void testGet() {
        LinkedListDeque<Double> lld = new LinkedListDeque<>();
        lld.addFirst(11.0);
        lld.addFirst(10.0);
        lld.addFirst(9.0);
        assertEquals(9.0, lld.get(0), 0.01);
        assertEquals(10.0, lld.get(1), 0.01);
        assertEquals(11.0, lld.get(2), 0.01);
    }

    //* Tests the LinkedListDeque.size method. */
    @Test
    public void testSize() {
        LinkedListDeque<Double> lld = new LinkedListDeque<>();
        assertEquals(0, lld.size());

        lld.addFirst(1.0);
        assertEquals(1, lld.size());

        lld.addLast(2.0);
        assertEquals(2, lld.size());

        lld.addFirst(5.0);
        lld.removeLast();
        lld.removeLast();
        assertEquals(1, lld.size());

        lld.removeLast();
        assertEquals(0, lld.size());
    }

    //* Tests the LinkedListDeque.removeFirst method. */
    @Test
    public void testRemoveFirst() {
        LinkedListDeque<Double> lld = new LinkedListDeque<>();
        lld.addFirst(3.0);
        lld.addFirst(2.0);
        lld.addFirst(1.0);
        double actual = lld.removeFirst();
        assertEquals(1.0, actual, 0.01);
        assertEquals(2.0, lld.get(0), 0.01);
        assertEquals(3.0, lld.get(1), 0.01);
    }


    //* Tests the LinkedListDeque.removeLast method. */
    @Test
    public void testRemoveLast() {
        LinkedListDeque<Double> lld = new LinkedListDeque<>();
        lld.addFirst(3.0);
        lld.addFirst(2.0);
        lld.addFirst(1.0);
        double actual = lld.removeLast();
        assertEquals(3.0, actual, 0.01);
        assertEquals(1.0, lld.get(0), 0.01);
        assertEquals(2.0, lld.get(1), 0.01);
    }

    //* Tests the LinkedListDeque.getRecursive method. */
    @Test
    public void testGetRecursive() {
        LinkedListDeque<Double> lld = new LinkedListDeque<>();
        lld.addFirst(11.0);
        lld.addFirst(10.0);
        lld.addFirst(9.0);
        assertEquals(9.0, lld.getRecursive(0), 0.01);
        assertEquals(10.0, lld.getRecursive(1), 0.01);
        assertEquals(11.0, lld.getRecursive(2), 0.01);
    }

    //* Tests initialization of LinkedListDeque that creates a deep copy. */
    @Test
    public void testLinkedListDequeDeepCopy() {
        LinkedListDeque<Double> lld1 = new LinkedListDeque<>();
        lld1.addFirst(3.0);
        lld1.addFirst(2.0);
        lld1.addFirst(1.0);
        LinkedListDeque<Double> lld2 = new LinkedListDeque<>(lld1);
        assertEquals(lld1.get(0), lld2.get(0));
        assertEquals(lld1.get(1), lld2.get(1));
        assertEquals(lld1.get(2), lld2.get(2));
        assertNotEquals(lld1, lld2);
    }

    /** Tests the scenario where a LinkedListDeque goes from empty, to some
     *  non-zero size back down to zero again, and then back to some non-zero
     *  size. */
    @Test
    public void testUpDownSize() {
        LinkedListDeque<Integer> lld1 = new LinkedListDeque<>();
        lld1.addFirst(3);
        lld1.addFirst(2);
        lld1.addFirst(1);
        lld1.removeLast();
        lld1.removeLast();
        lld1.removeLast();
        assertEquals(0, lld1.size());
        lld1.addFirst(10);
        lld1.addLast(11);
        lld1.addLast(12);
        assertEquals(3, lld1.size());
        int first = lld1.get(0);
        int second = lld1.get(1);
        int third = lld1.get(2);
        assertEquals(10, first);
        assertEquals(11, second);
        assertEquals(12, third);
    }

}
