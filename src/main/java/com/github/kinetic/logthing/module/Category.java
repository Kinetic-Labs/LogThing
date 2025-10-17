package com.github.kinetic.logthing.module;

/**
 * Category of the module
 */
@SuppressWarnings("unused")
public enum Category {
    WEB("Web"),
    MISC("Misc"),
    IO("IO"),
    DATA("Data");

    /**
     * The display name of the category
     */
    private final String displayName;

    /**
     * Create a new {@link Category}
     *
     * @param displayName the display name of the category
     */
    Category(final String displayName) {
        this.displayName = displayName;
    }

    /**
     * Get the display name of the category
     *
     * @return the display name of the category
     */
    public final String getDisplayName() {
        return displayName;
    }
}
