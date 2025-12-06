package ru.nsu.ga.grentseva.graph.RepresentationsOfGraph;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.nsu.ga.grentseva.graph.ReadGraphFromFile.ReadGraph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class IncidenceMatrixGraphTest {
    private IncidenceMatrixGraph graph;

    @BeforeEach
    void setUp() {
        ReadGraph rg = new ReadGraph();
        rg.n = 3;
        rg.edges = new ArrayList<>();
        rg.edges.add(new int[]{1, 2});
        rg.edges.add(new int[]{2, 3});
        graph = new IncidenceMatrixGraph(rg);
    }

    @Test
    void testInitialGraphStructure() {
        assertEquals(3, graph.getVertexCount());
        assertTrue(graph.hasEdge(1, 2));
        assertTrue(graph.hasEdge(2, 3));
        assertFalse(graph.hasEdge(1, 3));

        List<int[]> edges = graph.getEdges();
        assertEquals(2, edges.size());
        assertArrayEquals(new int[]{1, 2}, edges.get(0));
        assertArrayEquals(new int[]{2, 3}, edges.get(1));
    }

    @Test
    void testAddVertex() {
        graph.addVertex();
        assertEquals(4, graph.getVertexCount());
        assertTrue(graph.hasVertex(4));
        assertTrue(graph.getNeighbors(4).isEmpty());
    }

    @Test
    void testRemoveVertex() {
        graph.removeVertex(2);
        assertEquals(2, graph.getVertexCount());
        assertFalse(graph.hasEdge(1, 2));
        assertFalse(graph.hasEdge(2, 3));
    }

    @Test
    void testAddEdge() {
        graph.addEdge(1, 3);
        assertTrue(graph.hasEdge(1, 3));
        assertEquals(Arrays.asList(2, 3), graph.getNeighbors(1));
    }

    @Test
    void testAddDuplicateEdge() {
        graph.addEdge(1, 2);
        List<int[]> edges = graph.getEdges();
        long count = edges.stream().filter(e -> e[0] == 1 && e[1] == 2).count();
        assertEquals(1, count);
    }

    @Test
    void testRemoveEdge() {
        assertTrue(graph.hasEdge(1, 2));
        graph.removeEdge(1, 2);
        assertFalse(graph.hasEdge(1, 2));
        graph.removeEdge(1, 2);
        assertFalse(graph.hasEdge(1, 2));
    }

    @Test
    void testGetNeighbors() {
        assertEquals(List.of(2), graph.getNeighbors(1));
        assertEquals(List.of(3), graph.getNeighbors(2));
        assertTrue(graph.getNeighbors(3).isEmpty());
    }

    @Test
    void testHasVertex() {
        assertTrue(graph.hasVertex(1));
        assertTrue(graph.hasVertex(3));
        assertFalse(graph.hasVertex(4));
    }

    @Test
    void testToString() {
        String actual = graph.toString().trim();

        String[] lines = actual.split("\n");
        assertEquals(3, lines.length);
        for (String line : lines) {
            assertTrue(line.trim().split("\\s+").length >= 2);
        }

        assertTrue(actual.contains("-1"));
        assertTrue(actual.contains("1"));
    }
}
