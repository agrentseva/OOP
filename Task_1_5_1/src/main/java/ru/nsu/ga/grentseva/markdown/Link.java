package ru.nsu.ga.grentseva.markdown;

public class Link extends Element {
    private final String text;
    private final String url;

    public Link(String text, String url) {
        this.text = text;
        this.url = url;
    }

    @Override
    public String toMarkdown() {
        return "[" + text + "](" + url + ")";
    }
}
