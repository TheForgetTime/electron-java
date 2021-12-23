package io.gitee.nn.electron.api;

public final class BridgeSettings {
    private static String socketPort;
    private static String webPort;

    public static String getSocketPort() {
        return BridgeSettings.socketPort;
    }

    static void setSocketPort(String socketPort) {
        BridgeSettings.socketPort = socketPort;
    }

    public static String getWebPort() {
        return BridgeSettings.webPort;
    }

    static void setWebPort(String webPort) {
        BridgeSettings.webPort = webPort;
    }
}
