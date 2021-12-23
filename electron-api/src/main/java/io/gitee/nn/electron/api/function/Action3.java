package io.gitee.nn.electron.api.function;

import java.io.Serializable;
import java.util.Objects;

@SuppressWarnings("unused")
@FunctionalInterface
public interface Action3<T, T2, T3> extends Serializable {
    void accept(T t, T2 t2, T3 t3);

    default Action3<T, T2, T3> andThen(Action3<? super T, ? super T2, ? super T3> after) {
        Objects.requireNonNull(after);

        return (l, m, r) -> {
            accept(l, m, r);
            after.accept(l, m, r);
        };
    }
}
