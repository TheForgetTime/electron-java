package io.gitee.nn.electron.api.entities;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ShortcutLinkOperation {
    CREATE("create"),
    UPDATE("update"),
    REPLACE("replace");

    private final String value;

    ShortcutLinkOperation(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }
}
