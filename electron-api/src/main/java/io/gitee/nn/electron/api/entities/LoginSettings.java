package io.gitee.nn.electron.api.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginSettings {
    /**
     * <see langword="true"/> to open the app at login, <see langword="false"/> to remove the app as a login item.
     * Defaults to <see langword="false"/>.
     */
    private Boolean openAtLogin;

    /**
     * <see langword="true"/> to open the app as hidden. Defaults to <see langword="false"/>. The user can edit this
     * setting from the System Preferences so app.getLoginItemSettings().wasOpenedAsHidden should be checked when the app is
     * opened to know the current value. This setting is not available on <see href="https://www.electronjs.org/docs/tutorial/mac-app-store-submission-guide">MAS builds</see>.
     */
    private Boolean openAsHidden;

    /**
     * The executable to launch at login. Defaults to process.execPath.
     */
    private String path;

    /**
     * The command-line arguments to pass to the executable. Defaults to an empty
     * array.Take care to wrap paths in quotes.
     */
    private String[] args;
}
