package io.gitee.nn.electron.api.entities;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ProgressBarMode {
    /**
     * The none
     */
    NONE("none"),

    /**
     * The normal
     */
    NORMAL("normal"),

    /**
     * The indeterminate
     */
    INDETERMINATE("indeterminate"),

    /**
     * The error
     */
    ERROR("error"),

    /**
     * The paused
     */
    PAUSED("paused");

    private final String value;

    ProgressBarMode(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }
}
