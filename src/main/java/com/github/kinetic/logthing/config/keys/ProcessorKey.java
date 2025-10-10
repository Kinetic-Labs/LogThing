package com.github.kinetic.logthing.config.keys;

/**
 * Processor key record
 *
 * @param processorLevelPattern the pattern for log levels
 * @param timestampPattern      the pattern for timestamps
 * @param tagPattern            the pattern for tags (e.g., thread names)
 */
public record ProcessorKey(String processorLevelPattern, String timestampPattern, String tagPattern) {
}
