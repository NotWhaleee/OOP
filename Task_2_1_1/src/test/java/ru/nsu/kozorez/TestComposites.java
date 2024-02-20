package ru.nsu.kozorez;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import java.util.Arrays;


/**
 * Test parallel processing.
 */
public class TestComposites {
    int[] arr = new int[1000000];

    void setArr() {
        Arrays.fill(arr, 1559861749);
    }

    @Test
    void testSimple() {
        setArr();
        HasComposite simple = new HasComposite();

        assertFalse(simple.hasComposite(arr));
    }

    @Test
    void testStream() {
        setArr();

        HasCompositeStream stream = new HasCompositeStream();
        assertFalse(stream.hasCompositeStream(arr));

    }

    @Test
    void testThread2() throws InterruptedException {
        setArr();

        HasCompositeThread thread = new HasCompositeThread();
        assertFalse(thread.hasCompositeThread(arr, 2));
    }

    @Test
    void testThread4() throws InterruptedException {
        setArr();

        HasCompositeThread thread = new HasCompositeThread();
        assertFalse(thread.hasCompositeThread(arr, 4));

    }

    @Test
    void testThread8() throws InterruptedException {
        setArr();

        HasCompositeThread thread = new HasCompositeThread();
        assertFalse(thread.hasCompositeThread(arr, 8));

    }

    @Test
    void testThread12() throws InterruptedException {
        setArr();

        HasCompositeThread thread = new HasCompositeThread();
        assertFalse(thread.hasCompositeThread(arr, 12));

    }

    @Test
    void testThread100() throws InterruptedException {
        setArr();

        HasCompositeThread thread = new HasCompositeThread();
        assertFalse(thread.hasCompositeThread(arr, 100));

    }

    @Test
    void testSimpleTrue() {
        int[] test = {1, 3, 7, 13, 15};
        HasComposite simple = new HasComposite();
        assertTrue(simple.hasComposite(test));
    }

    @Test
    void testStreamTrue() {
        int[] test = {1, 3, 7, 13, 15};
        HasCompositeStream stream = new HasCompositeStream();
        assertTrue(stream.hasCompositeStream(test));
    }

    @Test
    void test0TreadsException() {
        int[] test = {1, 3, 7, 13, 15};
        HasCompositeThread thread = new HasCompositeThread();

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                thread.hasCompositeThread(test, 0));
        assertEquals("Threads number should be greater than 0", exception.getMessage());

    }

    @Test
    void testTreadsTrue() throws InterruptedException {
        int[] test = {1, 3, 7, 13, 15};
        HasCompositeThread thread = new HasCompositeThread();
        assertTrue(thread.hasCompositeThread(test, 12));
    }
}
