package com.github.kinetic.logthing.config.impl;

import com.github.kinetic.logthing.config.IConfig;

import java.util.HashMap;
import java.util.Map;

/**
 * Miscellaneous settings
 */
@SuppressWarnings("unused")
public final class MiscSettings implements IConfig {

    /**
     * Name of the settings
     */
    private final String settingsName;

    /**
     * Settings
     */
    private final Map<String, Map<String, Object>> settings;

    /**
     * Create a new {@link MiscSettings}
     *
     * @param name the name of the settings
     */
    public MiscSettings(final String name) {
        this.settingsName = name;
        this.settings = new HashMap<>();
    }

    /**
     * Add a setting to the settings map
     *
     * @param name  the name of the settings map
     * @param key   the key of the setting
     * @param value the value of the setting
     */
    public void add(final String name, final String key, final Object value) {
        settings.computeIfAbsent(name, _ -> new HashMap<>()).put(key, value);
    }

    /**
     * Get a setting from the settings map
     *
     * @param name the name of the settings map
     * @return the setting
     */
    public Map<String, Object> get(final String name) {
        return settings.get(name);
    }

    /**
     * Get the name of the settings map
     *
     * @return the name of the settings map
     */
    public String getSettingsName() {
        return settingsName;
    }

    /**
     * Get all the settings
     *
     * @return all the settings
     */
    public Map<String, Map<String, Object>> getAllSettings() {
        return settings;
    }
}
