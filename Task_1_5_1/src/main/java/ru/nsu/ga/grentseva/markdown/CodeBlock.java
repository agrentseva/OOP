package ru.nsu.ga.grentseva.markdown;

public class CodeBlock extends Element {
    private final String language;
    private final String code;

    public CodeBlock(String language, String code) {
        this.language = language;
        this.code = code;
    }

    @Override
    public String toMarkdown() {
        return "```" + language + "\n" + code + "\n```";
    }
}
