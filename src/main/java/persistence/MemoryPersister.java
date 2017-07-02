package persistence;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Created by Garu on 28/05/2017.
 */
public class MemoryPersister<T> implements Persister<T> {

    private Map<String, T> memory;

    public MemoryPersister() {
        this.memory = new HashMap<>();
    }

    @Override
    public Optional<T> get(String key) {
        return Optional.ofNullable(memory.getOrDefault(key, null));
    }

    @Override
    public void save(String key, T value) {
        this.memory.put(key, value);
    }

    @Override
    public void clean(String key) {
        this.memory.remove(key);
    }
}