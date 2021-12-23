package io.gitee.nn.controller;

import io.gitee.nn.electron.api.Electron;
import io.gitee.nn.electron.api.HybridSupport;
import io.gitee.nn.electron.api.entities.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/menus")
public class MenusController {
    @RequestMapping(value = {"", "/index"})
    public String Index() {
        if (HybridSupport.IsElectronActive()) {
            Electron.getApp().AddReady(this::CreateContextMenu);
            var menu = new MenuItem[]{
                    MenuItem.builder().label("编辑(Edit)").submenu(new MenuItem[]{
                            MenuItem.builder().label("撤销(Undo)").accelerator("CmdOrCtrl+Z").role(MenuRole.UNDO).build(),
                            MenuItem.builder().label("重做(Redo)").accelerator("Shift+CmdOrCtrl+Z").role(MenuRole.REDO).build(),
                            MenuItem.builder().type(MenuType.SEPARATOR).build(),
                            MenuItem.builder().label("剪切(Cut)").accelerator("CmdOrCtrl+X").role(MenuRole.CUT).build(),
                            MenuItem.builder().label("复制(Copy)").accelerator("CmdOrCtrl+C").role(MenuRole.COPY).build(),
                            MenuItem.builder().label("粘贴(Paste)").accelerator("CmdOrCtrl+V").role(MenuRole.PASTE).build(),
                            MenuItem.builder().label("全选(Select All)").accelerator("CmdOrCtrl+A").role(MenuRole.SELECT_ALL).build()
                    }).build(),
                    MenuItem.builder().label("视图(View)").submenu(new MenuItem[]{
                            MenuItem.builder().label("重载(Reload)").accelerator("CmdOrCtrl+R").click(() ->
                            {
                                // on reload, start fresh and close any old
                                // open secondary windows
                                var mainWindowId = Electron.getWindowManager().GetBrowserWindows().First().getId();
                                Electron.getWindowManager().GetBrowserWindows().forEach(browserWindow -> {
                                    if (!browserWindow.getId().equals(mainWindowId)) {
                                        browserWindow.Close();
                                    } else {
                                        browserWindow.Reload();
                                    }
                                });
                            }).build(),
                            MenuItem.builder().label("切换全屏(Toggle Full Screen)").accelerator("CmdOrCtrl+F").click(() -> Electron.getWindowManager().GetBrowserWindows().First().IsFullScreenAsync().thenAccept(isFullScreen -> Electron.getWindowManager().GetBrowserWindows().First().SetFullScreen(!isFullScreen))).build(),
                            MenuItem.builder().label("打开开发者工具(Open Developer Tools)").accelerator("CmdOrCtrl+I").click(() -> Electron.getWindowManager().GetBrowserWindows().First().getWebContents().OpenDevTools()).build(),
                            MenuItem.builder().type(MenuType.SEPARATOR).build(),
                            MenuItem.builder().label("应用程序菜单示例(App Menu Demo)").click(() -> {
                                var options = new MessageBoxOptions("这个演示是针对菜单部分的，展示了如何在应用程序菜单中创建一个可点击的菜单项" +
                                        "This demo is for the Menu section, showing how to create a clickable menu item in the application menu.");
                                options.setType(MessageBoxType.INFO);
                                options.setTitle("应用程序菜单示例(Application Menu Demo)");
                                Electron.getDialog().ShowMessageBoxAsync(options);
                            }).build()
                    }).build(),
                    MenuItem.builder().label("窗口(Window)").role(MenuRole.WINDOW).submenu(new MenuItem[]{
                            MenuItem.builder().label("最小化(Minimize)").accelerator("CmdOrCtrl+M").role(MenuRole.MINIMIZE).build(),
                            MenuItem.builder().label("关闭(Close)").accelerator("CmdOrCtrl+W").role(MenuRole.CLOSE).build()
                    }).build(),
                    MenuItem.builder().label("帮助(Help)").role(MenuRole.HELP).submenu(new MenuItem[]{
                            MenuItem.builder().label("学习更多(Learn More)").click(() -> Electron.getShell().OpenExternalAsync("https://github.com/ElectronNET")).build()
                    }).build()
            };
            Electron.getMenu().SetApplicationMenu(menu);
        }
        return "Menus/Index";
    }

    private void CreateContextMenu() {
        final var menu = new MenuItem[]{
                MenuItem.builder().label("你好(Hello)").click(() -> Electron.getDialog().ShowMessageBoxAsync("Electron.Java rocks!")).build(),
                MenuItem.builder().type(MenuType.SEPARATOR).build(),
                MenuItem.builder().label("Electron.Java").type(MenuType.CHECKBOX).checked(true).build()
        };

        final var mainWindow = Electron.getWindowManager().GetBrowserWindows().First();
        if (mainWindow != null) {
            Electron.getMenu().SetContextMenu(mainWindow, menu);
            Electron.getIpcMain().On("show-context-menu", (args) -> Electron.getMenu().ContextMenuPopup(mainWindow));
        }
    }
}
