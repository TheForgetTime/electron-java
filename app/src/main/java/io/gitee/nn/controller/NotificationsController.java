package io.gitee.nn.controller;

import io.gitee.nn.electron.api.Electron;
import io.gitee.nn.electron.api.HybridSupport;
import io.gitee.nn.electron.api.entities.NotificationOptions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/notifications")
public class NotificationsController {
    @RequestMapping(value = {"", "/index"})
    public String Index() {
        if (HybridSupport.IsElectronActive()) {
            Electron.getIpcMain().On("basic-noti", (args) -> {
                var options = new NotificationOptions("Basic Notification", "Short message part");

                options.setOnClick(() -> Electron.getDialog().ShowMessageBoxAsync("Notification clicked"));
                Electron.getNotification().Show(options);
            });

            Electron.getIpcMain().On("advanced-noti", (args) -> {
                var options = new NotificationOptions("Notification with image", "Short message plus a custom image");

                options.setOnClick(() -> Electron.getDialog().ShowMessageBoxAsync("Notification clicked"));
                options.setIcon("/static/img/programming.png");
                Electron.getNotification().Show(options);
            });
        }

        return "Notifications/Index";
    }
}
