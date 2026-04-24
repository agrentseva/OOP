package ru.nsu.ga.grentseva.checker.model;

import java.time.LocalDate;

public class Checkpoint {
    private String name;
    private LocalDate date;

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public LocalDate getDate() { return date; }

    public void setDate(LocalDate date) { this.date = date; }
}