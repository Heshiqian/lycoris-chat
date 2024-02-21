package cn.heshiqian.lycoris.core.server;

import cn.heshiqian.lycoris.core.properties.ServerConfig;
import cn.heshiqian.lycoris.core.session.SessionManager;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * This is ALL network protocol server's parent class, to provide full network activity lifecycle.
 *
 * @author Heshiqian
 * @since 2023/9/7
 */
public abstract class NetworkLycorisServer extends AbstractLycorisServer implements NetworkServerLifecycle {

    private final AtomicBoolean firstCreate = new AtomicBoolean(true);

    public NetworkLycorisServer(ServerConfig serverConfig) {
        super(serverConfig);
    }

    @Override
    public void start() {
        if (firstCreate.get()) {
            firstCreate.compareAndSet(true, false);
            onServerCreate();
        }
        onServerStart();
    }

    @Override
    public void shutdown() {
        onServerShutdown();
        onServerClose();
        onServerDestroy();
    }

}
