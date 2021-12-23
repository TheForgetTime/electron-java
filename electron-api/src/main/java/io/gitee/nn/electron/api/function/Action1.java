package io.gitee.nn.electron.api.function;

import java.io.Serializable;
import java.util.Objects;

@SuppressWarnings("unused")
@FunctionalInterface
public interface Action1<T> extends Serializable {
    void accept(T t);

    default Action1<T> andThen(Action1<? super T> after) {
        Objects.requireNonNull(after);

        return (t) -> {
            accept(t);
            after.accept(t);
        };
    }
}
