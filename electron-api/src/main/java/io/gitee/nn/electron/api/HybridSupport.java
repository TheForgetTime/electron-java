package io.gitee.nn.electron.api;

public final class HybridSupport {
    public static boolean IsElectronActive() {
        return !(BridgeSettings.getSocketPort() == null || BridgeSettings.getSocketPort().isEmpty());
    }
}
