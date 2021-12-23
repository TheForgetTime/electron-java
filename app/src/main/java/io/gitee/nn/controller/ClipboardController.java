package io.gitee.nn.controller;

import io.gitee.nn.electron.api.Electron;
import io.gitee.nn.electron.api.HybridSupport;
import io.gitee.nn.electron.api.entities.NativeImage;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;

@Controller
@RequestMapping("/clipboard")
public class ClipboardController {
    @RequestMapping(value = {"", "/index"})
    public String Index() {
        if (HybridSupport.IsElectronActive()) {
            Electron.getIpcMain().On("copy-to", (text) -> Electron.getClipboard().WriteText(text.toString()));

            Electron.getIpcMain().On("paste-to", (text) ->
            {
                Electron.getClipboard().WriteText(text.toString());
                Electron.getClipboard().ReadTextAsync().thenAccept(pasteText -> {
                    var mainWindow = Electron.getWindowManager().GetBrowserWindows().First();
                    Electron.getIpcMain().Send(mainWindow, "paste-from", pasteText);
                });
            });

            Electron.getIpcMain().On("copy-image-to", (test) ->
            {
                try {
                    NativeImage nativeImage = NativeImage.CreateFromDataURL(test.toString());
                    Electron.getClipboard().WriteImage(nativeImage);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            Electron.getIpcMain().On("paste-image-to", (test) ->
                    Electron.getClipboard().ReadImageAsync().thenAccept(nativeImage -> {
                        var mainWindow = Electron.getWindowManager().GetBrowserWindows().First();
                        Electron.getIpcMain().Send(mainWindow, "paste-image-from", Electron.toJsonString(nativeImage));
                    })
            );
        }
        return "Clipboard/Index";
    }
}
