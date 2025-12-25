package ru.nsu.ga.grentseva.markdown;

public class Image extends Element {
    private final String alt;
    private final String src;

    public Image(String alt, String src) {
        this.alt = alt;
        this.src = src;
    }

    @Override
    public String toMarkdown() {
        return "![" + alt + "](" + src + ")";
    }
}
