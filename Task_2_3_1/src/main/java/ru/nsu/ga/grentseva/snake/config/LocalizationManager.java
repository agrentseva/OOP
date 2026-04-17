package ru.nsu.ga.grentseva.snake.config;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

public final class LocalizationManager {

    private static Locale currentLocale = Locale.ENGLISH;
    private static ResourceBundle bundle = loadBundle();

    public static void setLocale(Locale locale) {
        currentLocale = locale;
        bundle = loadBundle();
    }

    public static String get(String key) {
        return bundle.getString(key);
    }

    private static ResourceBundle loadBundle() {
        try {
            String fileName = "messages_" + currentLocale.getLanguage() + ".properties";

            InputStream stream = LocalizationManager.class
                    .getClassLoader()
                    .getResourceAsStream(fileName);

            if (stream == null) {
                throw new RuntimeException("File not found: " + fileName);
            }

            return new PropertyResourceBundle(
                    new InputStreamReader(stream, StandardCharsets.UTF_8)
            );

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}