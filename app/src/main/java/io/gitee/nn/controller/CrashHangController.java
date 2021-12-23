package io.gitee.nn.controller;

import io.gitee.nn.electron.api.BridgeSettings;
import io.gitee.nn.electron.api.Electron;
import io.gitee.nn.electron.api.HybridSupport;
import io.gitee.nn.electron.api.entities.MessageBoxOptions;
import io.gitee.nn.electron.api.entities.MessageBoxType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/crashhang")
public class CrashHangController {
    @RequestMapping(value = {"", "/index"})
    public String Index() {
        if (HybridSupport.IsElectronActive()) {
            Electron.getIpcMain().On("process-crash", (args) ->
            {
                var viewPath = "http://localhost:" + BridgeSettings.getWebPort() + "/crashhang/processcrash";

                Electron.getWindowManager().CreateWindowAsync(viewPath).thenAccept(browserWindow -> browserWindow.getWebContents().AddOnCrashed((killed) -> {
                    System.out.println("This process has crashed");
                    var options = new MessageBoxOptions("This process has crashed.");
                    options.setType(MessageBoxType.INFO);
                    options.setTitle("Renderer Process Crashed");
                    options.setButtons(new String[]{"Reload", "Close"});
                    Electron.getDialog().ShowMessageBoxAsync(options).thenAccept(messageBoxResult -> {
                        if (messageBoxResult.getResponse() == 0) {
                            browserWindow.Reload();
                        } else {
                            browserWindow.Close();
                        }
                    });
                }));
            });

            Electron.getIpcMain().On("process-hang", (args) ->
            {
                var viewPath = "http://localhost:" + BridgeSettings.getWebPort() + "/crashhang/processhang";

                Electron.getWindowManager().CreateWindowAsync(viewPath).thenAccept(browserWindow -> browserWindow.AddOnUnresponsive(() ->
                {
                    var options = new MessageBoxOptions("This process is hanging.");
                    options.setType(MessageBoxType.INFO);
                    options.setTitle("Renderer Process Hanging");
                    options.setButtons(new String[]{"Reload", "Close"});
                    Electron.getDialog().ShowMessageBoxAsync(options).thenAccept(messageBoxResult -> {
                        if (messageBoxResult.getResponse() == 0) {
                            browserWindow.Reload();
                        } else {
                            browserWindow.Close();
                        }
                    });
                }));
            });
        }

        return "CrashHang/Index";
    }

    @RequestMapping("/processcrash")
    public String ProcessCrash() {
        return "CrashHang/ProcessCrash";
    }

    @RequestMapping("/processhang")
    public String ProcessHang() {
        return "CrashHang/ProcessHang";
    }
}
