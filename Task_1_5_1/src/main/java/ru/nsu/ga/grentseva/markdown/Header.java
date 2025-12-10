package ru.nsu.ga.grentseva.markdown;

public class Header extends Element {
    private final int level;
    private final Element content;

    public Header(int level, Element content) {
        this.level = Math.max(1, Math.min(level, 6));
        this.content = content;
    }

    @Override
    public String toMarkdown() {
        return "#".repeat(level) + " " + content.toMarkdown();
    }
}
