package ru.nsu.ga.grentseva.markdown;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TaskTest {

    @Test
    public void testTaskNotDone() {
        Task task = new Task(false, new Text.Plain("Write code"));
        assertEquals("- [ ] Write code", task.toMarkdown());
    }

    @Test
    public void testTaskDone() {
        Task task = new Task(true, new Text.Plain("Commit changes"));
        assertEquals("- [x] Commit changes", task.toMarkdown());
    }

    @Test
    public void testTaskWithBoldContent() {
        Task task = new Task(false, new Text.Bold("Important task"));
        assertEquals("- [ ] **Important task**", task.toMarkdown());
    }

    @Test
    public void testTaskWithItalicContent() {
        Task task = new Task(true, new Text.Italic("Optional task"));
        assertEquals("- [x] _Optional task_", task.toMarkdown());
    }

    @Test
    public void testEmptyTask() {
        Task task = new Task(false, new Text.Plain(""));
        assertEquals("- [ ] ", task.toMarkdown());
    }
}
