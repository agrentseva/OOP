package ru.nsu.ga.grentseva.substring;

import java.io.IOException;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {
        SubstringSearcher searcher = new SubstringSearcher();
        List<Long> foundPositions = searcher.findOccurrences("src/main/resources/input.txt", "бра");
        System.out.println(foundPositions);
    }
}