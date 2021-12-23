package io.gitee.nn.electron.api.entities;

import com.fasterxml.jackson.annotation.JsonValue;

public enum MenuType {
    NORMAL("normal"),
    SEPARATOR("separator"),
    SUBMENU("submenu"),
    CHECKBOX("checkbox"),
    RADIO("radio");

    private final String value;

    MenuType(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }
}
