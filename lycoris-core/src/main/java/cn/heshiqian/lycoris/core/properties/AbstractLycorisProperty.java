package cn.heshiqian.lycoris.core.properties;

import cn.heshiqian.lycoris.core.exception.CannotParsePropertyException;

import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @author Heshiqian
 * @version 1.0.0
 * @since 2023/7/25
 */
public abstract class AbstractLycorisProperty implements LycorisProperty{

    protected static final Map<String, Object> configurations = new HashMap<>(10);

    public AbstractLycorisProperty() {
        initDefault();
    }

    protected abstract void initDefault();

    protected Properties loadFromString(String propertyString) {
        try {
            Properties properties = new Properties();
            properties.load(new StringReader(propertyString));
            return properties;
        } catch (IOException e) {
            throw new CannotParsePropertyException("load string:["+propertyString+"] failure.", e);
        }
    }

    protected <T>T readPropertyValue(Properties properties,
                                     String key,
                                     Function<String, T> parser) {
        return readPropertyValue(properties, key, parser, () -> null);
    }

    protected <T>T readPropertyValue(Properties properties,
                                     String key,
                                     Function<String, T> parser,
                                     Supplier<T> onNullReturn) {
        String property = properties.getProperty(key);
        if (property == null || property.isEmpty() || property.trim().length() == 0 ) return onNullReturn.get();
        return parser.apply(property);
    }

    @Override
    public void set(String key, Object propValue) {
        configurations.put(key, propValue);
    }

    @Override
    public Object get(String key) {
        return configurations.get(key);
    }

    @Override
    public Object get(String key, Supplier<Object> defaultValue) {
        return configurations.getOrDefault(key, defaultValue == null ? null : defaultValue.get());
    }

    @Override
    public <T> T get(String key, Supplier<T> defaultValue, Class<T> type) {
        return type.cast(
                configurations.getOrDefault(key, defaultValue == null ? null : defaultValue.get())
        );
    }

}
