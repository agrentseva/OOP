package ru.nsu.ga.grentseva.pizzeria.storage;

import ru.nsu.ga.grentseva.pizzeria.ordermodel.Order;
import ru.nsu.ga.grentseva.pizzeria.ordermodel.OrderStatus;
import ru.nsu.ga.grentseva.pizzeria.util.Logger;
import java.util.LinkedList;
import java.util.List;

public class Warehouse implements Storage {

    private final int capacity;
    private final LinkedList<Order> pizzas = new LinkedList<>();
    private final Logger logger;

    private boolean closed = false;

    public Warehouse(int capacity, Logger logger) {
        this.capacity = capacity;
        this.logger = logger;
    }

    @Override
    public synchronized void put(Order order) throws InterruptedException {

        while (pizzas.size() >= capacity) {
            logger.log("Warehouse waiting: full");
            wait();
        }

        pizzas.add(order);
        order.setStatus(OrderStatus.STORED);

        notifyAll();
    }

    @Override
    public synchronized List<Order> take(int max) throws InterruptedException {

        while (pizzas.isEmpty() && !closed) {
            logger.log("Warehouse waiting: empty");
            wait();
        }

        if (pizzas.isEmpty()) {
            return null;
        }

        List<Order> result = new LinkedList<>();
        int count = Math.min(max, pizzas.size());
        for (int i = 0; i < count; i++) {
            result.add(pizzas.removeFirst());
        }

        notifyAll();

        return result;
    }

    @Override
    public synchronized void close() {
        closed = true;
        notifyAll();
    }
}