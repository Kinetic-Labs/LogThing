package com.github.kinetic.logthing.util.io.log;

import com.github.kinetic.logthing.util.Util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

@SuppressWarnings({"unused"})
public final class LogUtil implements Util {

    private static final String RESET = "\u001B[0m";
    private static final String PURPLE = "\u001B[35m";
    private static final String YELLOW = "\u001B[33m";
    private static final String RED = "\u001B[31m";
    private static final String BRIGHT_RED = "\u001B[91m";
    private static final String GREEN = "\u001B[32m";
    private static final String CYAN = "\u001B[36m";
    private static final DateTimeFormatter DATE_FORMATTER;

    static {
        DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    }

    /**
     * Log information
     * @param message information being logged
     */
    public void info(final String message) {
        log(message, LogLevel.INFO);
    }

    /**
     * Log a warning
     * @param message the warning message
     */
    public void warn(final String message) {
        log(message, LogLevel.WARN);
    }

    /**
     * Log an error
     * @param message the error message
     */
    public void error(final String message) {
        log(message, LogLevel.ERROR);
    }

    /**
     * Log a fatal message
     * @param message the fatal message
     */
    public void fatal(final String message) {
        log(message, LogLevel.FATAL);
    }

    /**
     * Print a debug log
     * @param message the debug message
     */
    public void debug(final String message) {
        log(message, LogLevel.DEBUG);
    }

    /**
     * Prints a stacktrace and message
     * @param message   the message to accompany the stacktrace
     * @param exception the exception caught
     */
    public void trace(final String message, final Exception exception) {
        log(message, LogLevel.TRACE);
        log("Stack Trace: ", LogLevel.TRACE);
        log(Arrays.toString(exception.getStackTrace()), LogLevel.TRACE);
    }

    private void log(final String msg, final LogLevel level) {
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
