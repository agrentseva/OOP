package ru.nsu.ga.grentseva.checker.model;

import java.time.LocalDate;
import java.util.Objects;

public class Submission {
    private String studentId;
    private String taskId;

    private LocalDate submitDate;

    private double bonus;

    public Submission() {
    }

    public Submission(String studentId,
                      String taskId,
                      LocalDate submitDate,
                      double bonus) {

        this.studentId = studentId;
        this.taskId = taskId;
        this.submitDate = submitDate;
        this.bonus = bonus;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public LocalDate getSubmitDate() {
        return submitDate;
    }

    public void setSubmitDate(LocalDate submitDate) {
        this.submitDate = submitDate;
    }

    public double getBonus() {
        return bonus;
    }

    public void setBonus(double bonus) {
        this.bonus = bonus;
    }

    @Override
    public String toString() {
        return "Submission{" +
                "studentId='" + studentId + '\'' +
                ", taskId='" + taskId + '\'' +
                ", submitDate=" + submitDate +
                ", bonus=" + bonus +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Submission that)) return false;

        return Objects.equals(studentId, that.studentId)
                && Objects.equals(taskId, that.taskId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(studentId, taskId);
    }
}