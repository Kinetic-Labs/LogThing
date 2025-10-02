package com.github.kinetic.logthing.utils.io.log;

import com.github.kinetic.logthing.utils.Utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

@SuppressWarnings({"unused"})
public class LogUtils extends Utils {

    private static final String RESET = "\u001B[0m";
    private static final String PURPLE = "\u001B[35m";
    private static final String YELLOW = "\u001B[33m";
    private static final String RED = "\u001B[31m";
    private static final String BRIGHT_RED = "\u001B[91m";
    private static final String GREEN = "\u001B[32m";
    private static final String CYAN = "\u001B[36m";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public LogUtils() {
        super("logutils");
    }

    public void info(String message) {
        log(message, LogLevel.INFO);
    }

    public void warn(String message) {
        log(message, LogLevel.WARN);
    }

    public void error(String message) {
        log(message, LogLevel.ERROR);
    }

    public void fatal(String message) {
        log(message, LogLevel.FATAL);
    }

    public void debug(String message) {
        log(message, LogLevel.DEBUG);
    }

    public void trace(String message, Exception exception) {
        log(message, LogLevel.TRACE);
        log("Stack Trace: ", LogLevel.TRACE);
        log(Arrays.toString(exception.getStackTrace()), LogLevel.TRACE);
    }

    private void log(String msg, LogLevel level) {
        String timestamp = LocalDateTime.now().format(DATE_FORMATTER);
        String threadName = Thread.currentThread().getName();
        String colorCode = getColorForLevel(level);
        String levelName = level.name();
        String threadCol = String.format("[%s]", threadName);
        String levelCol = String.format("[%s]", levelName);

        System.out.printf(
            "%s%s %-6s %-8s %s%s%n",
            colorCode,
            timestamp,
            threadCol,
            levelCol,
            msg,
            RESET
        );
    }

    private String getColorForLevel(LogLevel level) {
        return switch(level) {
            case INFO -> PURPLE;
            case WARN -> YELLOW;
            case ERROR -> RED;
            case FATAL -> BRIGHT_RED;
            case DEBUG -> GREEN;
            case TRACE -> CYAN;
        };
    }
}
