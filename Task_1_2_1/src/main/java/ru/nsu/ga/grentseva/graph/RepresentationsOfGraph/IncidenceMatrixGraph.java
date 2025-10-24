package ru.nsu.ga.grentseva.graph.RepresentationsOfGraph;

import ru.nsu.ga.grentseva.graph.GeneralGraphClasses.AbstractGraph;
import ru.nsu.ga.grentseva.graph.ReadGraphFromFile.ReadGraph;

import java.util.ArrayList;
import java.util.List;

public class IncidenceMatrixGraph extends AbstractGraph {
    private int n;
    private int m; // edges
    private int [][] matrix;

    public IncidenceMatrixGraph (ReadGraph graph) {
        n = graph.n;
        m = graph.edges.size();
        matrix = new int[n][m];

        for (int[] e : graph.edges) {
            int from = e[0];
            int to = e[1];
            addEdgeInit(from, to, graph.edges.indexOf(e));
        }
    }

    public void addVertex() {
        n++;
        int[][] newMatrix = new int[n][m];
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < m; j++) {
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
        for (int j = 0; j < m; j++) {
            if (matrix[v][j] != 0) {
                removeEdgeByIndex(j);
            }
        }

        int[][] newMatrix = new int[n - 1][m];
        for (int i = 0, ni = 0; i < n; i++) {
            if (i == v) continue;
            for (int j = 0, nj = 0; j < m; j++) {
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

    private void addEdgeInit(int from, int to, int ind){
        from--;
        to--;
        if (from >= 0 && from < n && to >= 0 && to < n) {
            matrix[from][ind] = -1;
            matrix[to][ind] = 1;
        }
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

        m++;
        int[][] newMatrix = new int[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m - 1; j++) {
                newMatrix[i][j] = matrix[i][j];
            }
        }

        from--;
        to--;
        newMatrix[from][m - 1] = -1;
        newMatrix[to][m - 1] = 1;
        matrix = newMatrix;
    }

    private void removeEdgeByIndex(int j) {
        int[][] newMatrix = new int[n][m - 1];
        for (int i = 0, ni = 0; i < n; i++) {
            for (int k = 0, nk = 0; k < m; k++) {
                if (k == j) continue;
                newMatrix[ni][nk] = matrix[i][k];
                nk++;
            }
            ni++;
        }

        m--;
        matrix = newMatrix;
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

        int j = getEdge(from - 1, to - 1);
        removeEdgeByIndex(j);
    }

    private int getEdge(int from, int to) {
        for (int j = 0; j < m; j++) {
            if (matrix[from][j] == -1 && matrix[to][j] == 1) {
                return j;
            }
        }
        System.err.println("The edge does not exist.");
        return -1;
    }

    public boolean hasEdge(int from, int to) {
        if (!hasVertex(from) || !hasVertex(to)) return false;
        return getEdge(from - 1, to - 1) != -1;
    }

    public List<int[]> getEdges() {
        List<int[]> edges = new ArrayList<>();
        for (int j = 0; j < m; j++) {
            int from = -1;
            int to = -1;
            for (int i = 0; i < n; i++) {
                if (matrix[i][j] == -1) {
                    from = i;
                }
                if (matrix[i][j] == 1) {
                    to = i;
                }
            }
            if (from != -1 && to != -1) {
                edges.add(new int[]{from + 1, to + 1});
            }
            else {
                System.err.println("The edge does not exist.");
                return edges;
            }
        }
        return edges;
    }

    public ArrayList<Integer> getNeighbors(int v) {
        ArrayList<Integer> listOfNeighbors = new ArrayList<>();
        if (!hasVertex(v)) return listOfNeighbors;

        for (int j = 0; j < m; j++) {
            if (matrix[v - 1][j] == -1) {
                for (int i = 0; i < n; i++) {
                    if (matrix[i][j] == 1) {
                        listOfNeighbors.add(i + 1);
                    }
                }
            }
        }
        return listOfNeighbors;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (j != m - 1 && matrix[i][j+1] == -1){
                    sb.append(matrix[i][j]).append(" ");
                }
                else {
                    sb.append(matrix[i][j]).append("  ");
                }
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
