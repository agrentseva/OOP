package ru.nsu.ga.grentseva.pizzeria.storage;

import ru.nsu.ga.grentseva.pizzeria.ordermodel.Order;

import java.util.List;

public interface Storage {

    void put(Order order) throws InterruptedException;

    List<Order> take(int max) throws InterruptedException;

    void close();
}