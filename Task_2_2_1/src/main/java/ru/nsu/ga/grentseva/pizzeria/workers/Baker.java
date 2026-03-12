package ru.nsu.ga.grentseva.pizzeria.workers;

import ru.nsu.ga.grentseva.pizzeria.ordermodel.Order;
import ru.nsu.ga.grentseva.pizzeria.ordermodel.OrderStatus;
import ru.nsu.ga.grentseva.pizzeria.storage.OrderSource;
import ru.nsu.ga.grentseva.pizzeria.storage.Storage;
import ru.nsu.ga.grentseva.pizzeria.util.Logger;

public class Baker extends Worker {

    private final int cookTime;
    private final OrderSource orderSource;
    private final Storage storage;
    private final Logger logger;

    public Baker(
            int id,
            int cookTime,
            OrderSource orderSource,
            Storage storage,
            Logger logger
    ) {
        super(id);
        this.cookTime = cookTime;
        this.orderSource = orderSource;
        this.storage = storage;
        this.logger = logger;
    }

    @Override
    public void run() {
        try {
            while (true) {
                Order order = orderSource.getOrder();
                if (order == null) {
                    logger.log(this + " finished");
                    break;
                }

                logger.log(this + " cooking " + order);
                order.setStatus(OrderStatus.COOKING);
                Thread.sleep(cookTime);

                order.setStatus(OrderStatus.COOKED);
                storage.put(order);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}