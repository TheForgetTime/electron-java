package io.gitee.nn.controller;

import io.gitee.nn.electron.api.Electron;
import io.gitee.nn.electron.api.HybridSupport;
import io.gitee.nn.electron.api.entities.MenuItem;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/tray")
public class TrayController {
    @RequestMapping(value = {"", "/index"})
    public String Index() {
        if (HybridSupport.IsElectronActive()) {
            Electron.getIpcMain().On("put-in-tray", (args) ->
            {
                if (Electron.getTray().getMenuItems().size() == 0) {
                    var menu = MenuItem.builder()
                            .label("Remove")
                            .click(() -> Electron.getTray().Destroy())
                            .build();

                    Electron.getTray().Show("/assets/electron_32x32.png", menu);
                    Electron.getTray().SetToolTip("Electron Demo in the tray.");
                } else {
                    Electron.getTray().Destroy();
                }
            });
        }
        return "Tray/Index";
    }
}
