package cn.heshiqian.lycoris.core.server;

import cn.heshiqian.lycoris.core.channel.LycorisChannel;
import cn.heshiqian.lycoris.core.worker.LycorisWorker;
import cn.heshiqian.lycoris.core.session.SessionManager;

/**
 * <p>The top-level Lycoris-Chat server interface, provide method to control server and named server.</p>
 * @author Heshiqian
 * @version 1.0.0
 * @since 2023/7/24
 */
public interface LycorisServer {

    /**
     * Start this server
     */
    void start();

    /**
     * Shutdown this server
     */
    void shutdown();

    /**
     * Get this server name, is not unique.
     * @return this server name
     */
    String getServerName();

    /**
     * Get this server session manager.
     * @return session manager
     */
    SessionManager getSessionManager();

}
