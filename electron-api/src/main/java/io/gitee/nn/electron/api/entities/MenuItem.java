package io.gitee.nn.electron.api.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.gitee.nn.electron.api.function.Action0;
import io.gitee.nn.electron.api.util.ResourceFileUtil;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
public class MenuItem {
    @JsonIgnore
    private Action0 click;
    private MenuRole role;
    private MenuType type;
    private String label;
    private String sublabel;
    private String accelerator;
    private String icon;
    @Builder.Default
    private Boolean enabled = true;
    @Builder.Default
    private Boolean visible = true;
    private Boolean checked;
    private MenuItem[] submenu;
    private String id;
    private String position;

    public MenuItem(Action0 click, MenuRole role, MenuType type, String label, String sublabel, String accelerator, String icon, Boolean enabled, Boolean visible, Boolean checked, MenuItem[] submenu, String id, String position) {
        this.click = click;
        this.role = role;
        this.type = type;
        this.label = label;
        this.sublabel = sublabel;
        this.accelerator = accelerator;
        this.icon = ResourceFileUtil.UnzipFromJar(icon);
        this.enabled = enabled;
        this.visible = visible;
        this.checked = checked;
        this.submenu = submenu;
        this.id = id;
        this.position = position;
    }

    public void setIcon(String icon) {
        this.icon = ResourceFileUtil.UnzipFromJar(icon);
    }
}
