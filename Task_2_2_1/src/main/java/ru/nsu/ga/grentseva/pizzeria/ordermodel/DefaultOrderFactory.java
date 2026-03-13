package ru.nsu.ga.grentseva.pizzeria.ordermodel;

public class DefaultOrderFactory implements OrderFactory {

    @Override
    public Order create(int id) {
        return new Order(id);
    }
}