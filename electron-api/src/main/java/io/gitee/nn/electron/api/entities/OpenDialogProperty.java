package io.gitee.nn.electron.api.entities;

import com.fasterxml.jackson.annotation.JsonValue;

public enum OpenDialogProperty {
    /**
     * 打开文件
     * The open file
     */
    OPEN_FILE("openFile"),

    /**
     * 打开文件夹
     * The open directory
     */
    OPEN_DIRECTORY("openDirectory"),

    /**
     * 复选
     * The multi selections
     */
    MULTI_SELECTIONS("multiSelections"),

    /**
     * 显示隐藏文件
     * The show hidden files
     */
    SHOW_HIDDEN_FILES("showHiddenFiles"),

    /**
     * 创建文件夹
     * The create directory
     */
    createDirectory("createDirectory"),

    /**
     * 创建提醒
     * The prompt to create
     */
    PROMPT_TO_CREATE("promptToCreate"),

    /**
     * 不解析别名
     * The no resolve aliases
     */
    NO_RESOLVE_ALIASES("noResolveAliases"),

    /**
     * 将包视为目录
     * The treat package as directory
     */
    TREAT_PACKAGE_AS_DIRECTORY("treatPackageAsDirectory"),
    /**
     * 不要将正在打开的项目添加到最近的文档列表中
     */
    DONT_ADD_TO_RECENT("dontAddToRecent");

    private final String value;

    OpenDialogProperty(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }
}
