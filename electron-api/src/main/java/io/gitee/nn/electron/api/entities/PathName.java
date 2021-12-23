package io.gitee.nn.electron.api.entities;

import com.fasterxml.jackson.annotation.JsonValue;

public enum PathName {
    HOME("home"),
    APP_DATA("appData"),
    USER_DATA("userData"),
    CACHE("cache"),
    TEMP("temp"),
    EXE("exe"),
    MODULE("module"),
    DESKTOP("desktop"),
    DOCUMENTS("documents"),
    DOWNLOADS("downloads"),
    MUSIC("music"),
    PICTURES("pictures"),
    VIDEOS("videos"),
    RECENT("recent"),
    LOGS("logs"),
    CRASH_DUMPS("crashDumps");

    private final String value;

    PathName(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }
}
