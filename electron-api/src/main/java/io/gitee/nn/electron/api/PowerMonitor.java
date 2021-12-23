package io.gitee.nn.electron.api;

import io.gitee.nn.electron.api.function.Action0;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public final class PowerMonitor {
    private volatile static PowerMonitor powerMonitor;
    private final List<Action0> _lockScreen = new ArrayList<>();
    private final List<Action0> _unlockScreen = new ArrayList<>();
    private final List<Action0> _suspend = new ArrayList<>();
    private final List<Action0> _resume = new ArrayList<>();
    private final List<Action0> _onAC = new ArrayList<>();
    private final List<Action0> _onBattery = new ArrayList<>();
    private final List<Action0> _shutdown = new ArrayList<>();

    PowerMonitor() {
    }

    static PowerMonitor getInstance() {
        if (powerMonitor == null) {
            synchronized (Dialog.class) {
                if (powerMonitor == null) {
                    powerMonitor = new PowerMonitor();
                }
            }
        }
        return powerMonitor;
    }

    public void AddOnLockScreen(Action0 value) {
        if (_lockScreen.size() == 0) {
            BridgeConnector.getSocket().on("pm-lock-screen", (unused) -> _lockScreen.forEach(Action0::accept));

            BridgeConnector.getSocket().emit("register-pm-lock-screen");
        }
        _lockScreen.add(value);
    }

    public void RemoveOnLockScreen(Action0 value) {
        _lockScreen.remove(value);

        if (_lockScreen.size() == 0)
            BridgeConnector.getSocket().off("pm-lock-screen");
    }

    /**
     * Emitted when the system is about to unlock the screen.
     */
    public void AddOnUnLockScreen(Action0 value) {
        if (_unlockScreen.size() == 0) {
            BridgeConnector.getSocket().on("pm-unlock-screen", (unused) -> _unlockScreen.forEach(Action0::accept));

            BridgeConnector.getSocket().emit("register-pm-unlock-screen");
        }
        _unlockScreen.add(value);
    }

    public void RemoveOnUnLockScreen(Action0 value) {
        _unlockScreen.remove(value);

        if (_unlockScreen.size() == 0)
            BridgeConnector.getSocket().off("pm-unlock-screen");
    }

    /**
     * Emitted when the system is suspending.
     */
    public void AddOnSuspend(Action0 value) {
        if (_suspend.size() == 0) {
            BridgeConnector.getSocket().on("pm-suspend", (unused) -> _suspend.forEach(Action0::accept));

            BridgeConnector.getSocket().emit("register-pm-suspend");
        }
        _suspend.add(value);
    }

    public void RemoveOnSuspend(Action0 value) {
        _suspend.remove(value);

        if (_suspend.size() == 0)
            BridgeConnector.getSocket().off("pm-suspend");
    }

    /**
     * Emitted when system is resuming.
     */
    public void AddOnResume(Action0 value) {
        if (_resume.size() == 0) {
            BridgeConnector.getSocket().on("pm-resume", (unused) -> _resume.forEach(Action0::accept));

            BridgeConnector.getSocket().emit("register-pm-resume");
        }
        _resume.add(value);
    }

    public void RemoveOnResume(Action0 value) {
        _resume.remove(value);

        if (_resume.size() == 0)
            BridgeConnector.getSocket().off("pm-resume");
    }

    /**
     * Emitted when the system changes to AC power.
     */
    public void AddOnAC(Action0 value) {
        if (_onAC.size() == 0) {
            BridgeConnector.getSocket().on("pm-on-ac", (unused) -> _onAC.forEach(Action0::accept));

            BridgeConnector.getSocket().emit("register-pm-on-ac");
        }
        _onAC.add(value);
    }

    public void RemoveOnAC(Action0 value) {
        _onAC.remove(value);

        if (_onAC.size() == 0)
            BridgeConnector.getSocket().off("pm-on-ac");
    }

    /**
     * Emitted when system changes to battery power.
     */
    public void AddOnBattery(Action0 value) {
        if (_onBattery.size() == 0) {
            BridgeConnector.getSocket().on("pm-on-battery", (unused) -> _onBattery.forEach(Action0::accept));

            BridgeConnector.getSocket().emit("register-pm-on-battery");
        }
        _onBattery.add(value);
    }

    public void RemoveOnBattery(Action0 value) {
        _onBattery.remove(value);

        if (_onBattery.size() == 0)
            BridgeConnector.getSocket().off("pm-on-battery");
    }

    /**
     * Emitted when the system is about to reboot or shut down. If the event handler
     * invokes `e.preventDefault()`, Electron will attempt to delay system shutdown in
     * order for the app to exit cleanly.If `e.preventDefault()` is called, the app
     * should exit as soon as possible by calling something like `app.quit()`.
     */
    public void AddOnShutdown(Action0 value) {
        if (_shutdown.size() == 0) {
            BridgeConnector.getSocket().on("pm-shutdown", (unused) -> _shutdown.forEach(Action0::accept));

            BridgeConnector.getSocket().emit("register-pm-shutdown");
        }
        _shutdown.add(value);
    }

    public void RemoveOnShutdown(Action0 value) {
        _shutdown.remove(value);

        if (_shutdown.size() == 0)
            BridgeConnector.getSocket().off("pm-on-shutdown");
    }
}
