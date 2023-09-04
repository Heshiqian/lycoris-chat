package cn.heshiqian.lycoris.core.server;

import cn.heshiqian.lycoris.core.channel.LycorisChannel;
import cn.heshiqian.lycoris.core.properties.ServerConfig;

import java.util.Objects;

/**
 *
 * @author Heshiqian
 * @version 1.0.0
 * @since 2023/9/4
 */
public abstract class AbstractLycorisServer implements LycorisServer {

    protected final ServerConfig serverConfig;
    protected LycorisChannel channel;

    public AbstractLycorisServer(ServerConfig serverConfig) {
        this.serverConfig = serverConfig;
    }

    @Override
    public String getServerName() {
        return serverConfig.getServerName();
    }

    @Override
    public void setChannel(LycorisChannel channel) {
        Objects.requireNonNull(channel, "Channel cannot be null");
        this.channel = channel;
    }
}
