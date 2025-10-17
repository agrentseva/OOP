package ru.nsu.ga.grentseva.graph;

import ru.nsu.ga.grentseva.graph.GraphSort.GraphSort;
import ru.nsu.ga.grentseva.graph.ReadGraphFromFile.ReadGraph;
import ru.nsu.ga.grentseva.graph.RepresentationsOfGraph.AdjacencyListGraph;
import ru.nsu.ga.grentseva.graph.RepresentationsOfGraph.AdjacencyMatrixGraph;
import ru.nsu.ga.grentseva.graph.RepresentationsOfGraph.IncidenceMatrixGraph;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        ReadGraph dataGraph = ReadGraph.readGraphFromFile("C:\\Users\\emoch\\OOP\\Task_1_2_1\\src\\main\\java\\ru\\nsu\\ga\\grentseva\\graph\\test");
        
        AdjacencyMatrixGraph graph1 = new AdjacencyMatrixGraph(dataGraph);
        System.out.println(graph1);
        AdjacencyMatrixGraph graph2 = new AdjacencyMatrixGraph(dataGraph);
        if (graph1.equals(graph2)){
            System.out.println("Super");
        }
        System.out.println();

        GraphSort sorter1 = new GraphSort(graph1);
        ArrayList<Integer> res1 = sorter1.topologicalSort();
        for (int vertex : res1) {
            System.out.print(vertex + " ");
        }
        System.out.println();

        IncidenceMatrixGraph graph3 = new IncidenceMatrixGraph(dataGraph);
        System.out.println(graph3);
        if (graph1.equals(graph3)){
            System.out.println("Super");
        }
        System.out.println();

        GraphSort sorter2 = new GraphSort(graph3);
        ArrayList<Integer> res2 = sorter2.topologicalSort();
        for (int vertex : res2) {
            System.out.print(vertex + " ");
        }
        System.out.println();

        AdjacencyListGraph graph4 = new AdjacencyListGraph(dataGraph);
        System.out.println(graph4);
        if (graph1.equals(graph4)){
            System.out.println("Super");
        }
        System.out.println();

        GraphSort sorter3 = new GraphSort(graph4);
        ArrayList<Integer> res3 = sorter3.topologicalSort();
        for (int vertex : res3) {
            System.out.print(vertex + " ");
        }
        System.out.println();

        //graph4.addEdge(6, 2);
        //System.out.println(graph4);
    }
}
