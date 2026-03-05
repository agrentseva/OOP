package ru.nsu.ga.grentseva.pizzeria;

import org.junit.jupiter.api.Test;
import ru.nsu.ga.grentseva.pizzeria.config.Config;
import ru.nsu.ga.grentseva.pizzeria.config.ConfigLoader;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class PizzeriaTest
{

    @Test
    public void testPizzeriaStart() throws Exception
    {
        Config config = ConfigLoader.load("config.json");
        Pizzeria pizzeria = new Pizzeria(config);
        assertDoesNotThrow(() -> pizzeria.start());
    }
}