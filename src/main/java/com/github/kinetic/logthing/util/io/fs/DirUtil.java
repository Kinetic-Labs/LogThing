package com.github.kinetic.logthing.util.io.fs;

import java.nio.file.Path;

public record DirUtil(Path directory) {

    /**
     * Creates the directory represented by the {@code directory} field if it does not already exist.
     * <p>
     * If the directory already exists, the method returns immediately without making any changes.
     * If the directory does not exist, it attempts to create it, including all necessary
     * but nonexistent parent directories.
     * </p>
     *
     * @throws RuntimeException if the directory cannot be created
     */
    public void create() {
        if(exists())
            return;

        final boolean result = directory.toFile().mkdirs();

        if(!result)
            throw new RuntimeException("Failed to create directory: " + directory);
    }

    /**
     * Checks if the directory represented by the {@code directory} field exists.
     *
     * @return true if the directory exists; false otherwise
     */
    public boolean exists() {
        return directory.toFile().exists();
    }
}
