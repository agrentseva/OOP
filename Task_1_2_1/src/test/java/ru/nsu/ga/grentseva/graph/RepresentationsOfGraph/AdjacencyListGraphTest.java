package ru.nsu.ga.grentseva.graph.RepresentationsOfGraph;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.nsu.ga.grentseva.graph.ReadGraphFromFile.ReadGraph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AdjacencyListGraphTest {
    private AdjacencyListGraph graph;

    @BeforeEach
    void setUp() {
        ReadGraph rg = new ReadGraph();
        rg.n = 3;
        rg.edges = new ArrayList<>();
        rg.edges.add(new int[]{1, 2});
        rg.edges.add(new int[]{2, 3});
        graph = new AdjacencyListGraph(rg);
    }

    @Test
    void testInitialGraphStructure() {
        assertEquals(3, graph.getVertexCount());
        assertTrue(graph.hasEdge(1, 2));
        assertTrue(graph.hasEdge(2, 3));
        assertFalse(graph.hasEdge(1, 3));
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
        assertFalse(graph.hasVertex(3));
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
        assertEquals(1, graph.getNeighbors(1).size());
    }

    @Test
    void testRemoveEdge() {
        assertTrue(graph.hasEdge(1, 2));
        graph.removeEdge(1, 2);
        assertFalse(graph.hasEdge(1, 2));
    }

    @Test
    void testGetEdges() {
        List<int[]> edges = graph.getEdges();
        assertEquals(2, edges.size());
        assertArrayEquals(new int[]{1, 2}, edges.get(0));
        assertArrayEquals(new int[]{2, 3}, edges.get(1));
    }

    @Test
    void testGetNeighbors() {
        ArrayList<Integer> neighbors = graph.getNeighbors(1);
        assertEquals(List.of(2), neighbors);
        assertTrue(graph.getNeighbors(99).isEmpty());
    }

    @Test
    void testHasVertex() {
        assertTrue(graph.hasVertex(1));
        assertFalse(graph.hasVertex(5));
    }

    @Test
    void testToString() {
        String expected = "1: 2 \n2: 3 \n3: \n";
        assertEquals(expected, graph.toString());
    }
}
