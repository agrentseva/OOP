package ru.nsu.ga.grentseva.graph.RepresentationsOfGraph;

import org.junit.jupiter.api.Test;
import ru.nsu.ga.grentseva.graph.GraphSort.GraphSort;
import ru.nsu.ga.grentseva.graph.ReadGraphFromFile.ReadGraph;

import java.util.ArrayList;


class AdjacencyMatrixGraphTest {

    @Test
    void test(){
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
    }

}