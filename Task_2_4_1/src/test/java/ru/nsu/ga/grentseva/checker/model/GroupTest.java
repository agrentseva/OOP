package ru.nsu.ga.grentseva.checker.model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GroupTest {

    @Test
    void emptyConstructorCreatesObject() {

        Group group =
                new Group();

        assertNotNull(group);

        assertNotNull(group.getStudents());
    }

    @Test
    void constructorSetsName() {

        Group group =
                new Group("24214");

        assertEquals(
                "24214",
                group.getName()
        );
    }

    @Test
    void setNameChangesName() {

        Group group =
                new Group();

        group.setName("24215");

        assertEquals(
                "24215",
                group.getName()
        );
    }

    @Test
    void addStudentAddsStudentToList() {

        Group group =
                new Group();

        Student student =
                new Student();

        student.setGithubId("agrentseva");

        group.addStudent(student);

        assertEquals(
                1,
                group.getStudents().size()
        );

        assertEquals(
                student,
                group.getStudents().get(0)
        );
    }

    @Test
    void setStudentsReplacesStudentsList() {

        Group group =
                new Group();

        List<Student> students =
                new ArrayList<>();

        Student student =
                new Student();

        student.setGithubId("VlanAni");

        students.add(student);

        group.setStudents(students);

        assertEquals(
                students,
                group.getStudents()
        );
    }

    @Test
    void equalsReturnsTrueForSameNames() {

        Group first =
                new Group("24214");

        Group second =
                new Group("24214");

        assertEquals(first, second);
    }

    @Test
    void equalsReturnsFalseForDifferentNames() {

        Group first =
                new Group("24214");

        Group second =
                new Group("24215");

        assertNotEquals(first, second);
    }

    @Test
    void hashCodeSameForEqualObjects() {

        Group first =
                new Group("24214");

        Group second =
                new Group("24214");

        assertEquals(
                first.hashCode(),
                second.hashCode()
        );
    }

    @Test
    void toStringContainsName() {

        Group group =
                new Group("24214");

        String text =
                group.toString();

        assertTrue(
                text.contains("24214")
        );
    }
}