import edu.princeton.cs.algs4.Queue;

import org.junit.Test;
import static org.junit.Assert.*;

public class TestSortAlgs {

    @Test
    public void testQuickSort() {

    }

    @Test
    public void testMergeSort1() {
        // Arrange
        Queue<Integer> unsorted = new Queue<Integer>();
        unsorted.enqueue(1);
        unsorted.enqueue(4);
        unsorted.enqueue(7);
        unsorted.enqueue(5);
        unsorted.enqueue(2);
        unsorted.enqueue(10);
        unsorted.enqueue(9);
        unsorted.enqueue(3);
        unsorted.enqueue(6);
        unsorted.enqueue(8);

        // Act
        Queue<Integer> sorted = MergeSort.mergeSort(unsorted);

        // Assert
        for (int expected = 1; expected < 11; expected++) {
            int actual = sorted.dequeue();
            assertEquals(expected, actual);
        }
    }

    @Test
    public void testMergeSort2() {
        // Arrange
        Queue<Integer> unsorted = new Queue<Integer>();
        unsorted.enqueue(9);
        unsorted.enqueue(3);
        unsorted.enqueue(4);
        unsorted.enqueue(7);
        unsorted.enqueue(6);
        unsorted.enqueue(5);
        unsorted.enqueue(8);
        unsorted.enqueue(2);
        unsorted.enqueue(10);
        unsorted.enqueue(1);

        // Act
        Queue<Integer> sorted = MergeSort.mergeSort(unsorted);

        // Assert
        for (int expected = 1; expected < 11; expected++) {
            int actual = sorted.dequeue();
            assertEquals(expected, actual);
        }
    }

    @Test
    public void testMergeSort3() {
        // Arrange
        Queue<Integer> unsorted = new Queue<Integer>();
        unsorted.enqueue(7);
        unsorted.enqueue(1);
        unsorted.enqueue(8);
        unsorted.enqueue(2);
        unsorted.enqueue(5);
        unsorted.enqueue(9);
        unsorted.enqueue(3);
        unsorted.enqueue(10);
        unsorted.enqueue(4);
        unsorted.enqueue(6);

        // Act
        Queue<Integer> sorted = MergeSort.mergeSort(unsorted);

        // Assert
        for (int expected = 1; expected < 11; expected++) {
            int actual = sorted.dequeue();
            assertEquals(expected, actual);
        }
    }

    @Test
    public void testMergeSort4() {
        // Arrange
        Queue<Integer> unsorted = new Queue<Integer>();
        unsorted.enqueue(7);
        unsorted.enqueue(6);
        unsorted.enqueue(2);
        unsorted.enqueue(8);
        unsorted.enqueue(9);
        unsorted.enqueue(10);
        unsorted.enqueue(4);
        unsorted.enqueue(3);
        unsorted.enqueue(1);
        unsorted.enqueue(5);

        // Act
        Queue<Integer> sorted = MergeSort.mergeSort(unsorted);

        // Assert
        for (int expected = 1; expected < 11; expected++) {
            int actual = sorted.dequeue();
            assertEquals(expected, actual);
        }
    }

    @Test
    public void testMergeSort5() {
        // Arrange
        Queue<Integer> unsorted = new Queue<Integer>();
        unsorted.enqueue(8);
        unsorted.enqueue(10);
        unsorted.enqueue(7);
        unsorted.enqueue(3);
        unsorted.enqueue(1);
        unsorted.enqueue(6);
        unsorted.enqueue(5);
        unsorted.enqueue(4);
        unsorted.enqueue(9);
        unsorted.enqueue(2);

        // Act
        Queue<Integer> sorted = MergeSort.mergeSort(unsorted);

        // Assert
        for (int expected = 1; expected < 11; expected++) {
            int actual = sorted.dequeue();
            assertEquals(expected, actual);
        }
    }

    @Test
    public void testMergeSort6() {
        // Arrange
        Queue<Integer> unsorted = new Queue<Integer>();
        unsorted.enqueue(2);
        unsorted.enqueue(5);
        unsorted.enqueue(10);
        unsorted.enqueue(4);
        unsorted.enqueue(8);
        unsorted.enqueue(9);
        unsorted.enqueue(1);
        unsorted.enqueue(3);
        unsorted.enqueue(7);
        unsorted.enqueue(6);

        // Act
        Queue<Integer> sorted = MergeSort.mergeSort(unsorted);

        // Assert
        for (int expected = 1; expected < 11; expected++) {
            int actual = sorted.dequeue();
            assertEquals(expected, actual);
        }
    }

    @Test
    public void testMergeSort7() {
        // Arrange
        Queue<Integer> unsorted = new Queue<Integer>();
        unsorted.enqueue(2);
        unsorted.enqueue(9);
        unsorted.enqueue(6);
        unsorted.enqueue(5);
        unsorted.enqueue(4);
        unsorted.enqueue(1);
        unsorted.enqueue(7);
        unsorted.enqueue(8);
        unsorted.enqueue(10);
        unsorted.enqueue(3);

        // Act
        Queue<Integer> sorted = MergeSort.mergeSort(unsorted);

        // Assert
        for (int expected = 1; expected < 11; expected++) {
            int actual = sorted.dequeue();
            assertEquals(expected, actual);
        }
    }

    @Test
    public void testMergeSort8() {
        // Arrange
        Queue<Integer> unsorted = new Queue<Integer>();
        unsorted.enqueue(8);
        unsorted.enqueue(10);
        unsorted.enqueue(9);
        unsorted.enqueue(3);
        unsorted.enqueue(6);
        unsorted.enqueue(7);
        unsorted.enqueue(5);
        unsorted.enqueue(2);
        unsorted.enqueue(4);
        unsorted.enqueue(1);

        // Act
        Queue<Integer> sorted = MergeSort.mergeSort(unsorted);

        // Assert
        for (int expected = 1; expected < 11; expected++) {
            int actual = sorted.dequeue();
            assertEquals(expected, actual);
        }
    }

    @Test
    public void testQuickSort1() {
        // Arrange
        Queue<Integer> unsorted = new Queue<Integer>();
        unsorted.enqueue(1);
        unsorted.enqueue(4);
        unsorted.enqueue(7);
        unsorted.enqueue(5);
        unsorted.enqueue(2);
        unsorted.enqueue(10);
        unsorted.enqueue(9);
        unsorted.enqueue(3);
        unsorted.enqueue(6);
        unsorted.enqueue(8);

        // Act
        Queue<Integer> sorted = QuickSort.quickSort(unsorted);

        // Assert
        for (int expected = 1; expected < 11; expected++) {
            int actual = sorted.dequeue();
            assertEquals(expected, actual);
        }
    }

    @Test
    public void testQuickSort2() {
        // Arrange
        Queue<Integer> unsorted = new Queue<Integer>();
        unsorted.enqueue(9);
        unsorted.enqueue(3);
        unsorted.enqueue(4);
        unsorted.enqueue(7);
        unsorted.enqueue(6);
        unsorted.enqueue(5);
        unsorted.enqueue(8);
        unsorted.enqueue(2);
        unsorted.enqueue(10);
        unsorted.enqueue(1);

        // Act
        Queue<Integer> sorted = QuickSort.quickSort(unsorted);

        // Assert
        for (int expected = 1; expected < 11; expected++) {
            int actual = sorted.dequeue();
            assertEquals(expected, actual);
        }
    }

    @Test
    public void testQuickSort3() {
        // Arrange
        Queue<Integer> unsorted = new Queue<Integer>();
        unsorted.enqueue(7);
        unsorted.enqueue(1);
        unsorted.enqueue(8);
        unsorted.enqueue(2);
        unsorted.enqueue(5);
        unsorted.enqueue(9);
        unsorted.enqueue(3);
        unsorted.enqueue(10);
        unsorted.enqueue(4);
        unsorted.enqueue(6);

        // Act
        Queue<Integer> sorted = QuickSort.quickSort(unsorted);

        // Assert
        for (int expected = 1; expected < 11; expected++) {
            int actual = sorted.dequeue();
            assertEquals(expected, actual);
        }
    }

    @Test
    public void testQuickSort4() {
        // Arrange
        Queue<Integer> unsorted = new Queue<Integer>();
        unsorted.enqueue(7);
        unsorted.enqueue(6);
        unsorted.enqueue(2);
        unsorted.enqueue(8);
        unsorted.enqueue(9);
        unsorted.enqueue(10);
        unsorted.enqueue(4);
        unsorted.enqueue(3);
        unsorted.enqueue(1);
        unsorted.enqueue(5);

        // Act
        Queue<Integer> sorted = QuickSort.quickSort(unsorted);

        // Assert
        for (int expected = 1; expected < 11; expected++) {
            int actual = sorted.dequeue();
            assertEquals(expected, actual);
        }
    }

    @Test
    public void testQuickSort5() {
        // Arrange
        Queue<Integer> unsorted = new Queue<Integer>();
        unsorted.enqueue(8);
        unsorted.enqueue(10);
        unsorted.enqueue(7);
        unsorted.enqueue(3);
        unsorted.enqueue(1);
        unsorted.enqueue(6);
        unsorted.enqueue(5);
        unsorted.enqueue(4);
        unsorted.enqueue(9);
        unsorted.enqueue(2);

        // Act
        Queue<Integer> sorted = QuickSort.quickSort(unsorted);

        // Assert
        for (int expected = 1; expected < 11; expected++) {
            int actual = sorted.dequeue();
            assertEquals(expected, actual);
        }
    }

    @Test
    public void testQuickSort6() {
        // Arrange
        Queue<Integer> unsorted = new Queue<Integer>();
        unsorted.enqueue(2);
        unsorted.enqueue(5);
        unsorted.enqueue(10);
        unsorted.enqueue(4);
        unsorted.enqueue(8);
        unsorted.enqueue(9);
        unsorted.enqueue(1);
        unsorted.enqueue(3);
        unsorted.enqueue(7);
        unsorted.enqueue(6);

        // Act
        Queue<Integer> sorted = QuickSort.quickSort(unsorted);

        // Assert
        for (int expected = 1; expected < 11; expected++) {
            int actual = sorted.dequeue();
            assertEquals(expected, actual);
        }
    }

    @Test
    public void testQuickSort7() {
        // Arrange
        Queue<Integer> unsorted = new Queue<Integer>();
        unsorted.enqueue(2);
        unsorted.enqueue(9);
        unsorted.enqueue(6);
        unsorted.enqueue(5);
        unsorted.enqueue(4);
        unsorted.enqueue(1);
        unsorted.enqueue(7);
        unsorted.enqueue(8);
        unsorted.enqueue(10);
        unsorted.enqueue(3);

        // Act
        Queue<Integer> sorted = QuickSort.quickSort(unsorted);

        // Assert
        for (int expected = 1; expected < 11; expected++) {
            int actual = sorted.dequeue();
            assertEquals(expected, actual);
        }
    }

    @Test
    public void testQuickSort8() {
        // Arrange
        Queue<Integer> unsorted = new Queue<Integer>();
        unsorted.enqueue(8);
        unsorted.enqueue(10);
        unsorted.enqueue(9);
        unsorted.enqueue(3);
        unsorted.enqueue(6);
        unsorted.enqueue(7);
        unsorted.enqueue(5);
        unsorted.enqueue(2);
        unsorted.enqueue(4);
        unsorted.enqueue(1);

        // Act
        Queue<Integer> sorted = QuickSort.quickSort(unsorted);

        // Assert
        for (int expected = 1; expected < 11; expected++) {
            int actual = sorted.dequeue();
            assertEquals(expected, actual);
        }
    }
    /**
     * Returns whether a Queue is sorted or not.
     *
     * @param items  A Queue of items
     * @return       true/false - whether "items" is sorted
     */
    private <Item extends Comparable> boolean isSorted(Queue<Item> items) {
        if (items.size() <= 1) {
            return true;
        }
        Item curr = items.dequeue();
        Item prev = curr;
        while (!items.isEmpty()) {
            prev = curr;
            curr = items.dequeue();
            if (curr.compareTo(prev) < 0) {
                return false;
            }
        }
        return true;
    }
}
