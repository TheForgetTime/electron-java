package io.gitee.nn.electron.api;

import io.gitee.nn.electron.api.entities.*;
import io.gitee.nn.electron.api.function.Action0;
import io.gitee.nn.electron.api.function.Action1;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

@SuppressWarnings("unused")
public final class BrowserWindow {
    private final Integer id;
    private final List<Action0> readyToShow = new ArrayList<>();
    private final List<Action1<String>> pageTitleUpdated = new ArrayList<>();
    private final List<Action0> close = new ArrayList<>();
    private final List<Action0> closed = new ArrayList<>();
    private final List<Action0> sessionEnd = new ArrayList<>();
    private final List<Action0> unresponsive = new ArrayList<>();
    private final List<Action0> move = new ArrayList<>();
    private final List<Action0> moved = new ArrayList<>();
    private final List<Action0> resize = new ArrayList<>();
    private final List<MenuItem> items = new ArrayList<>();
    private final List<ThumbarButton> thumbarButtons = new ArrayList<>();
    private final List<Action0> blur = new ArrayList<>();
    private final List<Action0> focus = new ArrayList<>();
    private final List<Action0> show = new ArrayList<>();
    private final List<Action0> hide = new ArrayList<>();
    private final List<Action0> maximize = new ArrayList<>();
    private final List<Action0> unmaximize = new ArrayList<>();
    private final List<Action0> minimize = new ArrayList<>();
    private final List<Action0> restore = new ArrayList<>();
    private final List<Action0> enterFullScreen = new ArrayList<>();
    private final List<Action0> leaveFullScreen = new ArrayList<>();
    private final List<Action0> enterHtmlFullScreen = new ArrayList<>();
    private final List<Action0> leaveHtmlFullScreen = new ArrayList<>();
    private final List<Action1<String>> appCommand = new ArrayList<>();
    private final List<Action0> scrollTouchBegin = new ArrayList<>();
    private final List<Action0> scrollTouchEnd = new ArrayList<>();
    private final List<Action0> scrollTouchEdge = new ArrayList<>();
    private final List<Action1<String>> swipe = new ArrayList<>();
    private final List<Action0> sheetBegin = new ArrayList<>();
    private final List<Action0> sheetEnd = new ArrayList<>();
    private final List<Action0> newWindowForTab = new ArrayList<>();
    private WebContents webContents;

    public BrowserWindow(Integer id) {
        this.id = id;
        webContents = new WebContents(id);
    }

    public Integer getId() {
        return id;
    }

    public WebContents getWebContents() {
        return webContents;
    }

    private void setWebContents(WebContents webContents) {
        this.webContents = webContents;
    }

    public void AddOnReadToShow(Action0 value) {
        if (readyToShow.size() == 0) {
            BridgeConnector.getSocket().on("browserWindow-ready-to-show" + id, (unused) -> readyToShow.forEach(Action0::accept));
            BridgeConnector.getSocket().emit("register-browserWindow-ready-to-show", id);
        }
        readyToShow.add(value);
    }

    public void RemoveOnReadToShow(Action0 value) {
        readyToShow.remove(value);
        if (readyToShow.size() == 0) {
            BridgeConnector.getSocket().off("browserWindow-ready-to-show" + id);
        }
    }

    public void AddOnPageTitleUpdated(Action1<String> value) {
        if (pageTitleUpdated.size() == 0) {
            BridgeConnector.getSocket().on("browserWindow-page-title-updated" + id, (title) ->
                    pageTitleUpdated.forEach(it -> it.accept(title[0].toString())));

            BridgeConnector.getSocket().emit("register-browserWindow-page-title-updated", id);
        }
        pageTitleUpdated.add(value);
    }

    public void RemoveOnPageTitleUpdated(Action1<String> value) {
        pageTitleUpdated.remove(value);
        if (pageTitleUpdated.size() == 0)
            BridgeConnector.getSocket().off("browserWindow-page-title-updated" + id);
    }

    public void AddOnClose(Action0 value) {
        if (close.size() == 0) {
            BridgeConnector.getSocket().on("browserWindow-close" + id, (unused) -> close.forEach(Action0::accept));
            BridgeConnector.getSocket().emit("register-browserWindow-close", id);
        }
        close.add(value);
    }

    public void RemoveOnClose(Action0 value) {
        close.remove(value);
        if (close.size() == 0) {
            BridgeConnector.getSocket().off("browserWindow-close" + id);
        }
    }

    public void AddOnClosed(Action0 value) {
        if (closed.size() == 0) {
            BridgeConnector.getSocket().on("browserWindow-closed" + id, (unused) -> closed.forEach(Action0::accept));
            BridgeConnector.getSocket().emit("register-browserWindow-closed", id);
        }
        closed.add(value);
    }

    public void RemoveOnClosed(Action0 value) {
        closed.remove(value);
        if (closed.size() == 0) {
            BridgeConnector.getSocket().off("browserWindow-closed" + id);
        }
    }

    public void AddOnSessionEnd(Action0 value) {
        if (sessionEnd.size() == 0) {
            BridgeConnector.getSocket().on("browserWindow-session-end" + id, (unused) -> sessionEnd.forEach(Action0::accept));
            BridgeConnector.getSocket().emit("register-browserWindow-session-end", id);
        }
        sessionEnd.add(value);
    }

    public void RemoveOnSessionEnd(Action0 value) {
        sessionEnd.remove(value);
        if (sessionEnd.size() == 0) {
            BridgeConnector.getSocket().off("browserWindow-session-end" + id);
        }
    }

    public void AddOnUnresponsive(Action0 value) {
        if (unresponsive.size() == 0) {
            BridgeConnector.getSocket().on("browserWindow-unresponsive" + id, (unused) -> unresponsive.forEach(Action0::accept));
            BridgeConnector.getSocket().emit("register-browserWindow-unresponsive", id);
        }
        unresponsive.add(value);
    }

    public void RemoveOnUnresponsive(Action0 value) {
        unresponsive.remove(value);
        if (unresponsive.size() == 0) {
            BridgeConnector.getSocket().off("browserWindow-unresponsive" + id);
        }
    }

    public void AddOnBlur(Action0 value) {
        if (blur.size() == 0) {
            BridgeConnector.getSocket().on("browserWindow-blur" + id, (unused) -> blur.forEach(Action0::accept));

            BridgeConnector.getSocket().emit("register-browserWindow-blur", id);
        }
        blur.add(value);
    }

    public void RemoveOnBlur(Action0 value) {
        blur.remove(value);

        if (blur.size() == 0)
            BridgeConnector.getSocket().off("browserWindow-blur" + id);
    }

    public void AddOnFocus(Action0 value) {
        if (focus.size() == 0) {
            BridgeConnector.getSocket().on("browserWindow-focus" + id, (unused) ->
                    focus.forEach(Action0::accept));

            BridgeConnector.getSocket().emit("register-browserWindow-focus", id);
        }
        focus.add(value);
    }

    public void RemoveOnFocus(Action0 value) {
        focus.remove(value);

        if (focus.size() == 0)
            BridgeConnector.getSocket().off("browserWindow-focus" + id);
    }

    public void AddOnShow(Action0 value) {
        if (show.size() == 0) {
            BridgeConnector.getSocket().on("browserWindow-show" + id, (unused) ->
                    show.forEach(Action0::accept));

            BridgeConnector.getSocket().emit("register-browserWindow-show", id);
        }
        show.add(value);
    }

    public void RemoveOnShow(Action0 value) {
        show.remove(value);

        if (show.size() == 0)
            BridgeConnector.getSocket().off("browserWindow-show" + id);
    }

    public void AddOnHide(Action0 value) {
        if (hide.size() == 0) {
            BridgeConnector.getSocket().on("browserWindow-hide" + id, (unused) ->
                    hide.forEach(Action0::accept));

            BridgeConnector.getSocket().emit("register-browserWindow-hide", id);
        }
        hide.add(value);
    }

    public void RemoveOnHide(Action0 value) {
        hide.remove(value);

        if (hide.size() == 0)
            BridgeConnector.getSocket().off("browserWindow-hide" + id);
    }

    public void AddOnMaximize(Action0 value) {
        if (maximize.size() == 0) {
            BridgeConnector.getSocket().on("browserWindow-maximize" + id, (unused) ->
                    maximize.forEach(Action0::accept));

            BridgeConnector.getSocket().emit("register-browserWindow-maximize", id);
        }
        maximize.add(value);
    }

    public void RemoveOnMaximize(Action0 value) {
        maximize.remove(value);

        if (maximize.size() == 0)
            BridgeConnector.getSocket().off("browserWindow-maximize" + id);
    }

    public void AddOnUnmaximize(Action0 value) {
        if (unmaximize.size() == 0) {
            BridgeConnector.getSocket().on("browserWindow-unmaximize" + id, (unused) ->
                    unmaximize.forEach(Action0::accept));

            BridgeConnector.getSocket().emit("register-browserWindow-unmaximize", id);
        }
        unmaximize.add(value);
    }

    public void RemoveOnUnmaximize(Action0 value) {
        unmaximize.remove(value);

        if (unmaximize.size() == 0)
            BridgeConnector.getSocket().off("browserWindow-unmaximize" + id);
    }

    public void AddOnMinimize(Action0 value) {
        if (minimize.size() == 0) {
            BridgeConnector.getSocket().on("browserWindow-minimize" + id, (unused) ->
                    minimize.forEach(Action0::accept));

            BridgeConnector.getSocket().emit("register-browserWindow-minimize", id);
        }
        minimize.add(value);
    }

    public void RemoveOnMinimize(Action0 value) {
        minimize.remove(value);

        if (minimize.size() == 0)
            BridgeConnector.getSocket().off("browserWindow-minimize" + id);
    }

    public void AddOnRestore(Action0 value) {
        if (restore.size() == 0) {
            BridgeConnector.getSocket().on("browserWindow-restore" + id, (unused) ->
                    restore.forEach(Action0::accept));

            BridgeConnector.getSocket().emit("register-browserWindow-restore", id);
        }
        restore.add(value);
    }

    public void RemoveOnRestore(Action0 value) {
        restore.remove(value);

        if (restore.size() == 0)
            BridgeConnector.getSocket().off("browserWindow-restore" + id);
    }

    public void AddOnResize(Action0 value) {
        if (resize.size() == 0) {
            BridgeConnector.getSocket().on("browserWindow-resize" + id, (unused) ->
                    resize.forEach(Action0::accept));

            BridgeConnector.getSocket().emit("register-browserWindow-resize", id);
        }
        resize.add(value);
    }

    public void RemoveOnResize(Action0 value) {
        resize.remove(value);
        if (resize.size() == 0)
            BridgeConnector.getSocket().off("browserWindow-resize" + id);
    }

    public void AddOnMove(Action0 value) {
        if (move.size() == 0) {
            BridgeConnector.getSocket().on("browserWindow-move" + id, (unused) ->
                    move.forEach(Action0::accept));

            BridgeConnector.getSocket().emit("register-browserWindow-move", id);
        }
        move.add(value);
    }

    public void RemoveOnMove(Action0 value) {
        move.remove(value);
        if (move.size() == 0)
            BridgeConnector.getSocket().off("browserWindow-move" + id);
    }

    public void AddOnMoved(Action0 value) {
        if (moved.size() == 0) {
            BridgeConnector.getSocket().on("browserWindow-moved" + id, (unused) ->
                    moved.forEach(Action0::accept));

            BridgeConnector.getSocket().emit("register-browserWindow-moved", id);
        }
        move.add(value);
    }

    public void RemoveOnMoved(Action0 value) {
        moved.remove(value);
        if (moved.size() == 0)
            BridgeConnector.getSocket().off("browserWindow-moved" + id);
    }

    public void AddOnEnterFullScreen(Action0 value) {
        if (enterFullScreen.size() == 0) {
            BridgeConnector.getSocket().on("browserWindow-enter-full-screen" + id, (unused) ->
                    enterFullScreen.forEach(Action0::accept));

            BridgeConnector.getSocket().emit("register-browserWindow-enter-full-screen", id);
        }
        enterFullScreen.add(value);
    }

    public void RemoveOnEnterFullScreen(Action0 value) {
        enterFullScreen.remove(value);

        if (enterFullScreen.size() == 0)
            BridgeConnector.getSocket().off("browserWindow-enter-full-screen" + id);
    }

    /**
     * Emitted when the window leaves a full-screen state.
     */
    public void AddOnLeaveFullScreen(Action0 value) {
        if (leaveFullScreen.size() == 0) {
            BridgeConnector.getSocket().on("browserWindow-leave-full-screen" + id, (unused) ->
                    leaveFullScreen.forEach(Action0::accept));

            BridgeConnector.getSocket().emit("register-browserWindow-leave-full-screen", id);
        }
        leaveFullScreen.add(value);
    }

    public void RemoveOnLeaveFullScreen(Action0 value) {
        leaveFullScreen.remove(value);

        if (leaveFullScreen.size() == 0)
            BridgeConnector.getSocket().off("browserWindow-leave-full-screen" + id);
    }

    /**
     * Emitted when the window enters a full-screen state triggered by HTML API.
     */
    public void AddOnEnterHtmlFullScreen(Action0 value) {
        if (enterHtmlFullScreen.size() == 0) {
            BridgeConnector.getSocket().on("browserWindow-enter-html-full-screen" + id, (unused) ->
                    enterHtmlFullScreen.forEach(Action0::accept));

            BridgeConnector.getSocket().emit("register-browserWindow-enter-html-full-screen", id);
        }
        enterHtmlFullScreen.add(value);
    }

    public void RemoveOnEnterHtmlFullScreen(Action0 value) {
        enterHtmlFullScreen.remove(value);

        if (enterHtmlFullScreen.size() == 0)
            BridgeConnector.getSocket().off("browserWindow-enter-html-full-screen" + id);
    }

    /**
     * Emitted when the window leaves a full-screen state triggered by HTML API.
     */
    public void AddOnLeaveHtmlFullScreen(Action0 value) {
        if (leaveHtmlFullScreen.size() == 0) {
            BridgeConnector.getSocket().on("browserWindow-leave-html-full-screen" + id, (unused) ->
                    leaveHtmlFullScreen.forEach(Action0::accept));

            BridgeConnector.getSocket().emit("register-browserWindow-leave-html-full-screen", id);
        }
        leaveHtmlFullScreen.add(value);
    }

    public void RemoveOnLeaveHtmlFullScreen(Action0 value) {
        leaveHtmlFullScreen.remove(value);

        if (leaveHtmlFullScreen.size() == 0)
            BridgeConnector.getSocket().off("browserWindow-leave-html-full-screen" + id);
    }

    /**
     * Emitted when an App Command is invoked. These are typically related to
     * keyboard media keys or browser commands, as well as the “Back” button
     * built into some mice on Windows.
     * <p>
     * Commands are lowercased, underscores are replaced with hyphens,
     * and the APPCOMMAND_ prefix is stripped off.e.g.APPCOMMAND_BROWSER_BACKWARD
     * is emitted as browser-backward.
     */
    public void AddOnAppCommand(Action1<String> value) {
        if (appCommand.size() == 0) {
            BridgeConnector.getSocket().on("browserWindow-app-command" + id, (command) ->
                    appCommand.forEach(it -> it.accept(command[0].toString())));

            BridgeConnector.getSocket().emit("register-browserWindow-app-command", id);
        }
        appCommand.add(value);
    }

    public void RemoveOnAppCommand(Action1<String> value) {
        appCommand.remove(value);

        if (appCommand.size() == 0)
            BridgeConnector.getSocket().off("browserWindow-app-command" + id);
    }

    /**
     * Emitted when scroll wheel event phase has begun.
     */
    public void AddOnScrollTouchBegin(Action0 value) {
        if (scrollTouchBegin.size() == 0) {
            BridgeConnector.getSocket().on("browserWindow-scroll-touch-begin" + id, (unused) ->
                    scrollTouchBegin.forEach(Action0::accept));

            BridgeConnector.getSocket().emit("register-browserWindow-scroll-touch-begin", id);
        }
        scrollTouchBegin.add(value);
    }

    public void RemoveOnScrollTouchBegin(Action0 value) {
        scrollTouchBegin.remove(value);

        if (scrollTouchBegin.size() == 0)
            BridgeConnector.getSocket().off("browserWindow-scroll-touch-begin" + id);
    }

    /**
     * Emitted when scroll wheel event phase has ended.
     */
    public void AddOnScrollTouchEnd(Action0 value) {
        if (scrollTouchEnd.size() == 0) {
            BridgeConnector.getSocket().on("browserWindow-scroll-touch-end" + id, (unused) ->
                    scrollTouchEnd.forEach(Action0::accept));

            BridgeConnector.getSocket().emit("register-browserWindow-scroll-touch-end", id);
        }
        scrollTouchEnd.add(value);
    }

    public void RemoveOnScrollTouchEnd(Action0 value) {
        scrollTouchEnd.remove(value);

        if (scrollTouchEnd.size() == 0)
            BridgeConnector.getSocket().off("browserWindow-scroll-touch-end" + id);
    }

    /**
     * Emitted when scroll wheel event phase filed upon reaching the edge of element.
     */
    public void AddOnScrollTouchEdge(Action0 value) {
        if (scrollTouchEdge.size() == 0) {
            BridgeConnector.getSocket().on("browserWindow-scroll-touch-edge" + id, (unused) ->
                    scrollTouchEdge.forEach(Action0::accept));

            BridgeConnector.getSocket().emit("register-browserWindow-scroll-touch-edge", id);
        }
        scrollTouchEdge.add(value);
    }

    public void RemoveOnScrollTouchEdge(Action0 value) {
        scrollTouchEdge.remove(value);

        if (scrollTouchEdge.size() == 0)
            BridgeConnector.getSocket().off("browserWindow-scroll-touch-edge" + id);
    }

    /**
     * Emitted on 3-finger swipe. Possible directions are up, right, down, left.
     */
    public void AddOnSwipe(Action1<String> value) {
        if (swipe.size() == 0) {
            BridgeConnector.getSocket().on("browserWindow-swipe" + id, (direction) ->
                    swipe.forEach(it -> it.accept(direction[0].toString())));

            BridgeConnector.getSocket().emit("register-browserWindow-swipe", id);
        }
        swipe.add(value);
    }

    public void RemoveOnSwipe(Action1<String> value) {
        swipe.remove(value);

        if (swipe.size() == 0)
            BridgeConnector.getSocket().off("browserWindow-swipe" + id);
    }

    /**
     * Emitted when the window opens a sheet.
     */
    public void AddOnSheetBegin(Action0 value) {
        if (sheetBegin.size() == 0) {
            BridgeConnector.getSocket().on("browserWindow-sheet-begin" + id, (unused) ->
                    sheetBegin.forEach(Action0::accept));

            BridgeConnector.getSocket().emit("register-browserWindow-sheet-begin", id);
        }
        sheetBegin.add(value);
    }

    public void RemoveOnSheetBegin(Action0 value) {
        sheetBegin.remove(value);

        if (sheetBegin.size() == 0)
            BridgeConnector.getSocket().off("browserWindow-sheet-begin" + id);
    }

    /**
     * Emitted when the window has closed a sheet.
     */
    public void AddOnSheetEnd(Action0 value) {
        if (sheetEnd.size() == 0) {
            BridgeConnector.getSocket().on("browserWindow-sheet-end" + id, (unused) ->
                    sheetEnd.forEach(Action0::accept));

            BridgeConnector.getSocket().emit("register-browserWindow-sheet-end", id);
        }
        sheetEnd.add(value);
    }

    public void RemoveOnSheetEnd(Action0 value) {
        sheetEnd.remove(value);

        if (sheetEnd.size() == 0)
            BridgeConnector.getSocket().off("browserWindow-sheet-end" + id);
    }

    /**
     * Emitted when the native new tab button is clicked.
     */
    public void AddOnNewWindowForTab(Action0 value) {
        if (newWindowForTab.size() == 0) {
            BridgeConnector.getSocket().on("browserWindow-new-window-for-tab" + id, (unused) ->
                    newWindowForTab.forEach(Action0::accept));

            BridgeConnector.getSocket().emit("register-browserWindow-new-window-for-tab", id);
        }
        newWindowForTab.add(value);
    }

    public void RemoveOnNewWindowForTab(Action0 value) {
        newWindowForTab.remove(value);

        if (newWindowForTab.size() == 0)
            BridgeConnector.getSocket().off("browserWindow-new-window-for-tab" + id);
    }

    public void Destroy() {
        BridgeConnector.getSocket().emit("browserWindowDestroy", id);
    }

    public void Close() {
        BridgeConnector.getSocket().emit("browserWindowClose", id);
    }

    public void Focus() {
        BridgeConnector.getSocket().emit("browserWindowFocus", id);
    }

    public void Blur() {
        BridgeConnector.getSocket().emit("browserWindowBlur", id);
    }

    public CompletableFuture<Boolean> IsFocusedAsync() {
        var taskCompletionSource = new CompletableFuture<Boolean>();

        BridgeConnector.getSocket().on("browserWindow-isFocused-completed", (isFocused) -> {
            BridgeConnector.getSocket().off("browserWindow-isFocused-completed");

            taskCompletionSource.complete((Boolean) isFocused[0]);
        });

        BridgeConnector.getSocket().emit("browserWindowIsFocused", id);

        return taskCompletionSource;
    }

    public CompletableFuture<Boolean> IsDestroyedAsync() {
        var taskCompletionSource = new CompletableFuture<Boolean>();

        BridgeConnector.getSocket().on("browserWindow-isDestroyed-completed", (isDestroyed) -> {
            BridgeConnector.getSocket().off("browserWindow-isDestroyed-completed");

            taskCompletionSource.complete((Boolean) isDestroyed[0]);
        });

        BridgeConnector.getSocket().emit("browserWindowIsDestroyed", id);

        return taskCompletionSource;
    }

    public void Show() {
        BridgeConnector.getSocket().emit("browserWindowShow", id);
    }

    public void ShowInactive() {
        BridgeConnector.getSocket().emit("browserWindowShowInactive", id);
    }

    public void Hide() {
        BridgeConnector.getSocket().emit("browserWindowHide", id);
    }

    public CompletableFuture<Boolean> IsVisibleAsync() {
        var taskCompletionSource = new CompletableFuture<Boolean>();

        BridgeConnector.getSocket().on("browserWindow-isVisible-completed", (isVisible) -> {
            BridgeConnector.getSocket().off("browserWindow-isVisible-completed");

            taskCompletionSource.complete((Boolean) isVisible[0]);
        });

        BridgeConnector.getSocket().emit("browserWindowIsVisible", id);

        return taskCompletionSource;
    }

    /**
     * Whether current window is a modal window.
     */

    public CompletableFuture<Boolean> IsModalAsync() {
        var taskCompletionSource = new CompletableFuture<Boolean>();

        BridgeConnector.getSocket().on("browserWindow-isModal-completed", (isModal) -> {
            BridgeConnector.getSocket().off("browserWindow-isModal-completed");

            taskCompletionSource.complete((Boolean) isModal[0]);
        });

        BridgeConnector.getSocket().emit("browserWindowIsModal", id);

        return taskCompletionSource;
    }

    /**
     * Maximizes the window. This will also show (but not focus) the window if it isn’t being displayed already.
     */
    public void Maximize() {
        BridgeConnector.getSocket().emit("browserWindowMaximize", id);
    }

    /**
     * Unmaximizes the window.
     */
    public void Unmaximize() {
        BridgeConnector.getSocket().emit("browserWindowUnmaximize", id);
    }

    /**
     * Whether the window is maximized.
     */

    public CompletableFuture<Boolean> IsMaximizedAsync() {
        var taskCompletionSource = new CompletableFuture<Boolean>();

        BridgeConnector.getSocket().on("browserWindow-isMaximized-completed", (isMaximized) -> {
            BridgeConnector.getSocket().off("browserWindow-isMaximized-completed");

            taskCompletionSource.complete((Boolean) isMaximized[0]);
        });

        BridgeConnector.getSocket().emit("browserWindowIsMaximized", id);

        return taskCompletionSource;
    }

    /**
     * Minimizes the window. On some platforms the minimized window will be shown in the Dock.
     */
    public void Minimize() {
        BridgeConnector.getSocket().emit("browserWindowMinimize", id);
    }

    /**
     * Restores the window from minimized state to its previous state.
     */
    public void Restore() {
        BridgeConnector.getSocket().emit("browserWindowRestore", id);
    }

    /**
     * Whether the window is minimized.
     */

    public CompletableFuture<Boolean> IsMinimizedAsync() {
        var taskCompletionSource = new CompletableFuture<Boolean>();

        BridgeConnector.getSocket().on("browserWindow-isMinimized-completed", (isMinimized) -> {
            BridgeConnector.getSocket().off("browserWindow-isMinimized-completed");

            taskCompletionSource.complete((Boolean) isMinimized[0]);
        });

        BridgeConnector.getSocket().emit("browserWindowIsMinimized", id);

        return taskCompletionSource;
    }

    public void SetFullScreen(Boolean flag) {
        BridgeConnector.getSocket().emit("browserWindowSetFullScreen", id, flag);
    }

    public CompletableFuture<Boolean> IsFullScreenAsync() {
        var taskCompletionSource = new CompletableFuture<Boolean>();

        BridgeConnector.getSocket().on("browserWindow-isFullScreen-completed", (isFullScreen) -> {
            BridgeConnector.getSocket().off("browserWindow-isFullScreen-completed");

            taskCompletionSource.complete((Boolean) isFullScreen[0]);
        });

        BridgeConnector.getSocket().emit("browserWindowIsFullScreen", id);

        return taskCompletionSource;
    }

    public void SetAspectRatio(int aspectRatio, Size extraSize) {
        BridgeConnector.getSocket().emit("browserWindowSetAspectRatio", id, aspectRatio, Electron.toJsonObject(extraSize));
    }

    public void PreviewFile(String path) {
        BridgeConnector.getSocket().emit("browserWindowPreviewFile", id, path);
    }

    public void CloseFilePreview() {
        BridgeConnector.getSocket().emit("browserWindowCloseFilePreview", id);
    }

    public void SetBounds(Rectangle bounds) {
        BridgeConnector.getSocket().emit("browserWindowSetBounds", id, Electron.toJsonObject(bounds));
    }

    public void SetBounds(Rectangle bounds, Boolean animate) {
        BridgeConnector.getSocket().emit("browserWindowSetBounds", id, Electron.toJsonObject(bounds), animate);
    }

    public CompletableFuture<Rectangle> GetBoundsAsync() {
        var taskCompletionSource = new CompletableFuture<Rectangle>();

        BridgeConnector.getSocket().on("browserWindow-getBounds-completed", (getBounds) -> {
            BridgeConnector.getSocket().off("browserWindow-getBounds-completed");

            taskCompletionSource.complete(Electron.fromJsonString(getBounds[0].toString(), Rectangle.class));
        });

        BridgeConnector.getSocket().emit("browserWindowGetBounds", id);

        return taskCompletionSource;
    }

    public void SetContentBounds(Rectangle bounds) {
        BridgeConnector.getSocket().emit("browserWindowSetContentBounds", id, Electron.toJsonObject(bounds));
    }

    public void SetContentBounds(Rectangle bounds, Boolean animate) {
        BridgeConnector.getSocket().emit("browserWindowSetContentBounds", id, Electron.toJsonObject(bounds), animate);
    }

    public CompletableFuture<Rectangle> GetContentBoundsAsync() {
        var taskCompletionSource = new CompletableFuture<Rectangle>();

        BridgeConnector.getSocket().on("browserWindow-getContentBounds-completed", (getContentBounds) -> {
            BridgeConnector.getSocket().off("browserWindow-getContentBounds-completed");

            taskCompletionSource.complete(Electron.fromJsonString(getContentBounds[0].toString(), Rectangle.class));
        });

        BridgeConnector.getSocket().emit("browserWindowGetContentBounds", id);

        return taskCompletionSource;
    }

    public void SetSize(Integer width, Integer height) {
        BridgeConnector.getSocket().emit("browserWindowSetSize", id, width, height);
    }

    public void SetSize(Integer width, Integer height, Boolean animate) {
        BridgeConnector.getSocket().emit("browserWindowSetSize", id, width, height, animate);
    }

    public CompletableFuture<Integer[]> GetSizeAsync() {
        var taskCompletionSource = new CompletableFuture<Integer[]>();

        BridgeConnector.getSocket().on("browserWindow-getSize-completed", (size) -> {
            BridgeConnector.getSocket().off("browserWindow-getSize-completed");
            List<Integer> sizeList = Electron.fromJsonString(size[0].toString(), ArrayList.class, Integer.class);
            taskCompletionSource.complete(sizeList.toArray(new Integer[0]));
        });

        BridgeConnector.getSocket().emit("browserWindowGetSize", id);

        return taskCompletionSource;
    }

    public void SetContentSize(Integer width, Integer height) {
        BridgeConnector.getSocket().emit("browserWindowSetContentSize", id, width, height);
    }

    public void SetContentSize(Integer width, Integer height, Boolean animate) {
        BridgeConnector.getSocket().emit("browserWindowSetContentSize", id, width, height, animate);
    }

    public CompletableFuture<Integer[]> GetContentSizeAsync() {
        var taskCompletionSource = new CompletableFuture<Integer[]>();

        BridgeConnector.getSocket().on("browserWindow-getContentSize-completed", (size) -> {
            BridgeConnector.getSocket().off("browserWindow-getContentSize-completed");
            List<Integer> sizeList = Electron.fromJsonString(size[0].toString(), ArrayList.class, Integer.class);
            taskCompletionSource.complete(sizeList.toArray(new Integer[0]));
        });

        BridgeConnector.getSocket().emit("browserWindowGetContentSize", id);

        return taskCompletionSource;
    }

    public void SetMinimumSize(Integer width, Integer height) {
        BridgeConnector.getSocket().emit("browserWindowSetMinimumSize", id, width, height);
    }

    public CompletableFuture<Integer[]> GetMinimumSizeAsync() {
        var taskCompletionSource = new CompletableFuture<Integer[]>();

        BridgeConnector.getSocket().on("browserWindow-getMinimumSize-completed", (size) -> {
            BridgeConnector.getSocket().off("browserWindow-getMinimumSize-completed");

            List<Integer> sizeList = Electron.fromJsonString(size[0].toString(), ArrayList.class, Integer.class);
            taskCompletionSource.complete(sizeList.toArray(new Integer[0]));
        });

        BridgeConnector.getSocket().emit("browserWindowGetMinimumSize", id);

        return taskCompletionSource;
    }

    public void SetMaximumSize(Integer width, Integer height) {
        BridgeConnector.getSocket().emit("browserWindowSetMaximumSize", id, width, height);
    }

    public CompletableFuture<Integer[]> GetMaximumSizeAsync() {
        var taskCompletionSource = new CompletableFuture<Integer[]>();

        BridgeConnector.getSocket().on("browserWindow-getMaximumSize-completed", (size) -> {
            BridgeConnector.getSocket().off("browserWindow-getMaximumSize-completed");

            List<Integer> sizeList = Electron.fromJsonString(size[0].toString(), ArrayList.class, Integer.class);
            taskCompletionSource.complete(sizeList.toArray(new Integer[0]));
        });

        BridgeConnector.getSocket().emit("browserWindowGetMaximumSize", id);

        return taskCompletionSource;
    }

    public void SetResizable(Boolean resizable) {
        BridgeConnector.getSocket().emit("browserWindowSetResizable", id, resizable);
    }

    public CompletableFuture<Boolean> IsResizableAsync() {
        var taskCompletionSource = new CompletableFuture<Boolean>();

        BridgeConnector.getSocket().on("browserWindow-isResizable-completed", (resizable) -> {
            BridgeConnector.getSocket().off("browserWindow-isResizable-completed");

            taskCompletionSource.complete((Boolean) resizable[0]);
        });

        BridgeConnector.getSocket().emit("browserWindowIsResizable", id);

        return taskCompletionSource;
    }

    public void SetMovable(Boolean movable) {
        BridgeConnector.getSocket().emit("browserWindowSetMovable", id, movable);
    }

    public CompletableFuture<Boolean> IsMovableAsync() {
        var taskCompletionSource = new CompletableFuture<Boolean>();

        BridgeConnector.getSocket().on("browserWindow-isMovable-completed", (movable) -> {
            BridgeConnector.getSocket().off("browserWindow-isMovable-completed");

            taskCompletionSource.complete((Boolean) movable[0]);
        });

        BridgeConnector.getSocket().emit("browserWindowIsMovable", id);

        return taskCompletionSource;
    }

    public void SetMinimizable(Boolean minimizable) {
        BridgeConnector.getSocket().emit("browserWindowSetMinimizable", id, minimizable);
    }

    public CompletableFuture<Boolean> IsMinimizableAsync() {
        var taskCompletionSource = new CompletableFuture<Boolean>();

        BridgeConnector.getSocket().on("browserWindow-isMinimizable-completed", (minimizable) -> {
            BridgeConnector.getSocket().off("browserWindow-isMinimizable-completed");

            taskCompletionSource.complete((Boolean) minimizable[0]);
        });

        BridgeConnector.getSocket().emit("browserWindowIsMinimizable", id);

        return taskCompletionSource;
    }

    public void SetMaximizable(Boolean maximizable) {
        BridgeConnector.getSocket().emit("browserWindowSetMaximizable", id, maximizable);
    }

    public CompletableFuture<Boolean> IsMaximizableAsync() {
        var taskCompletionSource = new CompletableFuture<Boolean>();

        BridgeConnector.getSocket().on("browserWindow-isMaximizable-completed", (maximizable) -> {
            BridgeConnector.getSocket().off("browserWindow-isMaximizable-completed");

            taskCompletionSource.complete((Boolean) maximizable[0]);
        });

        BridgeConnector.getSocket().emit("browserWindowIsMaximizable", id);

        return taskCompletionSource;
    }

    public void SetFullScreenable(Boolean fullscreenable) {
        BridgeConnector.getSocket().emit("browserWindowSetFullScreenable", id, fullscreenable);
    }

    public CompletableFuture<Boolean> IsFullScreenableAsync() {
        var taskCompletionSource = new CompletableFuture<Boolean>();

        BridgeConnector.getSocket().on("browserWindow-isFullScreenable-completed", (fullscreenable) -> {
            BridgeConnector.getSocket().off("browserWindow-isFullScreenable-completed");

            taskCompletionSource.complete((Boolean) fullscreenable[0]);
        });

        BridgeConnector.getSocket().emit("browserWindowIsFullScreenable", id);

        return taskCompletionSource;
    }

    public void SetClosable(Boolean closable) {
        BridgeConnector.getSocket().emit("browserWindowSetClosable", id, closable);
    }

    public CompletableFuture<Boolean> IsClosableAsync() {
        var taskCompletionSource = new CompletableFuture<Boolean>();

        BridgeConnector.getSocket().on("browserWindow-isClosable-completed", (closable) -> {
            BridgeConnector.getSocket().off("browserWindow-isClosable-completed");

            taskCompletionSource.complete((Boolean) closable[0]);
        });

        BridgeConnector.getSocket().emit("browserWindowIsClosable", id);

        return taskCompletionSource;
    }

    public void SetAlwaysOnTop(Boolean flag) {
        BridgeConnector.getSocket().emit("browserWindowSetAlwaysOnTop", id, flag);
    }

    public void SetAlwaysOnTop(Boolean flag, OnTopLevel level) {
        BridgeConnector.getSocket().emit("browserWindowSetAlwaysOnTop", id, flag, level.getValue());
    }

    public void SetAlwaysOnTop(Boolean flag, OnTopLevel level, Integer relativeLevel) {
        BridgeConnector.getSocket().emit("browserWindowSetAlwaysOnTop", id, flag, level.getValue(), relativeLevel);
    }

    public CompletableFuture<Boolean> IsAlwaysOnTopAsync() {
        var taskCompletionSource = new CompletableFuture<Boolean>();

        BridgeConnector.getSocket().on("browserWindow-isAlwaysOnTop-completed", (isAlwaysOnTop) -> {
            BridgeConnector.getSocket().off("browserWindow-isAlwaysOnTop-completed");

            taskCompletionSource.complete((Boolean) isAlwaysOnTop[0]);
        });

        BridgeConnector.getSocket().emit("browserWindowIsAlwaysOnTop", id);

        return taskCompletionSource;
    }

    public void Center() {
        BridgeConnector.getSocket().emit("browserWindowCenter", id);
    }

    public void SetPosition(Integer x, Integer y) {
        // Workaround Windows 10 / Electron Bug
        // https://github.com/electron/electron/issues/4045
        if (isWindows10()) {
            x = x - 7;
        }

        BridgeConnector.getSocket().emit("browserWindowSetPosition", id, x, y);
    }

    public void SetPosition(Integer x, Integer y, Boolean animate) {
        // Workaround Windows 10 / Electron Bug
        // https://github.com/electron/electron/issues/4045
        if (isWindows10()) {
            x = x - 7;
        }

        BridgeConnector.getSocket().emit("browserWindowSetPosition", id, x, y, animate);
    }

    private Boolean isWindows10() {
        return System.getProperty("os.name").contains("Windows 10");
    }

    public CompletableFuture<Integer[]> GetPositionAsync() {
        var taskCompletionSource = new CompletableFuture<Integer[]>();

        BridgeConnector.getSocket().on("browserWindow-getPosition-completed", (position) -> {
            BridgeConnector.getSocket().off("browserWindow-getPosition-completed");
            List<Integer> positionList = Electron.fromJsonString(position[0].toString(), ArrayList.class, Integer.class);
            taskCompletionSource.complete(positionList.toArray(new Integer[0]));
        });

        BridgeConnector.getSocket().emit("browserWindowGetPosition", id);

        return taskCompletionSource;
    }

    public void SetTitle(String title) {
        BridgeConnector.getSocket().emit("browserWindowSetTitle", id, title);
    }

    public CompletableFuture<String> GetTitleAsync() {
        var taskCompletionSource = new CompletableFuture<String>();

        BridgeConnector.getSocket().on("browserWindow-getTitle-completed", (title) -> {
            BridgeConnector.getSocket().off("browserWindow-getTitle-completed");

            taskCompletionSource.complete(title[0].toString());
        });

        BridgeConnector.getSocket().emit("browserWindowGetTitle", id);

        return taskCompletionSource;
    }

    public void SetSheetOffset(Float offsetY) {
        BridgeConnector.getSocket().emit("browserWindowSetSheetOffset", id, offsetY);
    }

    public void SetSheetOffset(Float offsetY, Float offsetX) {
        BridgeConnector.getSocket().emit("browserWindowSetSheetOffset", id, offsetY, offsetX);
    }

    public void FlashFrame(Boolean flag) {
        BridgeConnector.getSocket().emit("browserWindowFlashFrame", id, flag);
    }

    public void SetSkipTaskbar(Boolean skip) {
        BridgeConnector.getSocket().emit("browserWindowSetSkipTaskbar", id, skip);
    }

    public void SetKiosk(Boolean flag) {
        BridgeConnector.getSocket().emit("browserWindowSetKiosk", id, flag);
    }

    public CompletableFuture<Boolean> IsKioskAsync() {
        var taskCompletionSource = new CompletableFuture<Boolean>();

        BridgeConnector.getSocket().on("browserWindow-isKiosk-completed", (isKiosk) -> {
            BridgeConnector.getSocket().off("browserWindow-isKiosk-completed");

            taskCompletionSource.complete((Boolean) isKiosk[0]);
        });

        BridgeConnector.getSocket().emit("browserWindowIsKiosk", id);

        return taskCompletionSource;
    }

    public CompletableFuture<String> GetNativeWindowHandle() {
        var taskCompletionSource = new CompletableFuture<String>();

        BridgeConnector.getSocket().on("browserWindow-getNativeWindowHandle-completed", (nativeWindowHandle) ->
        {
            BridgeConnector.getSocket().off("browserWindow-getNativeWindowHandle-completed");
            taskCompletionSource.complete(nativeWindowHandle[0].toString());
        });

        BridgeConnector.getSocket().emit("browserWindowGetNativeWindowHandle", id);

        return taskCompletionSource;
    }

    public void SetRepresentedFilename(String filename) {
        BridgeConnector.getSocket().emit("browserWindowSetRepresentedFilename", id, filename);
    }

    public CompletableFuture<String> GetRepresentedFilenameAsync() {
        var taskCompletionSource = new CompletableFuture<String>();

        BridgeConnector.getSocket().on("browserWindow-getRepresentedFilename-completed", (pathname) -> {
            BridgeConnector.getSocket().off("browserWindow-getRepresentedFilename-completed");

            taskCompletionSource.complete(pathname[0].toString());
        });

        BridgeConnector.getSocket().emit("browserWindowGetRepresentedFilename", id);

        return taskCompletionSource;
    }

    public void SetDocumentEdited(Boolean edited) {
        BridgeConnector.getSocket().emit("browserWindowSetDocumentEdited", id, edited);
    }

    public CompletableFuture<Boolean> IsDocumentEditedAsync() {
        var taskCompletionSource = new CompletableFuture<Boolean>();

        BridgeConnector.getSocket().on("browserWindow-isDocumentEdited-completed", (edited) -> {
            BridgeConnector.getSocket().off("browserWindow-isDocumentEdited-completed");

            taskCompletionSource.complete((Boolean) edited[0]);
        });

        BridgeConnector.getSocket().emit("browserWindowIsDocumentEdited", id);

        return taskCompletionSource;
    }

    public void FocusOnWebView() {
        BridgeConnector.getSocket().emit("browserWindowFocusOnWebView", id);
    }

    public void BlurWebView() {
        BridgeConnector.getSocket().emit("browserWindowBlurWebView", id);
    }

    public void LoadURL(String url) {
        BridgeConnector.getSocket().emit("browserWindowLoadURL", id, url);
    }

    public void LoadURL(String url, LoadURLOptions options) {
        BridgeConnector.getSocket().emit("browserWindowLoadURL", id, url, Electron.toJsonObject(options));
    }

    public void Reload() {
        BridgeConnector.getSocket().emit("browserWindowReload", id);
    }

    public IReadOnlyCollection<MenuItem> getMenuItems() {
        return IReadOnlyCollection.AsReadOnly(items);
    }

    public void SetMenu(MenuItem[] menuItems) {
        MenuItemUtils.AddMenuItemsId(menuItems);
        BridgeConnector.getSocket().emit("browserWindowSetMenu", id, Electron.toJsonArray(menuItems));
        items.addAll(List.of(menuItems));

        BridgeConnector.getSocket().off("windowMenuItemClicked");
        BridgeConnector.getSocket().on("windowMenuItemClicked", (id) -> {
            MenuItem menuItem = MenuItemUtils.GetMenuItem(items, id[0].toString());
            if (menuItem.getClick() != null) {
                menuItem.getClick().accept();
            }
        });
    }

    public void RemoveMenu() {
        BridgeConnector.getSocket().emit("browserWindowRemoveMenu", id);
    }

    public void SetProgressBar(Double progress) {
        BridgeConnector.getSocket().emit("browserWindowSetProgressBar", id, progress);
    }

    public void SetProgressBar(double progress, ProgressBarOptions progressBarOptions) {
        BridgeConnector.getSocket().emit("browserWindowSetProgressBar", id, progress, Electron.toJsonObject(progressBarOptions));
    }

    public void SetHasShadow(Boolean hasShadow) {
        BridgeConnector.getSocket().emit("browserWindowSetHasShadow", id, hasShadow);
    }

    public CompletableFuture<Boolean> HasShadowAsync() {
        var taskCompletionSource = new CompletableFuture<Boolean>();

        BridgeConnector.getSocket().on("browserWindow-hasShadow-completed", (hasShadow) -> {
            BridgeConnector.getSocket().off("browserWindow-hasShadow-completed");

            taskCompletionSource.complete((Boolean) hasShadow[0]);
        });

        BridgeConnector.getSocket().emit("browserWindowHasShadow", id);

        return taskCompletionSource;
    }

    public IReadOnlyCollection<ThumbarButton> getThumbarButtons() {
        return IReadOnlyCollection.AsReadOnly(thumbarButtons);
    }

    public CompletableFuture<Boolean> SetThumbarButtonsAsync(ThumbarButton[] thumbarButtons) {
        var taskCompletionSource = new CompletableFuture<Boolean>();

        BridgeConnector.getSocket().on("browserWindowSetThumbarButtons-completed", (success) -> {
            BridgeConnector.getSocket().off("browserWindowSetThumbarButtons-completed");

            taskCompletionSource.complete((Boolean) success[0]);
        });

        ThumbarButtonUtils.AddThumbarButtonsId(thumbarButtons);
        BridgeConnector.getSocket().emit("browserWindowSetThumbarButtons", id, Electron.toJsonArray(thumbarButtons));
        this.thumbarButtons.clear();
        this.thumbarButtons.addAll(List.of(thumbarButtons));

        BridgeConnector.getSocket().off("thumbarButtonClicked");
        BridgeConnector.getSocket().on("thumbarButtonClicked", (id) -> {
            ThumbarButton thumbarButton = ThumbarButtonUtils.GetThumbarButton(this.thumbarButtons, id[0].toString());
            if (thumbarButton != null) {
                thumbarButton.getClick().accept();
            }
        });

        return taskCompletionSource;
    }

    public void SetThumbnailClip(Rectangle rectangle) {
        BridgeConnector.getSocket().emit("browserWindowSetThumbnailClip", id, rectangle);
    }

    public void SetThumbnailToolTip(String tooltip) {
        BridgeConnector.getSocket().emit("browserWindowSetThumbnailToolTip", id, tooltip);
    }

    public void SetAppDetails(AppDetailsOptions options) {
        BridgeConnector.getSocket().emit("browserWindowSetAppDetails", id, Electron.toJsonObject(options));
    }

    public void ShowDefinitionForSelection() {
        BridgeConnector.getSocket().emit("browserWindowShowDefinitionForSelection", id);
    }

    public void SetAutoHideMenuBar(Boolean hide) {
        BridgeConnector.getSocket().emit("browserWindowSetAutoHideMenuBar", id, hide);
    }

    public CompletableFuture<Boolean> IsMenuBarAutoHideAsync() {
        var taskCompletionSource = new CompletableFuture<Boolean>();

        BridgeConnector.getSocket().on("browserWindow-isMenuBarAutoHide-completed", (isMenuBarAutoHide) -> {
            BridgeConnector.getSocket().off("browserWindow-isMenuBarAutoHide-completed");

            taskCompletionSource.complete((Boolean) isMenuBarAutoHide[0]);
        });

        BridgeConnector.getSocket().emit("browserWindowIsMenuBarAutoHide", id);

        return taskCompletionSource;
    }

    public void SetMenuBarVisibility(Boolean visible) {
        BridgeConnector.getSocket().emit("browserWindowSetMenuBarVisibility", id, visible);
    }

    public CompletableFuture<Boolean> IsMenuBarVisibleAsync() {
        var taskCompletionSource = new CompletableFuture<Boolean>();

        BridgeConnector.getSocket().on("browserWindow-isMenuBarVisible-completed", (isMenuBarVisible) -> {
            BridgeConnector.getSocket().off("browserWindow-isMenuBarVisible-completed");

            taskCompletionSource.complete((Boolean) isMenuBarVisible[0]);
        });

        BridgeConnector.getSocket().emit("browserWindowIsMenuBarVisible", id);

        return taskCompletionSource;
    }

    public void SetVisibleOnAllWorkspaces(Boolean visible) {
        BridgeConnector.getSocket().emit("browserWindowSetVisibleOnAllWorkspaces", id, visible);
    }

    public CompletableFuture<Boolean> IsVisibleOnAllWorkspacesAsync() {
        var taskCompletionSource = new CompletableFuture<Boolean>();

        BridgeConnector.getSocket().on("browserWindow-isVisibleOnAllWorkspaces-completed", (isVisibleOnAllWorkspaces) -> {
            BridgeConnector.getSocket().off("browserWindow-isVisibleOnAllWorkspaces-completed");

            taskCompletionSource.complete((Boolean) isVisibleOnAllWorkspaces[0]);
        });

        BridgeConnector.getSocket().emit("browserWindowIsVisibleOnAllWorkspaces", id);

        return taskCompletionSource;
    }

    public void SetIgnoreMouseEvents(Boolean ignore) {
        BridgeConnector.getSocket().emit("browserWindowSetIgnoreMouseEvents", id, ignore);
    }

    public void SetContentProtection(Boolean enable) {
        BridgeConnector.getSocket().emit("browserWindowSetContentProtection", id, enable);
    }

    public void SetFocusable(Boolean focusable) {
        BridgeConnector.getSocket().emit("browserWindowSetFocusable", id, focusable);
    }

    public void SetParentWindow(BrowserWindow parent) {
        BridgeConnector.getSocket().emit("browserWindowSetParentWindow", Electron.toJsonObject(parent));
    }

    public CompletableFuture<BrowserWindow> GetParentWindowAsync() {
        var taskCompletionSource = new CompletableFuture<BrowserWindow>();

        BridgeConnector.getSocket().on("browserWindow-getParentWindow-completed", (id) -> {
            BridgeConnector.getSocket().off("browserWindow-getParentWindow-completed");
            var browserWindowId = Integer.parseInt(id[0].toString());
            var browserWindow = Electron.getWindowManager().GetBrowserWindows().stream().filter(x -> x.getId() == browserWindowId).findFirst();

            browserWindow.ifPresent(taskCompletionSource::complete);
        });

        BridgeConnector.getSocket().emit("browserWindowGetParentWindow", id);

        return taskCompletionSource;
    }

    public CompletableFuture<List<BrowserWindow>> GetChildWindowsAsync() {
        var taskCompletionSource = new CompletableFuture<List<BrowserWindow>>();

        BridgeConnector.getSocket().on("browserWindow-getChildWindows-completed", (ids) -> {
            BridgeConnector.getSocket().off("browserWindow-getChildWindows-completed");
            var browserWindowIds = Electron.fromJsonString(ids[0].toString(), ArrayList.class, Integer.class);
            List<BrowserWindow> browserWindows = new ArrayList<>();

            browserWindowIds.forEach(id ->
            {
                var browserWindow = Electron.getWindowManager().GetBrowserWindows().stream().filter(x -> Objects.equals(x.getId(), id)).findFirst();
                browserWindow.ifPresent(browserWindows::add);
            });

            taskCompletionSource.complete(browserWindows);
        });

        BridgeConnector.getSocket().emit("browserWindowGetChildWindows", id);

        return taskCompletionSource;
    }

    public void SetAutoHideCursor(Boolean autoHide) {
        BridgeConnector.getSocket().emit("browserWindowSetAutoHideCursor", id, autoHide);
    }

    public void SetVibrancy(Vibrancy type) {
        BridgeConnector.getSocket().emit("browserWindowSetVibrancy", id, type.getValue());
    }

    public void SetBrowserView(BrowserView browserView) {
        BridgeConnector.getSocket().emit("browserWindow-setBrowserView", id, browserView.getId());
    }
}
