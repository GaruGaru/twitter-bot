package persistence;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

/**
 * Created by Garu on 28/05/2017.
 */
public class PreferencePersister<T> implements Persister<T> {

    private final Logger logger = LoggerFactory.getLogger(PreferencePersister.class);

    private final Gson gson;
    private final Preferences preferences;

    private final Class<T> tClass;
    private final String prefix;

    public static <T> Persister<T> of(String prefix, Class<T> tClass) {
        return new PreferencePersister<>(prefix, tClass);
    }

    public static <T> Persister<T> of(Class<T> tClass) {
        return of("pref_", tClass);
    }

    private PreferencePersister(String prefix, Class<T> tClass) {
        this.preferences = Preferences.userRoot().node(getClass().getName());
        this.gson = new Gson();
        this.tClass = tClass;
        this.prefix = prefix;
    }

    @Override
    public Optional<T> get(String key) {
        String json = preferences.get(getKey(key), null);
        return json != null ? Optional.of(gson.fromJson(json, tClass)) : Optional.empty();
    }

    @Override
    public void save(String key, T value) {
        String json = gson.toJson(value);
        preferences.put(getKey(key), json);
        this.flush();
    }

    private String getKey(String key) {
        return this.prefix + key;
    }

    private void flush() {
        try {
            preferences.flush();
        } catch (BackingStoreException e) {
            logger.error("Error flushing preferences");
            e.printStackTrace();
        }
    }

    @Override
    public void clean(String key) {
        this.preferences.remove(key);
        flush();
    }
}