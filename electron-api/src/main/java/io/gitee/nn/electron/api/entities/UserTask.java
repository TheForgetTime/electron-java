package io.gitee.nn.electron.api.entities;

import io.gitee.nn.electron.api.util.ResourceFileUtil;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
public class UserTask {
    private String arguments;
    private String description;
    private Integer iconIndex;
    private String iconPath;
    private String program;
    private String title;
    private String workingDirectory;

    public UserTask(String arguments, String description, Integer iconIndex, String iconPath, String program, String title, String workingDirectory) {
        this.arguments = arguments;
        this.description = description;
        this.iconIndex = iconIndex;
        this.iconPath = ResourceFileUtil.UnzipFromJar(iconPath);
        this.program = program;
        this.title = title;
        this.workingDirectory = workingDirectory;
    }

    public void setIconPath(String iconPath) {
        this.iconPath = ResourceFileUtil.UnzipFromJar(iconPath);
    }
}
