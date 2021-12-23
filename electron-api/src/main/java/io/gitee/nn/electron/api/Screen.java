package io.gitee.nn.electron.api;

import io.gitee.nn.electron.api.entities.Display;
import io.gitee.nn.electron.api.entities.Point;
import io.gitee.nn.electron.api.entities.Rectangle;
import io.gitee.nn.electron.api.function.Action1;
import io.gitee.nn.electron.api.function.Action2;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@SuppressWarnings("unused")
public final class Screen {
    private volatile static Screen _screen;
    private final List<Action1<Display>> _onDisplayAdded = new ArrayList<>();
    private final List<Action1<Display>> _onDisplayRemoved = new ArrayList<>();
    private final List<Action2<Display, String[]>> _onDisplayMetricsChanged = new ArrayList<>();

    Screen() {
    }

    static Screen getInstance() {
        if (_screen == null) {
            synchronized (Screen.class) {
                if (_screen == null) {
                    _screen = new Screen();
                }
            }
        }
        return _screen;
    }

    public void AddOnDisplayAdded(Action1<Display> value) {
        if (_onDisplayAdded.size() == 0) {
            BridgeConnector.getSocket().on("screen-display-added-event" + hashCode(), (display) ->
                    _onDisplayAdded.forEach(it -> it.accept(Electron.fromJsonString(display[0].toString(), Display.class))));

            BridgeConnector.getSocket().emit("register-screen-display-added", hashCode());
        }
        _onDisplayAdded.add(value);
    }

    public void RemoveOnDisplayAdded(Action1<Display> value) {
        _onDisplayAdded.remove(value);

        if (_onDisplayAdded.size() == 0)
            BridgeConnector.getSocket().off("screen-display-added-event" + hashCode());
    }

    public void AddOnDisplayRemoved(Action1<Display> value) {
        if (_onDisplayRemoved.size() == 0) {
            BridgeConnector.getSocket().on("screen-display-removed-event" + hashCode(), (display) ->
                    _onDisplayRemoved.forEach(it -> it.accept(Electron.fromJsonString(display[0].toString(), Display.class))));

            BridgeConnector.getSocket().emit("register-screen-display-removed", hashCode());
        }
        _onDisplayRemoved.add(value);
    }

    public void RemoveOnDisplayRemoved(Action1<Display> value) {
        _onDisplayRemoved.remove(value);

        if (_onDisplayRemoved.size() == 0)
            BridgeConnector.getSocket().off("screen-display-removed-event" + hashCode());
    }

    public void AddOnDisplayMetricsChanged(Action2<Display, String[]> value) {
        if (_onDisplayMetricsChanged.size() == 0) {
            BridgeConnector.getSocket().on("screen-display-metrics-changed-event" + hashCode(), (args) ->
            {
                var argList = Electron.fromJsonString(args[0].toString(), ArrayList.class, Object.class);
                var display = (Display) argList.get(0);
                var metrics = (String[]) argList.get(1);

                _onDisplayMetricsChanged.forEach(it -> it.accept(display, metrics));
            });

            BridgeConnector.getSocket().emit("register-screen-display-metrics-changed", hashCode());
        }
        _onDisplayMetricsChanged.add(value);
    }

    public void RemoveOnDisplayMetricsChanged(Action2<Display, String[]> value) {
        _onDisplayMetricsChanged.remove(value);

        if (_onDisplayMetricsChanged.size() == 0)
            BridgeConnector.getSocket().off("screen-display-metrics-changed-event" + hashCode());
    }

    public CompletableFuture<Point> GetCursorScreenPointAsync() {
        var taskCompletionSource = new CompletableFuture<Point>();

        BridgeConnector.getSocket().on("screen-getCursorScreenPointCompleted", (point) ->
        {
            BridgeConnector.getSocket().off("screen-getCursorScreenPointCompleted");
            taskCompletionSource.complete(Electron.fromJsonString(point[0].toString(), Point.class));
        });

        BridgeConnector.getSocket().emit("screen-getCursorScreenPoint");

        return taskCompletionSource;
    }

    public CompletableFuture<Integer> GetMenuBarHeightAsync() {
        var taskCompletionSource = new CompletableFuture<Integer>();

        BridgeConnector.getSocket().on("screen-getMenuBarHeightCompleted", (height) ->
        {
            BridgeConnector.getSocket().off("screen-getMenuBarHeightCompleted");

            taskCompletionSource.complete(Integer.parseInt(height[0].toString()));
        });

        BridgeConnector.getSocket().emit("screen-getMenuBarHeight");

        return taskCompletionSource;
    }

    public CompletableFuture<Display> GetPrimaryDisplayAsync() {
        var taskCompletionSource = new CompletableFuture<Display>();

        BridgeConnector.getSocket().on("screen-getPrimaryDisplayCompleted", (display) ->
        {
            BridgeConnector.getSocket().off("screen-getPrimaryDisplayCompleted");

            taskCompletionSource.complete(Electron.fromJsonString(display[0].toString(), Display.class));
        });

        BridgeConnector.getSocket().emit("screen-getPrimaryDisplay");

        return taskCompletionSource;
    }

    public CompletableFuture<Display[]> GetAllDisplaysAsync() {
        var taskCompletionSource = new CompletableFuture<Display[]>();

        BridgeConnector.getSocket().on("screen-getAllDisplaysCompleted", (displays) ->
        {
            BridgeConnector.getSocket().off("screen-getAllDisplaysCompleted");
            var displayList = Electron.fromJsonString(displays[0].toString(), ArrayList.class, Display.class);
            taskCompletionSource.complete(displayList.toArray(new Display[0]));
        });

        BridgeConnector.getSocket().emit("screen-getAllDisplays");

        return taskCompletionSource;
    }

    public CompletableFuture<Display> GetDisplayNearestPointAsync(Point point) {
        var taskCompletionSource = new CompletableFuture<Display>();

        BridgeConnector.getSocket().on("screen-getDisplayNearestPointCompleted", (display) ->
        {
            BridgeConnector.getSocket().off("screen-getDisplayNearestPointCompleted");

            taskCompletionSource.complete(Electron.fromJsonString(display[0].toString(), Display.class));
        });

        BridgeConnector.getSocket().emit("screen-getDisplayNearestPoint", Electron.toJsonObject(point));

        return taskCompletionSource;
    }

    public CompletableFuture<Display> GetDisplayMatchingAsync(Rectangle rectangle) {
        var taskCompletionSource = new CompletableFuture<Display>();

        BridgeConnector.getSocket().on("screen-getDisplayMatching", (display) ->
        {
            BridgeConnector.getSocket().off("screen-getDisplayMatching");

            taskCompletionSource.complete(Electron.fromJsonString(display[0].toString(), Display.class));
        });

        BridgeConnector.getSocket().emit("screen-getDisplayMatching", Electron.toJsonObject(rectangle));

        return taskCompletionSource;
    }
}
