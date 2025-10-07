package com.github.kinetic.logthing.util.io.fs;

import com.github.kinetic.logthing.util.Util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Class to make it easier to work with files and paths
 *
 * @param path the path to work with
 */
public record FileUtil(Path path) implements Util {

    /**
     * Reads a file to string
     *
     * @return contents if exists, null if it does not
     */
    public String readToString() {
        String content;

        try {
            content = Files.readString(this.path.toAbsolutePath());
        } catch(IOException ioException) {
            content = null;

            log.trace("Error reading file: " + this.path, ioException);
        }

        return content;
    }
}
