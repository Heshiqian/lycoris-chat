package cn.heshiqian.lycoris.core.server;

import cn.heshiqian.lycoris.core.channel.LycorisChannel;
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

    /**
     * <p>Set {@link LycorisChannel} channel to this server, provided async data/event transport.</p>
     * <p>The channel created by factory producer, the factory must create channel and set channel to this server.
     * Technically this method's parameter not allow {@code null} value.</p>
     * @param channel A channel instance.
     */
    void setChannel(LycorisChannel channel);

}
