package ru.nsu.ga.grentseva.pizzeria;

import org.junit.jupiter.api.Test;
import ru.nsu.ga.grentseva.pizzeria.ordermodel.Order;
import ru.nsu.ga.grentseva.pizzeria.storage.Storage;
import ru.nsu.ga.grentseva.pizzeria.storage.Warehouse;
import ru.nsu.ga.grentseva.pizzeria.util.ConsoleLogger;
import ru.nsu.ga.grentseva.pizzeria.util.Logger;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class WarehouseTest {

    @Test
    public void testPutAndTake() throws Exception {

        Logger logger = new ConsoleLogger();
        Storage storage = new Warehouse(5, logger);

        Order order = new Order(1);

        storage.put(order);

        List<Order> orders = storage.take(1);

        assertEquals(1, orders.size());
    }
}