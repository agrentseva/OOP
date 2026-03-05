package ru.nsu.ga.grentseva.pizzeria.workers;

import ru.nsu.ga.grentseva.pizzeria.ordermodel.Order;
import ru.nsu.ga.grentseva.pizzeria.ordermodel.OrderStatus;
import ru.nsu.ga.grentseva.pizzeria.storage.Storage;
import ru.nsu.ga.grentseva.pizzeria.util.Logger;

import java.util.List;

public class Courier extends Worker {

    private final int capacity;
    private final int deliveryTime;
    private final Storage storage;

    public Courier(int id, int capacity, int deliveryTime, Storage storage) {
        super(id);
        this.capacity = capacity;
        this.deliveryTime = deliveryTime;
        this.storage = storage;
    }

    @Override
    public void run() {
        try {
            while (true) {
                List<Order> orders = storage.take(capacity);
                if (orders == null) {
                    Logger.log(this + " finished");
                    break;
                }

                Logger.log(this + " took " + orders.size() + " pizza" +
                        (orders.size() > 1 ? "s" : ""));
                for (Order order : orders) {
                    order.setStatus(OrderStatus.DELIVERING);
                }
                synchronized (this) {
                    wait(deliveryTime);
                }

                for (Order order : orders) {
                    order.setStatus(OrderStatus.DELIVERED);
                }
                synchronized (this) {
                    wait(deliveryTime);
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}