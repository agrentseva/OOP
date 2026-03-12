package ru.nsu.ga.grentseva.pizzeria.storage;

import ru.nsu.ga.grentseva.pizzeria.ordermodel.Order;
import ru.nsu.ga.grentseva.pizzeria.util.Logger;
import java.util.LinkedList;
import java.util.Queue;

public class OrderQueue implements OrderSource {

    private final Queue<Order> queue = new LinkedList<>();
    private final Logger logger;

    private boolean closed = false;

    public OrderQueue(Logger logger) {
        this.logger = logger;
    }

    @Override
    public synchronized void addOrder(Order order) {
        queue.add(order);
        notifyAll();
    }

    @Override
    public synchronized Order getOrder() throws InterruptedException {

        while (queue.isEmpty() && !closed) {
            logger.log("OrderQueue waiting: no orders");
            wait();
        }
        if (queue.isEmpty()) {
            return null;
        }

        return queue.poll();
    }

    @Override
    public synchronized void close() {
        closed = true;
        notifyAll();
    }
}