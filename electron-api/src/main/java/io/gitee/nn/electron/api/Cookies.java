package io.gitee.nn.electron.api;

import io.gitee.nn.electron.api.entities.Cookie;
import io.gitee.nn.electron.api.entities.CookieChangedCause;
import io.gitee.nn.electron.api.entities.CookieDetails;
import io.gitee.nn.electron.api.entities.CookieFilter;
import io.gitee.nn.electron.api.function.Action3;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@SuppressWarnings("unused")
public final class Cookies {
    private final int id;
    private final List<Action3<Cookie, CookieChangedCause, Boolean>> changed = new ArrayList<>();

    Cookies(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void AddOnChanged(Action3<Cookie, CookieChangedCause, Boolean> value) {
        if (changed.size() == 0) {
            BridgeConnector.getSocket().on("webContents-session-cookies-changed" + id, (args) ->
            {
                var argList = Electron.fromJsonString(args[0].toString(), ArrayList.class, Object.class);
                Cookie cookie = (Cookie) argList.get(0);
                CookieChangedCause cause = (CookieChangedCause) argList.get(1);
                Boolean removed = (Boolean) argList.get(2);
                changed.forEach(it -> it.accept(cookie, cause, removed));
            });

            BridgeConnector.getSocket().emit("register-webContents-session-cookies-changed", id);
        }
        changed.add(value);
    }

    public void RemoveOnChanged(Action3<Cookie, CookieChangedCause, Boolean> value) {
        changed.remove(value);

        if (changed.size() == 0)
            BridgeConnector.getSocket().off("webContents-session-cookies-changed" + id);
    }

    public CompletableFuture<Cookie[]> GetAsync(CookieFilter filter) {
        var taskCompletionSource = new CompletableFuture<Cookie[]>();
        String guid = UUID.randomUUID().toString();

        BridgeConnector.getSocket().on("webContents-session-cookies-get-completed" + guid, (cookies) ->
        {
            var result = Electron.fromJsonString(cookies[0].toString(), ArrayList.class, Cookie.class);

            BridgeConnector.getSocket().off("webContents-session-cookies-get-completed" + guid);
            taskCompletionSource.complete(result.toArray(new Cookie[0]));
        });

        BridgeConnector.getSocket().emit("webContents-session-cookies-get", id, Electron.toJsonObject(filter), guid);

        return taskCompletionSource;
    }

    public CompletableFuture<Void> SetAsync(CookieDetails details) {
        var taskCompletionSource = new CompletableFuture<Void>();
        String guid = UUID.randomUUID().toString();

        BridgeConnector.getSocket().on("webContents-session-cookies-set-completed" + guid, (unused) ->
        {
            BridgeConnector.getSocket().off("webContents-session-cookies-set-completed" + guid);
            taskCompletionSource.complete(null);
        });

        BridgeConnector.getSocket().emit("webContents-session-cookies-set", id, Electron.toJsonObject(details), guid);

        return taskCompletionSource;
    }

    public CompletableFuture<Void> RemoveAsync(String url, String name) {
        var taskCompletionSource = new CompletableFuture<Void>();
        String guid = UUID.randomUUID().toString();

        BridgeConnector.getSocket().on("webContents-session-cookies-remove-completed" + guid, (unused) ->
        {
            BridgeConnector.getSocket().off("webContents-session-cookies-remove-completed" + guid);
            taskCompletionSource.complete(null);
        });

        BridgeConnector.getSocket().emit("webContents-session-cookies-remove", id, url, name, guid);

        return taskCompletionSource;
    }

    public CompletableFuture<Void> FlushStoreAsync() {
        var taskCompletionSource = new CompletableFuture<Void>();
        String guid = UUID.randomUUID().toString();

        BridgeConnector.getSocket().on("webContents-session-cookies-flushStore-completed" + guid, (unused) ->
        {
            BridgeConnector.getSocket().off("webContents-session-cookies-flushStore-completed" + guid);
            taskCompletionSource.complete(null);
        });

        BridgeConnector.getSocket().emit("webContents-session-cookies-flushStore", id, guid);

        return taskCompletionSource;
    }

}
