package io.gitee.nn.electron.api;

import io.gitee.nn.electron.api.entities.LoadURLOptions;
import io.gitee.nn.electron.api.entities.PrintOptions;
import io.gitee.nn.electron.api.entities.PrintToPDFOptions;
import io.gitee.nn.electron.api.function.Action0;
import io.gitee.nn.electron.api.function.Action1;

import javax.print.attribute.standard.PrinterInfo;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentLinkedQueue;

@SuppressWarnings("unused")
public final class WebContents {
    private final int id;
    private final ConcurrentLinkedQueue<Action1<Boolean>> crashed = new ConcurrentLinkedQueue<>();
    private final ConcurrentLinkedQueue<Action0> didFinishLoad = new ConcurrentLinkedQueue<>();
    private Session session;

    WebContents(int id) {
        this.id = id;
        this.session = new Session(id);
    }

    public int getId() {
        return id;
    }

    public Session getSession() {
        return session;
    }

    void setSession(Session session) {
        this.session = session;
    }

    public void AddOnCrashed(Action1<Boolean> value) {
        if (crashed.size() == 0) {
            BridgeConnector.getSocket().on("webContents-crashed" + id, (killed) ->
                    crashed.forEach(it -> it.accept((Boolean) killed[0])));

            BridgeConnector.getSocket().emit("register-webContents-crashed", id);
        }
        crashed.add(value);
    }

    public void RemoveOnCrashed(Action1<Boolean> value) {
        crashed.remove(value);
        if (crashed.size() == 0)
            BridgeConnector.getSocket().off("webContents-crashed" + id);
    }

    public void AddOnDidFinishLoad(Action0 value) {
        if (didFinishLoad.size() == 0) {
            BridgeConnector.getSocket().on("webContents-didFinishLoad" + id, (unused) ->
                    didFinishLoad.forEach(Action0::accept));

            BridgeConnector.getSocket().emit("register-webContents-didFinishLoad", id);
        }
        didFinishLoad.add(value);
    }

    public void RemoveOnDidFinishLoad(Action0 value) {
        didFinishLoad.remove(value);

        if (didFinishLoad.size() == 0)
            BridgeConnector.getSocket().off("webContents-didFinishLoad" + id);
    }

    public void OpenDevTools() {
        BridgeConnector.getSocket().emit("webContentsOpenDevTools", id);
    }

    public CompletableFuture<List<PrinterInfo>> GetPrintersAsync() {
        var taskCompletionSource = new CompletableFuture<List<PrinterInfo>>();

        BridgeConnector.getSocket().on("webContents-getPrinters-completed", (printers) ->
        {
            BridgeConnector.getSocket().off("webContents-getPrinters-completed");

            taskCompletionSource.complete(Electron.fromJsonString(printers[0].toString(), ArrayList.class, PrinterInfo.class));
        });

        BridgeConnector.getSocket().emit("webContents-getPrinters", id);

        return taskCompletionSource;
    }

    public CompletableFuture<Boolean> PrintAsync(PrintOptions options) {
        var taskCompletionSource = new CompletableFuture<Boolean>();

        BridgeConnector.getSocket().on("webContents-print-completed", (success) ->
        {
            BridgeConnector.getSocket().off("webContents-print-completed");
            taskCompletionSource.complete((Boolean) success[0]);
        });

        if (options == null) {
            BridgeConnector.getSocket().emit("webContents-print", id, "");
        } else {
            BridgeConnector.getSocket().emit("webContents-print", id, Electron.toJsonObject(options));
        }

        return taskCompletionSource;
    }

    public CompletableFuture<Boolean> PrintToPDFAsync(String path) {
        return PrintToPDFAsync(path, null);
    }

    public CompletableFuture<Boolean> PrintToPDFAsync(String path, PrintToPDFOptions options) {
        var taskCompletionSource = new CompletableFuture<Boolean>();

        BridgeConnector.getSocket().on("webContents-printToPDF-completed", (success) ->
        {
            BridgeConnector.getSocket().off("webContents-printToPDF-completed");
            taskCompletionSource.complete((Boolean) success[0]);
        });

        if (options == null) {
            BridgeConnector.getSocket().emit("webContents-printToPDF", id, "", path);
        } else {
            BridgeConnector.getSocket().emit("webContents-printToPDF", id, Electron.toJsonObject(options), path);
        }

        return taskCompletionSource;
    }

    public CompletableFuture<String> GetUrl() {
        var taskCompletionSource = new CompletableFuture<String>();

        var eventString = "webContents-getUrl" + id;
        BridgeConnector.getSocket().on(eventString, (url) ->
        {
            BridgeConnector.getSocket().off(eventString);
            taskCompletionSource.complete((String) url[0]);
        });

        BridgeConnector.getSocket().emit("webContents-getUrl", id);

        return taskCompletionSource;
    }

    public CompletableFuture<Void> LoadURLAsync(String url) {
        return LoadURLAsync(url, new LoadURLOptions());
    }

    public CompletableFuture<Void> LoadURLAsync(String url, LoadURLOptions options) {
        var taskCompletionSource = new CompletableFuture<Void>();

        BridgeConnector.getSocket().on("webContents-loadURL-complete" + id, (unused) ->
        {
            BridgeConnector.getSocket().off("webContents-loadURL-complete" + id);
            BridgeConnector.getSocket().off("webContents-loadURL-error" + id);
            taskCompletionSource.complete(null);
        });

        BridgeConnector.getSocket().on("webContents-loadURL-error" + id, (error) ->
        {
            BridgeConnector.getSocket().off("webContents-loadURL-error" + id);
            taskCompletionSource.completeExceptionally(new IllegalStateException(error[0].toString()));
        });

        BridgeConnector.getSocket().emit("webContents-loadURL", id, url, Electron.toJsonObject(options));

        return taskCompletionSource;
    }


    public void InsertCSS(boolean isBrowserWindow, String path) {
        BridgeConnector.getSocket().emit("webContents-insertCSS", id, isBrowserWindow, path);
    }
}
