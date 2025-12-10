package ru.nsu.ga.grentseva.markdown;

import java.util.ArrayList;
import java.util.List;

public class ListElement extends Element {
    private final List<Element> items = new ArrayList<>();

    public static class Builder {
        private final ListElement list = new ListElement();
        public Builder add(Element el) { list.items.add(el); return this; }
        public ListElement build() { return list; }
    }

    @Override
    public String toMarkdown() {
        StringBuilder sb = new StringBuilder();
        for (var it : items) {
            sb.append("- ").append(it.toMarkdown()).append("\n");
        }
        return sb.toString().trim();
    }
}
