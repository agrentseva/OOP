package ru.nsu.ga.grentseva.graph;

import ru.nsu.ga.grentseva.graph.GraphSort.TopologicalSortDFS;
import ru.nsu.ga.grentseva.graph.ReadGraphFromFile.ReadGraph;
import ru.nsu.ga.grentseva.graph.RepresentationsOfGraph.AdjacencyListGraph;
import ru.nsu.ga.grentseva.graph.RepresentationsOfGraph.AdjacencyMatrixGraph;
import ru.nsu.ga.grentseva.graph.RepresentationsOfGraph.IncidenceMatrixGraph;

public class Main {

    public static void main(String[] args) {

        ReadGraph dataGraph = ReadGraph.readGraphFromFile(
                "C:\\Users\\emoch\\OOP\\Task_1_2_1\\src\\main\\java\\ru\\nsu\\ga\\grentseva\\graph\\test"
        );

        AdjacencyMatrixGraph graph1 = new AdjacencyMatrixGraph(dataGraph);
        System.out.println(graph1);

        AdjacencyMatrixGraph graph2 = new AdjacencyMatrixGraph(dataGraph);
        if (graph1.equals(graph2)) {
            System.out.println("Super");
        }
        System.out.println();

        TopologicalSortDFS sorter = new TopologicalSortDFS();
        sorter.sort(graph1);
        System.out.println("Topological DFS (AdjacencyMatrix): " + sorter);
        System.out.println();


        IncidenceMatrixGraph graph3 = new IncidenceMatrixGraph(dataGraph);
        System.out.println(graph3);

        if (graph1.equals(graph3)) {
            System.out.println("Super");
        }
        System.out.println();

        sorter.sort(graph3);
        System.out.println("Topological DFS (IncidenceMatrix): " + sorter);
        System.out.println();


        AdjacencyListGraph graph4 = new AdjacencyListGraph(dataGraph);
        System.out.println(graph4);

        if (graph1.equals(graph4)) {
            System.out.println("Super");
        }
        System.out.println();

        sorter.sort(graph4);
        System.out.println("Topological DFS (AdjacencyList): " + sorter);
        System.out.println();
    }
}
