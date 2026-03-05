package ru.nsu.ga.grentseva.pizzeria.workers;

import ru.nsu.ga.grentseva.pizzeria.ordermodel.Order;
import ru.nsu.ga.grentseva.pizzeria.storage.OrderQueue;
import ru.nsu.ga.grentseva.pizzeria.ordermodel.OrderStatus;
import ru.nsu.ga.grentseva.pizzeria.storage.Warehouse;

public class Baker extends Thread {

    private final int id;
    private final int cookTime;
    private final OrderQueue orderQueue;
    private final Warehouse warehouse;

    public Baker(int id, int cookTime, OrderQueue orderQueue, Warehouse warehouse) {
        this.id = id;
        this.cookTime = cookTime;
        this.orderQueue = orderQueue;
        this.warehouse = warehouse;
    }

    @Override
    public void run() {
        try {
            while (true) {
                Order order = orderQueue.getOrder();
                if (order == null) {
                    System.out.println("Baker " + id + " finished");
                    break;
                }

                System.out.println("Baker " + id + " cooking order " + order.getId());
                order.setStatus(OrderStatus.COOKING);
                Thread.sleep(cookTime);

                order.setStatus(OrderStatus.COOKED);
                warehouse.put(order);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}