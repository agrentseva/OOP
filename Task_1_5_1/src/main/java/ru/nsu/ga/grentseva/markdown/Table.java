package ru.nsu.ga.grentseva.markdown;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

public class Table extends Element {
    public static final int ALIGN_LEFT = -1;
    public static final int ALIGN_RIGHT = 1;

    private final List<List<Element>> rows;
    private final List<Integer> align;

    public Table(List<List<Element>> rows, List<Integer> align) {
        this.rows = rows;
        this.align = align;
    }

    @Override
    public String toMarkdown() {
        if (rows.isEmpty()) return "";

        int columns = rows.get(0).size();
        int[] maxWidth = new int[columns];

        for (var row : rows) {
            for (int c = 0; c < columns; c++) {
                int len = row.get(c).toMarkdown().length();
                maxWidth[c] = Math.max(maxWidth[c], len);
            }
        }

        StringBuilder sb = new StringBuilder();
        BiFunction<String, Integer, String> pad = (text, width) -> {
            int padding = width - text.length();
            if (padding < 0) padding = 0;
            return text + " ".repeat(padding);
        };

        sb.append("| ");
        for (int c = 0; c < columns; c++) {
            sb.append(pad.apply(rows.get(0).get(c).toMarkdown(), maxWidth[c])).append(" | ");
        }
        sb.append("\n");

        sb.append("| ");
        for (int c = 0; c < columns; c++) {
            int a = (align.size() > c) ? align.get(c) : ALIGN_LEFT;
            String alignStr = switch (a) {
                case ALIGN_LEFT -> ":---";
                case ALIGN_RIGHT -> "---:";
                default -> ":---:";
            };
            sb.append(pad.apply(alignStr, maxWidth[c])).append(" | ");
        }
        sb.append("\n");

        for (int r = 1; r < rows.size(); r++) {
            sb.append("| ");
            for (int c = 0; c < columns; c++) {
                sb.append(pad.apply(rows.get(r).get(c).toMarkdown(), maxWidth[c])).append(" | ");
            }
            sb.append("\n");
        }

        return sb.toString();
    }

    public static class Builder {
        private final List<List<Element>> rows = new ArrayList<>();
        private List<Integer> align = new ArrayList<>();
        private int rowLimit = Integer.MAX_VALUE;

        public Builder withAlignments(int... a) {
            align = new ArrayList<>();
            for (int x : a) align.add(x);
            return this;
        }

        public Builder withRowLimit(int limit) {
            this.rowLimit = limit;
            return this;
        }

        public Builder addRow(Object... data) {
            if (rows.size() >= rowLimit) return this;

            List<Element> row = new ArrayList<>();
            for (Object o : data) {
                if (o instanceof Element) row.add((Element) o);
                else row.add(new Text.Plain(o.toString()));
            }
            rows.add(row);
            return this;
        }

        public Table build() {
            return new Table(rows, align);
        }
    }
}
