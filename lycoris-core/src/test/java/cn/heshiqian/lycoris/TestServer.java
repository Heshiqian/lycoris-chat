package cn.heshiqian.lycoris;

import cn.heshiqian.lycoris.core.server.LycorisServer;
import cn.heshiqian.lycoris.core.server.LycorisServerLifecycle;
import cn.heshiqian.lycoris.core.session.MemorySessionManager;
import cn.heshiqian.lycoris.core.session.SessionManager;

/**
 * @author Heshiqian
 * @since 2024/2/22
 */
public class TestServer implements LycorisServer, LycorisServerLifecycle {

    @Override
    public void start() {
        System.out.println("TestServer.start");
    }

    @Override
    public void shutdown() {
        System.out.println("TestServer.shutdown");
    }

    @Override
    public String getServerName() {
        return "Test";
    }

    @Override
    public SessionManager getSessionManager() {
        return new MemorySessionManager();
    }

    @Override
    public void onServerCreate() {

    }

    @Override
    public void onServerStart() {

    }

    @Override
    public void onServerShutdown() {

    }

    @Override
    public void onServerDestroy() {

    }
}
