package persistence;

import java.util.Optional;

/**
 * Created by Garu on 28/05/2017.
 */
public interface Persister<T> {
    Optional<T> get(String key);

    void save(String key, T value);

    void clean(String key);
}