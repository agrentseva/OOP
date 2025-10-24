package ru.nsu.ga.grentseva.graph.GeneralGraphClasses;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AbstractGraphTest {

    // Concrete implementation of AbstractGraph for testing
    private static class TestGraph{
        private final List<int[]> edges;
        private final List<Integer> vertices;

        TestGraph(List<int[]> edges) {
            this.edges = new ArrayList<>(edges);
            this.vertices = new ArrayList<>();
            for (int[] edge : edges) {
                if (!vertices.contains(edge[0])) vertices.add(edge[0]);
                if (!vertices.contains(edge[1])) vertices.add(edge[1]);
            }
        }

        public List<int[]> getEdges() {
            return new ArrayList<>(edges); // Return a copy to prevent modification
        }

        public void addVertex() {
            // Minimal implementation: add a new vertex with the next available ID
            int newVertexId = vertices.isEmpty() ? 0 : vertices.stream().max(Integer::compare).get() + 1;
            vertices.add(newVertexId);
        }
    }

    private TestGraph graph1;
    private TestGraph graph2;

    @BeforeEach
    void setUp() {
        // Initialize with empty graphs for each test
        graph1 = new TestGraph(new ArrayList<>());
        graph2 = new TestGraph(new ArrayList<>());
    }

    @Test
    void testEqualsSameInstance() {
        // Test reflexive property: graph equals itself
        assertTrue(graph1.equals(graph1), "Graph should be equal to itself");
    }

    @Test
    void testEqualsNull() {
        // Test comparison with null
        assertFalse(graph1.equals(null), "Graph should not be equal to null");
    }

    @Test
    void testEqualsDifferentType() {
        // Test comparison with a non-Graph object
        Object notAGraph = new Object();
        assertFalse(graph1.equals(notAGraph), "Graph should not be equal to a non-Graph object");
    }

    @Test
    void testEqualsDifferentEdges() {
        // Test graphs with different edges
        graph1 = new TestGraph(List.of(new int[]{0, 1}, new int[]{1, 2}));
        graph2 = new TestGraph(List.of(new int[]{0, 1}, new int[]{2, 3}));
        assertFalse(graph1.equals(graph2), "Graphs with different edges should not be equal");
    }

    @Test
    void testEqualsDifferentEdgeCount() {
        // Test graphs with different number of edges
        graph1 = new TestGraph(List.of(new int[]{0, 1}, new int[]{1, 2}));
        graph2 = new TestGraph(List.of(new int[]{0, 1}));
        assertFalse(graph1.equals(graph2), "Graphs with different edge counts should not be equal");
    }

    @Test
    void testEqualsWithEmptyAndNonEmpty() {
        // Test empty graph vs non-empty graph
        graph1 = new TestGraph(new ArrayList<>());
        graph2 = new TestGraph(List.of(new int[]{0, 1}));
        assertFalse(graph1.equals(graph2), "Empty graph should not equal non-empty graph");
    }
}