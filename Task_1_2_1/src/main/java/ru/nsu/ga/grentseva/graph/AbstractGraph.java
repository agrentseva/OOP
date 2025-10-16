package ru.nsu.ga.grentseva.graph;

import java.util.List;

public abstract class AbstractGraph implements Graph {

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Graph)) return false;

        Graph other = (Graph) obj;
        List<int[]> edges1 = this.getEdges();
        List<int[]> edges2 = other.getEdges();

        if (edges1.size() != edges2.size()) return false;

        for (int[] e : edges1) {
            boolean found = false;
            for (int[] f : edges2) {
                if (e[0] == f[0] && e[1] == f[1]) {
                    found = true;
                    break;
                }
            }
            if (!found) return false;
        }
        return true;
    }
}

