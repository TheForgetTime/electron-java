package io.gitee.nn.electron.api;

import io.gitee.nn.electron.api.entities.MenuItem;
import io.gitee.nn.electron.api.entities.MenuType;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

final class MenuItemUtils {
    public static void AddMenuItemsId(MenuItem[] menuItems) {
        for (MenuItem menuItem : menuItems) {
            if (menuItem == null)
                continue;
            if (menuItem.getSubmenu() != null && menuItem.getSubmenu().length > 0) {
                AddMenuItemsId(menuItem.getSubmenu());
            }
            if ((menuItem.getId() == null || menuItem.getId().isEmpty()) && menuItem.getClick() != null) {
                menuItem.setId(UUID.randomUUID().toString());
            }
        }
    }

    static MenuItem GetMenuItem(List<MenuItem> menuItems, String id) {
        MenuItem result = new MenuItem();

        for (var item : menuItems) {
            if (id.equals(item.getId())) {
                result = item;
            } else if (item.getSubmenu() != null && item.getSubmenu().length > 0) {
                var menuItem = GetMenuItem(Arrays.stream(item.getSubmenu()).collect(Collectors.toList()), id);
                if (id.equals(menuItem.getId())) {
                    result = menuItem;
                }
            }
        }

        return result;
    }

    static void AddSubmenuTypes(MenuItem[] menuItems) {
        for (MenuItem menuItem : menuItems) {
            if (menuItem != null && menuItem.getSubmenu() != null && menuItem.getSubmenu().length > 0) {
                if (menuItem.getType() == MenuType.NORMAL) {
                    menuItem.setType(MenuType.SUBMENU);
                }

                AddSubmenuTypes(menuItem.getSubmenu());
            }
        }
    }
}
