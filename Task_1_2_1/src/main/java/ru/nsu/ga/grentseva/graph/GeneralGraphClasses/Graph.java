package ru.nsu.ga.grentseva.graph.GeneralGraphClasses;

import java.util.ArrayList;
import java.util.List;

public interface Graph {

    void addVertex();
    void removeVertex(int v);
    boolean hasVertex(int v);
    int getVertexCount();

    void addEdge(int from, int to);
    void removeEdge(int from, int to);
    List<int[]> getEdges();
    boolean hasEdge(int from, int to);

    ArrayList<Integer> getNeighbors(int v);

    @Override
    String toString();
}
