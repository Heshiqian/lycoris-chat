package cn.heshiqian.lycoris.core.spi;

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

    String getServerName();

    void setChannel(LycorisChannel channel);

}
