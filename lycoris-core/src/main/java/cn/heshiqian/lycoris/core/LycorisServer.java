package cn.heshiqian.lycoris.core;

import cn.heshiqian.lycoris.core.session.Session;

import java.nio.ByteBuffer;

/**
 * @author Heshiqian
 * @version 1.0.0
 * @since 2023/7/24
 */
public interface LycorisServer {

    void start();

    void shutdown();

    void onConnect(Session session);

    void onDisconnect(Session session);

    void onMessage(Session session);

    void onError(Session session);
}
