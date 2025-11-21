package ru.nsu.ga.grentseva.substring.auxiliaryArray;

import java.util.ArrayList;
import java.util.List;

public class IntArrayList {
    private final List<Integer> list = new ArrayList<>();

    public void add(int x) { list.add(x); }
    public int size() { return list.size(); }
    public int get(int i) { return list.get(i); }
    public void clear() { list.clear(); }
}
