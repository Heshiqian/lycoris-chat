package cn.heshiqian.lycoris.core.server.impl;

import cn.heshiqian.lycoris.core.server.BasicProtocol;
import cn.heshiqian.lycoris.core.server.NetworkLycorisServer;
import cn.heshiqian.lycoris.core.session.Session;

/**
 * @author Heshiqian
 * @version 1.0.0
 * @since 2023/7/25
 */
public class TcpLycorisServer extends NetworkLycorisServer {

    public TcpLycorisServer() {
        super(BasicProtocol.TCP);
    }

    @Override
    public void start() {

    }

    @Override
    public void shutdown() {

    }

    @Override
    public String getServerName() {
        return null;
    }

    @Override
    public void onConnect(Session session) {

    }

    @Override
    public void onDisconnect(Session session) {

    }

    @Override
    public void onMessage(Session session) {

    }

    @Override
    public void onError(Session session) {

    }
}
