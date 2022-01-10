package io.gitee.nn.electron.api;

import io.gitee.nn.electron.api.function.Action1;

import java.util.ArrayList;
import java.util.function.Function;

@SuppressWarnings("unused")
public final class IpcMain {
    private volatile static IpcMain ipcMain;

    IpcMain() {
    }

    static IpcMain getInstance() {
        if (ipcMain == null) {
            synchronized (IpcMain.class) {
                if (ipcMain == null) {
                    ipcMain = new IpcMain();
                }
            }
        }
        return ipcMain;
    }

    public void On(String channel, Action1<Object> listener) {
        var socket= BridgeConnector.getSocket();
        socket.emit("registerIpcMainChannel", channel);
        socket.off(channel);
        socket.on(channel, (args) -> {
            var argList = Electron.fromJsonString(args[0].toString(), ArrayList.class, Object.class);
            if (argList.size() == 1) {
                listener.accept(argList.get(0));
            } else {
                listener.accept(argList);
            }
        });
    }

    public void OnSync(String channel, Function<Object, Object> listener) {
        BridgeConnector.getSocket().emit("registerSyncIpcMainChannel", channel);
        BridgeConnector.getSocket().on(channel, (args) -> {
            var argList = Electron.fromJsonString(args[0].toString(), ArrayList.class, Object.class);
            Object parameter;
            if (argList.size() == 1) {
                parameter = argList.get(0);
            } else {
                parameter = argList;
            }
            var result = listener.apply(parameter);
            BridgeConnector.getSocket().emit(channel + "Sync", result);
        });
    }

    public void Once(String channel, Action1<Object> listener) {
        BridgeConnector.getSocket().emit("registerOnceIpcMainChannel", channel);
        BridgeConnector.getSocket().on(channel, (args) ->
        {
            var argList = Electron.fromJsonString(args[0].toString(), ArrayList.class, Object.class);
            if (argList.size() == 1) {
                listener.accept(argList.get(0));
            } else {
                listener.accept(argList);
            }
        });
    }

    public void RemoveAllListeners(String channel) {
        BridgeConnector.getSocket().emit("removeAllListenersIpcMainChannel", channel);
    }

    public void Send(BrowserWindow browserWindow, String channel, Object... data) {
        BridgeConnector.getSocket().emit("sendToIpcRenderer", browserWindow.getId(), channel, Electron.toJsonArray(data));
    }

    public void Send(BrowserView browserView, String channel, Object... data) {
        BridgeConnector.getSocket().emit("sendToIpcRenderer", browserView.getId(), channel, Electron.toJsonArray(data));
    }

}
