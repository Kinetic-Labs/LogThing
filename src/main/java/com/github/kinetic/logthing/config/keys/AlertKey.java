package com.github.kinetic.logthing.config.keys;

/**
 * Alert Key record
 *
 * @param condition the condition to match
 * @param window    the window of time that will trigger alert
 *                  if condition matches
 */
public record AlertKey(String condition, String window) {
}
