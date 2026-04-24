package ru.nsu.ga.grentseva.checker.dsl;

import groovy.lang.Closure;
import groovy.lang.GroovyObjectSupport;
import groovy.lang.Script;
import ru.nsu.ga.grentseva.checker.model.*;

import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public abstract class ConfigScript extends Script {

    private Config config;
    private Path basePath;

    public void setConfig(Config config) {
        this.config = config;
    }

    public void setBasePath(Path basePath) {
        this.basePath = basePath;
    }

    public void include(String fileName) throws IOException {
        System.out.println("Импортируем файл: " + fileName);
        if (basePath != null) {
            Path includedFile = basePath.resolve(fileName).normalize();
            ConfigDslParser.parseFile(includedFile, config);
        } else {
            System.err.println("Не удалось импортировать файл: неизвестна базовая директория.");
        }
    }

    public void tasks(Closure<?> closure) {
        closure.setDelegate(this);
        closure.call();
    }

    public void task(String id, Closure<?> closure) {
        Task task = new Task();
        task.setId(id);

        TaskBuilder builder = new TaskBuilder(task);
        closure.setDelegate(builder);
        closure.setResolveStrategy(Closure.DELEGATE_ONLY);
        closure.call();

        config.getTasks().add(task);
    }

    public void groups(Closure<?> closure) {
        closure.setDelegate(this);
        closure.call();
    }

    public void group(String name, Closure<?> closure) {
        Group group = new Group();
        group.setName(name);

        GroupBuilder builder = new GroupBuilder(group);
        closure.setDelegate(builder);
        closure.setResolveStrategy(Closure.DELEGATE_ONLY);
        closure.call();

        config.getGroups().add(group);
    }

    public void submissions(Closure<?> closure) {
        closure.setDelegate(this);
        closure.call();
    }

    public void submission(String studentId, String taskId, Closure<?> closure) {
        Submission sub = new Submission();
        sub.setStudentId(studentId);
        sub.setTaskId(taskId);

        SubmissionBuilder builder = new SubmissionBuilder(sub);
        closure.setDelegate(builder);
        closure.setResolveStrategy(Closure.DELEGATE_ONLY);
        closure.call();

        config.getSubmissions().add(sub);
    }

    public void checkpoints(Closure<?> closure) {
        closure.setDelegate(this);
        closure.call();
    }

    public void checkpoint(String name, Closure<?> closure) {
        Checkpoint cp = new Checkpoint();
        cp.setName(name);

        CheckpointBuilder builder = new CheckpointBuilder(cp);
        closure.setDelegate(builder);
        closure.setResolveStrategy(Closure.DELEGATE_ONLY);
        closure.call();

        config.getCheckpoints().add(cp);
    }

    public void settings(Closure<?> closure) {
        SettingsBuilder builder = new SettingsBuilder(config.getSettings());
        closure.setDelegate(builder);
        closure.setResolveStrategy(Closure.DELEGATE_ONLY);
        closure.call();
    }

    private static LocalDate parseDate(String dateStr) {
        if (dateStr == null || dateStr.equals("-")) return null;
        if (dateStr.contains("/")) {
            return LocalDate.parse(dateStr, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        }
        return LocalDate.parse(dateStr);
    }

    public static class TaskBuilder extends GroovyObjectSupport {
        private final Task task;
        public TaskBuilder(Task t) { this.task = t; }

        @Override
        public void setProperty(String name, Object value) {
            if (name.equals("name")) task.setName((String) value);
            else if (name.equals("maxScore")) task.setMaxScore(Double.parseDouble(value.toString()));
            else if (name.equals("softDeadline")) task.setSoftDeadline(parseDate(value.toString()));
            else if (name.equals("hardDeadline")) task.setHardDeadline(parseDate(value.toString()));
            else super.setProperty(name, value);
        }
    }

    public static class GroupBuilder extends GroovyObjectSupport {
        private final Group group;
        public GroupBuilder(Group g) { this.group = g; }

        public void student(String githubId, Closure<?> closure) {
            Student student = new Student();
            student.setGithubId(githubId);

            StudentBuilder builder = new StudentBuilder(student);
            closure.setDelegate(builder);
            closure.setResolveStrategy(Closure.DELEGATE_ONLY);
            closure.call();

            group.addStudent(student);
        }
    }

    public static class StudentBuilder extends GroovyObjectSupport {
        private final Student student;
        public StudentBuilder(Student s) { this.student = s; }

        @Override
        public void setProperty(String name, Object value) {
            if (name.equals("name")) student.setFullName((String) value);
            else if (name.equals("repo")) student.setRepoUrl((String) value);
            else super.setProperty(name, value);
        }
    }

    public static class SubmissionBuilder extends GroovyObjectSupport {
        private final Submission sub;
        public SubmissionBuilder(Submission s) { this.sub = s; }

        @Override
        public void setProperty(String name, Object value) {
            if (name.equals("accepted")) sub.setAccepted((Boolean) value);
            else if (name.equals("submitDate")) sub.setSubmitDate(parseDate(value.toString()));
            else if (name.equals("bonus")) sub.setBonus(Double.parseDouble(value.toString()));
            else super.setProperty(name, value);
        }
    }

    public static class CheckpointBuilder extends GroovyObjectSupport {
        private final Checkpoint cp;
        public CheckpointBuilder(Checkpoint c) { this.cp = c; }

        @Override
        public void setProperty(String name, Object value) {
            if (name.equals("date")) cp.setDate(parseDate(value.toString()));
            else super.setProperty(name, value);
        }
    }

    public static class SettingsBuilder extends GroovyObjectSupport {
        private final Settings settings;
        public SettingsBuilder(Settings s) { this.settings = s; }

        @Override
        public void setProperty(String name, Object value) {
            if (name.equals("penalty")) settings.setPenalty(Double.parseDouble(value.toString()));
            else if (name.equals("maxBonus")) settings.setMaxBonus(Double.parseDouble(value.toString()));
            else if (name.equals("testTimeoutSeconds")) settings.setTestTimeoutSeconds(Integer.parseInt(value.toString()));
            else super.setProperty(name, value);
        }
    }
}