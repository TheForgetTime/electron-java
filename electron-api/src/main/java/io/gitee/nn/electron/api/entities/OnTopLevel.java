package io.gitee.nn.electron.api.entities;

import com.fasterxml.jackson.annotation.JsonValue;

public enum OnTopLevel {
    /**
     * The normal
     */
    NORMAL("normal"),

    /**
     * The floating
     */
    FLOATING("floating"),

    /**
     * The torn off menu
     */
    TORN_OFF_MENU("torn-off-menu"),

    /**
     * The modal panel
     */
    MODAL_PANEL("modal-panel"),

    /**
     * The main menu
     */
    MAIN_MENU("main-menu"),

    /**
     * The status
     */
    STATUS("status"),

    /**
     * The pop up menu
     */
    POP_UP_MENU("pop-up-menu"),

    /**
     * The screen saver
     */
    SCREEN_SAVER("screen-saver");

    private final String value;

    OnTopLevel(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }
}
