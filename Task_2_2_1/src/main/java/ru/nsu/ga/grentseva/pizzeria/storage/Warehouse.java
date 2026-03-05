package ru.nsu.ga.grentseva.pizzeria.storage;

import ru.nsu.ga.grentseva.pizzeria.ordermodel.Order;
import ru.nsu.ga.grentseva.pizzeria.ordermodel.OrderStatus;
import java.util.LinkedList;
import java.util.List;

public class Warehouse {

    private final int capacity;
    private final List<Order> pizzas = new LinkedList<>();
    private boolean closed = false;

    public Warehouse(int capacity) {
        this.capacity = capacity;
    }

    public synchronized void put(Order order) throws InterruptedException {
        while (pizzas.size() >= capacity) {
            System.out.println("Baker waiting: warehouse full");
            wait();
        }
        pizzas.add(order);
        order.setStatus(OrderStatus.STORED);
        notifyAll();
    }

    public synchronized List<Order> take(int max) throws InterruptedException {
        while (pizzas.isEmpty() && !closed) {
            System.out.println("Courier waiting: warehouse empty");
            wait();
        }
        if (pizzas.isEmpty()) {
            return null;
        }

        List<Order> result = new LinkedList<>();
        int count = Math.min(max, pizzas.size());
        for (int i = 0; i < count; i++) {
            result.add(pizzas.remove(0));
        }

        notifyAll();
        return result;
    }

    public synchronized void close() {
        closed = true;
        notifyAll();
    }
}