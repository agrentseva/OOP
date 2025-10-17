package ru.nsu.ga.grentseva.graph.RepresentationsOfGraph;

import ru.nsu.ga.grentseva.graph.GeneralGraphClasses.AbstractGraph;
import ru.nsu.ga.grentseva.graph.ReadGraphFromFile.ReadGraph;

import java.util.ArrayList;
import java.util.List;

public class AdjacencyMatrixGraph extends AbstractGraph {
    private int n;
    private int [][] matrix;

    public AdjacencyMatrixGraph (ReadGraph graph) {
        n = graph.n;
        matrix = new int[n][n];

        for (int[] e : graph.edges) {
            int from = e[0];
            int to = e[1];
            addEdge(from, to);
        }
    }

    public void addVertex() {
        n++;
        int[][] newMatrix = new int[n][n];
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - 1; j++) {
                newMatrix[i][j] = matrix[i][j];
            }
        }
        matrix = newMatrix;
    }

    public void removeVertex(int v) {
        if (!hasVertex(v)) {
            System.err.println("Error: the vertex does not exist.");
            return;
        }

        v--;
        int[][] newMatrix = new int[n - 1][n - 1];
        for (int i = 0, ni = 0; i < n; i++) {
            if (i == v) continue;
            for (int j = 0, nj = 0; j < n; j++) {
                if (j == v) continue;
                newMatrix[ni][nj] = matrix[i][j];
                nj++;
            }
            ni++;
        }

        n--;
        matrix = newMatrix;
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

        from--;
        to--;

        matrix[from][to] = 1;
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

        from--;
        to--;
        matrix[from][to] = 0;
    }

    public boolean hasEdge(int from, int to) {
        if (!hasVertex(from) || !hasVertex(to)) return false;
        return matrix[from - 1][to - 1] != 0;
    }

    public List<int[]> getEdges() {
        List<int[]> edges = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (matrix[i][j] != 0) {
                    edges.add(new int[]{i + 1, j + 1});
                }
            }
        }
        return edges;
    }

    public ArrayList<Integer> getNeighbors(int v) {
        ArrayList<Integer> listOfNeighbors = new ArrayList<>();
        if (!hasVertex(v)) return listOfNeighbors;

        for (int j = 0; j < n; j++) {
            if (matrix[v - 1][j] == 1) {
                listOfNeighbors.add(j + 1);
            }
        }
        return listOfNeighbors;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                sb.append(matrix[i][j]).append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
