package io.gitee.nn.electron.api.entities;

import io.gitee.nn.electron.api.Session;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WebPreferences {
    @Builder.Default
    private Boolean devTools = true;
    @Builder.Default
    private Boolean nodeIntegration = false;
    private Boolean nodeIntegrationInWorker;
    private Boolean nodeIntegrationInSubFrames;
    private String preload;
    private Boolean sandbox;
    private Session session;
    private String partition;
    private Integer zoomFactor;
    @Builder.Default
    private Boolean javascript = true;
    @Builder.Default
    private Boolean webSecurity = true;
    private Boolean allowRunningInsecureContent;
    @Builder.Default
    private Boolean images = true;
    @Builder.Default
    private ImageAnimationPolicy imageAnimationPolicy = ImageAnimationPolicy.ANIMATE;
    @Builder.Default
    private Boolean textAreasAreResizable = true;
    @Builder.Default
    private Boolean webgl = true;
    private Boolean plugins;
    private Boolean experimentalFeatures;
    private Boolean scrollBounce;
    private String enableBlinkFeatures;
    private String disableBlinkFeatures;
    private DefaultFontFamily defaultFontFamily;
    @Builder.Default
    private Integer defaultFontSize = 16;
    @Builder.Default
    private Integer defaultMonospaceFontSize = 13;
    @Builder.Default
    private Integer minimumFontSize = 0;
    @Builder.Default
    private String defaultEncoding = "ISO-8859-1";
    @Builder.Default
    private Boolean backgroundThrottling = true;
    private Boolean offscreen;
    @Builder.Default
    private Boolean contextIsolation = false;
    @Builder.Default
    private Boolean nativeWindowOpen = true;
    @Builder.Default
    private Boolean webviewTag = false;
    @Builder.Default
    private Boolean enableRemoteModule = false;
    private String[] additionalArguments;
    private Boolean safeDialogs;
    private String safeDialogsMessage;
    @Builder.Default
    private Boolean disableDialogs = false;
}
