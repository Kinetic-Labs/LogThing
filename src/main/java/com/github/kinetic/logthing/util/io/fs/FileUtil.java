package com.github.kinetic.logthing.util.io.fs;

import com.github.kinetic.logthing.util.Util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

/**
 * Class to make it easier to work with files and paths
 *
 * @param file the file to work with
 */
@SuppressWarnings("unused")
public record FileUtil(Path file) implements Util {

    /**
     * Reads a file to string
     *
     * @return contents if exists, null if it does not
     */
    public String read() {
        String content;

        try {
            content = Files.readString(this.file.toAbsolutePath());
        } catch(IOException ioException) {
            content = null;

            log.trace("Failed to read content", ioException);
        }

        return content;
    }

    /**
     * Write a string to a file
     *
     * @param content the content to write
     */
    public boolean append(String content) {
        return this.write(
                content,
                StandardOpenOption.CREATE,
                StandardOpenOption.APPEND
        );
    }

    /**
     * Write a string to a file with specified open options
     *
     * @param content the content to write
     * @param options the open options (e.g., APPEND, CREATE)
     */
    public boolean write(String content, OpenOption... options) {
        try {
            Files.writeString(this.file.toAbsolutePath(), content, options);

            return true;
        } catch(IOException ioException) {
            log.trace("Error writing to file", ioException);

            return false;
        }
    }

    /**
     * Checks if the file or directory at the specified file exists.
     *
     * @return true if the file or directory exists; false otherwise
     */
    public boolean exists() {
        return Files.exists(this.file.toAbsolutePath());
    }
}
