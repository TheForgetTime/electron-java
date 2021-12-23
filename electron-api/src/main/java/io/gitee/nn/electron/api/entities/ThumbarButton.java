package io.gitee.nn.electron.api.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.gitee.nn.electron.api.function.Action0;
import io.gitee.nn.electron.api.util.ResourceFileUtil;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
public class ThumbarButton {
    @JsonIgnore
    public Action0 click;
    /**
     * Control specific states and behaviors of the button. By default, it is ["enabled"].
     * <p>
     * enabled - The button is active and available to the user.
     * disabled - The button is disabled.It is present, but has a visual state indicating it will not respond to user action.
     * dismissonclick - When the button is clicked, the thumbnail window closes immediately.
     * nobackground - Do not draw a button border, use only the image.
     * hidden - The button is not shown to the user.
     * noninteractive - The button is enabled but not interactive; no pressed button state is drawn.This value is intended for instances where the button is used in a notification.
     */
    @JsonProperty("flags")
    public ThumbarButtonFlag[] flags;
    /**
     * The icon showing in thumbnail toolbar.
     */
    public String icon;
    /**
     * The text of the button's tooltip.
     */
    public String tooltip;
    private String id;

    /**
     * Initializes a new instance of the <see cref="ThumbarButton"/> class.
     */
    public ThumbarButton(String icon) {
        this.icon = ResourceFileUtil.UnzipFromJar(icon);
    }

    public ThumbarButton(Action0 click, ThumbarButtonFlag[] flags, String icon, String tooltip, String id) {
        this.click = click;
        this.flags = flags;
        this.icon = ResourceFileUtil.UnzipFromJar(icon);
        this.tooltip = tooltip;
        this.id = id;
    }

    public void setIcon(String icon) {
        this.icon = ResourceFileUtil.UnzipFromJar(icon);
    }
}
