package ru.nsu.ga.grentseva.pizzeria.workers;

public abstract class Worker extends Thread {

    protected final int id;

    public Worker(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + " " + id;
    }
}