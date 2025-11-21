package ru.nsu.ga.grentseva.substring;

import ru.nsu.ga.grentseva.substring.search.SubstringSearcher;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        try {
            Path inputFile = Path.of("input.txt");
            Files.writeString(inputFile, "абракадабра");

            SubstringSearcher searcher = new SubstringSearcher();
            List<Long> positions = searcher.find(inputFile.toString(), "бра");
            System.out.println(positions);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
