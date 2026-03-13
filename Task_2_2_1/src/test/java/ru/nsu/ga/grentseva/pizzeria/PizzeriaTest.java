package ru.nsu.ga.grentseva.pizzeria;

import org.junit.jupiter.api.Test;
import ru.nsu.ga.grentseva.pizzeria.ordermodel.DefaultOrderFactory;
import ru.nsu.ga.grentseva.pizzeria.ordermodel.OrderFactory;
import ru.nsu.ga.grentseva.pizzeria.storage.OrderQueue;
import ru.nsu.ga.grentseva.pizzeria.storage.OrderSource;
import ru.nsu.ga.grentseva.pizzeria.storage.Storage;
import ru.nsu.ga.grentseva.pizzeria.storage.Warehouse;
import ru.nsu.ga.grentseva.pizzeria.util.ConsoleLogger;
import ru.nsu.ga.grentseva.pizzeria.util.Logger;
import ru.nsu.ga.grentseva.pizzeria.workers.Baker;
import ru.nsu.ga.grentseva.pizzeria.workers.Courier;
import ru.nsu.ga.grentseva.pizzeria.workers.Worker;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class PizzeriaTest {

    @Test
    public void testPizzeriaStart() {

        Logger logger = new ConsoleLogger();

        OrderSource orderSource = new OrderQueue(logger);
        Storage storage = new Warehouse(5, logger);

        OrderFactory orderFactory = new DefaultOrderFactory();

        List<Worker> workers = new ArrayList<>();

        workers.add(new Baker(1, 10, orderSource, storage, logger));
        workers.add(new Courier(1, 2, 10, storage, logger));

        Pizzeria pizzeria = new Pizzeria(
                orderSource,
                storage,
                workers,
                orderFactory,
                logger,
                100
        );

        assertDoesNotThrow(() -> pizzeria.start());
    }
}