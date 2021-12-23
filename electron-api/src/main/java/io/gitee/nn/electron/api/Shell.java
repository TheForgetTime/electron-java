package io.gitee.nn.electron.api;

import io.gitee.nn.electron.api.entities.OpenExternalOptions;
import io.gitee.nn.electron.api.entities.ShortcutDetails;
import io.gitee.nn.electron.api.entities.ShortcutLinkOperation;

import java.util.concurrent.CompletableFuture;

@SuppressWarnings("unused")
public final class Shell {
    private volatile static Shell shell;

    Shell() {
    }

    static Shell getInstance() {
        if (shell == null) {
            synchronized (Shell.class) {
                if (shell == null) {
                    shell = new Shell();
                }
            }
        }
        return shell;
    }

    public CompletableFuture<Void> ShowItemInFolderAsync(String fullPath) {
        var taskCompletionSource = new CompletableFuture<Void>();

        BridgeConnector.getSocket().on("shell-showItemInFolderCompleted", (unused) ->
                BridgeConnector.getSocket().off("shell-showItemInFolderCompleted"));

        BridgeConnector.getSocket().emit("shell-showItemInFolder", fullPath);

        return taskCompletionSource;
    }

    public CompletableFuture<String> OpenPathAsync(String path) {
        var taskCompletionSource = new CompletableFuture<String>();

        BridgeConnector.getSocket().on("shell-openPathCompleted", (errorMessage) ->
        {
            BridgeConnector.getSocket().off("shell-openPathCompleted");

            taskCompletionSource.complete(errorMessage[0].toString());
        });

        BridgeConnector.getSocket().emit("shell-openPath", path);

        return taskCompletionSource;
    }

    public CompletableFuture<String> OpenExternalAsync(String url) {
        return OpenExternalAsync(url, null);
    }

    public CompletableFuture<String> OpenExternalAsync(String url, OpenExternalOptions options) {
        var taskCompletionSource = new CompletableFuture<String>();

        BridgeConnector.getSocket().on("shell-openExternalCompleted", (error) ->
        {
            BridgeConnector.getSocket().off("shell-openExternalCompleted");

            taskCompletionSource.complete(error[0].toString());
        });

        if (options == null) {
            BridgeConnector.getSocket().emit("shell-openExternal", url);
        } else {
            BridgeConnector.getSocket().emit("shell-openExternal", url, Electron.toJsonObject(options));
        }

        return taskCompletionSource;
    }

    public CompletableFuture<Boolean> TrashItemAsync(String fullPath) {
        var taskCompletionSource = new CompletableFuture<Boolean>();

        BridgeConnector.getSocket().on("shell-trashItem-completed", (success) ->
        {
            BridgeConnector.getSocket().off("shell-trashItem-completed");

            taskCompletionSource.complete((Boolean) success[0]);
        });

        BridgeConnector.getSocket().emit("shell-trashItem", fullPath);

        return taskCompletionSource;
    }

    public void Beep() {
        BridgeConnector.getSocket().emit("shell-beep");
    }

    public CompletableFuture<Boolean> WriteShortcutLinkAsync(String shortcutPath, ShortcutLinkOperation operation, ShortcutDetails options) {
        var taskCompletionSource = new CompletableFuture<Boolean>();

        BridgeConnector.getSocket().on("shell-writeShortcutLinkCompleted", (success) ->
        {
            BridgeConnector.getSocket().off("shell-writeShortcutLinkCompleted");

            taskCompletionSource.complete((Boolean) success[0]);
        });

        BridgeConnector.getSocket().emit("shell-writeShortcutLink", shortcutPath, operation.getValue(), Electron.toJsonObject(options));

        return taskCompletionSource;
    }

    public CompletableFuture<ShortcutDetails> ReadShortcutLinkAsync(String shortcutPath) {
        var taskCompletionSource = new CompletableFuture<ShortcutDetails>();

        BridgeConnector.getSocket().on("shell-readShortcutLinkCompleted", (shortcutDetails) ->
        {
            BridgeConnector.getSocket().off("shell-readShortcutLinkCompleted");
            taskCompletionSource.complete(Electron.fromJsonString(shortcutDetails[0].toString(), ShortcutDetails.class));
        });

        BridgeConnector.getSocket().emit("shell-readShortcutLink", shortcutPath);
        return taskCompletionSource;
    }
}
