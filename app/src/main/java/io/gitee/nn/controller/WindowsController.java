package io.gitee.nn.controller;

import io.gitee.nn.electron.api.BridgeSettings;
import io.gitee.nn.electron.api.Electron;
import io.gitee.nn.electron.api.HybridSupport;
import io.gitee.nn.electron.api.entities.BrowserWindowOptions;
import io.gitee.nn.electron.api.function.Action0;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/windows")
public class WindowsController {
    private final Action0 UpdateReply = () -> {
        var browserWindow = Electron.getWindowManager().GetBrowserWindows().Last();
        browserWindow.GetBoundsAsync();
        browserWindow.GetSizeAsync().thenAcceptBothAsync(
                browserWindow.GetPositionAsync(),
                (size, position) -> {
                    String message = "Size: " + size[0] + "," + size[1] + " Position: " + position[0] + "," + position[1] + "";

                    var mainWindow = Electron.getWindowManager().GetBrowserWindows().First();
                    Electron.getIpcMain().Send(mainWindow, "manage-window-reply", message);
                });
    };

    @RequestMapping(value = {"", "/index"})
    public String Index() {
        if (HybridSupport.IsElectronActive()) {
            String viewPath = "http://localhost:" + BridgeSettings.getWebPort() + "/windows/demowindow";
            Electron.getIpcMain().On("new-window", (args) -> Electron.getWindowManager().CreateWindowAsync(viewPath));
            Electron.getIpcMain().On("manage-window", (args) ->
                    Electron.getWindowManager().CreateWindowAsync(viewPath)
                            .thenAccept(browserWindow -> {
                                browserWindow.AddOnMove(UpdateReply);
                                browserWindow.AddOnResize(UpdateReply);
                            }));
            Electron.getIpcMain().On("listen-to-window", (args) ->
            {
                var mainBrowserWindow = Electron.getWindowManager().GetBrowserWindows().First();

                Electron.getWindowManager().CreateWindowAsync(viewPath)
                        .thenAccept(browserWindow -> {
                            browserWindow.AddOnFocus(() -> Electron.getIpcMain().Send(mainBrowserWindow, "listen-to-window-focus"));
                            browserWindow.AddOnBlur(() -> Electron.getIpcMain().Send(mainBrowserWindow, "listen-to-window-blur"));
                            browserWindow.AddOnClose(() -> Electron.getIpcMain().Send(mainBrowserWindow, "listen-to-window-focus"));
                            Electron.getIpcMain().On("listen-to-window-set-focus", (x) -> browserWindow.Focus());
                        });
            });

            Electron.getIpcMain().On("frameless-window", (args) ->
            {
                var options = new BrowserWindowOptions();
                options.setFrame(false);
//                options.setTransparent(true);
                Electron.getWindowManager().CreateWindowAsync(options, viewPath);
            });
        }

        return "Windows/Index";
    }

    @RequestMapping("/demowindow")
    public String DemoWindow() {
        return "Windows/DemoWindow";
    }
}
