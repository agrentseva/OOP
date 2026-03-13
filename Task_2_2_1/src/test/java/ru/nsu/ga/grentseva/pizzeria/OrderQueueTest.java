package ru.nsu.ga.grentseva.pizzeria;

import org.junit.jupiter.api.Test;
import ru.nsu.ga.grentseva.pizzeria.ordermodel.Order;
import ru.nsu.ga.grentseva.pizzeria.storage.OrderQueue;
import ru.nsu.ga.grentseva.pizzeria.util.ConsoleLogger;
import ru.nsu.ga.grentseva.pizzeria.util.Logger;

import static org.junit.jupiter.api.Assertions.*;

public class OrderQueueTest {

    @Test
    public void testAddAndGetOrder() throws Exception {

        Logger logger = new ConsoleLogger();
        OrderQueue queue = new OrderQueue(logger);

        Order order = new Order(1);

        queue.addOrder(order);

        Order result = queue.getOrder();

        assertEquals(order, result);
    }
}