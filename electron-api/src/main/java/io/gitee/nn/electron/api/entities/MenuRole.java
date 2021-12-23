package io.gitee.nn.electron.api.entities;

import com.fasterxml.jackson.annotation.JsonValue;

public enum MenuRole {
    UNUSED("unused"),
    UNDO("undo"),
    REDO("redo"),
    CUT("cut"),
    COPY("copy"),
    PASTE("paste"),
    PASTE_AND_MATCH_STYLE("pasteandmatchstyle"),
    SELECT_ALL("selectall"),
    DELETE("delete"),
    MINIMIZE("minimize"),
    CLOSE("close"),
    QUIT("quit"),
    RELOAD("reload"),
    FORCE_RELOAD("forcereload"),
    TOGGLE_DEV_TOOLS("toggledevtools"),
    TOGGLE_FULLSCREEN("togglefullscreen"),
    RESET_ZOOM("resetzoom"),
    ZOOM_IN("zoomin"),
    ZOOM_OUT("zoomout"),
    EDIT_MENU("editMenu"),
    WINDOW_MENU("windowMenu"),
    ABOUT("about"),
    HIDE("hide"),
    HIDE_OTHERS("hideothers"),
    UN_HIDE("unhide"),
    START_SPEAKING("startspeaking"),
    STOP_SPEAKING("stopspeaking"),
    FRONT("front"),
    ZOOM("zoom"),
    WINDOW("window"),
    HELP("help"),
    SERVICES("services");

    private final String value;

    MenuRole(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }
}
