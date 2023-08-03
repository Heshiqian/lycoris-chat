package cn.heshiqian.lycoris.core.properties;

import java.util.Properties;
import java.util.function.Supplier;

/**
 * @author Heshiqian
 * @since 2023/8/3
 * @version 1.0.0
 */
public class WorkerConfig extends AbstractLycorisProperty{

    private static final int DEFAULT_MAX = 10;
    private static final int DEFAULT_TIMEOUT = 30_000;
    public static final String PROP_KEY_WORKER_MAX = "lycoris.worker.max";
    public static final String PROP_KEY_WORKER_TIMEOUT = "lycoris.worker.timeout";

    @Override
    protected void initDefault() {
        configurations.put(PROP_KEY_WORKER_MAX, DEFAULT_MAX);
        configurations.put(PROP_KEY_WORKER_TIMEOUT, DEFAULT_TIMEOUT);
    }

    @Override
    public void load(Properties properties) {
        configurations.put(PROP_KEY_WORKER_MAX, readPropertyValue(properties, PROP_KEY_WORKER_MAX, numberStr -> {
            try {
                return Integer.parseInt(numberStr);
            } catch (NumberFormatException e) {
                return DEFAULT_MAX;
            }
        }, () -> DEFAULT_MAX));
        configurations.put(PROP_KEY_WORKER_TIMEOUT, readPropertyValue(properties, PROP_KEY_WORKER_TIMEOUT, numberStr -> {
            try {
                return Integer.parseInt(numberStr);
            } catch (NumberFormatException e) {
                return DEFAULT_TIMEOUT;
            }
        }, () -> DEFAULT_TIMEOUT));
    }

    @Override
    public void load(String configStr) {
        load(loadFromString(configStr));
    }

}
