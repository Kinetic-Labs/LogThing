package com.github.kinetic.logthing.utils.io.fs;

import com.github.kinetic.logthing.utils.Utils;
import com.github.kinetic.logthing.utils.io.log.LogUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@SuppressWarnings("unused")
public class Resource extends Utils {
    private final String domain;
    private final String path;
    private final LogUtils log;

    public Resource(String domain, String path) {
        this.domain = domain;
        this.path = path;
        this.log = new LogUtils();

        super("resource");
    }

    public String read() {
        String resourcePath = domain + path;
        InputStream in = getClass().getClassLoader().getResourceAsStream(resourcePath);
        byte[] content;

        try(in) {
            if(in != null) {
                content = in.readAllBytes();
            } else {
                log.error("Resource not found: " + resourcePath);
                return null;
            }
        } catch(IOException ioException) {
            log.trace("Failed to read resource: " + resourcePath, ioException);
            return null;
        }

        return new String(content, StandardCharsets.UTF_8);
    }

    public boolean exists() {
        String resourcePath = domain + path;
        InputStream in = getClass().getClassLoader().getResourceAsStream(resourcePath);

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
        String resourcePath = domain + path;
        InputStream in = getClass().getClassLoader().getResourceAsStream(resourcePath);

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

    public String getDomain() {
        return domain;
    }

    public String getPath() {
        return path;
    }

    public String getFullPath() {
        return domain + path;
    }
}
