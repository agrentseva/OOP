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

    private CourseConfig config;
    private Path basePath;

    public void setConfig(CourseConfig config) {
        this.config = config;
    }

    public void setBasePath(Path basePath) {
        this.basePath = basePath;
    }

    public void include(String fileName) throws IOException {
        if (basePath == null) {
            throw new IOException("Базовая директория не задана");
        }

        Path includedFile = basePath.resolve(fileName).normalize();
        ConfigDslParser.loadFile(includedFile, config);
    }

    public void tasks(Closure<?> closure) {
        runClosure(closure, this);
    }

    public void task(String id, Closure<?> closure) {
        Task task = new Task();
        task.setId(id);

        TaskBuilder builder = new TaskBuilder(task);
        runClosure(closure, builder);

        config.addTask(task);
    }

    public void groups(Closure<?> closure) {
        runClosure(closure, this);
    }

    public void group(String name, Closure<?> closure) {
        Group group = new Group();
        group.setName(name);

        GroupBuilder builder = new GroupBuilder(group);
        runClosure(closure, builder);

        config.addGroup(group);
    }

    public void submissions(Closure<?> closure) {
        runClosure(closure, this);
    }

    public void submission(String studentId, String taskId, Closure<?> closure) {
        Submission submission = new Submission();
        submission.setStudentId(studentId);
        submission.setTaskId(taskId);

        SubmissionBuilder builder = new SubmissionBuilder(submission);
        runClosure(closure, builder);

        config.addSubmission(submission);
    }

    public void checkpoints(Closure<?> closure) {
        runClosure(closure, this);
    }

    public void checkpoint(String name, Closure<?> closure) {
        Checkpoint checkpoint = new Checkpoint();
        checkpoint.setName(name);

        CheckpointBuilder builder = new CheckpointBuilder(checkpoint);
        runClosure(closure, builder);

        config.addCheckpoint(checkpoint);
    }

    public void settings(Closure<?> closure) {
        SettingsBuilder builder = new SettingsBuilder(config.getSettings());
        runClosure(closure, builder);
    }

    private void runClosure(Closure<?> closure, Object delegate) {
        closure.setDelegate(delegate);
        closure.setResolveStrategy(Closure.DELEGATE_ONLY);
        closure.call();
    }

    protected static LocalDate parseDate(String value) {
        if (value == null || value.equals("-")) {
            return null;
        }

        if (value.contains("/")) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            return LocalDate.parse(value, formatter);
        }

        return LocalDate.parse(value);
    }

    public static class TaskBuilder extends GroovyObjectSupport {

        private final Task task;

        public TaskBuilder(Task task) {
            this.task = task;
        }

        @Override
        public void setProperty(String name, Object value) {
            switch (name) {
                case "name" ->
                        task.setName((String) value);
                case "maxScore" ->
                        task.setMaxScore(Double.parseDouble(value.toString()));
                case "softDeadline" ->
                        task.setSoftDeadline(parseDate(value.toString()));
                case "hardDeadline" ->
                        task.setHardDeadline(parseDate(value.toString()));
                default ->
                        super.setProperty(name, value);
            }
        }
    }

    public static class GroupBuilder extends GroovyObjectSupport {

        private final Group group;

        public GroupBuilder(Group group) {
            this.group = group;
        }

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

        public StudentBuilder(Student student) {
            this.student = student;
        }

        @Override
        public void setProperty(String name, Object value) {
            switch (name) {
                case "name" ->
                        student.setFullName((String) value);
                case "repositoryUrl" ->
                        student.setRepositoryUrl((String) value);
                default ->
                        super.setProperty(name, value);
            }
        }
    }

    public static class SubmissionBuilder extends GroovyObjectSupport {

        private final Submission submission;

        public SubmissionBuilder(Submission submission) {
            this.submission = submission;
        }

        @Override
        public void setProperty(String name, Object value) {
            switch (name) {
                case "submitDate" ->
                        submission.setSubmitDate(parseDate(value.toString()));
                case "bonus" ->
                        submission.setBonus(Double.parseDouble(value.toString()));
                default -> super.setProperty(name, value);
            }
        }
    }

    public static class CheckpointBuilder extends GroovyObjectSupport {

        private final Checkpoint checkpoint;

        public CheckpointBuilder(Checkpoint checkpoint) {
            this.checkpoint = checkpoint;
        }

        @Override
        public void setProperty(String name, Object value) {
            if (name.equals("date")) {
                checkpoint.setDate(parseDate(value.toString()));
            } else {
                super.setProperty(name, value);
            }
        }
    }

    public static class SettingsBuilder extends GroovyObjectSupport {

        private final Settings settings;

        public SettingsBuilder(Settings settings) {
            this.settings = settings;
        }

        @Override
        public void setProperty(String name, Object value) {
            switch (name) {
                case "softDeadlinePenalty" ->
                        settings.setSoftDeadlinePenalty(Double.parseDouble(value.toString()));
                case "maxBonus" ->
                        settings.setMaxBonus(Double.parseDouble(value.toString()));
                case "testTimeoutSeconds" ->
                        settings.setTestTimeoutSeconds(Integer.parseInt(value.toString()));
                default ->
                        super.setProperty(name, value);
            }
        }
    }
}