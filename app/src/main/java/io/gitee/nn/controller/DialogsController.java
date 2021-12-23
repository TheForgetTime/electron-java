package io.gitee.nn.controller;

import io.gitee.nn.electron.api.Electron;
import io.gitee.nn.electron.api.HybridSupport;
import io.gitee.nn.electron.api.entities.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/dialogs")
public class DialogsController {
    @RequestMapping(value = {"", "/index"})
    public String Index() {
        if (HybridSupport.IsElectronActive()) {
            Electron.getIpcMain().On("select-directory", (args) -> {
                var mainWindow = Electron.getWindowManager().GetBrowserWindows().First();
                var options = OpenDialogOptions.builder()
                        .properties(new OpenDialogProperty[]{
                                OpenDialogProperty.OPEN_FILE,
                                OpenDialogProperty.OPEN_DIRECTORY
                        })
                        .build();

                Electron.getDialog().ShowOpenDialogAsync(mainWindow, options).thenAccept((String[] files) -> Electron.getIpcMain().Send(mainWindow, "select-directory-reply", (Object[]) files));
            });

            Electron.getIpcMain().On("error-dialog", (args) ->
                    Electron.getDialog().ShowErrorBox("An Error Message", "Demonstrating an error message.")
            );

            Electron.getIpcMain().On("information-dialog", (args) ->
            {
                var options = MessageBoxOptions.builder()
                        .message("This is an information dialog. Isn't it nice?")
                        .type(MessageBoxType.INFO)
                        .title("Information")
                        .buttons(new String[]{"Yes", "No"})
                        .build();

                Electron.getDialog().ShowMessageBoxAsync(options).thenAccept(messageBoxResult -> {
                    var mainWindow = Electron.getWindowManager().GetBrowserWindows().First();
                    Electron.getIpcMain().Send(mainWindow, "information-dialog-reply", messageBoxResult.getResponse());
                });
            });

            Electron.getIpcMain().On("save-dialog", (args) ->
            {
                var mainWindow = Electron.getWindowManager().GetBrowserWindows().First();
                var options = SaveDialogOptions.builder()
                        .title("Save an Image")
                        .filters(new FileFilter[]{
                                FileFilter.builder().name("Images").extensions(new String[]{"jpg", "png", "gif"}).build()
                        }).build();

                Electron.getDialog().ShowSaveDialogAsync(mainWindow, options).thenAccept((result) -> Electron.getIpcMain().Send(mainWindow, "save-dialog-reply", result));
            });
        }

        return "Dialogs/Index";
    }
}
