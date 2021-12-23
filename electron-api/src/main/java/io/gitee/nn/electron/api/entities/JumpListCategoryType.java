package io.gitee.nn.electron.api.entities;

import com.fasterxml.jackson.annotation.JsonValue;

public enum JumpListCategoryType {
    TASKS("tasks"),
    FREQUENT("frequent"),
    RECENT("recent"),
    CUSTOM("custom");

    private final String value;

    JumpListCategoryType(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }
}
