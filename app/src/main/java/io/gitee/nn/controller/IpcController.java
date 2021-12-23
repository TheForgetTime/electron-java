package io.gitee.nn.controller;

import io.gitee.nn.electron.api.Electron;
import io.gitee.nn.electron.api.HybridSupport;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/ipc")
public class IpcController {
    @RequestMapping(value = {"", "/index"})
    public String Index() {
        if (HybridSupport.IsElectronActive()) {
            Electron.getIpcMain().On("async-msg", (args) ->
            {
                var mainWindow = Electron.getWindowManager().GetBrowserWindows().First();
                Electron.getIpcMain().Send(mainWindow, "asynchronous-reply", "pong");
            });

            Electron.getIpcMain().OnSync("sync-msg", (args) -> "pong");
        }
        return "Ipc/Index";
    }
}
