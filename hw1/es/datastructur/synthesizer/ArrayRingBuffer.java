package es.datastructur.synthesizer;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class ArrayRingBuffer<T> implements BoundedQueue<T> {
    /* Array for storing the buffer data. */
    private final T[] rb;
    /* Index for the next dequeue or peek. */
    private int first;
    /* Index for the next enqueue. */
    private int last;
    /* Variable for the fillCount. */
    private int fillCount;

    /**
     * Create a new ArrayRingBuffer with the given capacity.
     */
    public ArrayRingBuffer(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("You must initialize with capacity > 0");
        }
        this.rb = (T[]) new Object[capacity];
        this.first = 0;
        this.last = 0;
        this.fillCount = 0;
    }

    @Override
    public int capacity() {
        return rb.length;
    }

    @Override
    public int fillCount() {
        return fillCount;
    }

    /**
     * Adds x to the end of the ring buffer. If there is no room, then
     * throw new RuntimeException("Ring buffer overflow").
     */
    @Override
    public void enqueue(T x) {
        if (fillCount() == capacity()) {
            throw new IllegalArgumentException("Cannot enqueue another item because buffer is full");
        }
        rb[last] = x;
        fillCount++;
        last = (last + 1) % capacity();
    }

    /**
     * Dequeue oldest item in the ring buffer. If the buffer is empty, then
     * throw new RuntimeException("Ring buffer underflow").
     */
    @Override
    public T dequeue() {
        if (fillCount() == 0) {
            throw new NoSuchElementException("You cannot dequeue an empty buffer");
        }
        T first_item = rb[first];
        rb[first] = null;
        first = (first + 1) % capacity();
        fillCount--;
        return first_item;
    }

    /**
     * Return oldest item, but don't remove it. If the buffer is empty, then
     * throw new RuntimeException("Ring buffer underflow").
     */
    @Override
    public T peek() {
        if (fillCount() == 0) {
            throw new NoSuchElementException("You cannot peek in an empty buffer");
        }
        return rb[first];
    }

    @Override
    public Iterator<T> iterator() {
        return new ArrayRingBufferIterator();
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        } else if (other.getClass() != ArrayRingBuffer.class) {
            return false;
        } else if (((ArrayRingBuffer) other).fillCount() != this.fillCount()) {
            return false;
        } else if (((ArrayRingBuffer) other).capacity() != this.capacity()) {
            return false;
        } else {
            Iterator<T> thisIterator = this.iterator();
            Iterator<T> otherIterator = ((ArrayRingBuffer) other).iterator();
            while (thisIterator.hasNext() && otherIterator.hasNext()) {
                if (thisIterator.next() != otherIterator.next()) {
                    return false;
                }
            }
            return true;
        }
    }

    private class ArrayRingBufferIterator implements Iterator<T> {
        private int pos;

        public ArrayRingBufferIterator() {
            this.pos = 0;
        }

        public boolean hasNext() {
            return pos < fillCount();
        }

        public T next() {
            T item = rb[(first + pos) % capacity()];
            pos++;
            return item;
        }
    }
}
