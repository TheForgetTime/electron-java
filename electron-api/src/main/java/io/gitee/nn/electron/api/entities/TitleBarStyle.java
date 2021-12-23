package io.gitee.nn.electron.api.entities;

import com.fasterxml.jackson.annotation.JsonEnumDefaultValue;
import com.fasterxml.jackson.annotation.JsonValue;

public enum TitleBarStyle {
    @JsonEnumDefaultValue
    DEFAULT("default"),
    HIDDEN("hidden"),
    HIDDEN_INSET("hiddenInset"),
    CUSTOM_BUTTONS_ON_HOVER("customButtonsOnHover");

    private final String value;

    TitleBarStyle(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }
}
