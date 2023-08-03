package cn.heshiqian.lycoris.core.properties;

import cn.heshiqian.lycoris.core.exception.CannotParsePropertyException;

import java.io.IOException;
import java.io.StringReader;
import java.lang.reflect.Type;
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
public class ManagerConfig extends AbstractLycorisProperty{

    public static final String PROP_KEY_TARGET_SERVER = "lycoris.server.class";
    public static final String PROP_KEY_THREAD = "lycoris.server.thread";
    public static final String PROP_KEY_MULTI_INSTANCE = "lycoris.server.multi-instance";

    @Override
    protected void initDefault() {
        configurations.put(PROP_KEY_THREAD, false);
        configurations.put(PROP_KEY_MULTI_INSTANCE, false);
    }

    @Override
    public void load(Properties properties) {
        configurations.put(PROP_KEY_TARGET_SERVER, readPropertyValue(properties, PROP_KEY_TARGET_SERVER, Function.identity()));
    }

    @Override
    public void load(String configStr) {
        load(loadFromString(configStr));
    }

}
