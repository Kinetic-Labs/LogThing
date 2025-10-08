package com.github.kinetic.logthing.config;

import com.github.kinetic.logthing.config.keys.InputKey;
import com.github.kinetic.logthing.config.keys.ProcessorKey;

/**
 * Config record for LogThing's config system
 *
 * @param inputKey     input key record
 * @param processorKey processor key record
 */
public record LogThingConfig(InputKey inputKey, ProcessorKey processorKey) {
}
