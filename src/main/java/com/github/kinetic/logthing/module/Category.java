package com.github.kinetic.logthing.module;

public enum Category {
    WEB("Web");

    private final String displayName;

    Category(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
