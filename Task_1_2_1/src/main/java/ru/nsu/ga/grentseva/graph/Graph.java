package ru.nsu.ga.grentseva.graph;

import java.util.ArrayList;
import java.util.List;

public interface Graph {

    void addVertex();
    void removeVertex(int v);
    public boolean hasVertex(int v);

    void addEdge(int from, int to);
    void removeEdge(int from, int to);
    List<int[]> getEdges();
    boolean hasEdge(int from, int to);

        ArrayList getNeighbors(int v);
    @Override
    String toString();
}
