package io.gitee.nn.electron.api;

import io.gitee.nn.electron.api.entities.*;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@SuppressWarnings("unused")
public final class Dialog {
    private volatile static Dialog dialog;

    Dialog() {
    }

    static Dialog getInstance() {
        if (dialog == null) {
            synchronized (Dialog.class) {
                if (dialog == null) {
                    dialog = new Dialog();
                }
            }
        }
        return dialog;
    }

    public CompletableFuture<String[]> ShowOpenDialogAsync(BrowserWindow browserWindow, OpenDialogOptions options) {
        var taskCompletionSource = new CompletableFuture<String[]>();
        String guid = UUID.randomUUID().toString();

        BridgeConnector.getSocket().on("showOpenDialogComplete" + guid, (filePaths) ->
        {
            BridgeConnector.getSocket().off("showOpenDialogComplete" + guid);

            var result = Electron.fromJsonString(filePaths[0].toString(), ArrayList.class, String.class);
            var list = new ArrayList<String>();
            for (var item : result) {
                list.add(URLDecoder.decode(item, StandardCharsets.UTF_8));
            }
            taskCompletionSource.complete(list.toArray(new String[0]));
        });

        BridgeConnector.getSocket().emit("showOpenDialog",
                Electron.toJsonObject(browserWindow),
                Electron.toJsonObject(options),
                guid);

        return taskCompletionSource;
    }

    public CompletableFuture<String> ShowSaveDialogAsync(BrowserWindow browserWindow, SaveDialogOptions options) {
        var taskCompletionSource = new CompletableFuture<String>();
        String guid = UUID.randomUUID().toString();

        BridgeConnector.getSocket().on("showSaveDialogComplete" + guid, (filename) ->
        {
            BridgeConnector.getSocket().off("showSaveDialogComplete" + guid);

            taskCompletionSource.complete(filename[0].toString());
        });

        BridgeConnector.getSocket().emit("showSaveDialog",
                Electron.toJsonObject(browserWindow),
                Electron.toJsonObject(options),
                guid);

        return taskCompletionSource;
    }

    public CompletableFuture<MessageBoxResult> ShowMessageBoxAsync(String message) {
        return ShowMessageBoxAsync(null, new MessageBoxOptions(message));
    }

    public CompletableFuture<MessageBoxResult> ShowMessageBoxAsync(MessageBoxOptions messageBoxOptions) {
        return ShowMessageBoxAsync(null, messageBoxOptions);
    }

    public CompletableFuture<MessageBoxResult> ShowMessageBoxAsync(BrowserWindow browserWindow, String message) {
        return ShowMessageBoxAsync(browserWindow, new MessageBoxOptions(message));
    }

    public CompletableFuture<MessageBoxResult> ShowMessageBoxAsync(BrowserWindow browserWindow, MessageBoxOptions messageBoxOptions) {
        var taskCompletionSource = new CompletableFuture<MessageBoxResult>();
        var guid = UUID.randomUUID().toString();

        BridgeConnector.getSocket().on("showMessageBoxComplete" + guid, (args) ->
        {
            BridgeConnector.getSocket().off("showMessageBoxComplete" + guid);
            var result = Electron.fromJsonString(args[0].toString(), ArrayList.class, Object.class);
            taskCompletionSource.complete(new MessageBoxResult((Integer) result.get(0), (Boolean) result.get(1)));
        });

        if (browserWindow == null) {
            BridgeConnector.getSocket().emit("showMessageBox", Electron.toJsonObject(messageBoxOptions), guid);
        } else {
            BridgeConnector.getSocket().emit("showMessageBox",
                    Electron.toJsonObject(browserWindow),
                    Electron.toJsonObject(messageBoxOptions),
                    guid);
        }

        return taskCompletionSource;
    }

    public void ShowErrorBox(String title, String content) {
        BridgeConnector.getSocket().emit("showErrorBox", title, content);
    }

    public CompletableFuture<Void> ShowCertificateTrustDialogAsync(CertificateTrustDialogOptions options) {
        return ShowCertificateTrustDialogAsync(null, options);
    }

    public CompletableFuture<Void> ShowCertificateTrustDialogAsync(BrowserWindow browserWindow, CertificateTrustDialogOptions options) {
        var taskCompletionSource = new CompletableFuture<Void>();
        String guid = UUID.randomUUID().toString();

        BridgeConnector.getSocket().on("showCertificateTrustDialogComplete" + guid, (unused) ->
        {
            BridgeConnector.getSocket().off("showCertificateTrustDialogComplete" + guid);
            taskCompletionSource.complete(null);
        });

        BridgeConnector.getSocket().emit("showCertificateTrustDialog",
                Electron.toJsonObject(browserWindow),
                Electron.toJsonObject(options),
                guid);

        return taskCompletionSource;
    }
}
