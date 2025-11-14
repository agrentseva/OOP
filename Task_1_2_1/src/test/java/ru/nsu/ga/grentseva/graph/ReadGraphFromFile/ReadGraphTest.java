package ru.nsu.ga.grentseva.graph.ReadGraphFromFile;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ReadGraphTest {

    @Test
    void testReadGraphFromFileBasic() throws IOException {
        Path tempFile = Files.createTempFile("graph", ".txt");
        Files.writeString(tempFile, """
                1 2
                2 3
                1 3
                """);

        ReadGraph graph = ReadGraph.readGraphFromFile(tempFile.toString());

        assertEquals(3, graph.n, "Number of vertices should be 3");
        assertEquals(3, graph.edges.size(), "Number of edges should be 3");

        boolean containsEdge1 = graph.edges.stream().anyMatch(e -> e[0] == 1 && e[1] == 2);
        boolean containsEdge2 = graph.edges.stream().anyMatch(e -> e[0] == 2 && e[1] == 3);
        boolean containsEdge3 = graph.edges.stream().anyMatch(e -> e[0] == 1 && e[1] == 3);

        assertTrue(containsEdge1, "Graph should contain edge 1->2");
        assertTrue(containsEdge2, "Graph should contain edge 2->3");
        assertTrue(containsEdge3, "Graph should contain edge 1->3");

        Files.deleteIfExists(tempFile);
    }

    @Test
    void testReadGraphFromFileWithEmptyLines() throws IOException {
        Path tempFile = Files.createTempFile("graph_empty", ".txt");
        Files.writeString(tempFile, """
                1 2

                2 3
                3 1
                """);

        ReadGraph graph = ReadGraph.readGraphFromFile(tempFile.toString());

        assertEquals(3, graph.n, "Number of vertices should be 3");
        assertEquals(3, graph.edges.size(), "Number of edges should be 3");

        Files.deleteIfExists(tempFile);
    }

    @Test
    void testReadGraphFromFileNonexistent() {
        ReadGraph graph = ReadGraph.readGraphFromFile("file_does_not_exist.txt");
        assertNotNull(graph, "Graph object should be created even if file doesn't exist");
        assertEquals(0, graph.n, "Number of vertices should be 0 for nonexistent file");
        assertTrue(graph.edges.isEmpty(), "Edges list should be empty for nonexistent file");
    }

    @Test
    void testReadGraphFromFileSingleEdge() throws IOException {
        Path tempFile = Files.createTempFile("graph_single", ".txt");
        Files.writeString(tempFile, "5 10");

        ReadGraph graph = ReadGraph.readGraphFromFile(tempFile.toString());

        assertEquals(10, graph.n, "Number of vertices should match max vertex index");
        assertEquals(1, graph.edges.size(), "Should have exactly 1 edge");
        assertArrayEquals(new int[]{5, 10}, graph.edges.get(0), "Edge should be 5->10");

        Files.deleteIfExists(tempFile);
    }
}
