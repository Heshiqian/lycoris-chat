package cn.heshiqian.lycoris.module.server.tcp;

import cn.heshiqian.lycoris.core.channel.LycorisChannel;
import cn.heshiqian.lycoris.core.channel.impl.StandardChannel;
import cn.heshiqian.lycoris.core.properties.WorkerConfig;
import cn.heshiqian.lycoris.core.server.NetworkLycorisServer;
import cn.heshiqian.lycoris.core.session.MemorySessionManager;
import cn.heshiqian.lycoris.core.session.SessionManager;
import cn.heshiqian.lycoris.core.util.LycorisPropertyFinder;
import cn.heshiqian.lycoris.core.worker.LycorisWorker;
import cn.heshiqian.lycoris.module.server.tcp.socket.TCPServerSocket;

import java.io.IOException;
import java.util.Properties;

/**
 * @author Heshiqian
 * @version 1.0.0
 * @since 2023/9/7
 */
public class TCPLycorisServer extends NetworkLycorisServer {


    private TCPServerSocket tcpServerSocket;
    private LycorisWorker lycorisWorker;
    private SessionManager sessionManager;

    public TCPLycorisServer(TCPServerConfig tcpServerConfig) {
        super(tcpServerConfig);
    }

    @Override
    public void onServerCreate() {
        try {
            sessionManager = new MemorySessionManager();
            createWorker();
            createTcpSocketWithJava();
        } catch (IOException e) {
            throw new TCPLycorisServerException("Create the tpc server fail. reason: " + e.getMessage(), e);
        }
    }

    @Override
    public void onServerStart() {
        if (tcpServerSocket == null) {
            throw new TCPLycorisServerException("TCP server not create, or created not success.");
        }
        tcpServerSocket.setOnConnectionConnect(connection -> {

        });
        getWorker().loopInMain(tcpServerSocket);
    }

    @Override
    public void onServerClose() {


    }

    @Override
    public void onServerShutdown() {

    }

    @Override
    public void onServerDestroy() {

    }

    private void createWorker() {
        WorkerConfig workerConfig = new WorkerConfig();
        Properties properties = LycorisPropertyFinder.findProperties(true);
        workerConfig.load(properties);
        lycorisWorker = new LycorisWorker(workerConfig);
    }

    private void createTcpSocketWithJava() throws IOException {
        TCPServerConfig tcpServerConfig = (TCPServerConfig) serverConfig;
        int serverPort = tcpServerConfig.getServerPort();
        if (tcpServerConfig.isNio()) {
            // thrown not support;
            throw new UnsupportedOperationException("Not support NIO mode");
        } else {
            tcpServerSocket = new TCPServerSocket(serverPort);
        }
    }

    @Override
    public SessionManager getSessionManager() {
        return sessionManager;
    }

    @Override
    public LycorisWorker getWorker() {
        return lycorisWorker;
    }

    @Override
    public LycorisChannel getChannel() {
        return new StandardChannel();
    }

}
