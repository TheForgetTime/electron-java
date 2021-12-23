package io.gitee.nn.electron.api;

import io.gitee.nn.electron.api.entities.AutoResizeOptions;
import io.gitee.nn.electron.api.entities.Rectangle;

import java.util.concurrent.CompletableFuture;

@SuppressWarnings("unused")
public final class BrowserView {
    private final int id;
    private final WebContents webContents;

    public BrowserView(int id) {
        this.id = id;
        this.webContents = new WebContents(id + 1000);
    }

    public int getId() {
        return id;
    }

    public WebContents getWebContents() {
        return webContents;
    }

    public Rectangle getBounds() {
        return CompletableFuture.supplyAsync(() ->
        {
            var taskCompletionSource = new CompletableFuture<Rectangle>();

            BridgeConnector.getSocket().on("browserView-getBounds-reply", (result) ->
            {
                BridgeConnector.getSocket().off("browserView-getBounds-reply");
                taskCompletionSource.complete(Electron.fromJsonString(result[0].toString(), Rectangle.class));
            });

            BridgeConnector.getSocket().emit("browserView-getBounds", id);

            return taskCompletionSource.join();
        }).join();
    }

    public void setBounds(Rectangle value) {
        BridgeConnector.getSocket().emit("browserView-setBounds", id, Electron.toJsonObject(value));
    }

    public void SetAutoResize(AutoResizeOptions options) {
        BridgeConnector.getSocket().emit("browserView-setAutoResize", id, Electron.toJsonObject(options));
    }

    public void SetBackgroundColor(String color) {
        BridgeConnector.getSocket().emit("browserView-setBackgroundColor", id, color);
    }
}
