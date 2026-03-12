package ru.nsu.ga.grentseva.pizzeria.config;

import com.google.gson.Gson;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ConfigLoader {

    public static Config load(String filename) throws Exception {
        InputStream is = ConfigLoader.class
                .getClassLoader()
                .getResourceAsStream(filename);

        if (is == null) {
            throw new RuntimeException("Config file not found: " + filename);
        }

        try (InputStreamReader reader = new InputStreamReader(is)) {
            return new Gson().fromJson(reader, Config.class);
        }
    }
}