package io.gitee.nn.electron.api;

import io.gitee.nn.electron.api.function.Action0;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@SuppressWarnings("unused")
public final class GlobalShortcut {
    private volatile static GlobalShortcut globalShortcut;
    private final Map<String, Action0> shortcuts = new HashMap<>();

    GlobalShortcut() {
    }

    static GlobalShortcut getInstance() {
        if (globalShortcut == null) {
            synchronized (GlobalShortcut.class) {
                if (globalShortcut == null) {
                    globalShortcut = new GlobalShortcut();
                }
            }
        }
        return globalShortcut;
    }

    public void Register(String accelerator, Action0 function) {
        if (!shortcuts.containsKey(accelerator)) {
            shortcuts.put(accelerator, function);

            BridgeConnector.getSocket().off("globalShortcut-pressed");
            BridgeConnector.getSocket().on("globalShortcut-pressed", (shortcut) ->
            {
                if (shortcuts.containsKey(shortcut[0].toString())) {
                    shortcuts.get(shortcut[0].toString()).accept();
                }
            });

            BridgeConnector.getSocket().emit("globalShortcut-register", accelerator);
        }
    }

    public CompletableFuture<Boolean> IsRegisteredAsync(String accelerator) {
        var taskCompletionSource = new CompletableFuture<Boolean>();

        BridgeConnector.getSocket().on("globalShortcut-isRegisteredCompleted", (isRegistered) ->
        {
            BridgeConnector.getSocket().off("globalShortcut-isRegisteredCompleted");

            taskCompletionSource.complete((Boolean) isRegistered[0]);
        });

        BridgeConnector.getSocket().emit("globalShortcut-isRegistered", accelerator);

        return taskCompletionSource;
    }

    public void Unregister(String accelerator) {
        shortcuts.remove(accelerator);
        BridgeConnector.getSocket().emit("globalShortcut-unregister", accelerator);
    }

    public void UnregisterAll() {
        shortcuts.clear();
        BridgeConnector.getSocket().emit("globalShortcut-unregisterAll");
    }
}
