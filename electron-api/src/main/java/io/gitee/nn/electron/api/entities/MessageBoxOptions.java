package io.gitee.nn.electron.api.entities;

import io.gitee.nn.electron.api.util.ResourceFileUtil;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
public class MessageBoxOptions {
    /**
     * Can be "none", "info", "error", "question" or "warning". On Windows, "question"
     * displays the same icon as "info", unless you set an icon using the "icon"
     * option. On macOS, both "warning" and "error" display the same warning icon.
     */
    private MessageBoxType type;

    /**
     * Array of texts for buttons. On Windows, an empty array will result in one button
     * labeled "OK".
     */
    private String[] buttons;

    /**
     * Index of the button in the buttons array which will be selected by default when
     * the message box opens.
     */
    private int defaultId;

    /**
     * Title of the message box, some platforms will not show it.
     */
    private String title;

    /**
     * Content of the message box.
     */
    private String message;

    /**
     * Extra information of the message.
     */
    private String detail;

    /**
     * If provided, the message box will include a checkbox with the given label. The
     * checkbox state can be inspected only when using callback.
     */
    private String checkboxLabel;

    /**
     * Initial checked state of the checkbox. false by default.
     */
    private Boolean checkboxChecked;

    /**
     * Gets or sets the icon.
     */


    private String icon;

    /**
     * The index of the button to be used to cancel the dialog, via the Esc key. By
     * default this is assigned to the first button with "cancel" or "no" as the label.
     * If no such labeled buttons exist and this option is not set, 0 will be used as
     * the return value or callback response. This option is ignored on Windows.
     */
    private int cancelId;

    /**
     * On Windows Electron will try to figure out which one of the buttons are common
     * buttons(like "Cancel" or "Yes"), and show the others as command links in the
     * dialog.This can make the dialog appear in the style of modern Windows apps. If
     * you don't like this behavior, you can set noLink to true.
     */
    private Boolean noLink;

    /**
     * Normalize the keyboard access keys across platforms. Default is false. Enabling
     * this assumes AND character is used in the button labels for the placement of the keyboard
     * shortcut access key and labels will be converted so they work correctly on each
     * platform, AND characters are removed on macOS, converted to _ on Linux, and left
     * untouched on Windows.For example, a button label of VieANDw will be converted to
     * Vie_w on Linux and View on macOS and can be selected via Alt-W on Windows and
     * Linux.
     */
    private Boolean normalizeAccessKeys;

    /**
     * Initializes a new instance of the <see cref="MessageBoxOptions"/> class.
     */

    public MessageBoxOptions(String message) {
        this.message = message;
    }

    public MessageBoxOptions(MessageBoxType type, String[] buttons, int defaultId, String title, String message, String detail, String checkboxLabel, Boolean checkboxChecked, String icon, int cancelId, Boolean noLink, Boolean normalizeAccessKeys) {
        this.type = type;
        this.buttons = buttons;
        this.defaultId = defaultId;
        this.title = title;
        this.message = message;
        this.detail = detail;
        this.checkboxLabel = checkboxLabel;
        this.checkboxChecked = checkboxChecked;
        this.icon = ResourceFileUtil.UnzipFromJar(icon);
        this.cancelId = cancelId;
        this.noLink = noLink;
        this.normalizeAccessKeys = normalizeAccessKeys;
    }

    public void setIcon(String icon) {
        this.icon = ResourceFileUtil.UnzipFromJar(icon);
    }
}
