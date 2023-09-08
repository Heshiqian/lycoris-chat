package cn.heshiqian.lycoris.core.server.tcp;

import cn.heshiqian.lycoris.core.properties.ServerConfig;
import lombok.Getter;
import lombok.Setter;

import java.util.Properties;

/**
 * @author Heshiqian
 * @version 1.0.0
 * @since 2023/9/7
 */
@Getter
@Setter
public class TCPServerConfig extends ServerConfig {

    public static final String PROP_KEY_SERVER_NIO_MODE = "lycoris.server.nio";

    private boolean nio;

    @Override
    public void load(Properties properties) {
        super.load(properties);
        nio = readPropertyValue(properties, PROP_KEY_SERVER_NIO_MODE, this::fuzzyBooleanValue, () -> false);
    }
}
