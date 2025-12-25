package ru.nsu.ga.grentseva.markdown;

public abstract class Element {
    public abstract String toMarkdown();

    @Override
    public String toString() { return toMarkdown(); }
}
