package ru.nsu.ga.grentseva.pizzeria;

import ru.nsu.ga.grentseva.pizzeria.ordermodel.Order;
import ru.nsu.ga.grentseva.pizzeria.ordermodel.OrderFactory;
import ru.nsu.ga.grentseva.pizzeria.storage.OrderSource;
import ru.nsu.ga.grentseva.pizzeria.storage.Storage;
import ru.nsu.ga.grentseva.pizzeria.util.Logger;
import ru.nsu.ga.grentseva.pizzeria.workers.Worker;

import java.util.List;

public class Pizzeria {

    private final OrderSource orderSource;
    private final Storage storage;
    private final List<Worker> workers;
    private final OrderFactory orderFactory;
    private final Logger logger;
    private final int workingTime;

    private final Object timer = new Object();

    public Pizzeria(
            OrderSource orderSource,
            Storage storage,
            List<Worker> workers,
            OrderFactory orderFactory,
            Logger logger,
            int workingTime
    ) {
        this.orderSource = orderSource;
        this.storage = storage;
        this.workers = workers;
        this.orderFactory = orderFactory;
        this.logger = logger;
        this.workingTime = workingTime;
    }

    public void start() throws InterruptedException {
        logger.log("Pizzeria opened");
        for (Worker worker : workers) {
            worker.start();
        }

        int orderId = 1;
        long start = System.currentTimeMillis();
        while (System.currentTimeMillis() - start < workingTime) {
            Order order = orderFactory.create(orderId++);
            orderSource.addOrder(order);

            synchronized (timer) {
                timer.wait(500);
            }
        }

        logger.log("Stop accepting orders");
        orderSource.close();

        storage.close();

        for (Worker worker : workers) {
            worker.join();
        }

        logger.log("All workers finished");
        logger.log("Pizzeria closed");
    }
}