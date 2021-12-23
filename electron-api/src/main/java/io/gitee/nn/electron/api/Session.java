package io.gitee.nn.electron.api;

import io.gitee.nn.electron.api.entities.*;
import org.springframework.aop.framework.ProxyConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@SuppressWarnings("unused")
public final class Session {
    private final int id;
    private final Cookies cookies;

    public int getId() {
        return id;
    }

    public Cookies getCookies() {
        return cookies;
    }

    public Session(int id) {
        this.id = id;
        this.cookies = new Cookies(id);
    }

    public void AllowNTLMCredentialsForDomains(String domains) {
        BridgeConnector.getSocket().emit("webContents-session-allowNTLMCredentialsForDomains", id, domains);
    }

//    public CompletableFuture<Void> ClearAuthCacheAsync(RemovePassword options) {
//        var taskCompletionSource = new CompletableFuture<Void>();
//        var guid = UUID.randomUUID().toString();
//        BridgeConnector.getSocket().on("webContents-session-clearAuthCache-completed" + guid, (unused) ->
//        {
//            BridgeConnector.getSocket().off("webContents-session-clearAuthCache-completed" + guid);
//            taskCompletionSource.complete(null);
//        });
//        BridgeConnector.getSocket().emit("webContents-session-clearAuthCache", id, Electron.toJsonObject(options), guid);
//        return taskCompletionSource;
//    }

    public CompletableFuture<Void> ClearAuthCacheAsync() {
        var taskCompletionSource = new CompletableFuture<Void>();
        var guid = UUID.randomUUID().toString();
        BridgeConnector.getSocket().on("webContents-session-clearAuthCache-completed" + guid, (unused) ->
        {
            BridgeConnector.getSocket().off("webContents-session-clearAuthCache-completed" + guid);
            taskCompletionSource.complete(null);
        });
        BridgeConnector.getSocket().emit("webContents-session-clearAuthCache", id, guid);
        return taskCompletionSource;
    }

    public CompletableFuture<Void> ClearCacheAsync() {
        var taskCompletionSource = new CompletableFuture<Void>();
        var guid = UUID.randomUUID().toString();
        BridgeConnector.getSocket().on("webContents-session-clearCache-completed" + guid, (unused) ->
        {
            BridgeConnector.getSocket().off("webContents-session-clearCache-completed" + guid);
            taskCompletionSource.complete(null);
        });
        BridgeConnector.getSocket().emit("webContents-session-clearCache", id, guid);
        return taskCompletionSource;
    }

    public CompletableFuture<Void> ClearHostResolverCacheAsync() {
        var taskCompletionSource = new CompletableFuture<Void>();
        var guid = UUID.randomUUID().toString();
        BridgeConnector.getSocket().on("webContents-session-clearHostResolverCache-completed" + guid, (unused) ->
        {
            BridgeConnector.getSocket().off("webContents-session-clearHostResolverCache-completed" + guid);
            taskCompletionSource.complete(null);
        });
        BridgeConnector.getSocket().emit("webContents-session-clearHostResolverCache", id, guid);
        return taskCompletionSource;
    }

    public CompletableFuture<Void> ClearStorageDataAsync() {
        var taskCompletionSource = new CompletableFuture<Void>();
        var guid = UUID.randomUUID().toString();
        BridgeConnector.getSocket().on("webContents-session-clearStorageData-completed" + guid, (unused) ->
        {
            BridgeConnector.getSocket().off("webContents-session-clearStorageData-completed" + guid);
            taskCompletionSource.complete(null);
        });
        BridgeConnector.getSocket().emit("webContents-session-clearStorageData", id, guid);
        return taskCompletionSource;
    }

    public CompletableFuture<Void> ClearStorageDataAsync(ClearStorageDataOptions options) {
        var taskCompletionSource = new CompletableFuture<Void>();
        var guid = UUID.randomUUID().toString();
        BridgeConnector.getSocket().on("webContents-session-clearStorageData-options-completed" + guid, (unused) ->
        {
            BridgeConnector.getSocket().off("webContents-session-clearStorageData-options-completed" + guid);
            taskCompletionSource.complete(null);
        });
        BridgeConnector.getSocket().emit("webContents-session-clearStorageData-options", id, Electron.toJsonObject(options), guid);
        return taskCompletionSource;
    }

    public void CreateInterruptedDownload(CreateInterruptedDownloadOptions options) {
        BridgeConnector.getSocket().emit("webContents-session-createInterruptedDownload", id, Electron.toJsonObject(options));
    }

    public void DisableNetworkEmulation() {
        BridgeConnector.getSocket().emit("webContents-session-disableNetworkEmulation", id);
    }

    public void EnableNetworkEmulation(EnableNetworkEmulationOptions options) {
        BridgeConnector.getSocket().emit("webContents-session-enableNetworkEmulation", id, Electron.toJsonObject(options));
    }

    public void FlushStorageData() {
        BridgeConnector.getSocket().emit("webContents-session-flushStorageData", id);
    }

    public CompletableFuture<List<Integer>> GetBlobDataAsync(String identifier) {
        var taskCompletionSource = new CompletableFuture<List<Integer>>();
        var guid = UUID.randomUUID().toString();
        BridgeConnector.getSocket().on("webContents-session-getBlobData-completed" + guid, (buffer) ->
        {
            var result = Electron.fromJsonString(buffer[0].toString(), ArrayList.class, Integer.class);
            BridgeConnector.getSocket().off("webContents-session-getBlobData-completed" + guid);
            taskCompletionSource.complete(result);
        });
        BridgeConnector.getSocket().emit("webContents-session-getBlobData", id, identifier, guid);
        return taskCompletionSource;
    }

    public CompletableFuture<Integer> GetCacheSizeAsync() {
        var taskCompletionSource = new CompletableFuture<Integer>();
        var guid = UUID.randomUUID().toString();
        BridgeConnector.getSocket().on("webContents-session-getCacheSize-completed" + guid, (size) ->
        {
            BridgeConnector.getSocket().off("webContents-session-getCacheSize-completed" + guid);
            taskCompletionSource.complete((int) size[0]);
        });
        BridgeConnector.getSocket().emit("webContents-session-getCacheSize", id, guid);
        return taskCompletionSource;
    }

    public CompletableFuture<List<String>> GetPreloadsAsync() {
        var taskCompletionSource = new CompletableFuture<List<String>>();
        var guid = UUID.randomUUID().toString();
        BridgeConnector.getSocket().on("webContents-session-getPreloads-completed" + guid, (preloads) ->
        {
            var result = Electron.fromJsonString(preloads[0].toString(), ArrayList.class, String.class);
            BridgeConnector.getSocket().off("webContents-session-getPreloads-completed" + guid);
            taskCompletionSource.complete(result);
        });
        BridgeConnector.getSocket().emit("webContents-session-getPreloads", id, guid);
        return taskCompletionSource;
    }

    public CompletableFuture<String> GetUserAgent() {
        var taskCompletionSource = new CompletableFuture<String>();
        var guid = UUID.randomUUID().toString();
        BridgeConnector.getSocket().on("webContents-session-getUserAgent-completed" + guid, (userAgent) ->
        {
            BridgeConnector.getSocket().off("webContents-session-getUserAgent-completed" + guid);
            taskCompletionSource.complete(userAgent[0].toString());
        });
        BridgeConnector.getSocket().emit("webContents-session-getUserAgent", id, guid);
        return taskCompletionSource;
    }

    public CompletableFuture<String> ResolveProxyAsync(String url) {
        var taskCompletionSource = new CompletableFuture<String>();
        var guid = UUID.randomUUID().toString();
        BridgeConnector.getSocket().on("webContents-session-resolveProxy-completed" + guid, (proxy) ->
        {
            BridgeConnector.getSocket().off("webContents-session-resolveProxy-completed" + guid);
            taskCompletionSource.complete(proxy[0].toString());
        });
        BridgeConnector.getSocket().emit("webContents-session-resolveProxy", id, url, guid);
        return taskCompletionSource;
    }

    public void SetDownloadPath(String path) {
        BridgeConnector.getSocket().emit("webContents-session-setDownloadPath", id, path);
    }

    public void SetPreloads(String[] preloads) {
        BridgeConnector.getSocket().emit("webContents-session-setPreloads", id, Electron.toJsonArray(preloads));
    }

    public CompletableFuture<Void> SetProxyAsync(ProxyConfig config) {
        var taskCompletionSource = new CompletableFuture<Void>();
        var guid = UUID.randomUUID().toString();
        BridgeConnector.getSocket().on("webContents-session-setProxy-completed" + guid, (unused) ->
        {
            BridgeConnector.getSocket().off("webContents-session-setProxy-completed" + guid);
            taskCompletionSource.complete(null);
        });
        BridgeConnector.getSocket().emit("webContents-session-setProxy", id, Electron.toJsonObject(config), guid);
        return taskCompletionSource;
    }

    public void SetUserAgent(String userAgent) {
        BridgeConnector.getSocket().emit("webContents-session-setUserAgent", id, userAgent);
    }

    public void SetUserAgent(String userAgent, String acceptLanguages) {
        BridgeConnector.getSocket().emit("webContents-session-setUserAgent", id, userAgent, acceptLanguages);
    }

    public CompletableFuture<List<ChromeExtensionInfo>> GetAllExtensionsAsync() {
        var taskCompletionSource = new CompletableFuture<List<ChromeExtensionInfo>>();
        BridgeConnector.getSocket().on("webContents-session-getAllExtensions-completed", (extensionList) ->
        {
            BridgeConnector.getSocket().off("webContents-session-getAllExtensions-completed");
            var chromeExtensionInfos = Electron.fromJsonString(extensionList[0].toString(), ArrayList.class, ChromeExtensionInfo.class);
            taskCompletionSource.complete(chromeExtensionInfos);
        });
        BridgeConnector.getSocket().emit("webContents-session-getAllExtensions", id);
        return taskCompletionSource;
    }

    public void RemoveExtension(String name) {
        BridgeConnector.getSocket().emit("webContents-session-removeExtension", id, name);
    }

    public CompletableFuture<Extension> LoadExtensionAsync(String path) {
        return LoadExtensionAsync(path, false);
    }

    public CompletableFuture<Extension> LoadExtensionAsync(String path, boolean allowFileAccess) {
        CompletableFuture<Extension> taskCompletionSource = new CompletableFuture<>();
        BridgeConnector.getSocket().on("webContents-session-loadExtension-completed", (extension) ->
        {
            BridgeConnector.getSocket().off("webContents-session-loadExtension-completed");
            var extensionList = Electron.fromJsonString(extension[0].toString(), ArrayList.class, Extension.class);
            taskCompletionSource.complete(extensionList.get(0));
        });
        BridgeConnector.getSocket().emit("webContents-session-loadExtension", id, path, allowFileAccess);
        return taskCompletionSource;
    }
}
