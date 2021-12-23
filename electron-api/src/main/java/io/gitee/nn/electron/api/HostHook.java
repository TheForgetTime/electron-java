package io.gitee.nn.electron.api;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.ArrayList;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public final class HostHook {
    private volatile static HostHook hostHook;
    String oneCallGuid = UUID.randomUUID().toString();

    HostHook() {
    }

    static HostHook getInstance() {
        if (hostHook == null) {
            synchronized (HostHook.class) {
                if (hostHook == null) {
                    hostHook = new HostHook();
                }
            }
        }
        return hostHook;
    }

    public void Call(String socketEventName, Object[]... arguments) {
        BridgeConnector.getSocket().on(socketEventName + "Error" + oneCallGuid, (result) ->
        {
            BridgeConnector.getSocket().off(socketEventName + "Error" + oneCallGuid);
            Electron.getDialog().ShowErrorBox("Host Hook Exception", result[0].toString());
        });

        BridgeConnector.getSocket().emit(socketEventName, Electron.toJsonArray(arguments), oneCallGuid);
    }

    public <T> CompletableFuture<T> CallAsync(Class<T> type, String socketEventName, Object[]... arguments) {
        var taskCompletionSource = new CompletableFuture<T>();
        String guid = UUID.randomUUID().toString();

        BridgeConnector.getSocket().on(socketEventName + "Error" + guid, (result) ->
        {
            BridgeConnector.getSocket().off(socketEventName + "Error" + guid);
            Electron.getDialog().ShowErrorBox("Host Hook Exception", result[0].toString());
            taskCompletionSource.completeExceptionally(new Exception("Host Hook Exception " + result[0] + ""));
        });

        BridgeConnector.getSocket().on(socketEventName + "Complete" + guid, (result) ->
        {
            BridgeConnector.getSocket().off(socketEventName + "Error" + guid);
            BridgeConnector.getSocket().off(socketEventName + "Complete" + guid);
            T data = null;

            try {
                if (result[0].getClass().isPrimitive() || result[0] instanceof String) {
                    data = (T) result[0];
                } else {
                    var token = Electron.fromJsonString(result[0].toString(), Object.class);
                    if (token instanceof ArrayNode) {
                        data = (T) Electron.fromJsonString(result[0].toString(), ArrayList.class, type);
                    } else if (token instanceof ObjectNode) {
                        data = Electron.fromJsonString(result[0].toString(), type);
                    } else {
                        data = (T) result[0];
                    }
                }
            } catch (Exception exception) {
                taskCompletionSource.completeExceptionally(exception);
            }

            taskCompletionSource.complete(data);
        });

        BridgeConnector.getSocket().emit(socketEventName, arguments, guid);

        return taskCompletionSource;
    }
}
