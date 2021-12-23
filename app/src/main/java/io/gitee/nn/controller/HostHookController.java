package io.gitee.nn.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/hosthook")
public class HostHookController {
    @RequestMapping(value = {"", "/index"})
    public String Index() {
//        if (HybridSupport.IsElectronActive()) {
//            Electron.getIpcMain().On("start-hoosthook", (args) ->
//            {
//                var mainWindow = Electron.getWindowManager().GetBrowserWindows().First();
//                var options = OpenDialogOptions.builder()
//                        .properties(new OpenDialogProperty[]{
//                                OpenDialogProperty.OPEN_DIRECTORY
//                        })
//                        .build();
//                Electron.getDialog().ShowOpenDialogAsync(mainWindow, options)
//                        .thenAccept(folderPath -> Electron.getHostHook().CallAsync(String.class, "create-excel-file", folderPath)
//                                .thenAccept(resultFromTypeScript -> Electron.getIpcMain().Send(mainWindow, "excel-file-created", resultFromTypeScript)
//                                )
//                        );
//            });
//        }

        return "HostHook/Index";
    }
}
