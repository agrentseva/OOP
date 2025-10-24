package ru.nsu.ga.grentseva.graph.RepresentationsOfGraph;

import ru.nsu.ga.grentseva.graph.GeneralGraphClasses.AbstractGraph;
import ru.nsu.ga.grentseva.graph.ReadGraphFromFile.ReadGraph;

import java.util.ArrayList;
import java.util.List;

public class AdjacencyListGraph extends AbstractGraph {
    private int n;
    List<List<Integer>> adjList;

    public AdjacencyListGraph (ReadGraph graph) {
        n = graph.n;
        adjList = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            adjList.add(new ArrayList<>());
        }

        for (int[] e : graph.edges) {
            int from = e[0];
            int to = e[1];
            adjList.get(from - 1).add(to);
        }
    }

    public void addVertex() {
        adjList.add(new ArrayList<>());
        n++;
    }

    public void removeVertex(int v) {
        if (!hasVertex(v)) {
            System.err.println("Error: the vertex does not exist.");
            return;
        }
        adjList.remove(v - 1);

        for (List<Integer> neighbors : adjList) {
            neighbors.removeIf((u) -> u == v);
            for (int i = 0; i < neighbors.size(); i++) {
                if (neighbors.get(i) > v) {
                    neighbors.set(i, neighbors.get(i) - 1);
                }
            }
        }
        n--;
    }

    public boolean hasVertex(int v) {
        return v >= 1 && v <= n;
    }

    public int getVertexCount() {
        return n;
    }

    public void addEdge(int from, int to) {
        if (!hasVertex(from) || !hasVertex(to)) {
            System.err.println("Error: one of the vertices does not exist.");
            return;
        }
        if (hasEdge(from, to)) {
            System.err.println("The edge already exists.");
            return;
        }

        adjList.get(from - 1).add(to);
    }

    public void removeEdge(int from, int to) {
        if (!hasVertex(from) || !hasVertex(to)) {
            System.err.println("Error: one of the vertices does not exist.");
            return;
        }
        if (!hasEdge(from, to)) {
            System.err.println("The edge does not exist.");
            return;
        }

        adjList.get(from - 1).remove((Integer) to);
    }

    public boolean hasEdge(int from, int to) {
        return adjList.get(from - 1).contains(to);
    }

    public List<int[]> getEdges() {
        List<int[]> edges = new ArrayList<>();
        for (int v = 0; v < n; v++) {
            for (int u : adjList.get(v)){
                edges.add(new int[]{v + 1, u});
            }
        }
        return edges;
    }

    public ArrayList<Integer> getNeighbors(int v) {
        ArrayList<Integer> listOfNeighbors = new ArrayList<>();
        if (!hasVertex(v)) return listOfNeighbors;
        return new ArrayList<>(adjList.get(v - 1));
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            sb.append(i + 1).append(": ");
            for (int neighbor : adjList.get(i)) {
                sb.append(neighbor).append(" ");
            }
            sb.append("\n");

        }
        return sb.toString();
    }
}
