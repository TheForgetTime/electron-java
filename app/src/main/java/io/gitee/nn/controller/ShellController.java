package io.gitee.nn.controller;

import io.gitee.nn.electron.api.Electron;
import io.gitee.nn.electron.api.HybridSupport;
import io.gitee.nn.electron.api.entities.PathName;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/shell")
public class ShellController {
    @RequestMapping(value = {"", "/index"})
    public String Index() {
        if (HybridSupport.IsElectronActive()) {
            Electron.getIpcMain().On("open-file-manager", (args) ->
                    Electron.getApp().GetPathAsync(PathName.HOME).thenAccept(path ->
                            Electron.getShell().ShowItemInFolderAsync(path)
                    )
            );

            Electron.getIpcMain().On("open-ex-links", (args) ->
                    Electron.getShell().OpenExternalAsync("https://github.com/ElectronNET")
            );
        }
        return "Shell/Index";
    }
}
