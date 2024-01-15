package cn.heshiqian.lycoris.core.properties;

import cn.heshiqian.lycoris.core.exception.CannotParsePropertyException;
import cn.heshiqian.lycoris.core.exception.LycorisServerException;
import lombok.Getter;
import lombok.Setter;

import java.util.Properties;

/**
 * @author Heshiqian
 * @since 2023/8/3
 * @version 1.0.0
 */
@Getter
@Setter
public class WorkerConfig extends AbstractLycorisProperty{

    private static final int DEFAULT_MAX = 10;
    private static final int DEFAULT_TIMEOUT = 30_000;
    public static final String PROP_KEY_WORKER_CORE_SIZE = "lycoris.worker.core.size";
    public static final String PROP_KEY_WORKER_MAX = "lycoris.worker.max";
    public static final String PROP_KEY_WORKER_TIMEOUT = "lycoris.worker.timeout";


    private int coreSize = DEFAULT_MAX;
    private int workerMax = DEFAULT_MAX;
    private int workerTimeout = DEFAULT_TIMEOUT;

    @Override
    protected void initDefault() {
        configurations.put(PROP_KEY_WORKER_CORE_SIZE, DEFAULT_MAX);
        configurations.put(PROP_KEY_WORKER_MAX, DEFAULT_MAX);
        configurations.put(PROP_KEY_WORKER_TIMEOUT, DEFAULT_TIMEOUT);
    }

    @Override
    public void load(Properties properties) {
        coreSize = readPropertyValue(properties, PROP_KEY_WORKER_CORE_SIZE, numberStr -> {
            try {
                return Integer.parseInt(numberStr);
            } catch (NumberFormatException e) {
                throw new CannotParsePropertyException(PROP_KEY_WORKER_CORE_SIZE + " is not a integer number", e);
            }
        }, () -> DEFAULT_MAX);
        workerMax = readPropertyValue(properties, PROP_KEY_WORKER_MAX, numberStr -> {
            try {
                return Integer.parseInt(numberStr);
            } catch (NumberFormatException e) {
                throw new CannotParsePropertyException(PROP_KEY_WORKER_CORE_SIZE + " is not a integer number", e);
            }
        }, () -> DEFAULT_MAX);
        workerTimeout = readPropertyValue(properties, PROP_KEY_WORKER_TIMEOUT, numberStr -> {
            try {
                return Integer.parseInt(numberStr);
            } catch (NumberFormatException e) {
                throw new CannotParsePropertyException(PROP_KEY_WORKER_CORE_SIZE + " is not a integer number", e);
            }
        }, () -> DEFAULT_TIMEOUT);
    }

}
