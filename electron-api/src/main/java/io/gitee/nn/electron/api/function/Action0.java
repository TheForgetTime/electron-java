package io.gitee.nn.electron.api.function;

import java.io.Serializable;
import java.util.Objects;

@SuppressWarnings("unused")
@FunctionalInterface
public interface Action0 extends Serializable {
    void accept();

    default Action0 andThen(Action0 after) {
        Objects.requireNonNull(after);

        return () -> {
            accept();
            after.accept();
        };
    }
}
