package io.gitee.nn.electron.api;

@SuppressWarnings("unused")
public final class QuitEventArgs {
    public void PreventDefault() {
        Electron.getApp().PreventQuit();
    }
}
