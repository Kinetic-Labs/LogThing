package com.github.kinetic.logthing.config.impl;

import com.github.kinetic.logthing.config.AbstractConfigParser;
import com.github.kinetic.logthing.config.keys.*;
import com.github.kinetic.logthing.config.type.LogThingConfig;
import com.github.kinetic.logthing.exception.InvalidConfigException;
import org.tomlj.Toml;
import org.tomlj.TomlArray;
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

        result.errors().forEach(error -> log.error("ConfigError: " + error.toString()));

        final String inputFile_path = result.getString("inputs.file.path");
        final List<Object> inputFileLogKinds = checkNullKey(result.getArray("inputs.file.log_kinds")).toList();
        final String processorTimestamp_pattern = checkNullKey(result.getString("processor.timestamp_pattern"));
        final String processorLevel_pattern = checkNullKey(result.getString("processor.level_pattern"));
        final String processorTag_pattern = checkNullKey(result.getString("processor.tag_pattern"));
        final long webPort = checkNullKey(result.getLong("web.port"));
        final String alertsErrorSpike_condition = result.getString("alerts.error_spike.condition");
        final String alertsErrorSpike_window = result.getString("alerts.error_spike.window");

        final InputFileKey inputFileKey = new InputFileKey(inputFile_path, inputFileLogKinds);
        final InputKey inputKey = new InputKey(inputFileKey);
        final ProcessorKey processorKey = new ProcessorKey(
                processorLevel_pattern,
                processorTimestamp_pattern,
                processorTag_pattern
        );
        final WebKey webKey = new WebKey(webPort);
        final AlertKey alertKey = new AlertKey(alertsErrorSpike_condition, alertsErrorSpike_window);

        return new LogThingConfig(inputKey, processorKey, webKey, alertKey);
    }

    private String checkNullKey(final String pattern) {
        if(pattern == null)
            return "";

        return pattern;
    }

    private TomlArray checkNullKey(final TomlArray key) {
        return Objects.requireNonNull(key);
    }

    private long checkNullKey(final Long value) {
        if(value == null)
            return 0L;

        return value;
    }
}
