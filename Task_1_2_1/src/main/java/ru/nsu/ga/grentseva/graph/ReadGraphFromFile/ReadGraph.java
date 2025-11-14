package ru.nsu.ga.grentseva.graph.ReadGraphFromFile;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ReadGraph {
    public int n;
    public List<int[]> edges;

    public static ReadGraph readGraphFromFile(String filename) {
        ReadGraph graph = new ReadGraph();
        graph.edges = new ArrayList<>();
        graph.n = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;

                String[] parts = line.trim().split("\\s+");
                int u = Integer.parseInt(parts[0]);
                int v = Integer.parseInt(parts[1]);
                graph.edges.add(new int[]{u, v});

                graph.n = Math.max(graph.n, Math.max(u, v));
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
            e.printStackTrace();
        }
        return graph;
    }
}
