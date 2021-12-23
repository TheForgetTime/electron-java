package io.gitee.nn.electron.api.entities;

import com.fasterxml.jackson.annotation.JsonValue;

public enum VisualEffectState {
    FOLLOW_WINDOW("followWindow"),
    ACTIVE("active"),
    INACTIVE("inactive");

    private final String value;

    VisualEffectState(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }
}
