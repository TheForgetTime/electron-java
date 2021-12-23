package io.gitee.nn.electron.api.entities;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ThumbarButtonFlag {
    /**
     * The button is active and available to the user.
     */
    ENABLED("enabled"),

    /**
     * The button is disabled.It is present, but has a visual state indicating it will not respond to user action.
     */
    DISABLED("disabled"),

    /**
     * When the button is clicked, the thumbnail window closes immediately.
     */
    DISMISS_ON_CLICK("dismissonclick"),

    /**
     * Do not draw a button border, use only the image.
     */
    NO_BACKGROUND("nobackground"),

    /**
     * The button is not shown to the user.
     */
    HIDDEN("hidden"),

    /**
     * The button is enabled but not interactive; no pressed button state is drawn.This value is intended for instances where the button is used in a notification.
     */
    NONINTERACTIVE("noninteractive");

    private final String value;

    ThumbarButtonFlag(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }
}
