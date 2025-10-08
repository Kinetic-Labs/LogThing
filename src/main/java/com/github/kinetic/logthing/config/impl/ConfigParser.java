package com.github.kinetic.logthing.config.impl;

import com.github.kinetic.logthing.config.keys.InputKey;
import com.github.kinetic.logthing.config.LogThingConfig;
import com.github.kinetic.logthing.config.AbstractConfigParser;
import com.github.kinetic.logthing.config.keys.InputFileKey;
import com.github.kinetic.logthing.config.keys.ProcessorKey;
import com.github.kinetic.logthing.exception.InvalidConfigException;
import org.tomlj.Toml;
import org.tomlj.TomlParseResult;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;

public final class ConfigParser extends AbstractConfigParser {

    public ConfigParser(final String path) {
        super(path);
    }

    @Override
    public LogThingConfig parse() {
        final Path source = Paths.get(this.getPath());
        final TomlParseResult result;

        try {
            result = Toml.parse(source);
        } catch(IOException ioException) {
            log.trace("Failed to parse config", ioException);

            throw new InvalidConfigException("Failed to parse config");
        }

        result.errors().forEach(error -> log.debug("ConfigError: " + error.toString()));

        final String logInput = result.getString("inputs.file.path");
        final List<Object> logKinds = Objects.requireNonNull(result.getArray("inputs.file.log_kinds")).toList();
        final String processorLevelPattern = result.getString("processor.level_pattern");
        final InputFileKey inputFileKey = new InputFileKey(logInput, logKinds);
        final InputKey inputKey = new InputKey(inputFileKey);
        final ProcessorKey processorKey = new ProcessorKey(processorLevelPattern);

        return new LogThingConfig(inputKey, processorKey);
    }
}
