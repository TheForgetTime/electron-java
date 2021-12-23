package io.gitee.nn.electron.api;

import io.gitee.nn.electron.api.entities.Data;
import io.gitee.nn.electron.api.entities.NativeImage;
import io.gitee.nn.electron.api.entities.ReadBookmark;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

@SuppressWarnings("unused")
public final class Clipboard {
    private volatile static Clipboard _clipboard;

    static Clipboard getInstance() {
        if (_clipboard == null) {
            synchronized (Clipboard.class) {
                if (_clipboard == null) {
                    _clipboard = new Clipboard();
                }
            }
        }
        return _clipboard;
    }

    public CompletableFuture<String> ReadTextAsync() {
        return ReadTextAsync("");
    }

    public CompletableFuture<String> ReadTextAsync(String type) {
        var taskCompletionSource = new CompletableFuture<String>();

        BridgeConnector.getSocket().on("clipboard-readText-Completed", (text) ->
        {
            BridgeConnector.getSocket().off("clipboard-readText-Completed");

            taskCompletionSource.complete(text[0].toString());
        });

        BridgeConnector.getSocket().emit("clipboard-readText", type);

        return taskCompletionSource;
    }

    public void WriteText(String text) {
        WriteText(text, "");
    }

    public void WriteText(String text, String type) {
        BridgeConnector.getSocket().emit("clipboard-writeText", text, type);
    }

    public CompletableFuture<String> ReadHTMLAsync() {
        return ReadHTMLAsync("");
    }

    public CompletableFuture<String> ReadHTMLAsync(String type) {
        var taskCompletionSource = new CompletableFuture<String>();

        BridgeConnector.getSocket().on("clipboard-readHTML-Completed", (text) ->
        {
            BridgeConnector.getSocket().off("clipboard-readHTML-Completed");

            taskCompletionSource.complete(text[0].toString());
        });

        BridgeConnector.getSocket().emit("clipboard-readHTML", type);

        return taskCompletionSource;
    }

    public void WriteHTML(String markup) {
        WriteHTML(markup, "");
    }

    public void WriteHTML(String markup, String type) {
        BridgeConnector.getSocket().emit("clipboard-writeHTML", markup, type);
    }

    public CompletableFuture<String> ReadRTFAsync() {
        return ReadRTFAsync("");
    }

    public CompletableFuture<String> ReadRTFAsync(String type) {
        var taskCompletionSource = new CompletableFuture<String>();

        BridgeConnector.getSocket().on("clipboard-readRTF-Completed", (text) ->
        {
            BridgeConnector.getSocket().off("clipboard-readRTF-Completed");

            taskCompletionSource.complete(text[0].toString());
        });

        BridgeConnector.getSocket().emit("clipboard-readRTF", type);

        return taskCompletionSource;
    }

    public void WriteRTF(String text) {
        WriteRTF(text, "");
    }

    public void WriteRTF(String text, String type) {
        BridgeConnector.getSocket().emit("clipboard-writeHTML", text, type);
    }

    public CompletableFuture<ReadBookmark> ReadBookmarkAsync() {
        var taskCompletionSource = new CompletableFuture<ReadBookmark>();

        BridgeConnector.getSocket().on("clipboard-readBookmark-Completed", (bookmark) ->
        {
            BridgeConnector.getSocket().off("clipboard-readBookmark-Completed");

            taskCompletionSource.complete(Electron.fromJsonString(bookmark[0].toString(), ReadBookmark.class));
        });

        BridgeConnector.getSocket().emit("clipboard-readBookmark");

        return taskCompletionSource;
    }

    public void WriteBookmark(String title, String url) {
        WriteBookmark(title, url, "");
    }

    public void WriteBookmark(String title, String url, String type) {
        BridgeConnector.getSocket().emit("clipboard-writeBookmark", title, url, type);
    }

    public CompletableFuture<String> ReadFindTextAsync() {
        var taskCompletionSource = new CompletableFuture<String>();

        BridgeConnector.getSocket().on("clipboard-readFindText-Completed", (text) ->
        {
            BridgeConnector.getSocket().off("clipboard-readFindText-Completed");

            taskCompletionSource.complete(text[0].toString());
        });

        BridgeConnector.getSocket().emit("clipboard-readFindText");

        return taskCompletionSource;
    }

    public void WriteFindText(String text) {
        BridgeConnector.getSocket().emit("clipboard-writeFindText", text);
    }

    public void Clear() {
        Clear("");
    }

    public void Clear(String type) {
        BridgeConnector.getSocket().emit("clipboard-clear", type);
    }

    public CompletableFuture<String[]> AvailableFormatsAsync() {
        return AvailableFormatsAsync("");
    }

    public CompletableFuture<String[]> AvailableFormatsAsync(String type) {
        var taskCompletionSource = new CompletableFuture<String[]>();

        BridgeConnector.getSocket().on("clipboard-availableFormats-Completed", (formats) ->
        {
            BridgeConnector.getSocket().off("clipboard-availableFormats-Completed");
            var result = Electron.fromJsonString(formats[0].toString(), ArrayList.class, String.class);
            taskCompletionSource.complete(result.toArray(new String[0]));
        });

        BridgeConnector.getSocket().emit("clipboard-availableFormats", type);

        return taskCompletionSource;
    }

    public void Write(Data data) {
        Write(data, "");
    }

    public void Write(Data data, String type) {
        BridgeConnector.getSocket().emit("clipboard-write", Electron.toJsonObject(data), type);
    }

    public CompletableFuture<NativeImage> ReadImageAsync() {
        return ReadImageAsync("");
    }

    public CompletableFuture<NativeImage> ReadImageAsync(String type) {
        var taskCompletionSource = new CompletableFuture<NativeImage>();

        BridgeConnector.getSocket().on("clipboard-readImage-Completed", (image) ->
        {
            BridgeConnector.getSocket().off("clipboard-readImage-Completed");

            var nativeImage = Electron.fromJsonString(image[0].toString(), NativeImage.class);

            taskCompletionSource.complete(nativeImage);
        });

        BridgeConnector.getSocket().emit("clipboard-readImage", type);

        return taskCompletionSource;
    }

    public void WriteImage(NativeImage image) {
        WriteImage(image, "");
    }

    public void WriteImage(NativeImage image, String type) {
        BridgeConnector.getSocket().emit("clipboard-writeImage", Electron.toJsonString(image), type);
    }
}
