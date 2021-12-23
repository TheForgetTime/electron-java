package io.gitee.nn.controller;

import io.gitee.nn.electron.api.Electron;
import io.gitee.nn.electron.api.HybridSupport;
import io.gitee.nn.electron.api.entities.FileFilter;
import io.gitee.nn.electron.api.entities.PathName;
import io.gitee.nn.electron.api.entities.SaveDialogOptions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/pdf")
public class PdfController {
    @RequestMapping(value = {"", "/index"})
    public String Index() {
        if (HybridSupport.IsElectronActive()) {
            Electron.getIpcMain().On("print-pdf", (args) ->
            {
                var mainWindow = Electron.getWindowManager().GetBrowserWindows().First();
                Electron.getApp().GetPathAsync(PathName.DOCUMENTS).thenAccept(defaultPath -> {
                    var saveOptions = SaveDialogOptions.builder()
                            .title("Save an PDF-File")
                            .defaultPath(defaultPath)
                            .filters(new FileFilter[]{
                                    FileFilter.builder().name("PDF").extensions(new String[]{"pdf"}).build()
                            })
                            .build();

                    Electron.getDialog().ShowSaveDialogAsync(mainWindow, saveOptions).thenAccept(path -> mainWindow.getWebContents().PrintToPDFAsync(path).thenAccept(saved -> {
                        if (saved) {
                            Electron.getShell().OpenExternalAsync("file://" + path);
                        } else {
                            Electron.getDialog().ShowErrorBox("Error", "Failed to create pdf file.");
                        }
                    }));
                });
            });
        }
        return "Pdf/Index";
    }
}
