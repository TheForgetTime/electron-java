package io.gitee.nn.electron.api.entities;

import com.fasterxml.jackson.annotation.JsonValue;

public enum JumpListItemType {
    TASK("task"),
    SEPARATOR("separator"),
    FILE("file");

    private final String value;

    JumpListItemType(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }
}
