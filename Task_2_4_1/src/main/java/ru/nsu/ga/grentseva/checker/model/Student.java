package ru.nsu.ga.grentseva.checker.model;

import java.util.Objects;

public class Student {
    private String githubId;
    private String fullName;
    private String repositoryUrl;

    public Student() {
    }

    public Student(String githubId, String fullName, String repositoryUrl) {
        this.githubId = githubId;
        this.fullName = fullName;
        this.repositoryUrl = repositoryUrl;
    }

    public String getGithubId() {
        return githubId;
    }

    public void setGithubId(String githubId) {
        this.githubId = githubId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getRepositoryUrl() {
        return repositoryUrl;
    }

    public void setRepositoryUrl(String repositoryUrl) {
        this.repositoryUrl = repositoryUrl;
    }

    @Override
    public String toString() {
        return "Student{" +
                "githubId='" + githubId + '\'' +
                ", fullName='" + fullName + '\'' +
                ", repositoryUrl='" + repositoryUrl + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Student student)) return false;
        return Objects.equals(githubId, student.githubId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(githubId);
    }
}