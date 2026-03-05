package ru.nsu.ga.grentseva.pizzeria;

import ru.nsu.ga.grentseva.pizzeria.config.BakerConfig;
import ru.nsu.ga.grentseva.pizzeria.config.Config;
import ru.nsu.ga.grentseva.pizzeria.config.CourierConfig;
import ru.nsu.ga.grentseva.pizzeria.ordermodel.Order;
import ru.nsu.ga.grentseva.pizzeria.storage.OrderQueue;
import ru.nsu.ga.grentseva.pizzeria.storage.OrderSource;
import ru.nsu.ga.grentseva.pizzeria.storage.Storage;
import ru.nsu.ga.grentseva.pizzeria.storage.Warehouse;
import ru.nsu.ga.grentseva.pizzeria.util.Logger;
import ru.nsu.ga.grentseva.pizzeria.workers.Baker;
import ru.nsu.ga.grentseva.pizzeria.workers.Courier;

import java.util.ArrayList;
import java.util.List;

public class Pizzeria {

    private final OrderSource orderSource;
    private final Storage storage;

    private final List<Baker> bakers = new ArrayList<>();
    private final List<Courier> couriers = new ArrayList<>();

    private final int workingTime;

    public Pizzeria(Config config) {

        this.orderSource = new OrderQueue();
        this.storage = new Warehouse(config.getWarehouseCapacity());
        this.workingTime = config.getWorkingTime();

        int bakerId = 1;
        for (BakerConfig bakerConfig : config.getBakers()) {
            bakers.add(
                    new Baker(
                            bakerId++,
                            bakerConfig.getCookTime(),
                            orderSource,
                            storage
                    )
            );
        }

        int courierId = 1;
        for (CourierConfig courierConfig : config.getCouriers()) {
            couriers.add(
                    new Courier(
                            courierId++,
                            courierConfig.getCapacity(),
                            courierConfig.getDeliveryTime(),
                            storage
                    )
            );
        }
    }

    public void start() throws InterruptedException {
        Logger.log("Pizzeria opened");
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
            orderSource.addOrder(order);
            synchronized (this) {
                wait(500);
            }
        }

        Logger.log("Stop accepting orders");
        orderSource.close();

        for (Baker baker : bakers) {
            baker.join();
        }
        Logger.log("All bakers finished");

        storage.close();
        for (Courier courier : couriers) {
            courier.join();
        }
        Logger.log("All couriers finished");
        Logger.log("Pizzeria closed");
    }
}