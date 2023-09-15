package cn.heshiqian.lycoris.core.server.tcp;

import cn.heshiqian.lycoris.core.server.NetworkLycorisServer;
import cn.heshiqian.lycoris.core.session.Session;
import cn.heshiqian.lycoris.core.session.SessionConnectionInfo;
import cn.heshiqian.lycoris.core.session.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author Heshiqian
 * @version 1.0.0
 * @since 2023/9/7
 */
public class TCPLycorisServer extends NetworkLycorisServer {

    private static final Logger logger = LoggerFactory.getLogger(TCPLycorisServer.class);

    private static final SessionFactory tcpSessionFactory = TCPSessionFactory.getInstance();

    private boolean createFlag = false;
    private TCPServerThread tcpServerThread;

    @Override
    public void onServerCreate() {
        try {
            createTcpSocketWithJava();
        } catch (IOException e) {
            throw new TCPLycorisServerException("Create the tpc server fail. reason: " + e.getMessage(), e);
        }
    }

    @Override
    public void onServerStart() {

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

    private TCPServerConfig getTCPServerConfig() {
        return (TCPServerConfig) serverConfig;
    }

    private void createTcpSocketWithJava() throws IOException {
        TCPServerConfig tcpServerConfig = getTCPServerConfig();
        int serverPort = tcpServerConfig.getServerPort();
        if (tcpServerConfig.isNio()) {
            // thrown not support;
            throw new UnsupportedOperationException("Not support NIO mode");
        } else {
            // Set backlog size is max integer, which is prevented to masses clients connect to server socket queue is blocked.
            // Theoretically, this operate can raise client's handle count up to Integer.MAX_VALUE max.
            ServerSocket serverSocket = new ServerSocket(serverPort, Integer.MAX_VALUE);
            tcpServerThread = new TCPServerThread(serverSocket);
            tcpServerThread.start();
        }
    }

    static class TCPServerThread extends Thread {

        AtomicBoolean loop = new AtomicBoolean(true);

        ServerSocket serverSocket;

        public TCPServerThread(ServerSocket serverSocket) {
            this.serverSocket = serverSocket;
        }

        @Override
        public void start() {
            logger.debug("[TCP] TCP Server is start. port:[{}] bind:[{} --> {}]",
                    serverSocket.getLocalPort(),
                    Arrays.toString(serverSocket.getInetAddress().getAddress()),
                    serverSocket.getInetAddress().getHostAddress()
            );
            super.start();
        }

        @Override
        public void run() {
            while (loop.get()) {
                Socket accept = null;
                try {
                    // Blocked
                    accept = serverSocket.accept();
                    // Need to notice upper level to handle connect
                    SessionConnectionInfo sessionConnectionInfo = new SessionConnectionInfo();
                    sessionConnectionInfo.setAddress(accept.getInetAddress().getHostAddress());
                    sessionConnectionInfo.setIpAddress(((InetSocketAddress)accept.getRemoteSocketAddress()).getAddress().getAddress());

                    Session session = tcpSessionFactory.buildSession(sessionConnectionInfo);




                } catch (IOException e) {
                    // Abandon this connection
                    logger.debug("Connection was abandoned.", e);
                }
            }
        }
    }

    public TCPLycorisServer(TCPServerConfig tcpServerConfig) {
        super(tcpServerConfig);
    }
}
