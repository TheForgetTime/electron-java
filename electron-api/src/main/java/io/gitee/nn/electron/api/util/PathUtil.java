package io.gitee.nn.electron.api.util;

import java.io.File;

public class PathUtil {
    public static String GetFileNameWithoutExtension(String path) {
        var name = new File(path).getPath();
        int dotIndex = name.lastIndexOf(".");
        if (dotIndex != -1) {
            name = name.substring(0, dotIndex);
        }
        return name;
    }

    public static String GetExtension(String path) {
        var name = new File(path).getPath();
        int dotIndex = name.lastIndexOf(".");
        if (dotIndex != -1) {
            return name.substring(dotIndex + 1);
        } else {
            return "";
        }
    }
}
