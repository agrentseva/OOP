package ru.nsu.ga.grentseva.pizzeria;

import org.junit.jupiter.api.Test;
import ru.nsu.ga.grentseva.pizzeria.ordermodel.Order;
import ru.nsu.ga.grentseva.pizzeria.storage.Warehouse;
import ru.nsu.ga.grentseva.pizzeria.workers.Courier;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class CourierTest
{

    @Test
    public void testCourierTakesOrders() throws InterruptedException
    {
        Warehouse warehouse = new Warehouse(5);
        warehouse.put(new Order(1));
        warehouse.put(new Order(2));

        Courier courier = new Courier(
                1,
                2,
                50,
                warehouse
        );

        courier.start();
        Thread.sleep(200);
        warehouse.close();
        courier.join();

        assertFalse(courier.isAlive());
    }
}