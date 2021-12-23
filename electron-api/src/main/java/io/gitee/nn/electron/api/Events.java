package io.gitee.nn.electron.api;

import io.gitee.nn.electron.api.function.Action1;
import io.socket.emitter.Emitter;

final class Events {
    private volatile static Events events;

    Events() {
    }

    static Events getInstance() {
        if (events == null) {
            synchronized (Events.class) {
                if (events == null) {
                    events = new Events();
                }
            }
        }
        return events;
    }

    public void On(String moduleName, String eventName, Action1<Object> fn) {
        On(moduleName, eventName, (Emitter.Listener) fn::accept);
    }

    private void On(String moduleName, String eventName, Emitter.Listener fn) {
        var listener = "" + moduleName + eventName + "Completed";
        var subscriber = "register-" + moduleName + "-on-event";

        BridgeConnector.getSocket().on(listener, fn);
        BridgeConnector.getSocket().emit(subscriber, eventName, listener);
    }

    public void Once(String moduleName, String eventName, Action1<Object> fn) {
        Once(moduleName, eventName, (Emitter.Listener) fn::accept);
    }

    private void Once(String moduleName, String eventName, Emitter.Listener fn) {
        var listener = "" + moduleName + eventName + "Completed";
        var subscriber = "register-" + moduleName + "-once-event";
        BridgeConnector.getSocket().once(listener, fn);
        BridgeConnector.getSocket().emit(subscriber, eventName, listener);
    }

}
