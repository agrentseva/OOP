package ru.nsu.ga.grentseva.checker.model;

public class Student {
    private String githubId;
    private String fullName;
    private String repoUrl;

    public String getGithubId() { return githubId; }

    public void setGithubId(String githubId) { this.githubId = githubId; }

    public String getFullName() { return fullName; }

    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getRepoUrl() { return repoUrl; }

    public void setRepoUrl(String repoUrl) { this.repoUrl = repoUrl; }
}