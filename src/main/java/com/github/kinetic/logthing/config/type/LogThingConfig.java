package com.github.kinetic.logthing.config.type;

import com.github.kinetic.logthing.config.keys.AlertKey;
import com.github.kinetic.logthing.config.keys.InputKey;
import com.github.kinetic.logthing.config.keys.ProcessorKey;
import com.github.kinetic.logthing.config.keys.WebKey;

/**
 * Config record for LogThing's config system
 *
 * @param inputKey     input key record
 * @param processorKey processor key record
 * @param webKey       web key record
 * @param alertKey     alert key record
 */
public record LogThingConfig(InputKey inputKey, ProcessorKey processorKey, WebKey webKey, AlertKey alertKey) {
}
