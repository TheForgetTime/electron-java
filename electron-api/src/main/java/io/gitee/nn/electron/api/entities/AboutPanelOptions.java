package io.gitee.nn.electron.api.entities;

import io.gitee.nn.electron.api.util.ResourceFileUtil;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * About panel options.
 */
@Data
@Builder
@NoArgsConstructor
public class AboutPanelOptions {
    /**
     * The app's name.
     */
    private String applicationName;

    /**
     * The app's version.
     */
    private String applicationVersion;

    /**
     * Copyright information.
     */
    private String copyright;

    /**
     * The app's build version number.
     */
    private String version;

    /**
     * Credit information.
     */
    private String credits;

    /**
     * List of app authors.
     */
    private String[] authors;

    /**
     * The app's website.
     */
    private String website;

    /**
     * Path to the app's icon. On Linux, will be shown as 64x64 pixels while retaining aspect ratio.
     */
    private String iconPath;

    public void setIconPath(String iconPath) {
        this.iconPath = ResourceFileUtil.UnzipFromJar(iconPath);
    }

    public AboutPanelOptions(String applicationName, String applicationVersion, String copyright, String version, String credits, String[] authors, String website, String iconPath) {
        this.applicationName = applicationName;
        this.applicationVersion = applicationVersion;
        this.copyright = copyright;
        this.version = version;
        this.credits = credits;
        this.authors = authors;
        this.website = website;
        this.iconPath = ResourceFileUtil.UnzipFromJar(iconPath);
    }
}
