package ru.nsu.ga.grentseva.graph;


public class Main {
    public static void main(String[] args) {
        ReadGraph dataGraph = ReadGraph.readGraphFromFile("C:\\Users\\emoch\\OOP\\Task_1_2_1\\src\\main\\java\\ru\\nsu\\ga\\grentseva\\graph\\test");
        AdjacencyMatrixGraph graph = new AdjacencyMatrixGraph(dataGraph);
        System.out.println(graph);

        AdjacencyMatrixGraph graph2 = new AdjacencyMatrixGraph(dataGraph);

        if (graph.equals(graph2)){
            System.out.println("Super");
        }

        // нужно еще проверки на то есть ли ребро есть ли вершина в add и remove
        graph.addEdge(1, 5);
        System.out.println(graph);
        graph2.addEdge(3, 8);


    }
}
