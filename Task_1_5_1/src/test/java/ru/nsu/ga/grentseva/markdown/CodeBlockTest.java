package ru.nsu.ga.grentseva.markdown;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CodeBlockTest {

    @Test
    public void testSimpleCodeBlock() {
        CodeBlock cb = new CodeBlock("", "System.out.println(\"Hello\");");
        String expected = "```\nSystem.out.println(\"Hello\");\n```";
        assertEquals(expected, cb.toMarkdown());
    }

    @Test
    public void testCodeBlockWithLanguage() {
        CodeBlock cb = new CodeBlock("java", "System.out.println(\"Hello\");");
        String expected = "```java\nSystem.out.println(\"Hello\");\n```";
        assertEquals(expected, cb.toMarkdown());
    }

    @Test
    public void testCodeBlockMultiline() {
        String code = "int a = 5;\nint b = 10;\nSystem.out.println(a + b);";
        CodeBlock cb = new CodeBlock("java", code);
        String expected = "```java\nint a = 5;\nint b = 10;\nSystem.out.println(a + b);\n```";
        assertEquals(expected, cb.toMarkdown());
    }

    @Test
    public void testCodeBlockEmpty() {
        CodeBlock cb = new CodeBlock("python", "");
        String expected = "```python\n\n```";
        assertEquals(expected, cb.toMarkdown());
    }
}
