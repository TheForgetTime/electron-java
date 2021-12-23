package io.gitee.nn.electron.api.entities;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ThemeSourceMode {
    /**
     * Operating system default.
     */
    SYSTEM("system"),

    /**
     * Light theme.
     */
    LIGHT("light"),

    /**
     * Dark theme.
     */
    DARK("dark");

    private final String value;

    ThemeSourceMode(String value) {
        this.value = value;
    }

    public static ThemeSourceMode fromString(String value) {
        switch (value) {
            case "dark":
                return ThemeSourceMode.DARK;
            case "light":
                return ThemeSourceMode.LIGHT;
            default:
                return ThemeSourceMode.SYSTEM;
        }
    }

    @Override
    public String toString() {
        return value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }
}
