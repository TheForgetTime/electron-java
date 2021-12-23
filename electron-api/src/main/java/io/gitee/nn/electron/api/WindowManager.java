package io.gitee.nn.electron.api;

import io.gitee.nn.electron.api.entities.BrowserViewOptions;
import io.gitee.nn.electron.api.entities.BrowserWindowOptions;
import io.gitee.nn.electron.api.function.Action1;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@SuppressWarnings("unused")
public final class WindowManager {
    private volatile static WindowManager windowManager;
    private final List<BrowserWindow> browserWindows = new ArrayList<>();
    private final List<BrowserView> browserViews = new ArrayList<>();
    private boolean isQuitOnWindowAllClosed = true;

    WindowManager() {
    }

    static WindowManager getInstance() {
        if (windowManager == null) {
            synchronized (WindowManager.class) {
                if (windowManager == null) {
                    windowManager = new WindowManager();
                }
            }
        }
        return windowManager;
    }

    public boolean IsQuitOnWindowAllClosed() {
        return isQuitOnWindowAllClosed;
    }

    public void SetQuitOnWindowAllClosed(boolean value) {
        BridgeConnector.getSocket().emit("quit-app-window-all-closed-event", value);
        isQuitOnWindowAllClosed = value;
    }

    public IReadOnlyCollection<BrowserWindow> GetBrowserWindows() {
        return IReadOnlyCollection.AsReadOnly(browserWindows);
    }

    public IReadOnlyCollection<BrowserView> GetBrowserViews() {
        return IReadOnlyCollection.AsReadOnly(browserViews);
    }

    public CompletableFuture<BrowserWindow> CreateWindowAsync() {
        return CreateWindowAsync("http://localhost");
    }

    public CompletableFuture<BrowserWindow> CreateWindowAsync(String loadUrl) {
        return CreateWindowAsync(new BrowserWindowOptions(), loadUrl);
    }

    public CompletableFuture<BrowserWindow> CreateWindowAsync(BrowserWindowOptions options) {
        return CreateWindowAsync(options, "http://localhost");
    }

    public CompletableFuture<BrowserWindow> CreateWindowAsync(Action1<BrowserWindowOptions> optionsConfigure) {
        return CreateWindowAsync(optionsConfigure, "http://localhost");
    }

    public CompletableFuture<BrowserWindow> CreateWindowAsync(Action1<BrowserWindowOptions> optionsConfigure, String loadUrl) {
        var options = new BrowserWindowOptions();
        optionsConfigure.accept(options);
        return CreateWindowAsync(options, loadUrl);
    }

    public CompletableFuture<BrowserWindow> CreateWindowAsync(BrowserWindowOptions options, String loadUrl) {
        var taskCompletionSource = new CompletableFuture<BrowserWindow>();
        BridgeConnector.getSocket().on("BrowserWindowCreated", (id) ->
        {
            BridgeConnector.getSocket().off("BrowserWindowCreated");
            var windowId = id[0].toString();
            var browserWindow = new BrowserWindow(Integer.parseInt(windowId));
            browserWindows.add(browserWindow);

            taskCompletionSource.complete(browserWindow);
        });

        BridgeConnector.getSocket().off("BrowserWindowClosed");
        BridgeConnector.getSocket().on("BrowserWindowClosed", (ids) ->
        {
            var browserWindowIds = Electron.fromJsonString(ids[0].toString(), ArrayList.class, Integer.class);
            if (browserWindowIds.size() == 0) {
                browserWindows.clear();
            } else {
                browserWindows.removeIf(browserWindow -> !browserWindowIds.contains(browserWindow.getId()));
            }
        });

        if (loadUrl.equalsIgnoreCase("HTTP://LOCALHOST")) {
            loadUrl = loadUrl + ":" + BridgeSettings.getWebPort();
        }

        if (isWindows10()) {
            options.setWidth(options.getWidth() + 14);
            options.setHeight(options.getHeight() + 7);
        }

        if (options.getX() == -1 && options.getY() == -1) {
            options.setX(0);
            options.setY(0);
        } else {
            // Workaround Windows 10 / Electron Bug
            // https://github.com/electron/electron/issues/4045
            if (isWindows10()) {
                options.setX(options.getX() - 7);
            }
        }
        BridgeConnector.getSocket().emit("createBrowserWindow", Electron.toJsonObject(options), loadUrl);


        return taskCompletionSource;
    }

    private boolean isWindows10() {
        return System.getProperty("os.name").contains("Windows 10");
    }

    public CompletableFuture<BrowserView> CreateBrowserViewAsync() {
        return CreateBrowserViewAsync(new BrowserViewOptions());
    }

    public CompletableFuture<BrowserView> CreateBrowserViewAsync(BrowserViewOptions options) {
        CompletableFuture<BrowserView> taskCompletionSource = new CompletableFuture<>();

        BridgeConnector.getSocket().on("BrowserViewCreated", (id) ->
        {
            BridgeConnector.getSocket().off("BrowserViewCreated");

            var browserViewId = id[0].toString();
            var browserView = new BrowserView(Integer.parseInt(browserViewId));

            browserViews.add(browserView);

            taskCompletionSource.complete(browserView);
        });

        BridgeConnector.getSocket().emit("createBrowserView", Electron.toJsonObject(options));

        return taskCompletionSource;
    }

}
