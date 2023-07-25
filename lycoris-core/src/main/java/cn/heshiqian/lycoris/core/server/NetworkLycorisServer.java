package cn.heshiqian.lycoris.core.server;

import cn.heshiqian.lycoris.core.spi.LycorisServer;

/**
 * @author Heshiqian
 * @version 1.0.0
 * @since 2023/7/25
 */
public abstract class NetworkLycorisServer implements LycorisServer {

    private final Protocol protocol;

    public NetworkLycorisServer(Protocol protocol) {
        this.protocol = protocol;
    }



}
