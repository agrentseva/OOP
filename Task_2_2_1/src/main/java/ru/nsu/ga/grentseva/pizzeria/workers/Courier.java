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
    private final Logger logger;

    public Courier(
            int id,
            int capacity,
            int deliveryTime,
            Storage storage,
            Logger logger
    ) {
        super(id);
        this.capacity = capacity;
        this.deliveryTime = deliveryTime;
        this.storage = storage;
        this.logger = logger;
    }

    @Override
    public void run() {
        try {
            while (true) {
                List<Order> orders = storage.take(capacity);
                if (orders == null) {
                    logger.log(this + " finished");
                    break;
                }

                logger.log(this + " took " + orders.size() + " pizza" +
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