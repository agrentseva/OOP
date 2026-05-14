package ru.nsu.ga.grentseva.checker.model;

import java.util.ArrayList;
import java.util.List;

public class CourseConfig {

    private List<Task> tasks = new ArrayList<>();
    private List<Group> groups = new ArrayList<>();
    private List<Submission> submissions = new ArrayList<>();
    private List<Checkpoint> checkpoints = new ArrayList<>();
    private Settings settings = new Settings();

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public void addTask(Task task) {
        tasks.add(task);
    }

    public List<Group> getGroups() {
        return groups;
    }

    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }

    public void addGroup(Group group) {
        groups.add(group);
    }

    public List<Submission> getSubmissions() {
        return submissions;
    }

    public void setSubmissions(List<Submission> submissions) {
        this.submissions = submissions;
    }

    public void addSubmission(Submission submission) {
        submissions.add(submission);
    }

    public List<Checkpoint> getCheckpoints() {
        return checkpoints;
    }

    public void setCheckpoints(List<Checkpoint> checkpoints) {
        this.checkpoints = checkpoints;
    }

    public void addCheckpoint(Checkpoint checkpoint) {
        checkpoints.add(checkpoint);
    }

    public Settings getSettings() {
        return settings;
    }

    public void setSettings(Settings settings) {
        this.settings = settings;
    }
}