package com.github.kinetic.logthing.config.keys;

import java.util.List;

/**
 * Input key record
 *
 * @param inputsFileLogPath  the directory to scan for new logs
 * @param inputsFileLogKinds the log levels to be expected
 */
public record InputFileKey(String inputsFileLogPath, List<Object> inputsFileLogKinds) {
}
