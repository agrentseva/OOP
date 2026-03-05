package ru.nsu.ga.grentseva.pizzeria.workers;

import ru.nsu.ga.grentseva.pizzeria.ordermodel.Order;
import ru.nsu.ga.grentseva.pizzeria.ordermodel.OrderStatus;
import ru.nsu.ga.grentseva.pizzeria.storage.Warehouse;
import java.util.List;

public class Courier extends Thread {

    private final int capacity;
    private final int deliveryTime;
    private final Warehouse warehouse;
    private final int id;

    public Courier(int id, int capacity, int deliveryTime, Warehouse warehouse) {
        this.id = id;
        this.capacity = capacity;
        this.deliveryTime = deliveryTime;
        this.warehouse = warehouse;
    }

    @Override
    public void run() {
        try {
            while (true) {
                List<Order> orders = warehouse.take(capacity);
                if (orders == null) {
                    break;
                }
                System.out.println("Courier " + id + " took " + orders.size() + " pizza" +
                        (orders.size() > 1 ? "s" : ""));

                for (Order order : orders) {
                    order.setStatus(OrderStatus.DELIVERING);
                }
                Thread.sleep(deliveryTime);

                for (Order order : orders) {
                    order.setStatus(OrderStatus.DELIVERED);
                }
                Thread.sleep(deliveryTime);
            }

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}