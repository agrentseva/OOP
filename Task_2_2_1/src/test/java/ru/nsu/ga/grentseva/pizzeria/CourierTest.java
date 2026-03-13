package ru.nsu.ga.grentseva.pizzeria;

import org.junit.jupiter.api.Test;
import ru.nsu.ga.grentseva.pizzeria.ordermodel.Order;
import ru.nsu.ga.grentseva.pizzeria.storage.Storage;
import ru.nsu.ga.grentseva.pizzeria.storage.Warehouse;
import ru.nsu.ga.grentseva.pizzeria.util.ConsoleLogger;
import ru.nsu.ga.grentseva.pizzeria.util.Logger;
import ru.nsu.ga.grentseva.pizzeria.workers.Courier;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class CourierTest {

    @Test
    public void testCourierDeliversOrders() throws Exception {

        Logger logger = new ConsoleLogger();
        Storage storage = new Warehouse(5, logger);

        storage.put(new Order(1));

        Courier courier = new Courier(1, 2, 10, storage, logger);

        storage.close();

        courier.start();

        assertDoesNotThrow(() -> courier.join());
    }
}