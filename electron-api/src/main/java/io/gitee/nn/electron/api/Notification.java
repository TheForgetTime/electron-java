package io.gitee.nn.electron.api;

import io.gitee.nn.electron.api.entities.NotificationOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public final class Notification {
    private static final List<NotificationOptions> notificationOptions = new ArrayList<>();
    private volatile static Notification notification;

    Notification() {
    }

    static Notification getInstance() {
        if (notification == null) {
            synchronized (Notification.class) {
                if (notification == null) {
                    notification = new Notification();
                }
            }
        }
        return notification;
    }

    private static void GenerateIDsForDefinedActions(NotificationOptions notificationOptions) {
        boolean isActionDefined = false;

        if (notificationOptions.getOnShow() != null) {
            notificationOptions.setShowID(UUID.randomUUID().toString());
            isActionDefined = true;

            BridgeConnector.getSocket().off("NotificationEventShow");
            BridgeConnector.getSocket().on("NotificationEventShow", (id) -> Notification.notificationOptions.stream().filter(x -> Objects.equals(x.getShowID(), id[0].toString())).limit(1).forEach(it -> it.getOnShow().accept()));
        }

        if (notificationOptions.getOnClick() != null) {
            notificationOptions.setClickID(UUID.randomUUID().toString());
            isActionDefined = true;

            BridgeConnector.getSocket().off("NotificationEventClick");
            BridgeConnector.getSocket().on("NotificationEventClick", (id) -> Notification.notificationOptions.stream().filter(x -> Objects.equals(x.getClickID(), id[0].toString())).limit(1).forEach(it -> it.getOnClick().accept()));
        }

        if (notificationOptions.getOnClose() != null) {
            notificationOptions.setCloseID(UUID.randomUUID().toString());
            isActionDefined = true;

            BridgeConnector.getSocket().off("NotificationEventClose");
            BridgeConnector.getSocket().on("NotificationEventClose", (id) -> Notification.notificationOptions.stream().filter(x -> Objects.equals(x.getCloseID(), id[0].toString())).limit(1).forEach(it -> it.getOnClose().accept()));
        }

        if (notificationOptions.getOnReply() != null) {
            notificationOptions.setReplyID(UUID.randomUUID().toString());
            isActionDefined = true;

            BridgeConnector.getSocket().off("NotificationEventReply");
            BridgeConnector.getSocket().on("NotificationEventReply", (args) -> {
                var arguments = Electron.fromJsonString(args[0].toString(), ArrayList.class, String.class);
                Notification.notificationOptions.stream().filter(x -> Objects.equals(x.getReplyID(), arguments.get(0))).limit(1).forEach(it -> it.getOnReply().accept(arguments.get(1)));
            });
        }

        if (notificationOptions.getOnAction() != null) {
            notificationOptions.setActionID(UUID.randomUUID().toString());
            isActionDefined = true;

            BridgeConnector.getSocket().off("NotificationEventAction");
            BridgeConnector.getSocket().on("NotificationEventAction", (args) -> {
                var arguments = Electron.fromJsonString(args[0].toString(), ArrayList.class, String.class);
                Notification.notificationOptions.stream().filter(x -> Objects.equals(x.getReplyID(), arguments.get(0))).limit(1).forEach(it -> it.getOnAction().accept(arguments.get(1)));
            });
        }

        if (isActionDefined) {
            Notification.notificationOptions.add(notificationOptions);
        }
    }

    public void Show(NotificationOptions notificationOptions) {
        GenerateIDsForDefinedActions(notificationOptions);

        BridgeConnector.getSocket().emit("createNotification", Electron.toJsonObject(notificationOptions));
    }

    public CompletableFuture<Boolean> IsSupportedAsync() {
        var taskCompletionSource = new CompletableFuture<Boolean>();

        BridgeConnector.getSocket().on("notificationIsSupportedComplete", (isSupported) ->
        {
            BridgeConnector.getSocket().off("notificationIsSupportedComplete");
            taskCompletionSource.complete((Boolean) isSupported[0]);
        });

        BridgeConnector.getSocket().emit("notificationIsSupported");

        return taskCompletionSource;
    }
}
