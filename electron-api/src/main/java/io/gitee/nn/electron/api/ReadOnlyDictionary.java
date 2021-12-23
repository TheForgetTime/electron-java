package io.gitee.nn.electron.api;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;

@SuppressWarnings("SuspiciousMethodCalls")
final class ReadOnlyDictionary<K, V> implements IReadOnlyDictionary<K, V> {
    private final Map<K, V> data;

    ReadOnlyDictionary(Map<K, V> data) {
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
    public boolean containsKey(Object key) {
        return data.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return data.containsValue(value);
    }

    @Override
    public V get(Object key) {
        return data.get(key);
    }

    @Override
    public Set<K> keySet() {
        return data.keySet();
    }

    @Override
    public Collection<V> values() {
        return data.values();
    }

    @Override
    public Set<Map.Entry<K, V>> entrySet() {
        return data.entrySet();
    }

    @Override
    public V getOrDefault(Object key, V defaultValue) {
        return data.getOrDefault(key, defaultValue);
    }

    @Override
    public void forEach(BiConsumer<? super K, ? super V> action) {
        data.forEach(action);
    }
}
