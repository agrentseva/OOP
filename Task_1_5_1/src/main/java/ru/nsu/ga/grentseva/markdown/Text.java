package ru.nsu.ga.grentseva.markdown;

public class Text {

    public static class Plain extends Element {
        private final String text;
        public Plain(String text) { this.text = text; }
        @Override public String toMarkdown() { return text; }
    }

    public static class Bold extends Element {
        private final String text;
        public Bold(String text) { this.text = text; }
        @Override public String toMarkdown() { return "**" + text + "**"; }
    }

    public static class Italic extends Element {
        private final String text;
        public Italic(String text) { this.text = text; }
        @Override public String toMarkdown() { return "_" + text + "_"; }
    }

    public static class Strike extends Element {
        private final String text;
        public Strike(String text) { this.text = text; }
        @Override public String toMarkdown() { return "~~" + text + "~~"; }
    }

    public static class InlineCode extends Element {
        private final String text;
        public InlineCode(String text) { this.text = text; }
        @Override public String toMarkdown() { return "`" + text + "`"; }
    }
}
