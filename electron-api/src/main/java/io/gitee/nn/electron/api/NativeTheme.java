package io.gitee.nn.electron.api;

import io.gitee.nn.electron.api.entities.ThemeSourceMode;
import io.gitee.nn.electron.api.function.Action0;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@SuppressWarnings("unused")
public final class NativeTheme {
    private volatile static NativeTheme nativeTheme;
    private final List<Action0> _updated = new ArrayList<>();

    NativeTheme() {
    }

    static NativeTheme getInstance() {
        if (nativeTheme == null) {
            synchronized (NativeTheme.class) {
                if (nativeTheme == null) {
                    nativeTheme = new NativeTheme();
                }
            }
        }
        return nativeTheme;
    }

    public void SetThemeSource(ThemeSourceMode themeSourceMode) {
        var themeSource = themeSourceMode.getValue();

        BridgeConnector.getSocket().emit("nativeTheme-themeSource", themeSource);
    }

    public CompletableFuture<ThemeSourceMode> GetThemeSourceAsync() {
        var taskCompletionSource = new CompletableFuture<ThemeSourceMode>();

        BridgeConnector.getSocket().on("nativeTheme-themeSource-getCompleted", (themeSource) ->
        {
            BridgeConnector.getSocket().off("nativeTheme-themeSource-getCompleted");

            var themeSourceValue = ThemeSourceMode.fromString((String) themeSource[0]);

            taskCompletionSource.complete(themeSourceValue);
        });

        BridgeConnector.getSocket().emit("nativeTheme-themeSource-get");

        return taskCompletionSource;
    }

    public CompletableFuture<Boolean> ShouldUseDarkColorsAsync() {
        var taskCompletionSource = new CompletableFuture<Boolean>();

        BridgeConnector.getSocket().on("nativeTheme-shouldUseDarkColors-completed", (shouldUseDarkColors) -> {
            BridgeConnector.getSocket().off("nativeTheme-shouldUseDarkColors-completed");

            taskCompletionSource.complete((Boolean) shouldUseDarkColors[0]);
        });

        BridgeConnector.getSocket().emit("nativeTheme-shouldUseDarkColors");

        return taskCompletionSource;
    }

    public CompletableFuture<Boolean> ShouldUseHighContrastColorsAsync() {
        var taskCompletionSource = new CompletableFuture<Boolean>();

        BridgeConnector.getSocket().on("nativeTheme-shouldUseHighContrastColors-completed", (shouldUseHighContrastColors) -> {
            BridgeConnector.getSocket().off("nativeTheme-shouldUseHighContrastColors-completed");

            taskCompletionSource.complete((Boolean) shouldUseHighContrastColors[0]);
        });

        BridgeConnector.getSocket().emit("nativeTheme-shouldUseHighContrastColors");

        return taskCompletionSource;
    }

    public CompletableFuture<Boolean> ShouldUseInvertedColorSchemeAsync() {
        var taskCompletionSource = new CompletableFuture<Boolean>();

        BridgeConnector.getSocket().on("nativeTheme-shouldUseInvertedColorScheme-completed", (shouldUseInvertedColorScheme) -> {
            BridgeConnector.getSocket().off("nativeTheme-shouldUseInvertedColorScheme-completed");

            taskCompletionSource.complete((Boolean) shouldUseInvertedColorScheme[0]);
        });

        BridgeConnector.getSocket().emit("nativeTheme-shouldUseInvertedColorScheme");

        return taskCompletionSource;
    }

    public void AddOnUpdated(Action0 value) {
        if (_updated.size() == 0) {
            BridgeConnector.getSocket().on("nativeTheme-updated" + hashCode(), (unused) -> _updated.forEach(Action0::accept));

            BridgeConnector.getSocket().emit("register-nativeTheme-updated-event", hashCode());
        }
        _updated.add(value);
    }

    public void RemoveOnUpdated(Action0 value) {
        _updated.remove(value);

        if (_updated.size() == 0) {
            BridgeConnector.getSocket().off("nativeTheme-updated" + hashCode());
        }
    }
}
