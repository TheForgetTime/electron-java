package io.gitee.nn.electron.api.entities;

import io.gitee.nn.electron.api.util.ResourceFileUtil;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
public class JumpListItem {
    private String args;
    private String description;
    private Integer iconIndex;
    private String iconPath;
    private String path;
    private String program;
    private String title;
    private JumpListItemType type;

    public JumpListItem(String args, String description, Integer iconIndex, String iconPath, String path, String program, String title, JumpListItemType type) {
        this.args = args;
        this.description = description;
        this.iconIndex = iconIndex;
        this.iconPath = ResourceFileUtil.UnzipFromJar(iconPath);
        this.path = path;
        this.program = program;
        this.title = title;
        this.type = type;
    }

    public void setIconPath(String iconPath) {
        this.iconPath = ResourceFileUtil.UnzipFromJar(iconPath);
    }
}
