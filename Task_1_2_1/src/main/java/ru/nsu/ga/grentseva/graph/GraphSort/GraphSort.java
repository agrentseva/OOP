package ru.nsu.ga.grentseva.graph.GraphSort;

import ru.nsu.ga.grentseva.graph.GeneralGraphClasses.Graph;

import java.util.ArrayList;
import java.util.Collections;

public class GraphSort {

    private final Graph graph;
    int n;
    private final boolean [] visited;
    private final boolean [] inStack;
    ArrayList<Integer> sortArray;

    public GraphSort (Graph graph) {
        this.graph = graph;
        n = graph.getVertexCount();
        visited = new boolean[n + 1];
        inStack = new boolean[n + 1];
        sortArray = new ArrayList<>();
    }

    public ArrayList<Integer> topologicalSort() {
        for(int vertex = 1; vertex <= n; vertex++) {
            if (!visited[vertex]) {
                dfs(vertex);
            }
        }
        Collections.reverse(sortArray);
        return sortArray;
    }

    private void dfs(int v) {
        visited[v] = true;
        inStack[v] = true;

        for (int neighbor : graph.getNeighbors(v)) {

            if (!visited[neighbor]){
                dfs(neighbor);
            } else if (inStack[neighbor]){
                throw new RuntimeException("Error: the graph contains a cycle.");
            }
        }
        inStack[v] = false;
        sortArray.add(v);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            sb.append(sortArray.get(i));
            if (i < n - 1) {
                sb.append(" ");
            }
        }
        return sb.toString();
    }

}
