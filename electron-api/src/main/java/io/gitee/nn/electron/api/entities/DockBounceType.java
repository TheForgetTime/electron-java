package io.gitee.nn.electron.api.entities;

import com.fasterxml.jackson.annotation.JsonValue;

public enum DockBounceType {
    /**
     * Dock icon will bounce until either the application becomes active or the request is canceled.
     */
    CRITICAL("critical"),

    /**
     * The dock icon will bounce for one second.
     */
    INFORMATIONAL("informational");

    private final String value;

    DockBounceType(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }
}
