package io.gitee.nn.electron.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.gitee.nn.electron.api.entities.ProgressInfo;
import io.gitee.nn.electron.api.entities.SemVer;
import io.gitee.nn.electron.api.entities.UpdateCheckResult;
import io.gitee.nn.electron.api.entities.UpdateInfo;
import io.gitee.nn.electron.api.function.Action0;
import io.gitee.nn.electron.api.function.Action1;

import java.util.*;
import java.util.concurrent.CompletableFuture;

@SuppressWarnings("unused")
public final class AutoUpdater {
    private volatile static AutoUpdater autoUpdater;
    private final List<Action1<String>> error = new ArrayList<>();
    private final List<Action0> checkingForUpdate = new ArrayList<>();
    private final List<Action1<UpdateInfo>> updateAvailable = new ArrayList<>();
    private final List<Action1<UpdateInfo>> updateNotAvailable = new ArrayList<>();
    private final List<Action1<ProgressInfo>> downloadProgress = new ArrayList<>();
    private final List<Action1<UpdateInfo>> updateDownloaded = new ArrayList<>();

    AutoUpdater() {
    }

    static AutoUpdater getInstance() {
        if (autoUpdater == null) {
            synchronized (AutoUpdater.class) {
                if (autoUpdater == null) {
                    autoUpdater = new AutoUpdater();
                }
            }
        }
        return autoUpdater;
    }

    public Boolean GetAutoDownload() {
        return CompletableFuture.supplyAsync(() -> {
            var taskCompletionSource = new CompletableFuture<Boolean>();

            BridgeConnector.getSocket().on("autoUpdater-autoDownload-get-reply", (result) ->
            {
                BridgeConnector.getSocket().off("autoUpdater-autoDownload-get-reply");
                taskCompletionSource.complete((Boolean) result[0]);
            });

            BridgeConnector.getSocket().emit("autoUpdater-autoDownload-get");

            return taskCompletionSource.join();
        }).join();
    }

    public void SetAutoDownload(Boolean value) {
        BridgeConnector.getSocket().emit("autoUpdater-autoDownload-set", value);
    }

    public Boolean GetAutoInstallOnAppQuit() {
        return CompletableFuture.supplyAsync(() ->
        {
            var taskCompletionSource = new CompletableFuture<Boolean>();

            BridgeConnector.getSocket().on("autoUpdater-autoInstallOnAppQuit-get-reply", (result) ->
            {
                BridgeConnector.getSocket().off("autoUpdater-autoInstallOnAppQuit-get-reply");
                taskCompletionSource.complete((Boolean) result[0]);
            });

            BridgeConnector.getSocket().emit("autoUpdater-autoInstallOnAppQuit-get");

            return taskCompletionSource.join();
        }).join();
    }

    public void SetAutoInstallOnAppQuit(Boolean value) {
        BridgeConnector.getSocket().emit("autoUpdater-autoInstallOnAppQuit-set", value);
    }

    public Boolean GetAllowPrerelease() {
        return CompletableFuture.supplyAsync(() ->
        {
            var taskCompletionSource = new CompletableFuture<Boolean>();

            BridgeConnector.getSocket().on("autoUpdater-allowPrerelease-get-reply", (result) ->
            {
                BridgeConnector.getSocket().off("autoUpdater-allowPrerelease-get-reply");
                taskCompletionSource.complete((Boolean) result[0]);
            });

            BridgeConnector.getSocket().emit("autoUpdater-allowPrerelease-get");

            return taskCompletionSource.join();
        }).join();
    }

    public void SetAllowPrerelease(Boolean value) {
        BridgeConnector.getSocket().emit("autoUpdater-allowPrerelease-set", value);
    }

    public Boolean GetFullChangelog() {
        return CompletableFuture.supplyAsync(() ->
        {
            var taskCompletionSource = new CompletableFuture<Boolean>();

            BridgeConnector.getSocket().on("autoUpdater-fullChangelog-get-reply", (result) ->
            {
                BridgeConnector.getSocket().off("autoUpdater-fullChangelog-get-reply");
                taskCompletionSource.complete((Boolean) result[0]);
            });

            BridgeConnector.getSocket().emit("autoUpdater-fullChangelog-get");

            return taskCompletionSource.join();
        }).join();
    }

    public void SetFullChangelog(Boolean value) {
        BridgeConnector.getSocket().emit("autoUpdater-fullChangelog-set", value);
    }

    public Boolean GetAllowDowngrade() {
        return CompletableFuture.supplyAsync(() ->
        {
            var taskCompletionSource = new CompletableFuture<Boolean>();

            BridgeConnector.getSocket().on("autoUpdater-allowDowngrade-get-reply", (result) ->
            {
                BridgeConnector.getSocket().off("autoUpdater-allowDowngrade-get-reply");
                taskCompletionSource.complete((Boolean) result[0]);
            });

            BridgeConnector.getSocket().emit("autoUpdater-allowDowngrade-get");

            return taskCompletionSource.join();
        }).join();
    }

    public void SetAllowDowngrade(Boolean value) {
        BridgeConnector.getSocket().emit("autoUpdater-allowDowngrade-set", value);
    }

    public CompletableFuture<SemVer> GetCurrentVersionAsync() {
        var taskCompletionSource = new CompletableFuture<SemVer>();

        BridgeConnector.getSocket().on("autoUpdater-currentVersion-get-reply", (result) ->
        {
            BridgeConnector.getSocket().off("autoUpdater-currentVersion-get-reply");

            taskCompletionSource.complete(Electron.fromJsonString(result[0].toString(), SemVer.class));
        });
        BridgeConnector.getSocket().emit("autoUpdater-currentVersion-get");

        return taskCompletionSource;
    }

    public CompletableFuture<String> GetChannelAsync() {
        var taskCompletionSource = new CompletableFuture<String>();

        BridgeConnector.getSocket().on("autoUpdater-channel-get-reply", (result) ->
        {
            BridgeConnector.getSocket().off("autoUpdater-channel-get-reply");
            taskCompletionSource.complete(result[0].toString());
        });
        BridgeConnector.getSocket().emit("autoUpdater-channel-get");

        return taskCompletionSource;
    }

    public CompletableFuture<Map<String, String>> GetRequestHeadersAsync() {
        var taskCompletionSource = new CompletableFuture<Map<String, String>>();
        BridgeConnector.getSocket().on("autoUpdater-requestHeaders-get-reply", (headers) ->
        {
            BridgeConnector.getSocket().off("autoUpdater-requestHeaders-get-reply");
            try {
                taskCompletionSource.complete(Electron.objectMapper.readValue(headers[0].toString(), Electron.typeFactory.constructMapType(HashMap.class, String.class, String.class)));
            } catch (JsonProcessingException e) {
                taskCompletionSource.completeExceptionally(e);
            }
        });
        BridgeConnector.getSocket().emit("autoUpdater-requestHeaders-get");
        return taskCompletionSource;
    }

    public void SetRequestHeaders(Map<String, String> value) {
        BridgeConnector.getSocket().emit("autoUpdater-requestHeaders-set", Electron.toJsonObject(value));
    }

    public void AddOnError(final Action1<String> value) {
        if (error.size() == 0) {
            BridgeConnector.getSocket().on("autoUpdater-error" + hashCode(), (message) ->
                    error.forEach(it -> it.accept(message[0].toString())));

            BridgeConnector.getSocket().emit("register-autoUpdater-error-event", hashCode());
        }
        error.add(value);
    }

    public void RemoveOnError(final Action1<String> value) {
        error.remove(value);

        if (error.size() == 0)
            BridgeConnector.getSocket().off("autoUpdater-error" + hashCode());
    }

    public void AddOnCheckingForUpdate(Action0 value) {
        if (checkingForUpdate.size() == 0) {
            BridgeConnector.getSocket().on("autoUpdater-checking-for-update" + hashCode(), (unused) ->
                    checkingForUpdate.forEach(Action0::accept));

            BridgeConnector.getSocket().emit("register-autoUpdater-checking-for-update-event", hashCode());
        }
        checkingForUpdate.add(value);
    }

    public void RemoveOnCheckingForUpdate(Action0 value) {
        checkingForUpdate.remove(value);

        if (checkingForUpdate.size() == 0)
            BridgeConnector.getSocket().off("autoUpdater-checking-for-update" + hashCode());
    }

    public void AddOnUpdateAvailable(Action1<UpdateInfo> value) {
        if (updateAvailable.size() == 0) {
            BridgeConnector.getSocket().on("autoUpdater-update-available" + hashCode(), (updateInfo) -> updateAvailable.forEach(it -> it.accept(Electron.fromJsonString(updateInfo[0].toString(), UpdateInfo.class))));

            BridgeConnector.getSocket().emit("register-autoUpdater-update-available-event", hashCode());
        }
        updateAvailable.add(value);
    }

    public void RemoveOnUpdateAvailable(Action1<UpdateInfo> value) {
        updateAvailable.remove(value);

        if (updateAvailable.size() == 0)
            BridgeConnector.getSocket().off("autoUpdater-update-available" + hashCode());
    }

    public void AddOnUpdateNotAvailable(Action1<UpdateInfo> value) {
        if (updateNotAvailable.size() == 0) {
            BridgeConnector.getSocket().on("autoUpdater-update-not-available" + hashCode(), (updateInfo) -> updateNotAvailable.forEach(it -> it.accept(Electron.fromJsonString(updateInfo[0].toString(), UpdateInfo.class))));

            BridgeConnector.getSocket().emit("register-autoUpdater-update-not-available-event", hashCode());
        }
        updateNotAvailable.add(value);
    }

    public void RemoveOnUpdateNotAvailable(Action1<UpdateInfo> value) {
        updateNotAvailable.remove(value);

        if (updateNotAvailable.size() == 0)
            BridgeConnector.getSocket().off("autoUpdater-update-not-available" + hashCode());
    }

    public void AddOnDownloadProgress(Action1<ProgressInfo> value) {
        if (downloadProgress.size() == 0) {
            BridgeConnector.getSocket().on("autoUpdater-download-progress" + hashCode(), (progressInfo) -> downloadProgress.forEach(it -> it.accept(Electron.fromJsonString(progressInfo[0].toString(), ProgressInfo.class))));

            BridgeConnector.getSocket().emit("register-autoUpdater-download-progress-event", hashCode());
        }
        downloadProgress.add(value);
    }

    public void RemoveOnDownloadProgress(Action1<ProgressInfo> value) {
        downloadProgress.remove(value);

        if (downloadProgress.size() == 0)
            BridgeConnector.getSocket().off("autoUpdater-download-progress" + hashCode());
    }

    public void AddOnUpdateDownloaded(Action1<UpdateInfo> value) {
        if (updateDownloaded.size() == 0) {
            BridgeConnector.getSocket().on("autoUpdater-update-downloaded" + hashCode(), (updateInfo) -> updateDownloaded.forEach(it -> it.accept(Electron.fromJsonString(updateInfo[0].toString(), UpdateInfo.class))));

            BridgeConnector.getSocket().emit("register-autoUpdater-update-downloaded-event", hashCode());
        }
        updateDownloaded.add(value);
    }

    public void RemoveOnUpdateDownloaded(Action1<UpdateInfo> value) {
        updateDownloaded.remove(value);

        if (updateDownloaded.size() == 0)
            BridgeConnector.getSocket().off("autoUpdater-update-downloaded" + hashCode());
    }

    public CompletableFuture<UpdateCheckResult> CheckForUpdatesAsync() {
        var taskCompletionSource = new CompletableFuture<UpdateCheckResult>();
        String guid = UUID.randomUUID().toString();

        BridgeConnector.getSocket().on("autoUpdaterCheckForUpdatesComplete" + guid, (updateCheckResult) ->
        {
            BridgeConnector.getSocket().off("autoUpdaterCheckForUpdatesComplete" + guid);
            BridgeConnector.getSocket().off("autoUpdaterCheckForUpdatesError" + guid);
            taskCompletionSource.complete(Electron.fromJsonString(updateCheckResult[0].toString(), UpdateCheckResult.class));
        });
        BridgeConnector.getSocket().on("autoUpdaterCheckForUpdatesError" + guid, (error) ->
        {
            BridgeConnector.getSocket().off("autoUpdaterCheckForUpdatesComplete" + guid);
            BridgeConnector.getSocket().off("autoUpdaterCheckForUpdatesError" + guid);
            String message = "An error occurred in CheckForUpdatesAsync";
            if (error != null && error[0] != null && !error[0].toString().isEmpty())
                message = error[0].toString();
            taskCompletionSource.completeExceptionally(new Exception(message));
        });

        BridgeConnector.getSocket().emit("autoUpdaterCheckForUpdates", guid);

        return taskCompletionSource;
    }

    public CompletableFuture<UpdateCheckResult> CheckForUpdatesAndNotifyAsync() {
        var taskCompletionSource = new CompletableFuture<UpdateCheckResult>();
        String guid = UUID.randomUUID().toString();

        BridgeConnector.getSocket().on("autoUpdaterCheckForUpdatesAndNotifyComplete" + guid, (updateCheckResult) ->
        {
            BridgeConnector.getSocket().off("autoUpdaterCheckForUpdatesAndNotifyComplete" + guid);
            BridgeConnector.getSocket().off("autoUpdaterCheckForUpdatesAndNotifyError" + guid);
            if (updateCheckResult[0] == null)
                taskCompletionSource.complete(null);
            else {
                taskCompletionSource.complete(Electron.fromJsonString(updateCheckResult[0].toString(), UpdateCheckResult.class));
            }
        });
        BridgeConnector.getSocket().on("autoUpdaterCheckForUpdatesAndNotifyError" + guid, (error) ->
        {
            BridgeConnector.getSocket().off("autoUpdaterCheckForUpdatesAndNotifyComplete" + guid);
            BridgeConnector.getSocket().off("autoUpdaterCheckForUpdatesAndNotifyError" + guid);
            String message = "An error occurred in autoUpdaterCheckForUpdatesAndNotify";
            if (error[0] != null)
                message = error[0].toString();
            taskCompletionSource.completeExceptionally(new Exception(message));
        });

        BridgeConnector.getSocket().emit("autoUpdaterCheckForUpdatesAndNotify", guid);

        return taskCompletionSource;
    }

    public void QuitAndInstall() {
        QuitAndInstall(false, false);
    }

    public void QuitAndInstall(Boolean isSilent, Boolean isForceRunAfter) {
        BridgeConnector.getSocket().emit("autoUpdaterQuitAndInstall", isSilent, isForceRunAfter);
    }

    public CompletableFuture<String> DownloadUpdateAsync() {
        var taskCompletionSource = new CompletableFuture<String>();
        String guid = UUID.randomUUID().toString();

        BridgeConnector.getSocket().on("autoUpdaterDownloadUpdateComplete" + guid, (downloadedPath) ->
        {
            BridgeConnector.getSocket().off("autoUpdaterDownloadUpdateComplete" + guid);
            taskCompletionSource.complete(downloadedPath[0].toString());
        });

        BridgeConnector.getSocket().emit("autoUpdaterDownloadUpdate", guid);

        return taskCompletionSource;
    }

    public CompletableFuture<String> GetFeedURLAsync() {
        var taskCompletionSource = new CompletableFuture<String>();
        String guid = UUID.randomUUID().toString();

        BridgeConnector.getSocket().on("autoUpdaterGetFeedURLComplete" + guid, (downloadedPath) ->
        {
            BridgeConnector.getSocket().off("autoUpdaterGetFeedURLComplete" + guid);
            taskCompletionSource.complete(downloadedPath[0].toString());
        });

        BridgeConnector.getSocket().emit("autoUpdaterGetFeedURL", guid);

        return taskCompletionSource;
    }
}
