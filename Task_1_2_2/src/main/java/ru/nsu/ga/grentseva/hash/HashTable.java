package ru.nsu.ga.grentseva.hash;

import java.lang.reflect.Array;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.ConcurrentModificationException;

public class HashTable<K, V> implements Iterable<Node<K, V>> {
    private Node<K, V>[] table;
    private int size;
    private int capacity;
    private int modificationsCount;

    public HashTable() {
        capacity = 11;
        table = (Node<K, V>[]) Array.newInstance(Node.class, capacity);
        size = 0;
        modificationsCount = 0;
    }

    private int hash(K key) {
        return Math.abs(key.hashCode() % capacity);
    }

    public void put(K key, V value) {
        if (size >= capacity) resize();

        int index = hash(key);
        Node<K, V> current = table[index];
        while (current != null) {
            if (current.getKey().equals(key)) {
                current.setValue(value);
                return;
            }
            current = current.getNext();
        }

        Node<K, V> newNode = new Node<>(key, value);
        newNode.setNext(table[index]);
        table[index] = newNode;

        size++;
        modificationsCount++;
    }

    public void update(K key, V value) {
        int index = hash(key);
        Node<K, V> current = table[index];
        while (current != null) {
            if (current.getKey().equals(key)) {
                current.setValue(value);
                modificationsCount++;
                return;
            }
            current = current.getNext();
        }
        throw new NoSuchElementException("Key not found: " + key);
    }

    public V get(K key) {
        int index = hash(key);
        Node<K, V> current = table[index];
        while (current != null) {
            if (current.getKey().equals(key)) return current.getValue();
            current = current.getNext();
        }
        return null;
    }

    public boolean remove(K key) {
        int index = hash(key);
        Node<K, V> current = table[index];
        Node<K, V> prev = null;

        while (current != null) {
            if (current.getKey().equals(key)) {
                if (prev == null) table[index] = current.getNext();
                else prev.setNext(current.getNext());
                size--;
                modificationsCount++;
                return true;
            }
            prev = current;
            current = current.getNext();
        }
        return false;
    }

    public boolean containsKey(K key) {
        return get(key) != null;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof HashTable)) return false;

        HashTable<K, V> other = (HashTable<K, V>) obj;
        if (this.size != other.size) return false;

        for (Node<K, V> chain : table) {
            Node<K, V> node = chain;
            while (node != null) {
                if (!other.containsKey(node.getKey()) ||
                        !other.get(node.getKey()).equals(node.getValue())) {
                    return false;
                }
                node = node.getNext();
            }
        }
        return true;
    }

    private void resize() {
        capacity *= 2;
        Node<K, V>[] oldTable = table;
        table = (Node<K, V>[]) Array.newInstance(Node.class, capacity);
        size = 0;

        for (Node<K, V> chain : oldTable) {
            Node<K, V> node = chain;
            while (node != null) {
                put(node.getKey(), node.getValue());
                node = node.getNext();
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("{");
        boolean first = true;
        for (Node<K, V> chain : table) {
            Node<K, V> node = chain;
            while (node != null) {
                if (!first) sb.append(", ");
                sb.append(node);
                first = false;
                node = node.getNext();
            }
        }
        sb.append("}");
        return sb.toString();
    }

    @Override
    public Iterator<Node<K, V>> iterator() {
        return new Iterator<>() {
            private int chainIndex = 0;
            private Node<K, V> current = null;
            private final int expectedModificationsCount = modificationsCount;

            @Override
            public boolean hasNext() {
                if (expectedModificationsCount != modificationsCount)
                    throw new ConcurrentModificationException();

                if (current != null && current.getNext() != null) return true;

                for (int i = chainIndex; i < capacity; i++)
                    if (table[i] != null) return true;

                return false;
            }

            @Override
            public Node<K, V> next() {
                if (expectedModificationsCount != modificationsCount)
                    throw new ConcurrentModificationException();

                if (current != null && current.getNext() != null) {
                    current = current.getNext();
                    return current;
                }

                while (chainIndex < capacity && table[chainIndex] == null)
                    chainIndex++;

                if (chainIndex >= capacity)
                    throw new NoSuchElementException();

                current = table[chainIndex++];
                return current;
            }
        };
    }
}
