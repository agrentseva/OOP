package ru.nsu.ga.grentseva.snake.config;

import org.junit.jupiter.api.Test;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

public class LocalizationManagerTest {

    @Test
    public void testEnglishLocalization() {
        LocalizationManager.setLocale(Locale.ENGLISH);
        assertEquals("Play", LocalizationManager.get("menu.play"));
        assertEquals("Exit", LocalizationManager.get("menu.exit"));
        assertEquals("Snake", LocalizationManager.get("game.title"));
    }

    @Test
    public void testRussianLocalization() {
        LocalizationManager.setLocale(new Locale("ru"));
        assertEquals("Играть по уровням", LocalizationManager.get("menu.play"));
        assertEquals("Выход", LocalizationManager.get("menu.exit"));
        assertEquals("Змейка", LocalizationManager.get("game.title"));
    }

    @Test
    public void testMissingKeyThrowsException() {
        LocalizationManager.setLocale(Locale.ENGLISH);
        assertThrows(Exception.class, () -> {
            LocalizationManager.get("non.existent.key");
        });
    }

    @Test
    public void testMissingLocaleThrowsException() {
        assertThrows(RuntimeException.class, () -> {
            LocalizationManager.setLocale(Locale.JAPANESE);
        });
    }
}
