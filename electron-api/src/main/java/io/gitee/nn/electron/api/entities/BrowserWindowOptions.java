package io.gitee.nn.electron.api.entities;

import io.gitee.nn.electron.api.BrowserWindow;
import io.gitee.nn.electron.api.util.ResourceFileUtil;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
public class BrowserWindowOptions {
    @Builder.Default
    private Integer width = 800;
    @Builder.Default
    private Integer height = 600;
    @Builder.Default
    private Integer x = -1;
    @Builder.Default
    private Integer y = -1;
    private Boolean useContentSize;
    private Boolean center;
    private Integer minWidth;
    private Integer minHeight;
    private Integer maxWidth;
    private Integer maxHeight;
    @Builder.Default
    private Boolean resizable = true;
    @Builder.Default
    private Boolean movable = true;
    @Builder.Default
    private Boolean minimizable = true;
    @Builder.Default
    private Boolean maximizable = true;
    @Builder.Default
    private Boolean closable = true;
    @Builder.Default
    private Boolean focusable = true;
    private Boolean alwaysOnTop;
    private Boolean fullscreen;
    @Builder.Default
    private Boolean fullscreenable = true;
    @Builder.Default
    private Boolean simpleFullscreen = true;
    private Boolean skipTaskbar;
    private Boolean kiosk;
    @Builder.Default
    private String title = "Electron.Java";
    private String icon;
    @Builder.Default
    private Boolean show = true;
    @Builder.Default
    private Boolean paIntegerWhenInitiallyHidden = true;
    @Builder.Default
    private Boolean frame = true;
    private BrowserWindow parent;
    private Boolean modal;
    private Boolean acceptFirstMouse;
    private Boolean disableAutoHideCursor;
    private Boolean autoHideMenuBar;
    private Boolean enableLargerThanScreen;
    private String backgroundColor;
    private Boolean hasShadow;
    @Builder.Default
    private Float opacity = 1.0F;
    private Boolean darkTheme;
    private Boolean transparent;
    private String type;
    private VisualEffectState visualEffectState;
    private TitleBarStyle titleBarStyle;
    private Point trafficLightPosition;
    @Builder.Default
    private Boolean roundedCorners = true;
    private Boolean fullscreenWindowTitle;
    @Builder.Default
    private Boolean thickFrame = true;
    private Vibrancy vibrancy;
    private Boolean zoomToPageWidth;
    private String tabbingIdentifier;
    private WebPreferences webPreferences = new WebPreferences();
    private TitleBarOverlay titleBarOverlay;

    public BrowserWindowOptions(Integer width, Integer height, Integer x, Integer y, Boolean useContentSize, Boolean center, Integer minWidth, Integer minHeight, Integer maxWidth, Integer maxHeight, Boolean resizable, Boolean movable, Boolean minimizable, Boolean maximizable, Boolean closable, Boolean focusable, Boolean alwaysOnTop, Boolean fullscreen, Boolean fullscreenable, Boolean simpleFullscreen, Boolean skipTaskbar, Boolean kiosk, String title, String icon, Boolean show, Boolean paIntegerWhenInitiallyHidden, Boolean frame, BrowserWindow parent, Boolean modal, Boolean acceptFirstMouse, Boolean disableAutoHideCursor, Boolean autoHideMenuBar, Boolean enableLargerThanScreen, String backgroundColor, Boolean hasShadow, Float opacity, Boolean darkTheme, Boolean transparent, String type, VisualEffectState visualEffectState, TitleBarStyle titleBarStyle, Point trafficLightPosition, Boolean roundedCorners, Boolean fullscreenWindowTitle, Boolean thickFrame, Vibrancy vibrancy, Boolean zoomToPageWidth, String tabbingIdentifier, WebPreferences webPreferences, TitleBarOverlay titleBarOverlay) {
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;
        this.useContentSize = useContentSize;
        this.center = center;
        this.minWidth = minWidth;
        this.minHeight = minHeight;
        this.maxWidth = maxWidth;
        this.maxHeight = maxHeight;
        this.resizable = resizable;
        this.movable = movable;
        this.minimizable = minimizable;
        this.maximizable = maximizable;
        this.closable = closable;
        this.focusable = focusable;
        this.alwaysOnTop = alwaysOnTop;
        this.fullscreen = fullscreen;
        this.fullscreenable = fullscreenable;
        this.simpleFullscreen = simpleFullscreen;
        this.skipTaskbar = skipTaskbar;
        this.kiosk = kiosk;
        this.title = title;
        this.icon = ResourceFileUtil.UnzipFromJar(icon);
        this.show = show;
        this.paIntegerWhenInitiallyHidden = paIntegerWhenInitiallyHidden;
        this.frame = frame;
        this.parent = parent;
        this.modal = modal;
        this.acceptFirstMouse = acceptFirstMouse;
        this.disableAutoHideCursor = disableAutoHideCursor;
        this.autoHideMenuBar = autoHideMenuBar;
        this.enableLargerThanScreen = enableLargerThanScreen;
        this.backgroundColor = backgroundColor;
        this.hasShadow = hasShadow;
        this.opacity = opacity;
        this.darkTheme = darkTheme;
        this.transparent = transparent;
        this.type = type;
        this.visualEffectState = visualEffectState;
        this.titleBarStyle = titleBarStyle;
        this.trafficLightPosition = trafficLightPosition;
        this.roundedCorners = roundedCorners;
        this.fullscreenWindowTitle = fullscreenWindowTitle;
        this.thickFrame = thickFrame;
        this.vibrancy = vibrancy;
        this.zoomToPageWidth = zoomToPageWidth;
        this.tabbingIdentifier = tabbingIdentifier;
        this.webPreferences = webPreferences;
        this.titleBarOverlay = titleBarOverlay;
    }

    public void setIcon(String icon) {
        this.icon = ResourceFileUtil.UnzipFromJar(icon);
    }
}
