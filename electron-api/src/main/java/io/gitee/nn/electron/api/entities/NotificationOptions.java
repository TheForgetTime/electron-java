package io.gitee.nn.electron.api.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.gitee.nn.electron.api.function.Action0;
import io.gitee.nn.electron.api.function.Action1;
import io.gitee.nn.electron.api.util.ResourceFileUtil;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
public class NotificationOptions {
    /**
     * Gets or sets the show identifier.
     */
    @JsonProperty
    String showID;
    /**
     * Gets or sets the click identifier.
     */
    @JsonProperty
    String clickID;
    /**
     * Gets or sets the close identifier.
     */
    @JsonProperty
    String closeID;
    /**
     * Gets or sets the reply identifier.
     */
    @JsonProperty
    String replyID;
    /**
     * Gets or sets the action identifier.
     */
    @JsonProperty
    String actionID;
    /**
     * Emitted when the notification is shown to the user, note this could be fired
     * multiple times as a notification can be shown multiple times through the Show()
     * method.
     */
    @JsonIgnore
    private Action0 onShow;
    /**
     * Emitted when the notification is clicked by the user.
     */
    @JsonIgnore
    private Action0 onClick;
    /**
     * Emitted when the notification is closed by manual intervention from the user.
     * This event is not guarunteed to be emitted in all cases where the notification is closed.
     */
    @JsonIgnore
    private Action0 onClose;
    /**
     * macOS only: Emitted when the user clicks the “Reply” button on a notification with hasReply: true.
     * The String the user entered into the inline reply field
     */
    @JsonIgnore
    private Action1<String> onReply;
    /**
     * macOS only - The index of the action that was activated
     */
    @JsonIgnore
    private Action1<String> onAction;
    /**
     * A title for the notification, which will be shown at the top of the notification
     * window when it is shown.
     */
    private String title;
    /**
     * A subtitle for the notification, which will be displayed below the title.
     */
    private String subTitle;
    /**
     * The body text of the notification, which will be displayed below the title or
     * subtitle.
     */
    private String body;
    /**
     * Whether or not to emit an OS notification noise when showing the notification.
     */
    private Boolean silent;
    /**
     * An icon to use in the notification.
     */
    private String icon;
    /**
     * Whether or not to add an inline reply option to the notification.
     */
    private Boolean hasReply;
    /**
     * The timeout duration of the notification. Can be 'default' or 'never'.
     */
    private String timeoutType;
    /**
     * The placeholder to write in the inline reply input field.
     */
    private String replyPlaceholder;
    /**
     * The name of the sound file to play when the notification is shown.
     */
    private String sound;
    /**
     * The urgency level of the notification. Can be 'normal', 'critical', or 'low'.
     */
    private String urgency;
    /**
     * Actions to add to the notification. Please read the available actions and
     * limitations in the NotificationAction documentation.
     */
    private NotificationAction actions;
    /**
     * A custom title for the close button of an alert. An empty String will cause the
     * default localized text to be used.
     */
    private String closeButtonText;

    /**
     * Initializes a new instance of the <see cref="NotificationOptions"/> class.
     *
     * @param title The title.
     * @param body  The body.
     */
    public NotificationOptions(String title, String body) {
        this.title = title;
        this.body = body;
    }

    public NotificationOptions(String showID, String clickID, String closeID, String replyID, String actionID, Action0 onShow, Action0 onClick, Action0 onClose, Action1<String> onReply, Action1<String> onAction, String title, String subTitle, String body, Boolean silent, String icon, Boolean hasReply, String timeoutType, String replyPlaceholder, String sound, String urgency, NotificationAction actions, String closeButtonText) {
        this.showID = showID;
        this.clickID = clickID;
        this.closeID = closeID;
        this.replyID = replyID;
        this.actionID = actionID;
        this.onShow = onShow;
        this.onClick = onClick;
        this.onClose = onClose;
        this.onReply = onReply;
        this.onAction = onAction;
        this.title = title;
        this.subTitle = subTitle;
        this.body = body;
        this.silent = silent;
        this.icon = ResourceFileUtil.UnzipFromJar(icon);
        this.hasReply = hasReply;
        this.timeoutType = timeoutType;
        this.replyPlaceholder = replyPlaceholder;
        this.sound = sound;
        this.urgency = urgency;
        this.actions = actions;
        this.closeButtonText = closeButtonText;
    }

    public void setIcon(String icon) {
        this.icon = ResourceFileUtil.UnzipFromJar(icon);
    }
}
