package ru.nsu.ga.grentseva.substring.search;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SubstringSearcher {

    private static final int BUFFER_SIZE = 8192;

    public List<Long> find(String filePath, String substring) throws IOException {
        if (substring == null || substring.isEmpty()) {
            return Collections.emptyList();
        }

        int[] pattern = substring.codePoints().toArray();
        int[] prefix = buildPrefixFunction(pattern);

        List<Long> positions = new ArrayList<>();

        try (InputStreamReader reader = new InputStreamReader(new FileInputStream(filePath), StandardCharsets.UTF_8)) {
            char[] buffer = new char[BUFFER_SIZE];
            int read;

            int[] leftover = new int[0];
            long globalPos = 0;
            int kmpState = 0;

            while ((read = reader.read(buffer)) != -1) {
                String chunk = new String(buffer, 0, read);
                int[] chunkCodepoints = chunk.codePoints().toArray();

                int[] text = new int[leftover.length + chunkCodepoints.length];
                System.arraycopy(leftover, 0, text, 0, leftover.length);
                System.arraycopy(chunkCodepoints, 0, text, leftover.length, chunkCodepoints.length);

                for (int i = 0; i < text.length; i++) {
                    while (kmpState > 0 && text[i] != pattern[kmpState]) {
                        kmpState = prefix[kmpState - 1];
                    }
                    if (text[i] == pattern[kmpState]) {
                        kmpState++;
                    }
                    if (kmpState == pattern.length) {
                        long start = globalPos + i - leftover.length - pattern.length + 1;
                        positions.add(start);
                        kmpState = prefix[kmpState - 1];
                    }
                }

                int tailLength = Math.min(pattern.length - 1, text.length);
                leftover = new int[tailLength];
                System.arraycopy(text, text.length - tailLength, leftover, 0, tailLength);

                globalPos += chunkCodepoints.length;
            }
        }

        return positions;
    }

    private int[] buildPrefixFunction(int[] p) {
        int n = p.length;
        int[] pi = new int[n];
        int j = 0;

        for (int i = 1; i < n; i++) {
            while (j > 0 && p[i] != p[j]) {
                j = pi[j - 1];
            }
            if (p[i] == p[j]) {
                j++;
            }
            pi[i] = j;
        }
        return pi;
    }
}
