package io.gitee.nn.controller;

import io.gitee.nn.electron.api.Electron;
import io.gitee.nn.electron.api.HybridSupport;
import io.gitee.nn.electron.api.entities.MessageBoxOptions;
import io.gitee.nn.electron.api.entities.MessageBoxType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.concurrent.CompletableFuture;

@Controller
@RequestMapping("/shortcuts")
public class ShortcutsController {
    @RequestMapping(value = {"", "/index"})
    public String Index() {
        if (HybridSupport.IsElectronActive()) {
            Electron.getGlobalShortcut().Register("CommandOrControl+Alt+K", () ->
            {
                var options = new MessageBoxOptions("You pressed the registered global shortcut keybinding.");
                options.setType(MessageBoxType.INFO);
                options.setTitle("Success!");
                Electron.getDialog().ShowMessageBoxAsync(options);
            });
            Electron.getApp().AddWillQuit(unused -> CompletableFuture.runAsync(() -> Electron.getGlobalShortcut().UnregisterAll()));
        }

        return "Shortcuts/Index";
    }
}
