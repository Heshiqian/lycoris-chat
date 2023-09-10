package cn.heshiqian.lycoris.core.server;

import cn.heshiqian.lycoris.core.properties.ServerConfig;

/**
 * This is ALL network protocol server's parent class, to provide full network activity lifecycle.
 *
 * @author Heshiqian
 * @since 2023/9/7
 */
public abstract class NetworkLycorisServer extends AbstractLycorisServer implements NetworkServerLifecycle {

    public NetworkLycorisServer(ServerConfig serverConfig) {
        super(serverConfig);
    }



    @Override
    public void start() {

    }

    @Override
    public void shutdown() {

    }

}
