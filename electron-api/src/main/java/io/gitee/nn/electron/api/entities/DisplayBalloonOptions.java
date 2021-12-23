package io.gitee.nn.electron.api.entities;

import io.gitee.nn.electron.api.util.ResourceFileUtil;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
public class DisplayBalloonOptions {
    /**
     * Gets or sets the icon.
     */
    private String icon;

    /**
     * Gets or sets the title.
     */
    private String title;

    /**
     * Gets or sets the content.
     */
    private String content;

    public void setIcon(String icon) {
        this.icon = ResourceFileUtil.UnzipFromJar(icon);
        ;
    }

    public DisplayBalloonOptions(String icon, String title, String content) {
        this.icon = ResourceFileUtil.UnzipFromJar(icon);
        ;
        this.title = title;
        this.content = content;
    }
}
