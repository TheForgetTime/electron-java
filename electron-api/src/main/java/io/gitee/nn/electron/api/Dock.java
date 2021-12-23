package io.gitee.nn.electron.api;

import io.gitee.nn.electron.api.entities.DockBounceType;
import io.gitee.nn.electron.api.entities.MenuItem;
import io.gitee.nn.electron.api.util.ResourceFileUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@SuppressWarnings("unused")
public final class Dock {
    private volatile static Dock dock;
    private final List<MenuItem> _items = new ArrayList<>();

    static Dock getInstance() {
        if (dock == null) {
            synchronized (Dock.class) {
                if (dock == null) {
                    dock = new Dock();
                }
            }
        }
        return dock;
    }

    public CompletableFuture<Integer> BounceAsync(DockBounceType type) {
        var taskCompletionSource = new CompletableFuture<Integer>();
        BridgeConnector.getSocket().on("dock-bounce-completed", (id) ->
        {
            BridgeConnector.getSocket().off("dock-bounce-completed");
            taskCompletionSource.complete((Integer) id[0]);
        });

        BridgeConnector.getSocket().emit("dock-bounce", type.getValue());

        return taskCompletionSource;
    }

    public void CancelBounce(Integer id) {
        BridgeConnector.getSocket().emit("dock-cancelBounce", id);
    }

    public void DownloadFinished(String filePath) {
        BridgeConnector.getSocket().emit("dock-downloadFinished", filePath);
    }

    public void SetBadge(String text) {
        BridgeConnector.getSocket().emit("dock-setBadge", text);
    }

    public CompletableFuture<String> GetBadgeAsync() {


        var taskCompletionSource = new CompletableFuture<String>();
        BridgeConnector.getSocket().on("dock-getBadge-completed", (text) ->
        {
            BridgeConnector.getSocket().off("dock-getBadge-completed");
            taskCompletionSource.complete((String) text[0]);
        });

        BridgeConnector.getSocket().emit("dock-getBadge");

        return taskCompletionSource;
    }

    public void Hide() {
        BridgeConnector.getSocket().emit("dock-hide");
    }

    public void Show() {
        BridgeConnector.getSocket().emit("dock-show");
    }

    public CompletableFuture<Boolean> IsVisibleAsync() {


        var taskCompletionSource = new CompletableFuture<Boolean>();

        BridgeConnector.getSocket().on("dock-isVisible-completed", (isVisible) ->
        {
            BridgeConnector.getSocket().off("dock-isVisible-completed");
            taskCompletionSource.complete((Boolean) isVisible[0]);
        });

        BridgeConnector.getSocket().emit("dock-isVisible");

        return taskCompletionSource;
    }

    public IReadOnlyCollection<MenuItem> getMenuItems() {
        return IReadOnlyCollection.AsReadOnly(_items);
    }

    public void SetMenu(MenuItem[] menuItems) {
        MenuItemUtils.AddMenuItemsId(menuItems);
        BridgeConnector.getSocket().emit("dock-setMenu", Electron.toJsonArray(menuItems));
        _items.addAll(List.of(menuItems));

        BridgeConnector.getSocket().off("dockMenuItemClicked");
        BridgeConnector.getSocket().on("dockMenuItemClicked", (id) -> {
            MenuItem menuItem = MenuItemUtils.GetMenuItem(_items, id[0].toString());
            if (menuItem != null) {
                menuItem.getClick().accept();
            }
        });
    }

    public CompletableFuture<Menu> GetMenu() {


        var taskCompletionSource = new CompletableFuture<Menu>();
        BridgeConnector.getSocket().on("dock-getMenu-completed", (menu) ->
        {
            BridgeConnector.getSocket().off("dock-getMenu-completed");
            taskCompletionSource.complete(Electron.fromJsonString(menu[0].toString(), Menu.class));
        });

        BridgeConnector.getSocket().emit("dock-getMenu");

        return taskCompletionSource;
    }

    public void SetIcon(String image) {
        image = ResourceFileUtil.UnzipFromJar(image);
        BridgeConnector.getSocket().emit("dock-setIcon", image);
    }
}
