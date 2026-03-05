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

    public Baker(int id, int cookTime, OrderSource orderSource, Storage storage) {
        super(id);
        this.cookTime = cookTime;
        this.orderSource = orderSource;
        this.storage = storage;
    }

    @Override
    public void run() {
        try {
            while (true) {
                Order order = orderSource.getOrder();
                if (order == null) {
                    Logger.log(this + " finished");
                    break;
                }

                Logger.log(this + " cooking " + order);
                order.setStatus(OrderStatus.COOKING);
                synchronized (this) {
                    wait(cookTime);
                }

                order.setStatus(OrderStatus.COOKED);
                storage.put(order);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}