package ru.nsu.ga.grentseva.hash;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NodeTest {

    @Test
    void testGettersAndSetters() {
        Node<String, Integer> node = new Node<>("key", 10);

        assertEquals("key", node.getKey());
        assertEquals(10, node.getValue());
        assertNull(node.getNext());

        node.setValue(20);
        assertEquals(20, node.getValue());

        Node<String, Integer> nextNode = new Node<>("next", 30);
        node.setNext(nextNode);
        assertEquals(nextNode, node.getNext());
    }

    @Test
    void testEquals() {
        Node<String, Integer> node1 = new Node<>("key", 10);
        Node<String, Integer> node2 = new Node<>("key", 10);
        Node<String, Integer> node3 = new Node<>("key", 20);
        Node<String, Integer> node4 = new Node<>("other", 10);

        assertEquals(node1, node2);
        assertNotEquals(node1, node3);
        assertNotEquals(node1, node4);
        assertNotEquals(node1, null);
        assertNotEquals(node1, "some string");
    }

    @Test
    void testToString() {
        Node<String, Integer> node = new Node<>("key", 10);
        String str = node.toString();
        assertTrue(str.contains("key"));
        assertTrue(str.contains("10"));
        assertEquals("{key: 10}", str);
    }

    @Test
    void testChaining() {
        Node<String, Integer> first = new Node<>("first", 1);
        Node<String, Integer> second = new Node<>("second", 2);
        Node<String, Integer> third = new Node<>("third", 3);

        first.setNext(second);
        second.setNext(third);

        assertEquals(second, first.getNext());
        assertEquals(third, first.getNext().getNext());
        assertNull(third.getNext());
    }
}
