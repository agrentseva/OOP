package ru.nsu.ga.grentseva.pizzeria;

import org.junit.jupiter.api.Test;
import ru.nsu.ga.grentseva.pizzeria.ordermodel.Order;
import ru.nsu.ga.grentseva.pizzeria.storage.OrderQueue;
import static org.junit.jupiter.api.Assertions.*;

class OrderQueueTest {

    @Test
    void testAddAndGetOrder() throws InterruptedException {
        OrderQueue queue = new OrderQueue();
        Order order = new Order(1);
        queue.addOrder(order);
        Order result = queue.getOrder();

        assertNotNull(result);
        assertEquals(1, result.getId());
    }

    @Test
    void testQueueClose() throws InterruptedException {
        OrderQueue queue = new OrderQueue();
        queue.close();
        Order result = queue.getOrder();

        assertNull(result);
    }
}