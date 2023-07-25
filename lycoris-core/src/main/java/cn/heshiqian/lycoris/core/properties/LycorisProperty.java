package cn.heshiqian.lycoris.core.properties;

import java.lang.reflect.Type;
import java.util.function.Supplier;

/**
 * @author Heshiqian
 * @version 1.0.0
 * @since 2023/7/25
 */
public interface LycorisProperty {

    void set(String key, Object propValue);

    Object get(String key);

    Object get(String key, Supplier<Object> defaultValue);

    <T> T get(String key, Supplier<T> defaultValue, Type type);

}
