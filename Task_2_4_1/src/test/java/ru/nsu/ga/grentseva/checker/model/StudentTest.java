package ru.nsu.ga.grentseva.checker.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class StudentTest {

    @Test
    void emptyConstructorCreatesObject() {
        Student student = new Student();

        assertNotNull(student);
    }

    @Test
    void constructorSetsFields() {
        Student student = new Student(
                "agrentseva",
                "Гренцева Алина",
                "https://github.com/agrentseva/OOP.git"
        );

        assertEquals("agrentseva", student.getGithubId());
        assertEquals("Гренцева Алина", student.getFullName());
        assertEquals("https://github.com/agrentseva/OOP.git", student.getRepositoryUrl());
    }

    @Test
    void settersWorkCorrectly() {
        Student student = new Student();

        student.setGithubId("VlanAni");
        student.setFullName("Анисимов Владимир");
        student.setRepositoryUrl("https://github.com/VlanAni/OOP.git");

        assertEquals("VlanAni", student.getGithubId());
        assertEquals("Анисимов Владимир", student.getFullName());
        assertEquals("https://github.com/VlanAni/OOP.git", student.getRepositoryUrl());
    }

    @Test
    void equalsReturnsTrueForSameGithubId() {
        Student first = new Student("agrentseva", "Alina", "repo1");
        Student second = new Student("agrentseva", "Another Name", "repo2");

        assertEquals(first, second);
    }

    @Test
    void equalsReturnsFalseForDifferentGithubIds() {
        Student first = new Student("agrentseva", "Alina", "repo1");
        Student second = new Student("VlanAni", "Vladimir", "repo2");

        assertNotEquals(first, second);
    }

    @Test
    void hashCodeSameForEqualObjects() {
        Student first = new Student("agrentseva", "Alina", "repo1");
        Student second = new Student("agrentseva", "Another", "repo2");

        assertEquals(first.hashCode(), second.hashCode());
    }

    @Test
    void toStringContainsFields() {
        Student student = new Student("agrentseva", "Гренцева Алина", "repo");
        String text = student.toString();

        assertTrue(text.contains("agrentseva"));
        assertTrue(text.contains("Гренцева Алина"));
        assertTrue(text.contains("repo"));
    }
}