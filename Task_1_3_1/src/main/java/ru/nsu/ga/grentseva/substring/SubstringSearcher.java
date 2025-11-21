package ru.nsu.ga.grentseva.substring;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SubstringSearcher {

    private static final int BUFFER_SIZE = 8192;

    public List<Long> findOccurrences(String filePath, String substring) throws IOException {
        if (substring == null || substring.isEmpty()) {
            return Collections.emptyList();
        }

        int[] prefixFunction = buildPrefixFunction(substring);
        List<Long> listOfPositions = new ArrayList<>();

        try (Reader reader = new InputStreamReader(
                new FileInputStream(filePath), StandardCharsets.UTF_8)) {

            char[] readBuffer = new char[BUFFER_SIZE];
            StringBuilder leftoverText = new StringBuilder();
            long globalPos = 0;
            int kmpState = 0;

            int read;
            while ((read = reader.read(readBuffer)) != -1) {
                String chunk = new String(readBuffer, 0, read);
                String textToProcess = leftoverText + chunk;
                leftoverText.setLength(0);

                int i = 0;
                while (i < textToProcess.length()) {
                    char c = textToProcess.charAt(i);

                    while (kmpState > 0 && c != substring.charAt(kmpState)) {
                        kmpState = prefixFunction[kmpState - 1];
                    }
                    if (c == substring.charAt(kmpState)) {
                        kmpState++;
                    }

                    if (kmpState == substring.length()) {
                        long matchStart = globalPos + i - substring.length() + 1;
                        listOfPositions.add(matchStart);

                        kmpState = prefixFunction[kmpState - 1];
                    }
                    i++;
                }

                int start = Math.max(0, textToProcess.length() - substring.length() + 1);
                leftoverText.append(textToProcess.substring(start));

                globalPos += chunk.length();
            }
        }

        return listOfPositions;
    }
    
    private int[] buildPrefixFunction(String substring) {
        int n = substring.length();
        int[] pi = new int[n];
        int j = 0;

        for (int i = 1; i < n; i++) {
            while (j > 0 && substring.charAt(i) != substring.charAt(j)) {
                j = pi[j - 1];
            }
            if (substring.charAt(i) == substring.charAt(j)) {
                j++;
            }
            pi[i] = j;
        }
        return pi;
    }
}