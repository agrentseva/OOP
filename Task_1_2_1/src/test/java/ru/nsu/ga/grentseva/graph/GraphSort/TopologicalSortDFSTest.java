package ru.nsu.ga.grentseva.graph.GraphSort;

import org.junit.jupiter.api.Test;
import ru.nsu.ga.grentseva.graph.GeneralGraphClasses.Graph;
import ru.nsu.ga.grentseva.graph.RepresentationsOfGraph.AdjacencyListGraph;
import ru.nsu.ga.grentseva.graph.ReadGraphFromFile.ReadGraph;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TopologicalSortDFSTest {

    @Test
    public void testSimpleDAG() {
        ReadGraph rg = new ReadGraph();
        rg.n = 4;
        rg.edges = List.of(
                new int[]{1, 2},
                new int[]{1, 3},
                new int[]{3, 4}
        );

        Graph graph = new AdjacencyListGraph(rg);
        TopologicalSortDFS sorter = new TopologicalSortDFS();
        List<Integer> sorted = sorter.sort(graph);

        for (int[] edge : rg.edges) {
            int fromIndex = sorted.indexOf(edge[0]);
            int toIndex = sorted.indexOf(edge[1]);
            assertTrue(fromIndex < toIndex, "Edge " + edge[0] + "->" + edge[1] + " violated order");
        }

        String str = sorter.toString();
        for (int v : sorted) {
            assertTrue(str.contains(String.valueOf(v)), "toString missing vertex " + v);
        }
    }

    @Test
    public void testEmptyGraph() {
        ReadGraph rg = new ReadGraph();
        rg.n = 0;
        rg.edges = List.of();

        Graph graph = new AdjacencyListGraph(rg);
        TopologicalSortDFS sorter = new TopologicalSortDFS();
        List<Integer> sorted = sorter.sort(graph);

        assertTrue(sorted.isEmpty());
        assertEquals("", sorter.toString());
    }

    @Test
    public void testSingleVertex() {
        ReadGraph rg = new ReadGraph();
        rg.n = 1;
        rg.edges = List.of();

        Graph graph = new AdjacencyListGraph(rg);
        TopologicalSortDFS sorter = new TopologicalSortDFS();
        List<Integer> sorted = sorter.sort(graph);

        assertEquals(1, sorted.size());
        assertEquals(1, sorted.get(0));
        assertEquals("1", sorter.toString());
    }

    @Test
    public void testGraphWithMultipleBranches() {
        ReadGraph rg = new ReadGraph();
        rg.n = 5;
        rg.edges = List.of(
                new int[]{1, 2},
                new int[]{1, 3},
                new int[]{3, 4},
                new int[]{2, 4},
                new int[]{4, 5}
        );

        Graph graph = new AdjacencyListGraph(rg);
        TopologicalSortDFS sorter = new TopologicalSortDFS();
        List<Integer> sorted = sorter.sort(graph);

        for (int[] edge : rg.edges) {
            int fromIndex = sorted.indexOf(edge[0]);
            int toIndex = sorted.indexOf(edge[1]);
            assertTrue(fromIndex < toIndex, "Edge " + edge[0] + "->" + edge[1] + " violated order");
        }
    }

    @Test
    public void testCycleDetection() {
        ReadGraph rg = new ReadGraph();
        rg.n = 3;
        rg.edges = List.of(
                new int[]{1, 2},
                new int[]{2, 3},
                new int[]{3, 1} // цикл
        );

        Graph graph = new AdjacencyListGraph(rg);
        TopologicalSortDFS sorter = new TopologicalSortDFS();

        RuntimeException exception = assertThrows(RuntimeException.class, () -> sorter.sort(graph));
        assertEquals("Error: the graph contains a cycle.", exception.getMessage());
    }
}
