package ru.nsu.ga.grentseva.pizzeria;

import org.junit.jupiter.api.Test;
import ru.nsu.ga.grentseva.pizzeria.ordermodel.Order;
import ru.nsu.ga.grentseva.pizzeria.storage.OrderQueue;
import ru.nsu.ga.grentseva.pizzeria.storage.Warehouse;
import ru.nsu.ga.grentseva.pizzeria.workers.Baker;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BakerTest
{

    @Test
    public void testBakerProcessesOrder() throws InterruptedException
    {
        OrderQueue queue = new OrderQueue();
        Warehouse warehouse = new Warehouse(5);
        Baker baker = new Baker(
                1,
                50,
                queue,
                warehouse
        );

        Order order = new Order(1);
        queue.addOrder(order);
        queue.close();

        baker.start();
        baker.join();

        assertEquals(1, warehouse.take(1).size());
    }
}