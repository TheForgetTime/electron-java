package io.gitee.nn.electron.api.entities;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Vibrancy {
    APPEARANCE_BASED("appearance-based"),
    LIGHT("light"),
    DARK("dark"),
    TITLE_BAR("titlebar"),
    SELECTION("selection"),
    MENU("menu"),
    POPOVER("popover"),
    SIDEBAR("sidebar"),
    MEDIUM_LIGHT("medium-light"),
    ULTRA_DARK("ultra-dark"),
    HEADER("header"),
    SHEET("sheet"),
    WINDOW("window"),
    HUD("hud"),
    FULLSCREEN_UI("fullscreen-ui"),
    TOOLTIP("tooltip"),
    CONTENT("content"),
    UNDER_WINDOW("under-window"),
    UNDER_PAGE("under-page");

    private final String value;

    Vibrancy(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }
}