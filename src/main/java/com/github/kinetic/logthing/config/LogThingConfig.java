package com.github.kinetic.logthing.config;

import java.util.List;

/**
 * Config record for LogThing's config system
 *
 * @param inputsFileLogPath  the directory to scan for new logs
 * @param inputsFileLogKinds the log levels to be expected
 * @param processorPattern   the pattern to use to parse logs
 */
public record LogThingConfig(String inputsFileLogPath, List<Object> inputsFileLogKinds, String processorPattern) {
}
