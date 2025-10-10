package com.github.kinetic.logthing.config.impl;

import com.github.kinetic.logthing.config.IConfig;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("unused")
public final class MiscSettings implements IConfig {

    private final String settingsName;
    private final Map<String, Map<String, Object>> settings;

    public MiscSettings(final String name) {
        this.settingsName = name;
        this.settings = new HashMap<>();
    }

    public void add(final String name, final String key, final Object value) {
        settings.computeIfAbsent(name, _ -> new HashMap<>()).put(key, value);
    }

    public Map<String, Object> get(final String name) {
        return settings.get(name);
    }

    public String getSettingsName() {
        return settingsName;
    }

    public Map<String, Map<String, Object>> getAllSettings() {
        return settings;
    }
}
