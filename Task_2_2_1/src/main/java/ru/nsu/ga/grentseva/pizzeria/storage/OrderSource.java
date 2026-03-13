package ru.nsu.ga.grentseva.pizzeria.storage;

import ru.nsu.ga.grentseva.pizzeria.ordermodel.Order;

public interface OrderSource {

    void addOrder(Order order);

    Order getOrder() throws InterruptedException;

    void close();
}