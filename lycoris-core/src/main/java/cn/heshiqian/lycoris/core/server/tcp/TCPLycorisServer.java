package cn.heshiqian.lycoris.core.server.tcp;

import cn.heshiqian.lycoris.core.server.NetworkLycorisServer;

import java.io.IOException;
import java.net.ServerSocket;

/**
 * @author Heshiqian
 * @version 1.0.0
 * @since 2023/9/7
 */
public class TCPLycorisServer extends NetworkLycorisServer {

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



        }
    }

    static class TCPServerThread extends Thread {

        public TCPServerThread(ServerSocket serverSocket) {

        }

        @Override
        public void run() {


        }
    }

    public TCPLycorisServer(TCPServerConfig tcpServerConfig) {
        super(tcpServerConfig);
    }
}
