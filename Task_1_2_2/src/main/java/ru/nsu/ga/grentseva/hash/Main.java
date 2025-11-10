package ru.nsu.ga.grentseva.hash;

public class Main {
    public static void main(String[] args) {
        HashTable<String, Number> hashTable = new HashTable <>();
        hashTable.put("one", 1);
        hashTable.update("one", 1.0);
        hashTable.put("six", 3);
        hashTable.put("two", 5);
        System.out.println(hashTable.get("one"));
        System.out.println(hashTable);
    }
}