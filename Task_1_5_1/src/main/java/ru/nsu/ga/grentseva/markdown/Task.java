package ru.nsu.ga.grentseva.markdown;

public class Task extends Element {
    private final boolean done;
    private final Element content;

    public Task(boolean done, Element content) {
        this.done = done;
        this.content = content;
    }

    @Override
    public String toMarkdown() {
        return "- [" + (done ? "x" : " ") + "] " + content.toMarkdown();
    }
}
