package ru.nsu.ga.grentseva.pizzeria;

import ru.nsu.ga.grentseva.pizzeria.config.BakerConfig;
import ru.nsu.ga.grentseva.pizzeria.config.Config;
import ru.nsu.ga.grentseva.pizzeria.config.ConfigLoader;
import ru.nsu.ga.grentseva.pizzeria.config.CourierConfig;
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
import java.util.Random;

public class Main {

    public static void main(String[] args) throws Exception {
        Config config = ConfigLoader.load("config.json");
        Logger logger = new ConsoleLogger();

        OrderSource orderSource = new OrderQueue(logger);
        Storage storage = new Warehouse(
                config.getWarehouseCapacity(),
                logger
        );

        OrderFactory orderFactory = new DefaultOrderFactory();
        List<Worker> workers = new ArrayList<>();

        int bakerId = 1;
        for (BakerConfig bakerConfig : config.getBakers()) {
            workers.add(
                    new Baker(
                            bakerId++,
                            bakerConfig.getCookTime(),
                            orderSource,
                            storage,
                            logger
                    )
            );
        }

        Random random = new Random();
        int courierId = 1;
        for (CourierConfig courierConfig : config.getCouriers()) {
            int deliveryTime = 500 + random.nextInt(1000);
            workers.add(
                    new Courier(
                            courierId++,
                            courierConfig.getCapacity(),
                            deliveryTime,
                            storage,
                            logger
                    )
            );
        }

        Pizzeria pizzeria = new Pizzeria(
                orderSource,
                storage,
                workers,
                orderFactory,
                logger,
                config.getWorkingTime()
        );

        pizzeria.start();
    }
}