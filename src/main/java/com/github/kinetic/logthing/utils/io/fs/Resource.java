package com.github.kinetic.logthing.utils.io.fs;

import com.github.kinetic.logthing.utils.Utils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@SuppressWarnings("unused")
public record Resource(String domain, String path) implements Utils {

    public String read() {
        final String resourcePath = domain + path;
        final InputStream in = getClass().getClassLoader().getResourceAsStream(resourcePath);
        final byte[] content;

        try(in) {
            if(in != null) {
                content = in.readAllBytes();
            } else {
                log.error("Resource not found: " + resourcePath);
                return null;
            }
        } catch(final IOException ioException) {
            log.trace("Failed to read resource: " + resourcePath, ioException);
            return null;
        }

        return new String(content, StandardCharsets.UTF_8);
    }

    public boolean exists() {
        final String resourcePath = domain + path;
        final InputStream in = getClass().getClassLoader().getResourceAsStream(resourcePath);

        if(in != null) {
            try {
                in.close();
                return true;
            } catch(IOException ex) {
                log.trace("Error closing stream while checking existence", ex);
                return false;
            }
        }

        return false;
    }

    public byte[] readBytes() {
        final String resourcePath = domain + path;
        final InputStream in = getClass().getClassLoader().getResourceAsStream(resourcePath);

        try(in) {
            if(in != null) {
                return in.readAllBytes();
            } else {
                log.error("Resource not found: " + resourcePath);
                return null;
            }
        } catch(IOException ex) {
            log.trace("Failed to read resource bytes: " + resourcePath, ex);
            return null;
        }
    }

    public String getFullPath() {
        return domain + path;
    }
}
