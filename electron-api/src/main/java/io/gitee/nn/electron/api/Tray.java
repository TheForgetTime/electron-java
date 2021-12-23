package io.gitee.nn.electron.api;

import io.gitee.nn.electron.api.entities.DisplayBalloonOptions;
import io.gitee.nn.electron.api.entities.MenuItem;
import io.gitee.nn.electron.api.entities.Rectangle;
import io.gitee.nn.electron.api.entities.TrayClickEventArgs;
import io.gitee.nn.electron.api.function.Action0;
import io.gitee.nn.electron.api.function.Action2;
import io.gitee.nn.electron.api.util.ResourceFileUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@SuppressWarnings("unused")
public final class Tray {
    private volatile static Tray tray;
    private final List<Action2<TrayClickEventArgs, Rectangle>> click = new ArrayList<>();
    private final List<Action2<TrayClickEventArgs, Rectangle>> rightClick = new ArrayList<>();
    private final List<Action2<TrayClickEventArgs, Rectangle>> doubleClick = new ArrayList<>();
    private final List<Action0> balloonShow = new ArrayList<>();
    private final List<Action0> balloonClick = new ArrayList<>();
    private final List<Action0> balloonClosed = new ArrayList<>();
    private final List<MenuItem> items = new ArrayList<>();

    Tray() {
    }

    static Tray getInstance() {
        if (tray == null) {
            synchronized (Tray.class) {
                if (tray == null) {
                    tray = new Tray();
                }
            }
        }
        return tray;
    }

    public void AddOnClick(Action2<TrayClickEventArgs, Rectangle> value) {
        if (click.size() == 0) {
            BridgeConnector.getSocket().on("tray-click-event" + hashCode(), (result) ->
            {
                var args = Electron.fromJsonString(result[0].toString(), ArrayList.class, Object.class);
                var trayClickEventArgs = (TrayClickEventArgs) args.get(0);
                var bounds = (Rectangle) args.get(1);
                click.forEach(it -> it.accept(trayClickEventArgs, bounds));
            });

            BridgeConnector.getSocket().emit("register-tray-click", hashCode());
        }
        click.add(value);
    }

    public void RemoveOnClick(Action2<TrayClickEventArgs, Rectangle> value) {
        click.remove(value);

        if (click.size() == 0)
            BridgeConnector.getSocket().off("tray-click-event" + hashCode());
    }

    public void AddOnRightClick(Action2<TrayClickEventArgs, Rectangle> value) {
        if (rightClick.size() == 0) {
            BridgeConnector.getSocket().on("tray-right-click-event" + hashCode(), (result) ->
            {
                var args = Electron.fromJsonString(result[0].toString(), ArrayList.class, Object.class);
                var trayClickEventArgs = (TrayClickEventArgs) args.get(0);
                var bounds = (Rectangle) args.get(1);
                rightClick.forEach(it -> it.accept(trayClickEventArgs, bounds));
            });

            BridgeConnector.getSocket().emit("register-tray-right-click", hashCode());
        }
        rightClick.add(value);
    }

    public void RemoveOnRightClick(Action2<TrayClickEventArgs, Rectangle> value) {
        rightClick.remove(value);

        if (rightClick.size() == 0)
            BridgeConnector.getSocket().off("tray-right-click-event" + hashCode());
    }

    public void AddOnDoubleClick(Action2<TrayClickEventArgs, Rectangle> value) {
        if (doubleClick.size() == 0) {
            BridgeConnector.getSocket().on("tray-double-click-event" + hashCode(), (result) ->
            {
                var args = Electron.fromJsonString(result[0].toString(), ArrayList.class, Object.class);
                var trayClickEventArgs = (TrayClickEventArgs) args.get(0);
                var bounds = (Rectangle) args.get(1);
                doubleClick.forEach(it -> it.accept(trayClickEventArgs, bounds));
            });

            BridgeConnector.getSocket().emit("register-tray-double-click", hashCode());
        }
        doubleClick.add(value);
    }

    public void RemoveOnDoubleClick(Action2<TrayClickEventArgs, Rectangle> value) {
        doubleClick.remove(value);

        if (doubleClick.size() == 0)
            BridgeConnector.getSocket().off("tray-double-click-event" + hashCode());
    }

    public void AddOnBalloonShow(Action0 value) {
        if (balloonShow.size() == 0) {
            BridgeConnector.getSocket().on("tray-balloon-show-event" + hashCode(), (unused) -> balloonShow.forEach(Action0::accept));

            BridgeConnector.getSocket().emit("register-tray-balloon-show", hashCode());
        }
        balloonShow.add(value);
    }

    public void RemoveOnBalloonShow(Action0 value) {
        balloonShow.remove(value);

        if (balloonShow.size() == 0)
            BridgeConnector.getSocket().off("tray-balloon-show-event" + hashCode());
    }

    public void AddOnBalloonClick(Action0 value) {
        if (balloonClick.size() == 0) {
            BridgeConnector.getSocket().on("tray-balloon-click-event" + hashCode(), (unused) -> balloonClick.forEach(Action0::accept));

            BridgeConnector.getSocket().emit("register-tray-balloon-click", hashCode());
        }
        balloonClick.add(value);
    }

    public void RemoveOnBalloonClick(Action0 value) {
        balloonClick.remove(value);

        if (balloonClick.size() == 0)
            BridgeConnector.getSocket().off("tray-balloon-click-event" + hashCode());
    }

    public void AddOnBalloonClosed(Action0 value) {
        if (balloonClosed.size() == 0) {
            BridgeConnector.getSocket().on("tray-balloon-closed-event" + hashCode(), (unused) -> balloonClosed.forEach(Action0::accept));

            BridgeConnector.getSocket().emit("register-tray-balloon-closed", hashCode());
        }
        balloonClosed.add(value);
    }

    public void RemoveOnBalloonClosed(Action0 value) {
        balloonClosed.remove(value);

        if (balloonClosed.size() == 0)
            BridgeConnector.getSocket().off("tray-balloon-closed-event" + hashCode());
    }

    public IReadOnlyCollection<MenuItem> getMenuItems() {
        return IReadOnlyCollection.AsReadOnly(items);
    }

    public void Show(String image, MenuItem menuItem) {
        Show(image, new MenuItem[]{menuItem});
    }

    public void Show(String image, MenuItem[] menuItems) {
        image = ResourceFileUtil.UnzipFromJar(image);
        MenuItemUtils.AddMenuItemsId(menuItems);
        BridgeConnector.getSocket().emit("create-tray", image, Electron.toJsonArray(menuItems));
        items.clear();
        items.addAll(List.of(menuItems));

        BridgeConnector.getSocket().off("trayMenuItemClicked");
        BridgeConnector.getSocket().on("trayMenuItemClicked", (id) ->
        {
            MenuItem menuItem = MenuItemUtils.GetMenuItem(items, id[0].toString());
            if (menuItem.getClick() != null) {
                menuItem.getClick().accept();
            }
        });
    }

    public void Show(String image) {
        image = ResourceFileUtil.UnzipFromJar(image);
        BridgeConnector.getSocket().emit("create-tray", image);
    }

    public void Destroy() {
        BridgeConnector.getSocket().emit("tray-destroy");
        items.clear();
    }

    public void SetImage(String image) {
        BridgeConnector.getSocket().emit("tray-setImage", image);
    }

    public void SetPressedImage(String image) {
        BridgeConnector.getSocket().emit("tray-setPressedImage", image);
    }

    public void SetToolTip(String toolTip) {
        BridgeConnector.getSocket().emit("tray-setToolTip", toolTip);
    }

    public void SetTitle(String title) {
        BridgeConnector.getSocket().emit("tray-setTitle", title);
    }

    public void DisplayBalloon(DisplayBalloonOptions options) {
        BridgeConnector.getSocket().emit("tray-displayBalloon", Electron.toJsonObject(options));
    }

    public CompletableFuture<Boolean> IsDestroyedAsync() {
        var taskCompletionSource = new CompletableFuture<Boolean>();

        BridgeConnector.getSocket().on("tray-isDestroyedCompleted", (isDestroyed) ->
        {
            BridgeConnector.getSocket().off("tray-isDestroyedCompleted");

            taskCompletionSource.complete((Boolean) isDestroyed[0]);
        });

        BridgeConnector.getSocket().emit("tray-isDestroyed");

        return taskCompletionSource;
    }
}
