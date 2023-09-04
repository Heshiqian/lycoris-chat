package cn.heshiqian.lycoris.core.properties;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Properties;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

/**
 * @author Heshiqian
 * @version 1.0.0
 * @since 2023/9/4
 */
@EqualsAndHashCode(callSuper = false)
@Data
public class ServerConfig extends AbstractLycorisProperty {

    public static final String PROP_KEY_SERVER_PORT = "lycoris.server.port";
    public static final String PROP_KEY_SERVER_NAME = "lycoris.server.name";

    private static final int DEFAULT_SERVER_PORT = 32100;
    private static final String DEFAULT_SERVER_NAME_PREFIX = "lycoris-server-";

    private static final AtomicInteger instanceCount = new AtomicInteger(1);

    private int serverPort;
    private String serverName;

    @Override
    protected void initDefault() {
        // Handled by #load() method, default value will use #readPropertyValue() null supplier
    }

    @Override
    public void load(Properties properties) {
        // Read property String:"32101" to int:32101 or default: 32100
        serverPort = readPropertyValue(properties, PROP_KEY_SERVER_PORT, Integer::parseInt,
                () -> DEFAULT_SERVER_PORT);
        // Read property String:"any-server-name" or default: lycoris-server-[1|number]
        serverName = readPropertyValue(properties, PROP_KEY_SERVER_PORT, Function.identity(),
                () -> DEFAULT_SERVER_NAME_PREFIX + instanceCount.getAndIncrement());
    }

}

