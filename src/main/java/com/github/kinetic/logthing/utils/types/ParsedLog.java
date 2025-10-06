package com.github.kinetic.logthing.utils.types;

/**
 * Represents parsed log
 *
 * @param level   the log level
 * @param message the log message
 */
public record ParsedLog(String level, String message) {
}
