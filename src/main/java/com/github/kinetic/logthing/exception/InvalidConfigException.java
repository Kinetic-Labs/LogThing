package com.github.kinetic.logthing.exception;

/**
 * An exception thrown when the config is invalid
 */
public final class InvalidConfigException extends RuntimeException {

    /**
     * Create a new {@link InvalidConfigException}
     * @param message the message to display
     */
    public InvalidConfigException(final String message) {
        super(message);
    }

}
