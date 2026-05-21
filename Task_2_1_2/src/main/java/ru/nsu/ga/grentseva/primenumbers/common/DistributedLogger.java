package ru.nsu.ga.grentseva.primenumbers.common;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class DistributedLogger {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private DistributedLogger() {}

    public static void info(String message) {
        System.out.println(formatMessage("INFO", message));
    }

    public static void error(String message) {
        System.err.println(formatMessage("ERROR", message));
    }

    private static String formatMessage(String level, String message) {
        return "[" + LocalDateTime.now().format(FORMATTER) + "] " + level + " | " + message;
    }
}