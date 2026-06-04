package ru.nsu.ga.grentseva.checker.service.parser;

import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilderFactory;

import java.io.File;

public class TestResultParser {

    public int[] parse(File directory) {
        File resultsFolder = new File(directory, "build/test-results/test");
        if (!resultsFolder.exists()) {
            return new int[]{0, 0, 0};
        }

        int passed = 0;
        int failed = 0;
        int skipped = 0;

        File[] files = resultsFolder.listFiles((dir, name) -> name.endsWith(".xml"));
        if (files == null) {
            return new int[]{0, 0, 0};
        }

        for (File file : files) {
            try {
                Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(file);

                var root = document.getDocumentElement();

                int tests = Integer.parseInt(root.getAttribute("tests"));
                int failures = Integer.parseInt(root.getAttribute("failures"));
                int skippedTests = Integer.parseInt(root.getAttribute("skipped"));

                passed += tests - failures - skippedTests;
                failed += failures;
                skipped += skippedTests;

            } catch (Exception ignored) {
            }
        }

        return new int[]{passed, failed, skipped};
    }
}