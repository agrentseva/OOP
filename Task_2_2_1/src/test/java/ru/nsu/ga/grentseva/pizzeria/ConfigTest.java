package ru.nsu.ga.grentseva.pizzeria;

import org.junit.jupiter.api.Test;
import ru.nsu.ga.grentseva.pizzeria.config.Config;
import ru.nsu.ga.grentseva.pizzeria.config.ConfigLoader;
import static org.junit.jupiter.api.Assertions.*;

public class ConfigTest
{

    @Test
    public void testConfigLoading() throws Exception
    {
        Config config = ConfigLoader.load("config.json");

        assertNotNull(config);

        assertEquals(3, config.getWarehouseCapacity());
        assertEquals(2000, config.getWorkingTime());

        assertNotNull(config.getBakers());
        assertNotNull(config.getCouriers());

        assertEquals(1, config.getBakers().size());
        assertEquals(1, config.getCouriers().size());

        assertEquals(200, config.getBakers().get(0).getCookTime());
        assertEquals(2, config.getCouriers().get(0).getCapacity());
    }
}
