package ru.nsu.ga.grentseva.pizzeria;

import org.junit.jupiter.api.Test;
import ru.nsu.ga.grentseva.pizzeria.ordermodel.Order;
import ru.nsu.ga.grentseva.pizzeria.storage.Warehouse;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class WarehouseTest {

    @Test
    void testPutAndTake() throws InterruptedException {
        Warehouse warehouse = new Warehouse(5);
        warehouse.put(new Order(1));
        warehouse.put(new Order(2));
        List<Order> orders = warehouse.take(2);

        assertEquals(2, orders.size());
    }

    @Test
    void testCapacityLimit() throws InterruptedException {
        Warehouse warehouse = new Warehouse(1);
        warehouse.put(new Order(1));

        assertThrows(InterruptedException.class, () -> {
            Thread.currentThread().interrupt();
            warehouse.put(new Order(2));
        });
    }
}