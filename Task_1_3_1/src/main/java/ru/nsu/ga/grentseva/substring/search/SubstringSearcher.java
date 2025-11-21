package ru.nsu.ga.grentseva.substring.search;

import ru.nsu.ga.grentseva.substring.auxiliaryArray.IntArrayList;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SubstringSearcher {

    public List<Long> find(String filePath, String substring) throws IOException {
        if (substring == null || substring.isEmpty()) {
            return Collections.emptyList();
        }

        int[] pattern = substring.codePoints().toArray();
        int[] prefix = buildPrefixFunction(pattern);

        List<Long> positions = new ArrayList<>();
        IntArrayList leftover = new IntArrayList();
        long globalPos = 0;
        int kmpState = 0;

        try (InputStreamReader reader = new InputStreamReader(new FileInputStream(filePath), StandardCharsets.UTF_8)) {
            char[] buffer = new char[8192];
            int read;

            while ((read = reader.read(buffer)) != -1) {
                int[] chunkCodepoints = new String(buffer, 0, read).codePoints().toArray();
                int[] text = concat(leftover, chunkCodepoints);
                leftover.clear();

                for (int i = 0; i < text.length; i++) {
                    while (kmpState > 0 && text[i] != pattern[kmpState]) {
                        kmpState = prefix[kmpState - 1];
                    }
                    if (text[i] == pattern[kmpState]) {
                        kmpState++;
                    }
                    if (kmpState == pattern.length) {
                        long start = globalPos + i - pattern.length + 1;
                        positions.add(start);
                        kmpState = prefix[kmpState - 1];
                    }
                }

                int tailStart = Math.max(0, text.length - pattern.length + 1);
                for (int i = tailStart; i < text.length; i++) {
                    leftover.add(text[i]);
                }

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

    private int[] concat(IntArrayList leftover, int[] chunk) {
        int[] res = new int[leftover.size() + chunk.length];
        for (int i = 0; i < leftover.size(); i++) {
            res[i] = leftover.get(i);
        }
        System.arraycopy(chunk, 0, res, leftover.size(), chunk.length);
        return res;
    }

}
