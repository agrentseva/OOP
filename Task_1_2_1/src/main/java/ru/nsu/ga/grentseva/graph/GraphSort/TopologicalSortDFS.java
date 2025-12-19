package ru.nsu.ga.grentseva.graph.GraphSort;

import ru.nsu.ga.grentseva.graph.GeneralGraphClasses.Graph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TopologicalSortDFS implements GraphSorter {

    private boolean[] visited;
    private boolean[] inStack;
    private ArrayList<Integer> sortArray;

    @Override
    public List<Integer> sort(Graph graph) {
        int n = graph.getVertexCount();

        visited = new boolean[n + 1];
        inStack = new boolean[n + 1];
        sortArray = new ArrayList<>();

        for (int vertex = 1; vertex <= n; vertex++) {
            if (!visited[vertex]) {
                dfs(graph, vertex);
            }
        }

        Collections.reverse(sortArray);
        return sortArray;
    }

    private void dfs(Graph graph, int v) {
        visited[v] = true;
        inStack[v] = true;

        for (int neighbor : graph.getNeighbors(v)) {
            if (!visited[neighbor]) {
                dfs(graph, neighbor);
            } else if (inStack[neighbor]) {
                throw new RuntimeException("Error: the graph contains a cycle.");
            }
        }

        inStack[v] = false;
        sortArray.add(v);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int v : sortArray) {
            sb.append(v).append(" ");
        }
        return sb.toString().trim();
    }
}
