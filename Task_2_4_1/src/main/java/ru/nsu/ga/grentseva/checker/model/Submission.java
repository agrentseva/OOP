package ru.nsu.ga.grentseva.checker.model;

import java.time.LocalDate;

public class Submission {
    private String studentId;
    private String taskId;
    private boolean accepted;
    private LocalDate submitDate;
    private double bonus;

    public String getStudentId() { return studentId; }

    public void setStudentId(String studentId) { this.studentId = studentId; }

    public String getTaskId() { return taskId; }

    public void setTaskId(String taskId) { this.taskId = taskId; }

    public boolean isAccepted() { return accepted; }

    public void setAccepted(boolean accepted) { this.accepted = accepted; }

    public LocalDate getSubmitDate() { return submitDate; }

    public void setSubmitDate(LocalDate submitDate) { this.submitDate = submitDate; }

    public double getBonus() { return bonus; }

    public void setBonus(double bonus) { this.bonus = bonus; }
}