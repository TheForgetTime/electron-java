package io.gitee.nn.electron.api;

import io.socket.client.IO;
import io.socket.client.Socket;

import java.net.URI;

final class BridgeConnector {
    private volatile static Socket socket;

    public static Socket getSocket() {
        if (socket == null && HybridSupport.IsElectronActive()) {
            synchronized (BridgeConnector.class) {
                if (socket == null && HybridSupport.IsElectronActive()) {
                    IO.Options options = IO.Options.builder().build();
                    socket = IO.socket(URI.create("http://localhost:" + BridgeSettings.getSocketPort()), options);
                    socket.connect();
                }
            }
        } else if (socket == null && !HybridSupport.IsElectronActive()) {
            synchronized (BridgeConnector.class) {
                if (socket == null && !HybridSupport.IsElectronActive()) {
                    socket = IO.socket(URI.create("http://localhost"), new IO.Options());
                }
            }
        }

        return socket;
    }
}
