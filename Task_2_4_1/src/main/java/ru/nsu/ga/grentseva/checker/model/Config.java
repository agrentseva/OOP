package ru.nsu.ga.grentseva.checker.model;

import java.util.ArrayList;
import java.util.List;

public class Config {
    private List<Task> tasks = new ArrayList<>();
    private List<Group> groups = new ArrayList<>();
    private List<Submission> submissions = new ArrayList<>();
    private List<Checkpoint> checkpoints = new ArrayList<>();
    private Settings settings = new Settings();

    public List<Task> getTasks() { return tasks; }

    public List<Group> getGroups() { return groups; }

    public List<Submission> getSubmissions() { return submissions; }

    public List<Checkpoint> getCheckpoints() { return checkpoints; }

    public Settings getSettings() { return settings; }
}