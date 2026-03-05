package ru.nsu.ga.grentseva.pizzeria;

import ru.nsu.ga.grentseva.pizzeria.config.Config;
import ru.nsu.ga.grentseva.pizzeria.config.ConfigLoader;

public class Main {

    public static void main(String[] args) throws Exception {
        Config config = ConfigLoader.load("config.json");
        Pizzeria pizzeria = new Pizzeria(config);
        pizzeria.start();
    }
}