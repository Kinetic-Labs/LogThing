package com.github.kinetic.logthing.util.types;

import java.io.Serializable;

/**
 * Represents parsed log
 *
 * @param timestamp the timestamp of the log
 * @param tag       the tag of log (e.g., thread name)
 * @param level     the log level
 * @param message   the log message
 */
public record ParsedLog(String timestamp, String tag, String level, String message) implements Serializable {
}
