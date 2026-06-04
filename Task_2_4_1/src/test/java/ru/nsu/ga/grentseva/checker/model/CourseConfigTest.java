package ru.nsu.ga.grentseva.checker.model;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class CourseConfigTest {

    @Test
    void defaultConstructorCreatesCollections() {
        CourseConfig config = new CourseConfig();

        assertNotNull(config.getTasks());
        assertNotNull(config.getGroups());
        assertNotNull(config.getSubmissions());
        assertNotNull(config.getCheckpoints());
        assertNotNull(config.getSettings());
    }

    @Test
    void addTaskAddsTaskToList() {
        CourseConfig config = new CourseConfig();
        Task task = new Task();
        task.setId("1.1.1");

        config.addTask(task);

        assertEquals(1, config.getTasks().size());
        assertEquals(task, config.getTasks().get(0));
    }

    @Test
    void addGroupAddsGroupToList() {
        CourseConfig config = new CourseConfig();
        Group group = new Group();
        group.setName("24214");

        config.addGroup(group);

        assertEquals(1, config.getGroups().size());
        assertEquals(group, config.getGroups().get(0));
    }

    @Test
    void addSubmissionAddsSubmissionToList() {
        CourseConfig config = new CourseConfig();
        Submission submission = new Submission();
        submission.setStudentId("agrentseva");

        config.addSubmission(submission);

        assertEquals(1, config.getSubmissions().size());
        assertEquals(submission, config.getSubmissions().get(0));
    }

    @Test
    void addCheckpointAddsCheckpointToList() {
        CourseConfig config = new CourseConfig();
        Checkpoint checkpoint = new Checkpoint();
        checkpoint.setName("Checkpoint 1");

        config.addCheckpoint(checkpoint);

        assertEquals(1, config.getCheckpoints().size());
        assertEquals(checkpoint, config.getCheckpoints().get(0));
    }

    @Test
    void setTasksReplacesTasksList() {
        CourseConfig config = new CourseConfig();
        List<Task> tasks = new ArrayList<>();
        Task task = new Task();
        task.setId("2.2.1");
        tasks.add(task);

        config.setTasks(tasks);

        assertEquals(tasks, config.getTasks());
    }

    @Test
    void setGroupsReplacesGroupsList() {
        CourseConfig config = new CourseConfig();
        List<Group> groups = new ArrayList<>();
        Group group = new Group();
        group.setName("24214");
        groups.add(group);

        config.setGroups(groups);

        assertEquals(groups, config.getGroups());
    }

    @Test
    void setSubmissionsReplacesSubmissionsList() {
        CourseConfig config = new CourseConfig();
        List<Submission> submissions = new ArrayList<>();
        Submission submission = new Submission();
        submission.setStudentId("agrentseva");
        submissions.add(submission);

        config.setSubmissions(submissions);

        assertEquals(submissions, config.getSubmissions());
    }

    @Test
    void setCheckpointsReplacesCheckpointsList() {
        CourseConfig config = new CourseConfig();
        List<Checkpoint> checkpoints = new ArrayList<>();
        Checkpoint checkpoint = new Checkpoint();
        checkpoint.setName("Checkpoint");
        checkpoints.add(checkpoint);

        config.setCheckpoints(checkpoints);

        assertEquals(checkpoints, config.getCheckpoints());
    }

    @Test
    void setSettingsReplacesSettings() {
        CourseConfig config = new CourseConfig();
        Settings settings = new Settings();
        settings.setMaxBonus(5);

        config.setSettings(settings);

        assertEquals(settings, config.getSettings());
    }
}