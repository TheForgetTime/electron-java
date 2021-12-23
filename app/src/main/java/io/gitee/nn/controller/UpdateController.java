package io.gitee.nn.controller;

import io.gitee.nn.electron.api.Electron;
import io.gitee.nn.electron.api.HybridSupport;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/update")
public class UpdateController {
    @RequestMapping(value = {"", "/index"})
    public String Index() {
        if (HybridSupport.IsElectronActive()) {
            Electron.getAutoUpdater().AddOnError((message) -> Electron.getDialog().ShowErrorBox("Error", message));
            Electron.getAutoUpdater().AddOnCheckingForUpdate(() -> Electron.getDialog().ShowMessageBoxAsync("Checking for Update"));
            Electron.getAutoUpdater().AddOnUpdateNotAvailable((info) -> Electron.getDialog().ShowMessageBoxAsync("Update not available"));
            Electron.getAutoUpdater().AddOnUpdateAvailable((info) -> Electron.getDialog().ShowMessageBoxAsync("Update available" + info.getVersion()));
            Electron.getAutoUpdater().AddOnDownloadProgress((info) ->
            {
                var message1 = "Download speed: " + info.getBytesPerSecond() + "\n<br/>";
                var message2 = "Downloaded " + info.getPercent() + "%" + "\n<br/>";
                var message3 = "(" + info.getTransferred() + "/" + info.getTotal() + ")" + "\n<br/>";
                var message4 = "Progress: " + info.getProgress() + "\n<br/>";
                var information = message1 + message2 + message3 + message4;

                var mainWindow = Electron.getWindowManager().GetBrowserWindows().First();
                Electron.getIpcMain().Send(mainWindow, "auto-update-reply", information);
            });
            Electron.getAutoUpdater().AddOnUpdateDownloaded((info) -> Electron.getDialog().ShowMessageBoxAsync("Update complete!" + info.getVersion()));

            Electron.getIpcMain().On("auto-update", (args) -> Electron.getApp().GetVersionAsync().thenAcceptBothAsync(Electron.getAutoUpdater().CheckForUpdatesAndNotifyAsync(), (currentVersion, updateCheckResult) -> {
                var availableVersion = updateCheckResult.getUpdateInfo().getVersion();
                String information = "Current version: " + currentVersion + " - available version: " + availableVersion + "";
                var mainWindow = Electron.getWindowManager().GetBrowserWindows().First();
                Electron.getIpcMain().Send(mainWindow, "auto-update-reply", information);
            }));
        }
        return "Update/Index";
    }
}
