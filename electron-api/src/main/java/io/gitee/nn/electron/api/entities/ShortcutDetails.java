package io.gitee.nn.electron.api.entities;

import io.gitee.nn.electron.api.util.ResourceFileUtil;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
public class ShortcutDetails {
    /**
     * The Application User Model ID. Default is <see cref="string.Empty"/>.
     */
    private String appUserModelId;

    /**
     * The arguments to be applied to <see cref="Target"/> when launching from this shortcut. Default is <see cref="string.Empty"/>.
     */
    private String args;

    /**
     * The working directory. Default is <see cref="string.Empty"/>.
     */
    private String cwd;

    /**
     * The description of the shortcut. Default is <see cref="string.Empty"/>.
     */
    private String description;

    /**
     * The path to the icon, can be a DLL or EXE. <see cref="Icon"/> and <see cref="IconIndex"/> have to be set
     * together. Default is <see cref="string.Empty"/>, which uses the target's icon.
     */
    private String icon;

    /**
     * The resource ID of icon when <see cref="Icon"/> is a DLL or EXE. Default is 0.
     */
    private Integer iconIndex;

    /**
     * The target to launch from this shortcut.
     */
    private String target;

    public ShortcutDetails(String appUserModelId, String args, String cwd, String description, String icon, Integer iconIndex, String target) {
        this.appUserModelId = appUserModelId;
        this.args = args;
        this.cwd = cwd;
        this.description = description;
        this.icon = ResourceFileUtil.UnzipFromJar(icon);
        this.iconIndex = iconIndex;
        this.target = target;
    }

    public void setIcon(String icon) {
        this.icon = ResourceFileUtil.UnzipFromJar(icon);
    }
}
