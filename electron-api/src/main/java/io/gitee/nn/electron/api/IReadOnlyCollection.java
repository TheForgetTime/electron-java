package io.gitee.nn.electron.api;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;

@SuppressWarnings("unused")
public interface IReadOnlyCollection<E> {
    static <E> IReadOnlyCollection<E> AsReadOnly(List<E> data) {
        return new ReadOnlyCollection<>(data);
    }

    int size();

    boolean isEmpty();

    boolean contains(Object o);

    Iterator<E> iterator();

    Object[] toArray();

    <T> T[] toArray(T[] a);

    boolean containsAll(Collection<?> c);

    Stream<E> stream();

    Stream<E> parallelStream();

    void forEach(Consumer<? super E> action);

    E Last();

    E First();
}