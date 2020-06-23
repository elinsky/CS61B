package es.datastructur.synthesizer;

import org.junit.Test;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.junit.Assert.*;

/**
 * Tests the ArrayRingBuffer class.
 *
 * @author Josh Hug
 */

public class TestArrayRingBuffer {
    @Test
    public void WhenRingBufferInitializedWithFiveCapacityShouldReturnFive() {
        // Arrange
        ArrayRingBuffer<Integer> arb = new ArrayRingBuffer<Integer>(5);

        // Act
        int actual = arb.capacity();

        // Assert
        int expected = 5;
        assertEquals(expected, actual);
    }

    @Test(expected = IllegalArgumentException.class)
    public void WhenInitializedShouldThrowException() {
        // Act
        ArrayRingBuffer<Integer> arb = new ArrayRingBuffer<Integer>(0);
    }

    // Test scenario where you add 3 items to buffer, then call fillCount and get 3
    @Test
    public void WhenAddThreeItemsFillCountShouldReturnThree() {
        // Arrange
        ArrayRingBuffer<Integer> arb = new ArrayRingBuffer<Integer>(5);
        arb.enqueue(1);
        arb.enqueue(2);
        arb.enqueue(3);

        // Act
        int actual = arb.fillCount();

        // Assert
        int expected = 3;
        assertEquals(expected, actual);
    }

    // Test scenario where you enqueue and item and dequeue.  Should be same item.
    @Test
    public void WhenEnqueueDequeueShouldReturnSameItem() {
        // Arrange
        ArrayRingBuffer<Integer> arb = new ArrayRingBuffer<Integer>(5);
        arb.enqueue(1);

        // Act
        int actual = arb.dequeue();

        // Assert
        int expected = 1;
        assertEquals(expected, actual);
    }

    // Test scenario where you create buffer, don't add to it.  and verify isEmpty == True
    @Test
    public void WhenBuffferEmptyIsEmptyShouldReturnTrue() {
        // Arrange
        ArrayRingBuffer<Integer> arb = new ArrayRingBuffer<Integer>(5);

        // Act
        boolean actual = arb.isEmpty();

        // Assert
        assertTrue(actual);
    }

    // Test scenario where you create buffer, add to it.  verify isEmpty == False
    @Test
    public void WhenBufferHasItemsIsEmptyShouldReturnFalse() {
        // Arrange
        ArrayRingBuffer<Integer> arb = new ArrayRingBuffer<Integer>(5);
        arb.enqueue(1);
        arb.enqueue(2);
        arb.enqueue(3);

        // Act
        boolean actual = arb.isEmpty();

        // Assert
        assertFalse(actual);
    }

    // Test scenario where create buffer.  Fill it up.  isFull should equal True
    @Test
    public void WhenBufferFullIsFullShouldReturnTrue() {
        // Arrange
        ArrayRingBuffer<Integer> arb = new ArrayRingBuffer<Integer>(3);
        arb.enqueue(1);
        arb.enqueue(2);
        arb.enqueue(3);

        // Act
        boolean actual = arb.isFull();

        // Assert
        assertTrue(actual);
    }

    // Test scenario where you create buffer.  Fill halfway.  isFull should equal False
    @Test
    public void WhenNotFullIsFullShouldReturnFalse() {
        // Arrange
        ArrayRingBuffer<Integer> arb = new ArrayRingBuffer<Integer>(3);
        arb.enqueue(1);
        arb.enqueue(2);

        // Act
        boolean actual = arb.isFull();

        // Assert
        assertFalse(actual);

    }

    // Test scenario where you create buffer.  Fill it up.  Dequeue an item.  IsFull == False
    @Test
    public void PreviouslyFullBufferShouldNotStillBeFull() {
        // Arrange
        ArrayRingBuffer<Integer> arb = new ArrayRingBuffer<Integer>(3);
        arb.enqueue(1);
        arb.enqueue(2);
        arb.enqueue(3);
        arb.dequeue();

        // Act
        boolean actual = arb.isFull();

        // Assert
        assertFalse(actual);
    }

    // Test scenario where you add item to buffer.  Check fill count.  Peek.  fill count should stay the same
    @Test
    public void WhenPeekingFillCountShouldNotChange() {
        // Arrange
        ArrayRingBuffer<Integer> arb = new ArrayRingBuffer<Integer>(3);
        arb.enqueue(1);
        arb.enqueue(2);
        int expected = arb.fillCount();

        // Act
        arb.peek();
        int actual = arb.fillCount();

        // Assert
        assertEquals(expected, actual);
    }

    // Test scenario where you try to dequeue an empty queue.  Should throw an exception
    @Test(expected = NoSuchElementException.class)
    public void WhenDequeueEmptyQueueShouldThrowException() {
        // Arrange
        ArrayRingBuffer<Integer> arb = new ArrayRingBuffer<Integer>(3);

        // Act
        arb.dequeue();
    }

    // Test scenario where you enqueue a full queue.  Should throw an exception.
    @Test(expected = IllegalArgumentException.class)
    public void WhenEnqueueFullQueueShouldThrowException() {
        // Arrange
        ArrayRingBuffer<Integer> arb = new ArrayRingBuffer<Integer>(3);
        arb.enqueue(1);
        arb.enqueue(2);
        arb.enqueue(3);

        // Act
        arb.enqueue(4);
    }

    @Test
    public void WhenIteratingShouldReturnContents() {
        // Arrange
        ArrayRingBuffer<Integer> arb = new ArrayRingBuffer<Integer>(3);
        arb.enqueue(1);
        arb.enqueue(2);
        arb.enqueue(3);
        Iterator<Integer> it = arb.iterator();

        // Act
        int first = it.next();
        int second = it.next();
        int third = it.next();

        // Assert
        assertEquals(first, 1);
        assertEquals(second, 2);
        assertEquals(third, 3);
    }

    @Test
    public void WhenComparingIdenticalBuffersShouldReturnEqual() {
        // Arrange
        ArrayRingBuffer<Integer> arb = new ArrayRingBuffer<Integer>(3);
        arb.enqueue(1);
        arb.enqueue(2);
        arb.enqueue(3);
        ArrayRingBuffer<Integer> arb2 = new ArrayRingBuffer<Integer>(3);
        arb2.enqueue(1);
        arb2.enqueue(2);
        arb2.enqueue(3);

        // Assert
        assertEquals(arb, arb2);
    }

    @Test
    public void WhenComparingDifferentBuffersShouldReturnNotEqual() {
        // Arrange
        ArrayRingBuffer<Integer> arb = new ArrayRingBuffer<Integer>(3);
        arb.enqueue(1);
        arb.enqueue(2);
        arb.enqueue(3);
        ArrayRingBuffer<Integer> arb2 = new ArrayRingBuffer<Integer>(3);
        arb2.enqueue(1);
        arb2.enqueue(2);

        // Assert
        assertNotEquals(arb, arb2);

    }
}
