package io.gitee.nn.electron.api;

import io.gitee.nn.electron.api.entities.MenuItem;

import java.util.*;
import java.util.stream.Collectors;

@SuppressWarnings("unused")
public final class Menu {
    private volatile static Menu menu;
    private final List<MenuItem> menuItems = new ArrayList<>();
    private final Map<Integer, List<MenuItem>> contextMenuItems = new HashMap<>();
    private IReadOnlyDictionary<Integer, IReadOnlyCollection<MenuItem>> ContextMenuItems;

    Menu() {
    }

    static Menu getInstance() {
        if (menu == null) {
            synchronized (Menu.class) {
                if (menu == null) {
                    menu = new Menu();
                }
            }
        }
        return menu;
    }

    public IReadOnlyCollection<MenuItem> getMenuItems() {
        return IReadOnlyCollection.AsReadOnly(menuItems);
    }

    public void SetApplicationMenu(MenuItem[] menuItems) {
        this.menuItems.clear();
        if (menuItems != null) {
            MenuItemUtils.AddMenuItemsId(menuItems);
            MenuItemUtils.AddSubmenuTypes(menuItems);
            this.menuItems.addAll(List.of(menuItems));
        }

        BridgeConnector.getSocket().emit("menu-setApplicationMenu", Electron.toJsonArray(menuItems));

        BridgeConnector.getSocket().off("menuItemClicked");
        BridgeConnector.getSocket().on("menuItemClicked", (id) -> {
            MenuItem menuItem = MenuItemUtils.GetMenuItem(this.menuItems, id[0].toString());
            if (menuItem.getClick() != null) {
                menuItem.getClick().accept();
            }
        });
    }

    public IReadOnlyDictionary<Integer, IReadOnlyCollection<MenuItem>> getContextMenuItems() {
        return ContextMenuItems;
    }

    public void SetContextMenu(BrowserWindow browserWindow, MenuItem[] menuItems) {
        if (browserWindow == null) {
            return;
        }
        if (menuItems != null) {
            MenuItemUtils.AddMenuItemsId(menuItems);
            MenuItemUtils.AddSubmenuTypes(menuItems);

            if (browserWindow.getId() != null && !contextMenuItems.containsKey(browserWindow.getId())) {
                contextMenuItems.put(browserWindow.getId(), Arrays.stream(menuItems).collect(Collectors.toList()));
                Map<Integer, IReadOnlyCollection<MenuItem>> tmp = new HashMap<>();
                contextMenuItems.forEach((key, value) -> tmp.put(key, IReadOnlyCollection.AsReadOnly(value)));
                ContextMenuItems = IReadOnlyDictionary.AsReadOnly(tmp);
            }
        }
        BridgeConnector.getSocket().emit("menu-setContextMenu", browserWindow.getId(), Electron.toJsonArray(menuItems));

        BridgeConnector.getSocket().off("contextMenuItemClicked");
        BridgeConnector.getSocket().on("contextMenuItemClicked", (results) ->
        {
            List<Object> result = Electron.fromJsonString(results[0].toString(), ArrayList.class, Object.class);
            var id = result.get(0).toString();
            var browserWindowId = Integer.valueOf(result.get(1).toString());

            MenuItem menuItem = MenuItemUtils.GetMenuItem(contextMenuItems.get(browserWindowId), id);
            if (menuItem.getClick() != null) {
                menuItem.getClick().accept();
            }
        });
    }

    public void ContextMenuPopup(BrowserWindow browserWindow) {
        BridgeConnector.getSocket().emit("menu-contextMenuPopup", browserWindow.getId());
    }
}
