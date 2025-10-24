package ru.nsu.ga.grentseva.graph.GraphSort;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.nsu.ga.grentseva.graph.GeneralGraphClasses.Graph;
import ru.nsu.ga.grentseva.graph.ReadGraphFromFile.ReadGraph;
import ru.nsu.ga.grentseva.graph.RepresentationsOfGraph.AdjacencyListGraph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GraphSortTest {

    private AdjacencyListGraph graph;

    @BeforeEach
    void setUp() {
        ReadGraph rg = new ReadGraph();
        rg.n = 4;
        rg.edges = new ArrayList<>();
        rg.edges.add(new int[]{1, 2});
        rg.edges.add(new int[]{2, 3});
        rg.edges.add(new int[]{1, 4});
        graph = new AdjacencyListGraph(rg);
    }

    @Test
    void testTopologicalSort() {
        GraphSort sorter = new GraphSort(graph);
        ArrayList<Integer> result = sorter.topologicalSort();

        List<List<Integer>> validOrders = List.of(
                Arrays.asList(1, 2, 3, 4),
                Arrays.asList(1, 4, 2, 3)
        );

        assertTrue(validOrders.contains(result));
    }

    @Test
    void testToStringOutput() {
        GraphSort sorter = new GraphSort(graph);
        sorter.topologicalSort();

        String str = sorter.toString();
        assertNotNull(str);
        assertTrue(str.matches("(\\d+\\s*)+"), "Output must contain vertex numbers separated by spaces");
    }

    @Test
    void testCycleDetection() {
        graph.removeEdge(1, 4);
        graph.addEdge(3, 1); // цикл

        GraphSort sorter = new GraphSort(graph);
        RuntimeException ex = assertThrows(RuntimeException.class, sorter::topologicalSort);
        assertEquals("Error: the graph contains a cycle.", ex.getMessage());
    }
}
