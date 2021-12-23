package io.gitee.nn.electron.api.function;

import java.io.Serializable;
import java.util.Objects;

@SuppressWarnings("unused")
@FunctionalInterface
public interface Action2<T, T1> extends Serializable {
    void accept(T t, T1 t1);

    default Action2<T, T1> andThen(Action2<? super T, ? super T1> after) {
        Objects.requireNonNull(after);

        return (t, t1) -> {
            accept(t, t1);
            after.accept(t, t1);
        };
    }
}
