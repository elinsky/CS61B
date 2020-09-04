package bearmaps.hw4.project2ab;

import org.junit.Test;

import java.util.NoSuchElementException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ArrayHeapMinPQTest {

    @Test
    public void NewPQSizeShouldReturnZero() {
        // Arrange
        ArrayHeapMinPQ<Integer> pq = new ArrayHeapMinPQ<>();

        // Act
        int actual = pq.size();

        // Assert
        assertEquals(0, actual);
    }

    @Test
    public void PQWithThreeItemsShouldReturnSizeThree() {
        // Arrange
        ArrayHeapMinPQ<Integer> pq = new ArrayHeapMinPQ<>();
        pq.add(5, 1);
        pq.add(6, 2);
        pq.add(7, 3);

        // Act
        int actual = pq.size();

        // Assert
        assertEquals(3, actual);
    }

    @Test
    public void RemovingItemShouldDecrementSize() {
        // Arrange
        ArrayHeapMinPQ<Integer> pq = new ArrayHeapMinPQ<>();
        pq.add(5, 1);
        pq.add(6, 2);
        pq.add(7, 3);
        pq.removeSmallest();

        // Act
        int actual = pq.size();

        // Assert
        assertEquals(2, actual);
    }

    @Test(expected = IllegalArgumentException.class)
    public void AddingExistingItemShouldThrowException() {
        // Arrange
        ArrayHeapMinPQ<Integer> pq = new ArrayHeapMinPQ<>();
        pq.add(5, 1);

        // Act
        pq.add(5, 2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void AddingNullItemShouldThrowException() {
        // Arrange
        ArrayHeapMinPQ<Integer> pq = new ArrayHeapMinPQ<>();

        // Act
        pq.add(null, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ChangingPriorityOfNullItemShouldThrowException() {
        // Arrange
        ArrayHeapMinPQ<Integer> pq = new ArrayHeapMinPQ<>();
        pq.add(1, 0);

        // Act
        pq.changePriority(null, 1);
    }

    @Test(expected = NoSuchElementException.class)
    public void ChangingPriorityMissingItemShouldThrowException() {
        // Arrange
        ArrayHeapMinPQ<Integer> pq = new ArrayHeapMinPQ<>();
        pq.add(1, 0);

        // Act
        pq.changePriority(2, 1);
    }

    @Test(expected = NoSuchElementException.class)
    public void GetSmallestOfEmptyPQShouldThrowException() {
        // Arrange
        ArrayHeapMinPQ<Integer> pq = new ArrayHeapMinPQ<>();

        // Act
        pq.getSmallest();
    }

    @Test(expected = NoSuchElementException.class)
    public void RemoveSmallestOfEmptyPQShouldThrowException() {
        // Arrange
        ArrayHeapMinPQ<Integer> pq = new ArrayHeapMinPQ<>();

        // Act
        pq.removeSmallest();
    }

    @Test
    public void ContainsShouldReturnTrueForItemInPQ() {
        // Arrange
        ArrayHeapMinPQ<Integer> pq = new ArrayHeapMinPQ<>();
        pq.add(5, 1);

        // Act
        boolean actual = pq.contains(5);

        // Assert
        assertTrue(actual);
    }

    @Test
    public void ContainsShouldReturnFalseForItemNotInPQ() {
        // Arrange
        ArrayHeapMinPQ<Integer> pq = new ArrayHeapMinPQ<>();
        pq.add(5, 1);

        // Act
        boolean actual = pq.contains(4);

        // Assert
        assertFalse(actual);
    }

    @Test
    public void ContainsShouldReturnFalseForItemRemovedFromPQ() {
        // Arrange
        ArrayHeapMinPQ<Integer> pq = new ArrayHeapMinPQ<>();
        pq.add(5, 1);
        pq.removeSmallest();

        // Act
        boolean actual = pq.contains(5);

        // Assert
        assertFalse(actual);
    }

    @Test
    public void GetSmallestShouldReturnSmallest() {
        // Arrange
        ArrayHeapMinPQ<Integer> pq = new ArrayHeapMinPQ<>();
        pq.add(7, 1);
        pq.add(6, 2);
        pq.add(5, 3);

        // Act
        Integer actual = pq.getSmallest();

        // Assert
        Integer expected = 7;
        assertEquals(expected, actual);
    }

    @Test
    public void RemoveSmallestShouldReturnSmallest() {
        // Arrange
        ArrayHeapMinPQ<Integer> pq = new ArrayHeapMinPQ<>();
        pq.add(7, 1);
        pq.add(6, 2);
        pq.add(5, 3);

        // Act
        Integer actual = pq.removeSmallest();

        // Assert
        Integer expected = 7;
        assertEquals(expected, actual);
    }

    @Test
    public void RandomOrderInsertShouldntImpactRemoveOrder1() {
        // Arrange
        ArrayHeapMinPQ<Integer> pq = new ArrayHeapMinPQ<>();
        pq.add(3, 3);
        pq.add(2, 2);
        pq.add(4, 4);
        pq.add(0, 0);
        pq.add(5, 5);
        pq.add(1, 1);
        pq.add(6, 6);

        // Act
        Integer actual0 = pq.removeSmallest();
        Integer actual1 = pq.removeSmallest();
        Integer actual2 = pq.removeSmallest();
        Integer actual3 = pq.removeSmallest();
        Integer actual4 = pq.removeSmallest();
        Integer actual5 = pq.removeSmallest();
        Integer actual6 = pq.removeSmallest();

        // Assert
        assertEquals(Integer.valueOf(0), actual0);
        assertEquals(Integer.valueOf(1), actual1);
        assertEquals(Integer.valueOf(2), actual2);
        assertEquals(Integer.valueOf(3), actual3);
        assertEquals(Integer.valueOf(4), actual4);
        assertEquals(Integer.valueOf(5), actual5);
        assertEquals(Integer.valueOf(6), actual6);
    }

    @Test
    public void RandomOrderInsertShouldntImpactRemoveOrder2() {
        // Arrange
        ArrayHeapMinPQ<Integer> pq = new ArrayHeapMinPQ<>();
        pq.add(0, 0);
        pq.add(6, 6);
        pq.add(4, 4);
        pq.add(2, 2);
        pq.add(3, 3);
        pq.add(1, 1);
        pq.add(5, 5);

        // Act
        Integer actual0 = pq.removeSmallest();
        Integer actual1 = pq.removeSmallest();
        Integer actual2 = pq.removeSmallest();
        Integer actual3 = pq.removeSmallest();
        Integer actual4 = pq.removeSmallest();
        Integer actual5 = pq.removeSmallest();
        Integer actual6 = pq.removeSmallest();

        // Assert
        assertEquals(Integer.valueOf(0), actual0);
        assertEquals(Integer.valueOf(1), actual1);
        assertEquals(Integer.valueOf(2), actual2);
        assertEquals(Integer.valueOf(3), actual3);
        assertEquals(Integer.valueOf(4), actual4);
        assertEquals(Integer.valueOf(5), actual5);
        assertEquals(Integer.valueOf(6), actual6);
    }

    @Test
    public void RandomOrderInsertShouldntImpactRemoveOrder3() {
        // Arrange
        ArrayHeapMinPQ<Integer> pq = new ArrayHeapMinPQ<>();
        pq.add(3, 3);
        pq.add(2, 2);
        pq.add(6, 6);
        pq.add(1, 1);
        pq.add(4, 4);
        pq.add(0, 0);
        pq.add(5, 5);

        // Act
        Integer actual0 = pq.removeSmallest();
        Integer actual1 = pq.removeSmallest();
        Integer actual2 = pq.removeSmallest();
        Integer actual3 = pq.removeSmallest();
        Integer actual4 = pq.removeSmallest();
        Integer actual5 = pq.removeSmallest();
        Integer actual6 = pq.removeSmallest();

        // Assert
        assertEquals(Integer.valueOf(0), actual0);
        assertEquals(Integer.valueOf(1), actual1);
        assertEquals(Integer.valueOf(2), actual2);
        assertEquals(Integer.valueOf(3), actual3);
        assertEquals(Integer.valueOf(4), actual4);
        assertEquals(Integer.valueOf(5), actual5);
        assertEquals(Integer.valueOf(6), actual6);
    }

    @Test
    public void RandomOrderInsertShouldntImpactRemoveOrder4() {
        // Arrange
        ArrayHeapMinPQ<Integer> pq = new ArrayHeapMinPQ<>();
        pq.add(6, 6);
        pq.add(2, 2);
        pq.add(0, 0);
        pq.add(4, 4);
        pq.add(1, 1);
        pq.add(5, 5);
        pq.add(3, 3);

        // Act
        Integer actual0 = pq.removeSmallest();
        Integer actual1 = pq.removeSmallest();
        Integer actual2 = pq.removeSmallest();
        Integer actual3 = pq.removeSmallest();
        Integer actual4 = pq.removeSmallest();
        Integer actual5 = pq.removeSmallest();
        Integer actual6 = pq.removeSmallest();

        // Assert
        assertEquals(Integer.valueOf(0), actual0);
        assertEquals(Integer.valueOf(1), actual1);
        assertEquals(Integer.valueOf(2), actual2);
        assertEquals(Integer.valueOf(3), actual3);
        assertEquals(Integer.valueOf(4), actual4);
        assertEquals(Integer.valueOf(5), actual5);
        assertEquals(Integer.valueOf(6), actual6);
    }

    @Test
    public void RandomOrderInsertShouldntImpactRemoveOrder5() {
        // Arrange
        ArrayHeapMinPQ<Integer> pq = new ArrayHeapMinPQ<>();
        pq.add(0, 0);
        pq.add(3, 3);
        pq.add(4, 4);
        pq.add(5, 5);
        pq.add(1, 1);
        pq.add(6, 6);
        pq.add(2, 2);

        // Act
        Integer actual0 = pq.removeSmallest();
        Integer actual1 = pq.removeSmallest();
        Integer actual2 = pq.removeSmallest();
        Integer actual3 = pq.removeSmallest();
        Integer actual4 = pq.removeSmallest();
        Integer actual5 = pq.removeSmallest();
        Integer actual6 = pq.removeSmallest();

        // Assert
        assertEquals(Integer.valueOf(0), actual0);
        assertEquals(Integer.valueOf(1), actual1);
        assertEquals(Integer.valueOf(2), actual2);
        assertEquals(Integer.valueOf(3), actual3);
        assertEquals(Integer.valueOf(4), actual4);
        assertEquals(Integer.valueOf(5), actual5);
        assertEquals(Integer.valueOf(6), actual6);
    }

    @Test
    public void RandomOrderInsertShouldntImpactRemoveOrder6() {
        // Arrange
        ArrayHeapMinPQ<Integer> pq = new ArrayHeapMinPQ<>();
        pq.add(3, 3);
        pq.add(2, 2);
        pq.add(6, 6);
        pq.add(5, 5);
        pq.add(1, 1);
        pq.add(0, 0);
        pq.add(4, 4);

        // Act
        Integer actual0 = pq.removeSmallest();
        Integer actual1 = pq.removeSmallest();
        Integer actual2 = pq.removeSmallest();
        Integer actual3 = pq.removeSmallest();
        Integer actual4 = pq.removeSmallest();
        Integer actual5 = pq.removeSmallest();
        Integer actual6 = pq.removeSmallest();

        // Assert
        assertEquals(Integer.valueOf(0), actual0);
        assertEquals(Integer.valueOf(1), actual1);
        assertEquals(Integer.valueOf(2), actual2);
        assertEquals(Integer.valueOf(3), actual3);
        assertEquals(Integer.valueOf(4), actual4);
        assertEquals(Integer.valueOf(5), actual5);
        assertEquals(Integer.valueOf(6), actual6);
    }

    @Test
    public void ChangePriorityShouldReturnIntegersInOrder1() {
        // Arrange
        ArrayHeapMinPQ<Integer> pq = new ArrayHeapMinPQ<>();
        pq.add(1, 0);
        pq.add(4, 1);
        pq.add(3, 2);
        pq.add(2, 3);
        pq.add(5, 4);
        pq.add(0, 5);
        pq.add(6, 6);

        // Act
        pq.changePriority(0, 0);
        pq.changePriority(1, 1);
        pq.changePriority(2, 2);
        pq.changePriority(3, 3);
        pq.changePriority(4, 4);
        pq.changePriority(5, 5);
        pq.changePriority(6, 6);


        // Assert
        Integer actual0 = pq.removeSmallest();
        Integer actual1 = pq.removeSmallest();
        Integer actual2 = pq.removeSmallest();
        Integer actual3 = pq.removeSmallest();
        Integer actual4 = pq.removeSmallest();
        Integer actual5 = pq.removeSmallest();
        Integer actual6 = pq.removeSmallest();

        assertEquals(Integer.valueOf(0), actual0);
        assertEquals(Integer.valueOf(1), actual1);
        assertEquals(Integer.valueOf(2), actual2);
        assertEquals(Integer.valueOf(3), actual3);
        assertEquals(Integer.valueOf(4), actual4);
        assertEquals(Integer.valueOf(5), actual5);
        assertEquals(Integer.valueOf(6), actual6);
    }

    @Test
    public void ChangePriorityShouldReturnIntegersInOrder2() {
        // Arrange
        ArrayHeapMinPQ<Integer> pq = new ArrayHeapMinPQ<>();
        pq.add(1, 0);
        pq.add(3, 1);
        pq.add(4, 2);
        pq.add(0, 3);
        pq.add(5, 4);
        pq.add(2, 5);
        pq.add(6, 6);

        // Act
        pq.changePriority(0, 0);
        pq.changePriority(1, 1);
        pq.changePriority(2, 2);
        pq.changePriority(3, 3);
        pq.changePriority(4, 4);
        pq.changePriority(5, 5);
        pq.changePriority(6, 6);


        // Assert
        Integer actual0 = pq.removeSmallest();
        Integer actual1 = pq.removeSmallest();
        Integer actual2 = pq.removeSmallest();
        Integer actual3 = pq.removeSmallest();
        Integer actual4 = pq.removeSmallest();
        Integer actual5 = pq.removeSmallest();
        Integer actual6 = pq.removeSmallest();

        assertEquals(Integer.valueOf(0), actual0);
        assertEquals(Integer.valueOf(1), actual1);
        assertEquals(Integer.valueOf(2), actual2);
        assertEquals(Integer.valueOf(3), actual3);
        assertEquals(Integer.valueOf(4), actual4);
        assertEquals(Integer.valueOf(5), actual5);
        assertEquals(Integer.valueOf(6), actual6);
    }

    @Test
    public void ChangePriorityShouldReturnIntegersInOrder3() {
        // Arrange
        ArrayHeapMinPQ<Integer> pq = new ArrayHeapMinPQ<>();
        pq.add(5, 0);
        pq.add(4, 1);
        pq.add(2, 2);
        pq.add(3, 3);
        pq.add(1, 4);
        pq.add(6, 5);
        pq.add(0, 6);

        // Act
        pq.changePriority(0, 0);
        pq.changePriority(1, 1);
        pq.changePriority(2, 2);
        pq.changePriority(3, 3);
        pq.changePriority(4, 4);
        pq.changePriority(5, 5);
        pq.changePriority(6, 6);


        // Assert
        Integer actual0 = pq.removeSmallest();
        Integer actual1 = pq.removeSmallest();
        Integer actual2 = pq.removeSmallest();
        Integer actual3 = pq.removeSmallest();
        Integer actual4 = pq.removeSmallest();
        Integer actual5 = pq.removeSmallest();
        Integer actual6 = pq.removeSmallest();

        assertEquals(Integer.valueOf(0), actual0);
        assertEquals(Integer.valueOf(1), actual1);
        assertEquals(Integer.valueOf(2), actual2);
        assertEquals(Integer.valueOf(3), actual3);
        assertEquals(Integer.valueOf(4), actual4);
        assertEquals(Integer.valueOf(5), actual5);
        assertEquals(Integer.valueOf(6), actual6);
    }

    @Test
    public void ChangePriorityShouldReturnIntegersInOrder4() {
        // Arrange
        ArrayHeapMinPQ<Integer> pq = new ArrayHeapMinPQ<>();
        pq.add(2, 0);
        pq.add(6, 1);
        pq.add(0, 2);
        pq.add(4, 3);
        pq.add(5, 4);
        pq.add(1, 5);
        pq.add(3, 6);

        // Act
        pq.changePriority(0, 0);
        pq.changePriority(1, 1);
        pq.changePriority(2, 2);
        pq.changePriority(3, 3);
        pq.changePriority(4, 4);
        pq.changePriority(5, 5);
        pq.changePriority(6, 6);


        // Assert
        Integer actual0 = pq.removeSmallest();
        Integer actual1 = pq.removeSmallest();
        Integer actual2 = pq.removeSmallest();
        Integer actual3 = pq.removeSmallest();
        Integer actual4 = pq.removeSmallest();
        Integer actual5 = pq.removeSmallest();
        Integer actual6 = pq.removeSmallest();

        assertEquals(Integer.valueOf(0), actual0);
        assertEquals(Integer.valueOf(1), actual1);
        assertEquals(Integer.valueOf(2), actual2);
        assertEquals(Integer.valueOf(3), actual3);
        assertEquals(Integer.valueOf(4), actual4);
        assertEquals(Integer.valueOf(5), actual5);
        assertEquals(Integer.valueOf(6), actual6);
    }

    @Test
    public void ChangePriorityShouldReturnIntegersInOrder5() {
        // Arrange
        ArrayHeapMinPQ<Integer> pq = new ArrayHeapMinPQ<>();
        pq.add(2, 0);
        pq.add(1, 1);
        pq.add(5, 2);
        pq.add(3, 3);
        pq.add(0, 4);
        pq.add(6, 5);
        pq.add(4, 6);

        // Act
        pq.changePriority(0, 0);
        pq.changePriority(1, 1);
        pq.changePriority(2, 2);
        pq.changePriority(3, 3);
        pq.changePriority(4, 4);
        pq.changePriority(5, 5);
        pq.changePriority(6, 6);


        // Assert
        Integer actual0 = pq.removeSmallest();
        Integer actual1 = pq.removeSmallest();
        Integer actual2 = pq.removeSmallest();
        Integer actual3 = pq.removeSmallest();
        Integer actual4 = pq.removeSmallest();
        Integer actual5 = pq.removeSmallest();
        Integer actual6 = pq.removeSmallest();

        assertEquals(Integer.valueOf(0), actual0);
        assertEquals(Integer.valueOf(1), actual1);
        assertEquals(Integer.valueOf(2), actual2);
        assertEquals(Integer.valueOf(3), actual3);
        assertEquals(Integer.valueOf(4), actual4);
        assertEquals(Integer.valueOf(5), actual5);
        assertEquals(Integer.valueOf(6), actual6);
    }

    @Test
    public void ResizingShouldWork() {
        // Arrange
        ArrayHeapMinPQ<String> pq = new ArrayHeapMinPQ<>();

        for (int i = 1; i < 455; i++) {
            pq.add("hi" + i, i);
            assertTrue(pq.contains("hi" + i));
        }

        for (int i = 1; i < 455; i++) {
            String removed = pq.removeSmallest();
            assertEquals("hi" + i, removed);

        }
    }
}
