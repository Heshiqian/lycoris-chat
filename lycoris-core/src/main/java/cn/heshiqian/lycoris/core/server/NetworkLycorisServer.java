package cn.heshiqian.lycoris.core.server;

import cn.heshiqian.lycoris.core.properties.ServerConfig;
import cn.heshiqian.lycoris.core.session.SessionManager;

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
        onServerCreate();
        onServerStart();
    }

    @Override
    public void shutdown() {
        onServerShutdown();
        onServerClose();
        onServerDestroy();
    }

    @Override
    public SessionManager getSessionManager() {
        return null;
    }
}
