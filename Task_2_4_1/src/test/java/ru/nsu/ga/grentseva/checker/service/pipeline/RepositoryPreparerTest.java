package ru.nsu.ga.grentseva.checker.service.pipeline;

import org.junit.jupiter.api.Test;
import ru.nsu.ga.grentseva.checker.model.CourseConfig;
import ru.nsu.ga.grentseva.checker.model.Group;
import ru.nsu.ga.grentseva.checker.model.Student;

import java.io.File;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class RepositoryPreparerTest {

    @Test
    void prepareReturnsSet() {
        CourseConfig config = new CourseConfig();
        Group group = new Group("24214");
        Student student = new Student();

        student.setGithubId("student");
        student.setRepositoryUrl("invalid-url");
        group.addStudent(student);
        config.addGroup(group);

        RepositoryPreparer preparer = new RepositoryPreparer();
        Set<String> prepared = preparer.prepare(config, new File("test_workspace"));

        assertNotNull(prepared);
    }

    @Test
    void failedRepositoryNotAdded() {
        CourseConfig config = new CourseConfig();
        Group group = new Group("24214");
        Student student = new Student();

        student.setGithubId("student");
        student.setRepositoryUrl("invalid-url");
        group.addStudent(student);
        config.addGroup(group);

        RepositoryPreparer preparer = new RepositoryPreparer();
        Set<String> prepared = preparer.prepare(config, new File("test_workspace"));

        assertFalse(prepared.contains("student"));
    }
}