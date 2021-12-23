package io.gitee.nn.electron.api.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginItemSettings {
    /**
     * <see langword="true"/> if the app is set to open at login.
     */
    private Boolean openAtLogin;

    /**
     * <see langword="true"/> if the app is set to open as hidden at login. This setting is not available
     * on <see href="https://www.electronjs.org/docs/tutorial/mac-app-store-submission-guide">MAS builds</see>.
     */
    private Boolean openAsHidden;

    /**
     * <see langword="true"/> if the app was opened at login automatically. This setting is not available
     * on <see href="https://www.electronjs.org/docs/tutorial/mac-app-store-submission-guide">MAS builds</see>.
     */
    private Boolean wasOpenedAtLogin;

    /**
     * <see langword="true"/> if the app was opened as a hidden login item. This indicates that the app should not
     * open any windows at startup. This setting is not available on
     * <see href="https://www.electronjs.org/docs/tutorial/mac-app-store-submission-guide">MAS builds</see>.
     */
    private Boolean wasOpenedAsHidden;

    /**
     * <see langword="true"/> if the app was opened as a login item that should restore the state from the previous
     * session. This indicates that the app should restore the windows that were open the last time the app was closed.
     * This setting is not available on <see href="https://www.electronjs.org/docs/tutorial/mac-app-store-submission-guide">MAS builds</see>.
     */
    private Boolean restoreState;
}
