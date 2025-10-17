package com.github.kinetic.logthing.util.io.log;

import com.github.kinetic.logthing.LogThing;
import com.github.kinetic.logthing.util.Util;
import com.github.kinetic.logthing.util.io.fs.DirUtil;
import com.github.kinetic.logthing.util.io.fs.FileUtil;
import com.github.kinetic.logthing.util.misc.AnsiUtil;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@SuppressWarnings({"unused"})
public final class LogUtil implements Util {

    /**
     * Colors
     */
    private static final String RESET = "\u001B[0m";
    private static final String PURPLE = "\u001B[35m";
    private static final String YELLOW = "\u001B[33m";
    private static final String RED = "\u001B[31m";
    private static final String BRIGHT_RED = "\u001B[91m";
    private static final String GREEN = "\u001B[32m";
    private static final String CYAN = "\u001B[36m";

    /**
     * Date formatter
     */
    private static final DateTimeFormatter DATE_FORMATTER;

    /**
     * Log file name
     */
    private static final String LOG_FILE_NAME;

    static {
        DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
        LOG_FILE_NAME = LocalDateTime.now().format(DATE_FORMATTER);
    }

    /**
     * Log information
     *
     * @param message information being logged
     */
    public void info(final String message) {
        log(message, LogLevel.INFO);
    }

    /**
     * Log a warning
     *
     * @param message the warning message
     */
    public void warn(final String message) {
        log(message, LogLevel.WARN);
    }

    /**
     * Log an error
     *
     * @param message the error message
     */
    public void error(final String message) {
        log(message, LogLevel.ERROR);
    }

    /**
     * Log a fatal message
     *
     * @param message the fatal message
     */
    public void fatal(final String message) {
        log(message, LogLevel.FATAL);
    }

    /**
     * Print a debug log
     *
     * @param message the debug message
     */
    public void debug(final String message) {
        final boolean debug = LogThing.getInstance().isDebugMode();

        // don't print if debug mode is disabled
        if(debug)
            log(message, LogLevel.DEBUG);
    }

    /**
     * Prints a stacktrace and message
     *
     * @param message   the message to accompany the stacktrace
     * @param exception the exception caught
     */
    public void trace(final String message, final Exception exception) {
        final String padding = stringUtil.indent(2);

        log(message, LogLevel.TRACE);
        log("Stack Trace: ", LogLevel.TRACE);

        // print elements of stacktrace
        for(StackTraceElement element : exception.getStackTrace())
            log(padding + element.toString(), LogLevel.TRACE);
    }

    private void log(final String msg, final LogLevel level) {
        final String timestamp = LocalDateTime.now().format(DATE_FORMATTER);
        final String finalMessage = getFinalMessage(msg, level, timestamp);
        final DirUtil logDirectory = new DirUtil(Path.of("kinetic.logthing"));
        final FileUtil logFile = new FileUtil(Path.of("kinetic.logthing", LOG_FILE_NAME + ".log"));
        final AnsiUtil ansiUtil = new AnsiUtil(finalMessage);

        logDirectory.create();
        logFile.append(ansiUtil.stripAnsi());

        System.out.print(finalMessage);
    }

    @NotNull
    private String getFinalMessage(String msg, LogLevel level, String timestamp) {
        final String threadName = Thread.currentThread().getName();
        final String colorCode = getColorForLevel(level);
        final String levelName = level.name();
        final String threadCol = String.format("[%s]", threadName);
        final String levelCol = String.format("[%s]", levelName);

        return String.format(
                "%s%s %-6s %-8s %s%s%n",
                colorCode,
                timestamp,
                threadCol,
                levelCol,
                msg,
                RESET
        );
    }

    @NotNull
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
