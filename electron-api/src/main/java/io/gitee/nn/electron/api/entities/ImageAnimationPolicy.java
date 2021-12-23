package io.gitee.nn.electron.api.entities;

import com.fasterxml.jackson.annotation.JsonEnumDefaultValue;
import com.fasterxml.jackson.annotation.JsonValue;

public enum ImageAnimationPolicy {
    @JsonEnumDefaultValue
    ANIMATE("animate"),
    ANIMATE_ONCE("animateOnce"),
    NO_ANIMATION("noAnimation");
    private final String value;

    ImageAnimationPolicy(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }
}
