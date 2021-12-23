package io.gitee.nn.electron.api;

import io.gitee.nn.electron.api.entities.*;
import io.gitee.nn.electron.api.function.Action0;
import io.gitee.nn.electron.api.function.Action1;
import io.gitee.nn.electron.api.function.Action2;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.function.Supplier;

@SuppressWarnings("unused")
public final class App {
    private static final String ModuleName = "app";
    private volatile static App app;
    private final List<Action0> windowAllClosed = new ArrayList<>();
    private final List<Function<QuitEventArgs, CompletableFuture<?>>> beforeQuit = new ArrayList<>();
    private final List<Function<QuitEventArgs, CompletableFuture<?>>> willQuit = new ArrayList<>();
    private final List<Supplier<CompletableFuture<?>>> quitting = new ArrayList<>();
    private final List<Action0> browserWindowBlur = new ArrayList<>();
    private final List<Action0> browserWindowFocus = new ArrayList<>();
    private final List<Action0> browserWindowCreated = new ArrayList<>();
    private final List<Action0> webContentsCreated = new ArrayList<>();
    private final List<Action0> accessibilitySupportChanged = new ArrayList<>();
    private final List<Action0> ready = new ArrayList<>();
    private final List<Action1<String>> openFile = new ArrayList<>();
    private final List<Action1<String>> openUrl = new ArrayList<>();
    private final CommandLine commandLine;
    private boolean preventQuit = false;
    private boolean isReady = false;

    App() {
        commandLine = new CommandLine();
    }

    static App getInstance() {
        if (app == null) {
            synchronized (App.class) {
                if (app == null) {
                    app = new App();
                }
            }
        }
        return app;
    }

    public void AddWindowAllClosed(Action0 value) {
        if (windowAllClosed.size() == 0) {
            BridgeConnector.getSocket().on("app-window-all-closed" + hashCode(), (unused) -> {
                if (!Electron.getWindowManager().IsQuitOnWindowAllClosed() || System.getProperty("os.name").contains("drawin")) {
                    windowAllClosed.forEach(Action0::accept);
                }
            });
            BridgeConnector.getSocket().emit("register-app-window-all-closed-event", hashCode());
        }
        windowAllClosed.add(value);
    }

    public void RemoveWindowAllClosed(Action0 value) {
        windowAllClosed.remove(value);
        if (windowAllClosed.size() == 0) {
            BridgeConnector.getSocket().off("app-window-all-closed" + hashCode());
        }
    }

    public void AddBeforeQuit(Function<QuitEventArgs, CompletableFuture<?>> value) {
        if (beforeQuit.size() == 0) {
            BridgeConnector.getSocket().on("app-before-quit" + hashCode(), (unused) -> {
                beforeQuit.forEach(it -> it.apply(new QuitEventArgs()));
                if (preventQuit) {
                    preventQuit = false;
                } else {
                    if (willQuit.size() == 0 && quitting.size() == 0) {
                        Exit();
                    } else if (willQuit.size() != 0) {
                        willQuit.forEach(it -> {
                            System.out.println("AddBeforeQuit willquit");
                            it.apply(new QuitEventArgs());
                        });
                        if (preventQuit) {
                            preventQuit = false;
                        } else {
                            if (quitting.size() != 0) {
                                quitting.forEach(Supplier::get);
                            }
                            Exit();
                        }
                    } else {
                        quitting.forEach(Supplier::get);
                        Exit();
                    }
                }
            });
            BridgeConnector.getSocket().emit("register-app-before-quit-event", hashCode());
        }
        beforeQuit.add(value);
    }

    public void RemoveBeforeQuit(Function<QuitEventArgs, CompletableFuture<?>> value) {
        beforeQuit.remove(value);
        if (beforeQuit.size() == 0) {
            BridgeConnector.getSocket().off("app-before-quit" + hashCode());
        }
    }

    public void AddWillQuit(Function<QuitEventArgs, CompletableFuture<?>> value) {
        if (willQuit.size() == 0) {
            BridgeConnector.getSocket().on("app-will-quit" + hashCode(), (unused) -> {
                willQuit.forEach(it -> it.apply(new QuitEventArgs()));
                if (preventQuit) {
                    preventQuit = false;
                } else {
                    if (quitting.size() != 0) {
                        quitting.forEach(Supplier::get);
                    }
                    Exit();
                }
            });
            BridgeConnector.getSocket().emit("register-app-will-quit-event", hashCode());
        }
        willQuit.add(value);
    }

    public void RemoveWillQuit(Function<QuitEventArgs, CompletableFuture<?>> value) {
        willQuit.remove(value);
        if (willQuit.size() == 0)
            BridgeConnector.getSocket().off("app-will-quit" + hashCode());
    }

    public void AddQuitting(Supplier<CompletableFuture<?>> value) {
        if (quitting.size() == 0) {
            BridgeConnector.getSocket().on("app-will-quit" + hashCode() + "quitting", (unused) ->
            {
                if (willQuit.size() == 0) {
                    quitting.forEach(Supplier::get);
                    Exit();
                }
            });

            BridgeConnector.getSocket().emit("register-app-will-quit-event", hashCode() + "quitting");
        }
        quitting.add(value);
    }

    public void RemoveQuitting(Supplier<CompletableFuture<?>> value) {
        quitting.remove(value);
        if (quitting.size() == 0)
            BridgeConnector.getSocket().off("app-will-quit" + hashCode() + "quitting");
    }

    public void AddBrowserWindowBlur(Action0 value) {
        if (browserWindowBlur.size() == 0) {
            BridgeConnector.getSocket().on("app-browser-window-blur" + hashCode(), (unused) -> browserWindowBlur.forEach(Action0::accept));
            BridgeConnector.getSocket().emit("register-app-browser-window-blur-event", hashCode());
        }
        browserWindowBlur.add(value);
    }

    public void RemoveBrowserWindowBlur(Action0 value) {
        browserWindowBlur.remove(value);
        if (browserWindowBlur.size() == 0) {
            BridgeConnector.getSocket().off("app-browser-window-blur" + hashCode());
        }
    }

    public void AddBrowserWindowFocus(Action0 value) {
        if (browserWindowFocus.size() == 0) {
            BridgeConnector.getSocket().on("app-browser-window-focus" + hashCode(), (unused) -> browserWindowFocus.forEach(Action0::accept));
            BridgeConnector.getSocket().emit("register-app-browser-window-focus-event", hashCode());
        }
        browserWindowFocus.add(value);
    }

    public void RemoveBrowserWindowFocus(Action0 value) {
        browserWindowFocus.remove(value);
        if (browserWindowFocus.size() == 0) {
            BridgeConnector.getSocket().off("app-browser-window-focus" + hashCode());
        }
    }

    public void AddBrowserWindowCreated(Action0 value) {
        if (browserWindowCreated.size() == 0) {
            BridgeConnector.getSocket().on("app-browser-window-created" + hashCode(), (unused) -> browserWindowCreated.forEach(Action0::accept));
            BridgeConnector.getSocket().emit("register-app-browser-window-created-event", hashCode());
        }
        browserWindowCreated.add(value);
    }

    public void RemoveBrowserWindowCreated(Action0 value) {
        browserWindowCreated.remove(value);
        if (browserWindowCreated.size() == 0) {
            BridgeConnector.getSocket().off("app-browser-window-created" + hashCode());
        }
    }

    public void AddWebContentsCreated(Action0 value) {
        if (webContentsCreated.size() == 0) {
            BridgeConnector.getSocket().on("app-web-contents-created" + hashCode(), (unused) -> webContentsCreated.forEach(Action0::accept));
            BridgeConnector.getSocket().emit("register-app-web-contents-created-event", hashCode());
        }
        webContentsCreated.add(value);
    }

    public void RemoveWebContentsCreated(Action0 value) {
        webContentsCreated.remove(value);
        if (webContentsCreated.size() == 0) {
            BridgeConnector.getSocket().off("app-web-contents-created" + hashCode());
        }
    }

    public void AddAccessibilitySupportChanged(Action0 value) {
        if (accessibilitySupportChanged.size() == 0) {
            BridgeConnector.getSocket().on("app-accessibility-support-changed" + hashCode(), (unused) -> accessibilitySupportChanged.forEach(Action0::accept));
            BridgeConnector.getSocket().emit("register-app-accessibility-support-changed", hashCode());
        }
        accessibilitySupportChanged.add(value);
    }

    public void RemoveAccessibilitySupportChanged(Action0 value) {
        accessibilitySupportChanged.remove(value);
        if (accessibilitySupportChanged.size() == 0) {
            BridgeConnector.getSocket().off("app-accessibility-support-changed" + hashCode());
        }
    }

    public void AddReady(Action0 value) {
        if (GetIsReady()) {
            value.accept();
        }
        ready.add(value);
    }

    public void RemoveReady(Action0 value) {
        ready.remove(value);
    }

    public boolean GetIsReady() {
        return isReady;
    }

    void setIsReady(Boolean value) {
        isReady = value;
        if (value) {
            ready.forEach(Action0::accept);
        }
    }

    public void AddOpenFile(Action1<String> value) {
        if (openFile.size() == 0) {
            BridgeConnector.getSocket().on("app-open-file" + hashCode(), (file) ->
                    openFile.forEach(it -> it.accept(file[0].toString())));

            BridgeConnector.getSocket().emit("register-app-open-file-event", hashCode());
        }
        openFile.add(value);
    }

    public void RemoveOpenFile(Action1<String> value) {
        openFile.remove(value);
        if (openFile.size() == 0) {
            BridgeConnector.getSocket().off("app-open-file" + hashCode());
        }
    }

    public void AddOpenUrl(Action1<String> value) {
        if (openUrl.size() == 0) {
            BridgeConnector.getSocket().on("app-open-url" + hashCode(), (file) ->
                    openUrl.forEach(it -> it.accept(file[0].toString())));

            BridgeConnector.getSocket().emit("register-app-open-url-event", hashCode());
        }
        openUrl.add(value);
    }

    public void RemoveOpenUrl(Action1<String> value) {
        openUrl.remove(value);
        if (openUrl.size() == 0) {
            BridgeConnector.getSocket().off("app-open-url" + hashCode());
        }
    }

    public CompletableFuture<String> GetNameAsync() {
        var taskCompletionSource = new CompletableFuture<String>();

        BridgeConnector.getSocket().on("appGetNameCompleted", (result) ->
        {
            BridgeConnector.getSocket().off("appGetNameCompleted");
            taskCompletionSource.complete(result[0].toString());
        });

        BridgeConnector.getSocket().emit("appGetName");

        return taskCompletionSource;
    }

    public void Quit() {
        BridgeConnector.getSocket().emit("appQuit");
    }

    public void Exit() {
        Exit(0);
    }

    public void Exit(int exitCode) {
        BridgeConnector.getSocket().emit("appExit", exitCode);
    }

    public void Relaunch() {
        BridgeConnector.getSocket().emit("appRelaunch");
    }

    public void Relaunch(RelaunchOptions relaunchOptions) {
        BridgeConnector.getSocket().emit("appRelaunch", Electron.toJsonObject(relaunchOptions));
    }

    public void Focus() {
        BridgeConnector.getSocket().emit("appFocus");
    }

    public void Focus(FocusOptions focusOptions) {
        BridgeConnector.getSocket().emit("appFocus", Electron.toJsonObject(focusOptions));
    }

    public void Hide() {
        BridgeConnector.getSocket().emit("appHide");
    }

    public void Show() {
        BridgeConnector.getSocket().emit("appShow");
    }

    public CompletableFuture<String> GetAppPathAsync() {
        var taskCompletionSource = new CompletableFuture<String>();
        BridgeConnector.getSocket().on("appGetAppPathCompleted", (path) ->
        {
            BridgeConnector.getSocket().off("appGetAppPathCompleted");
            taskCompletionSource.complete(path[0].toString());
        });

        BridgeConnector.getSocket().emit("appGetAppPath");

        return taskCompletionSource;
    }

    public void SetAppLogsPath(String path) {
        BridgeConnector.getSocket().emit("appSetAppLogsPath", path);
    }

    public CompletableFuture<String> GetPathAsync(PathName pathName) {
        var taskCompletionSource = new CompletableFuture<String>();
        BridgeConnector.getSocket().on("appGetPathCompleted", (path) ->
        {
            BridgeConnector.getSocket().off("appGetPathCompleted");

            taskCompletionSource.complete(path[0].toString());
        });

        BridgeConnector.getSocket().emit("appGetPath", pathName.getValue());

        return taskCompletionSource;
    }

    public void SetPath(PathName name, String path) {
        BridgeConnector.getSocket().emit("appSetPath", name.getValue(), path);
    }

    public CompletableFuture<String> GetVersionAsync() {

        var taskCompletionSource = new CompletableFuture<String>();
        BridgeConnector.getSocket().on("appGetVersionCompleted", (version) ->
        {
            BridgeConnector.getSocket().off("appGetVersionCompleted");
            taskCompletionSource.complete(version[0].toString());
        });

        BridgeConnector.getSocket().emit("appGetVersion");

        return taskCompletionSource;
    }

    public CompletableFuture<String> GetLocaleAsync() {
        var taskCompletionSource = new CompletableFuture<String>();
        BridgeConnector.getSocket().on("appGetLocaleCompleted", (local) ->
        {
            BridgeConnector.getSocket().off("appGetLocaleCompleted");
            taskCompletionSource.complete(local[0].toString());
        });

        BridgeConnector.getSocket().emit("appGetLocale");

        return taskCompletionSource;
    }

    public void AddRecentDocument(String path) {
        BridgeConnector.getSocket().emit("appAddRecentDocument", path);
    }

    public void ClearRecentDocuments() {
        BridgeConnector.getSocket().emit("appClearRecentDocuments");
    }

    public CompletableFuture<Boolean> SetAsDefaultProtocolClientAsync(String protocol) {
        return SetAsDefaultProtocolClientAsync(protocol, null, null);
    }

    public CompletableFuture<Boolean> SetAsDefaultProtocolClientAsync(String protocol, String path) {
        return SetAsDefaultProtocolClientAsync(protocol, path, null);
    }

    public CompletableFuture<Boolean> SetAsDefaultProtocolClientAsync(String protocol, String path, String[] args) {
        var taskCompletionSource = new CompletableFuture<Boolean>();
        BridgeConnector.getSocket().on("appSetAsDefaultProtocolClientCompleted", (success) ->
        {
            BridgeConnector.getSocket().off("appSetAsDefaultProtocolClientCompleted");
            taskCompletionSource.complete((Boolean) success[0]);
        });

        BridgeConnector.getSocket().emit("appSetAsDefaultProtocolClient", protocol, path, args);

        return taskCompletionSource;
    }

    public CompletableFuture<Boolean> RemoveAsDefaultProtocolClientAsync(String protocol) {
        return RemoveAsDefaultProtocolClientAsync(protocol, null, null);
    }

    public CompletableFuture<Boolean> RemoveAsDefaultProtocolClientAsync(String protocol, String path) {
        return RemoveAsDefaultProtocolClientAsync(protocol, path, null);
    }

    public CompletableFuture<Boolean> RemoveAsDefaultProtocolClientAsync(String protocol, String path, String[] args) {
        var taskCompletionSource = new CompletableFuture<Boolean>();
        BridgeConnector.getSocket().on("appRemoveAsDefaultProtocolClientCompleted", (success) ->
        {
            BridgeConnector.getSocket().off("appRemoveAsDefaultProtocolClientCompleted");
            taskCompletionSource.complete((Boolean) success[0]);
        });

        BridgeConnector.getSocket().emit("appRemoveAsDefaultProtocolClient", protocol, path, args);

        return taskCompletionSource;
    }

    public CompletableFuture<Boolean> IsDefaultProtocolClientAsync(String protocol) {
        return IsDefaultProtocolClientAsync(protocol, null, null);
    }

    public CompletableFuture<Boolean> IsDefaultProtocolClientAsync(String protocol, String path) {
        return IsDefaultProtocolClientAsync(protocol, path, null);
    }

    public CompletableFuture<Boolean> IsDefaultProtocolClientAsync(String protocol, String path, String[] args) {

        var taskCompletionSource = new CompletableFuture<Boolean>();

        BridgeConnector.getSocket().on("appIsDefaultProtocolClientCompleted", (success) ->
        {
            BridgeConnector.getSocket().off("appIsDefaultProtocolClientCompleted");
            taskCompletionSource.complete((Boolean) success[0]);
        });

        BridgeConnector.getSocket().emit("appIsDefaultProtocolClient", protocol, path, args);

        return taskCompletionSource;
    }

    public CompletableFuture<Boolean> SetUserTasksAsync(UserTask[] userTasks) {
        var taskCompletionSource = new CompletableFuture<Boolean>();
        BridgeConnector.getSocket().on("appSetUserTasksCompleted", (success) ->
        {
            BridgeConnector.getSocket().off("appSetUserTasksCompleted");
            taskCompletionSource.complete((Boolean) success[0]);
        });

        BridgeConnector.getSocket().emit("appSetUserTasks", Electron.toJsonArray(userTasks));

        return taskCompletionSource;
    }

    public CompletableFuture<JumpListSettings> GetJumpListSettingsAsync() {
        var taskCompletionSource = new CompletableFuture<JumpListSettings>();
        BridgeConnector.getSocket().on("appGetJumpListSettingsCompleted", (jumpListSettings) ->
        {
            BridgeConnector.getSocket().off("appGetJumpListSettingsCompleted");
            taskCompletionSource.complete(Electron.fromJsonString(jumpListSettings[0].toString(), JumpListSettings.class));
        });

        BridgeConnector.getSocket().emit("appGetJumpListSettings");

        return taskCompletionSource;
    }

    public void SetJumpList(JumpListCategory[] categories) {
        BridgeConnector.getSocket().emit("appSetJumpList", Electron.toJsonArray(categories));
    }

    public CompletableFuture<Boolean> RequestSingleInstanceLockAsync(Action2<String[], String> newInstanceOpened) {
        var taskCompletionSource = new CompletableFuture<Boolean>();
        BridgeConnector.getSocket().on("appRequestSingleInstanceLockCompleted", (success) ->
        {
            BridgeConnector.getSocket().off("appRequestSingleInstanceLockCompleted");
            taskCompletionSource.complete((Boolean) success[0]);
        });

        BridgeConnector.getSocket().off("secondInstance");
        BridgeConnector.getSocket().on("secondInstance", (result) ->
        {
            var results = Electron.fromJsonString(result[0].toString(), ArrayList.class, String.class);
            String[] args = Electron.fromJsonString(results.get(0), ArrayList.class, String.class).toArray(new String[0]);
            String workingDirectory = results.get(1);

            newInstanceOpened.accept(args, workingDirectory);
        });

        BridgeConnector.getSocket().emit("appRequestSingleInstanceLock");

        return taskCompletionSource;
    }

    public void ReleaseSingleInstanceLock() {
        BridgeConnector.getSocket().emit("appReleaseSingleInstanceLock");
    }

    public CompletableFuture<Boolean> HasSingleInstanceLockAsync() {
        var taskCompletionSource = new CompletableFuture<Boolean>();
        BridgeConnector.getSocket().on("appHasSingleInstanceLockCompleted", (hasLock) ->
        {
            BridgeConnector.getSocket().off("appHasSingleInstanceLockCompleted");
            taskCompletionSource.complete((Boolean) hasLock[0]);
        });

        BridgeConnector.getSocket().emit("appHasSingleInstanceLock");

        return taskCompletionSource;
    }

    public void SetUserActivity(String type, Object userInfo) {
        SetUserActivity(type, userInfo, null);
    }

    public void SetUserActivity(String type, Object userInfo, String webpageUrl) {
        BridgeConnector.getSocket().emit("appSetUserActivity", type, Electron.toJsonObject(userInfo), webpageUrl);
    }

    public CompletableFuture<String> GetCurrentActivityTypeAsync() {

        var taskCompletionSource = new CompletableFuture<String>();
        BridgeConnector.getSocket().on("appGetCurrentActivityTypeCompleted", (activityType) ->
        {
            BridgeConnector.getSocket().off("appGetCurrentActivityTypeCompleted");
            taskCompletionSource.complete(activityType[0].toString());
        });

        BridgeConnector.getSocket().emit("appGetCurrentActivityType");

        return taskCompletionSource;
    }

    public void InvalidateCurrentActivity() {
        BridgeConnector.getSocket().emit("appInvalidateCurrentActivity");
    }

    public void ResignCurrentActivity() {
        BridgeConnector.getSocket().emit("appResignCurrentActivity");
    }

    public void SetAppUserModelId(String id) {
        BridgeConnector.getSocket().emit("appSetAppUserModelId", id);
    }

    public CompletableFuture<Integer> ImportCertificateAsync(ImportCertificateOptions options) {

        var taskCompletionSource = new CompletableFuture<Integer>();
        BridgeConnector.getSocket().on("appImportCertificateCompleted", (result) ->
        {
            BridgeConnector.getSocket().off("appImportCertificateCompleted");
            taskCompletionSource.complete((Integer) result[0]);
        });

        BridgeConnector.getSocket().emit("appImportCertificate", Electron.toJsonObject(options));

        return taskCompletionSource;
    }

    public CompletableFuture<ProcessMetric[]> GetAppMetricsAsync() {
        var taskCompletionSource = new CompletableFuture<ProcessMetric[]>();
        BridgeConnector.getSocket().on("appGetAppMetricsCompleted", (result) ->
        {
            BridgeConnector.getSocket().off("appGetAppMetricsCompleted");
            var processMetrics = Electron.fromJsonString(result[0].toString(), ArrayList.class, ProcessMetric.class).toArray(new ProcessMetric[0]);

            taskCompletionSource.complete(processMetrics);
        });

        BridgeConnector.getSocket().emit("appGetAppMetrics");

        return taskCompletionSource;
    }

    public CompletableFuture<GPUFeatureStatus> GetGpuFeatureStatusAsync() {
        var taskCompletionSource = new CompletableFuture<GPUFeatureStatus>();
        BridgeConnector.getSocket().on("appGetGpuFeatureStatusCompleted", (result) ->
        {
            BridgeConnector.getSocket().off("appGetGpuFeatureStatusCompleted");
            taskCompletionSource.complete(Electron.fromJsonString(result[0].toString(), GPUFeatureStatus.class));
        });

        BridgeConnector.getSocket().emit("appGetGpuFeatureStatus");

        return taskCompletionSource;
    }

    public CompletableFuture<Boolean> SetBadgeCountAsync(int count) {
        var taskCompletionSource = new CompletableFuture<Boolean>();
        BridgeConnector.getSocket().on("appSetBadgeCountCompleted", (success) ->
        {
            BridgeConnector.getSocket().off("appSetBadgeCountCompleted");
            taskCompletionSource.complete((Boolean) success[0]);
        });

        BridgeConnector.getSocket().emit("appSetBadgeCount", count);

        return taskCompletionSource;
    }

    public CompletableFuture<Integer> GetBadgeCountAsync() {


        var taskCompletionSource = new CompletableFuture<Integer>();
        BridgeConnector.getSocket().on("appGetBadgeCountCompleted", (count) ->
        {
            BridgeConnector.getSocket().off("appGetBadgeCountCompleted");
            taskCompletionSource.complete((Integer) count[0]);
        });

        BridgeConnector.getSocket().emit("appGetBadgeCount");

        return taskCompletionSource;
    }

    public CommandLine getCommandLine() {
        return commandLine;
    }

    public CompletableFuture<Boolean> IsUnityRunningAsync() {
        var taskCompletionSource = new CompletableFuture<Boolean>();
        BridgeConnector.getSocket().on("appIsUnityRunningCompleted", (isUnityRunning) ->
        {
            BridgeConnector.getSocket().off("appIsUnityRunningCompleted");
            taskCompletionSource.complete((Boolean) isUnityRunning[0]);
        });

        BridgeConnector.getSocket().emit("appIsUnityRunning");

        return taskCompletionSource;
    }

    public CompletableFuture<LoginItemSettings> GetLoginItemSettingsAsync() {
        return GetLoginItemSettingsAsync(null);
    }

    public CompletableFuture<LoginItemSettings> GetLoginItemSettingsAsync(LoginItemSettingsOptions options) {
        var taskCompletionSource = new CompletableFuture<LoginItemSettings>();
        BridgeConnector.getSocket().on("appGetLoginItemSettingsCompleted", (loginItemSettings) ->
        {
            BridgeConnector.getSocket().off("appGetLoginItemSettingsCompleted");
            taskCompletionSource.complete(Electron.fromJsonString(loginItemSettings[0].toString(), LoginItemSettings.class));
        });

        if (options == null) {
            BridgeConnector.getSocket().emit("appGetLoginItemSettings");
        } else {
            BridgeConnector.getSocket().emit("appGetLoginItemSettings", Electron.toJsonObject(options));
        }

        return taskCompletionSource;
    }

    public void SetLoginItemSettings(LoginSettings loginSettings) {
        BridgeConnector.getSocket().emit("appSetLoginItemSettings", Electron.toJsonObject(loginSettings));
    }

    public CompletableFuture<Boolean> IsAccessibilitySupportEnabledAsync() {
        var taskCompletionSource = new CompletableFuture<Boolean>();
        BridgeConnector.getSocket().on("appIsAccessibilitySupportEnabledCompleted", (isAccessibilitySupportEnabled) ->
        {
            BridgeConnector.getSocket().off("appIsAccessibilitySupportEnabledCompleted");
            taskCompletionSource.complete((Boolean) isAccessibilitySupportEnabled[0]);
        });

        BridgeConnector.getSocket().emit("appIsAccessibilitySupportEnabled");
        return taskCompletionSource;
    }

    public void SetAccessibilitySupportEnabled(Boolean enabled) {
        BridgeConnector.getSocket().emit("appSetAboutPanelOptions", enabled);
    }

    public void ShowAboutPanel() {
        BridgeConnector.getSocket().emit("appShowAboutPanel");
    }

    public void SetAboutPanelOptions(AboutPanelOptions options) {
        BridgeConnector.getSocket().emit("appSetAboutPanelOptions", Electron.toJsonObject(options));
    }

    public CompletableFuture<String> getUserAgentFallbackAsync() {
        var taskCompletionSource = new CompletableFuture<String>();

        BridgeConnector.getSocket().on("appGetUserAgentFallbackCompleted", (result) ->
        {
            BridgeConnector.getSocket().off("appGetUserAgentFallbackCompleted");
            taskCompletionSource.complete((String) result[0]);
        });

        BridgeConnector.getSocket().emit("appGetUserAgentFallback");

        return taskCompletionSource;
    }

    void PreventQuit() {
        preventQuit = true;
    }

    public void On(String eventName, Action1<Object> fn) {
        Events.getInstance().On(ModuleName, eventName, fn);
    }

    public void Once(String eventName, Action1<Object> fn) {
        Events.getInstance().Once(ModuleName, eventName, fn);
    }
}
