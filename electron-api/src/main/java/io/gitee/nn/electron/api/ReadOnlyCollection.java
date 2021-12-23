package io.gitee.nn.electron.api;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;

@SuppressWarnings({"SuspiciousToArrayCall", "SuspiciousMethodCalls"})
final class ReadOnlyCollection<E> implements IReadOnlyCollection<E> {
    private final List<E> data;

    ReadOnlyCollection(List<E> data) {
        this.data = data;
    }

    @Override
    public int size() {
        return data.size();
    }

    @Override
    public boolean isEmpty() {
        return data.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return data.contains(o);
    }

    @Override
    public Iterator<E> iterator() {
        return data.iterator();
    }

    @Override
    public Object[] toArray() {
        return data.toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return data.toArray(a);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return data.containsAll(c);
    }

    @Override
    public Stream<E> stream() {
        return data.stream();
    }

    @Override
    public Stream<E> parallelStream() {
        return data.parallelStream();
    }

    @Override
    public void forEach(Consumer<? super E> action) {
        data.forEach(action);
    }

    @Override
    public E Last() {
        if (data.size() == 0) {
            return null;
        }
        if (data.size() == 1) {
            return data.get(0);
        }
        return data.get(data.size() - 1);
    }

    @Override
    public E First() {
        if (data.size() > 0)
            return data.get(0);
        return null;
    }
}
