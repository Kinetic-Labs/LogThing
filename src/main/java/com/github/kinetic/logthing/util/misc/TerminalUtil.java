package com.github.kinetic.logthing.util.misc;

import com.github.kinetic.logthing.LogThing;
import com.github.kinetic.logthing.util.Util;

/**
 * Utilities for working with terminals
 */
public final class TerminalUtil implements Util {

    /**
     * Colors
     */
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_BOLD = "\u001B[1m";
    private static final String ANSI_PURPLE = "\u001B[35m";

    /**
     * Progress bar state
     */
    private static boolean progressBarActive = false;
    private static int currentStep = 0;
    private static int totalSteps = 0;
    private static int lastLineLength = 0;
    private static long startTime = 0;

    /**
     * Initializes a progress tracker with a set number of steps
     *
     * @param steps Total number of steps for this operation
     * @param task  The task name to display throughout (will be displayed in bold)
     */
    public static void startProgress(int steps, String task) {
        final boolean debug = LogThing.getInstance().isDebugMode();
        if(debug) {
            log.debug(task);
            return;
        }

        currentStep = 0;
        totalSteps = steps;
        progressBarActive = false;
        lastLineLength = 0;
        startTime = System.currentTimeMillis();

        drawProgressBar(currentStep, totalSteps, task);
    }

    /**
     * Updates progress to the next step
     *
     * @param task The task name (will be displayed in bold)
     */
    public static void nextStep(String task) {
        final boolean debug = LogThing.getInstance().isDebugMode();
        if(debug)
            return;

        currentStep++;
        drawProgressBar(currentStep, totalSteps, task);
    }

    /**
     * Draws a progress bar that overwrites the current line
     *
     * @param current Current progress value
     * @param total   Total progress value
     * @param task    The current task name (will be displayed in bold)
     */
    public static void drawProgressBar(int current, int total, String task) {
        final boolean debug = LogThing.getInstance().isDebugMode();
        if(debug) {
            log.debug(task + " [" + current + "]");

            return;
        }

        final int barWidth = 13;
        final int percentage = Math.min(100, (int) ((current / (double) total) * 100));
        final int filledWidth = Math.min(barWidth, (int) ((current / (double) total) * barWidth));

        final long elapsed = System.currentTimeMillis() - startTime;
        final String timeStr = formatTime(elapsed);

        final StringBuilder bar = new StringBuilder();

        if(progressBarActive) {
            bar.append("\r");
            bar.append(" ".repeat(Math.max(0, lastLineLength + 1)));
            bar.append("\r");
        }

        bar.append("<");
        bar.append(ANSI_PURPLE);
        bar.append("=".repeat(filledWidth));
        bar.append(ANSI_RESET);
        bar.append("-".repeat(barWidth - filledWidth));
        bar.append("> ");
        bar.append(ANSI_BOLD);
        bar.append(percentage).append("%");
        bar.append(ANSI_RESET);
        bar.append(" ").append(task);
        bar.append(" [").append(timeStr).append("]");

        final String output = bar.toString();
        lastLineLength = output.replaceAll("\u001B\\[[;\\d]*m", "").length();

        System.out.print(output);
        System.out.flush();
        progressBarActive = true;

        if(current >= total) {
            System.out.println();
            progressBarActive = false;
        }
    }

    /**
     * Formats elapsed time in milliseconds or seconds
     *
     * @param millis Elapsed time in milliseconds
     * @return Formatted time string
     */
    private static String formatTime(long millis) {
        if(millis < 1000) {
            return millis + "ms";
        } else {
            return String.format("%ss", Math.round(millis / 1000.0));
        }
    }
}
