package ru.nsu.ga.grentseva.pizzeria;

import org.junit.jupiter.api.Test;
import ru.nsu.ga.grentseva.pizzeria.ordermodel.Order;
import ru.nsu.ga.grentseva.pizzeria.storage.OrderQueue;
import ru.nsu.ga.grentseva.pizzeria.storage.Storage;
import ru.nsu.ga.grentseva.pizzeria.storage.Warehouse;
import ru.nsu.ga.grentseva.pizzeria.util.ConsoleLogger;
import ru.nsu.ga.grentseva.pizzeria.util.Logger;
import ru.nsu.ga.grentseva.pizzeria.workers.Baker;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class BakerTest {

    @Test
    public void testBakerProcessesOrder() {

        Logger logger = new ConsoleLogger();
        OrderQueue queue = new OrderQueue(logger);
        Storage storage = new Warehouse(5, logger);

        Baker baker = new Baker(1, 10, queue, storage, logger);

        queue.addOrder(new Order(1));
        queue.close();

        baker.start();

        assertDoesNotThrow(() -> baker.join());
    }
}