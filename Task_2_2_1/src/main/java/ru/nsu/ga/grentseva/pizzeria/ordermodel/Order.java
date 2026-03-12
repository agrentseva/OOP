package ru.nsu.ga.grentseva.pizzeria.ordermodel;

public class Order {

    private final int id;
    private OrderStatus status;

    public Order(int id) {
        this.id = id;
        this.status = OrderStatus.NEW;
        printStatus();
    }

    public synchronized void setStatus(OrderStatus status) {
        this.status = status;
        printStatus();
    }

    private void printStatus() {
        System.out.println(id + " " + status);
    }

    @Override
    public String toString() {
        return "order " + id;
    }
}