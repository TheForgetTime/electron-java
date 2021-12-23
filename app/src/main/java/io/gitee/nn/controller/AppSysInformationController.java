package io.gitee.nn.controller;

import io.gitee.nn.electron.api.Electron;
import io.gitee.nn.electron.api.HybridSupport;
import io.gitee.nn.electron.api.entities.PathName;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/appsysinformation")
public class AppSysInformationController {
    @RequestMapping(value = {"", "/index"})
    public String Index() {
        if (HybridSupport.IsElectronActive()) {
            Electron.getIpcMain().On("app-info", (args) -> Electron.getApp().GetAppPathAsync().thenAccept(appPath -> {
                var mainWindow = Electron.getWindowManager().GetBrowserWindows().First();
                Electron.getIpcMain().Send(mainWindow, "got-app-path", appPath);
            }));

            Electron.getIpcMain().On("sys-info", (args) -> Electron.getApp().GetPathAsync(PathName.HOME).thenAccept(homePath -> {
                var mainWindow = Electron.getWindowManager().GetBrowserWindows().First();
                Electron.getIpcMain().Send(mainWindow, "got-sys-info", homePath);
            }));

            Electron.getIpcMain().On("screen-info", (args) -> Electron.getScreen().GetPrimaryDisplayAsync().thenAccept(display -> {
                var mainWindow = Electron.getWindowManager().GetBrowserWindows().First();
                Electron.getIpcMain().Send(mainWindow, "got-screen-info", display.getSize());
            }));
        }
        return "AppSysInformation/Index";
    }
}
