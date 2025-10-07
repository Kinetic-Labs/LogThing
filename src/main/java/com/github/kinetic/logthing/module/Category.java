package com.github.kinetic.logthing.module;

/**
 * Category of the module
 */
@SuppressWarnings("unused")
public enum Category {
    WEB("Web"),
    MISC("Misc"),
    DATA("Data");

    private final String displayName;

    Category(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
