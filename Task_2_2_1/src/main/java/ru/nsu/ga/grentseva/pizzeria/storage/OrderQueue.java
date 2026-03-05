package ru.nsu.ga.grentseva.pizzeria.storage;

import ru.nsu.ga.grentseva.pizzeria.ordermodel.Order;
import java.util.LinkedList;
import java.util.Queue;

public class OrderQueue {

    private final Queue<Order> queue = new LinkedList<>();
    private boolean closed = false;

    public synchronized void addOrder(Order order) {
        queue.add(order);
        notifyAll();
    }

    public synchronized Order getOrder() throws InterruptedException {
        while (queue.isEmpty() && !closed) {
            System.out.println("Baker waiting: no orders");
            wait();
        }

        if (queue.isEmpty()) {
            return null;
        }
        return queue.poll();
    }

    public synchronized void close() {
        closed = true;
        notifyAll();
    }
}