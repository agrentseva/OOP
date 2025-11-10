package ru.nsu.ga.grentseva.hash;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class HashTableTest {

    private HashTable<String, Number> table;

    @BeforeEach
    void setUp() {
        table = new HashTable<>();
    }

    @Test
    void testPutAndGet() {
        table.put("one", 1);
        table.put("two", 2);
        assertEquals(1, table.get("one"));
        assertEquals(2, table.get("two"));
        assertNull(table.get("three"));
    }

    @Test
    void testUpdate() {
        table.put("one", 1);
        table.update("one", 1.5);
        assertEquals(1.5, table.get("one"));

        assertThrows(NoSuchElementException.class, () -> table.update("two", 2));
    }

    @Test
    void testRemove() {
        table.put("one", 1);
        table.put("two", 2);

        assertTrue(table.remove("one"));
        assertNull(table.get("one"));
        assertFalse(table.remove("three"));
    }

    @Test
    void testContainsKey() {
        table.put("one", 1);
        assertTrue(table.containsKey("one"));
        assertFalse(table.containsKey("two"));
    }

    @Test
    void testEquals() {
        HashTable<String, Number> t1 = new HashTable<>();
        HashTable<String, Number> t2 = new HashTable<>();

        t1.put("one", 1);
        t1.put("two", 2);
        t2.put("two", 2);
        t2.put("one", 1);

        assertEquals(t1, t2);

        t2.put("three", 3);
        assertNotEquals(t1, t2);
    }

    @Test
    void testToString() {
        table.put("one", 1);
        table.put("two", 2);
        String s = table.toString();
        assertTrue(s.contains("one"));
        assertTrue(s.contains("two"));
        assertTrue(s.contains("1"));
        assertTrue(s.contains("2"));
    }

    @Test
    void testIterator() {
        table.put("one", 1);
        table.put("two", 2);
        table.put("three", 3);

        int count = 0;
        for (Node<String, Number> node : table) {
            assertNotNull(node.getKey());
            assertNotNull(node.getValue());
            count++;
        }
        assertEquals(3, count);
    }

    @Test
    void testIteratorConcurrentModification() {
        table.put("one", 1);
        table.put("two", 2);

        Iterator<Node<String, Number>> it = table.iterator();
        table.put("three", 3);
        assertThrows(ConcurrentModificationException.class, it::hasNext);
        assertThrows(ConcurrentModificationException.class, it::next);
    }

    @Test
    void testResize() {
        for (int i = 0; i < 20; i++) {
            table.put("key" + i, i);
        }

        for (int i = 0; i < 20; i++) {
            assertEquals(i, table.get("key" + i));
        }
    }
}
