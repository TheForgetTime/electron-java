package io.gitee.nn.electron.api;

import java.util.concurrent.CompletableFuture;

@SuppressWarnings("unused")
public final class CommandLine {
    private volatile static CommandLine commandLine;

    CommandLine() {
    }

    static CommandLine getInstance() {
        if (commandLine == null) {
            synchronized (CommandLine.class) {
                if (commandLine == null) {
                    commandLine = new CommandLine();
                }
            }
        }
        return commandLine;
    }

    public void AppendSwitch(String the_switch) {
        AppendSwitch(the_switch, "");
    }

    public void AppendSwitch(String the_switch, String value) {
        BridgeConnector.getSocket().emit("appCommandLineAppendSwitch", the_switch, value);
    }

    public void AppendArgument(String value) {
        BridgeConnector.getSocket().emit("appCommandLineAppendArgument", value);
    }

    public CompletableFuture<Boolean> HasSwitchAsync(String switchName) {
        var taskCompletionSource = new CompletableFuture<Boolean>();
        BridgeConnector.getSocket().on("appCommandLineHasSwitchCompleted", (result) ->
        {
            BridgeConnector.getSocket().off("appCommandLineHasSwitchCompleted");
            taskCompletionSource.complete((Boolean) result[0]);
        });

        BridgeConnector.getSocket().emit("appCommandLineHasSwitch", switchName);

        return taskCompletionSource;
    }

    public CompletableFuture<String> GetSwitchValueAsync(String switchName) {
        var taskCompletionSource = new CompletableFuture<String>();
        BridgeConnector.getSocket().on("appCommandLineGetSwitchValueCompleted", (result) ->
        {
            BridgeConnector.getSocket().off("appCommandLineGetSwitchValueCompleted");
            taskCompletionSource.complete((String) result[0]);
        });

        BridgeConnector.getSocket().emit("appCommandLineGetSwitchValue", switchName);

        return taskCompletionSource;
    }
}
