package ru.nsu.ga.grentseva.pizzeria;

import ru.nsu.ga.grentseva.pizzeria.config.BakerConfig;
import ru.nsu.ga.grentseva.pizzeria.config.Config;
import ru.nsu.ga.grentseva.pizzeria.config.CourierConfig;
import ru.nsu.ga.grentseva.pizzeria.ordermodel.Order;
import ru.nsu.ga.grentseva.pizzeria.storage.OrderQueue;
import ru.nsu.ga.grentseva.pizzeria.storage.Warehouse;
import ru.nsu.ga.grentseva.pizzeria.workers.Baker;
import ru.nsu.ga.grentseva.pizzeria.workers.Courier;
import java.util.ArrayList;
import java.util.List;

public class Pizzeria {

    private final OrderQueue orderQueue;
    private final Warehouse warehouse;

    private final List<Baker> bakers = new ArrayList<>();
    private final List<Courier> couriers = new ArrayList<>();

    private final int workingTime;

    public Pizzeria(Config config) {
        this.orderQueue = new OrderQueue();
        this.warehouse = new Warehouse(config.getWarehouseCapacity());
        this.workingTime = config.getWorkingTime();

        int bakerId = 1;
        for (BakerConfig b : config.getBakers()) {
            bakers.add(
                    new Baker(
                            bakerId++,
                            b.getCookTime(),
                            orderQueue,
                            warehouse
                    )
            );
        }

        int courierId = 1;
        for (CourierConfig c : config.getCouriers()) {
            couriers.add(
                    new Courier(
                            courierId++,
                            c.getCapacity(),
                            c.getDeliveryTime(),
                            warehouse
                    )
            );
        }
    }

    public void start() throws InterruptedException {
        System.out.println("Pizzeria opened");
        for (Baker baker : bakers) {
            baker.start();
        }
        for (Courier courier : couriers) {
            courier.start();
        }

        int orderId = 1;
        long start = System.currentTimeMillis();
        while (System.currentTimeMillis() - start < workingTime) {
            Order order = new Order(orderId++);
            orderQueue.addOrder(order);
            Thread.sleep(500);
        }

        System.out.println("Stop accepting orders");
        orderQueue.close();

        for (Baker baker : bakers) {
            baker.join();
        }
        System.out.println("All bakers finished");

        warehouse.close();

        for (Courier courier : couriers) {
            courier.join();
        }
        System.out.println("All couriers finished");
        System.out.println("Pizzeria closed");
    }
}