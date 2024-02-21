package cn.heshiqian.lycoris.module.server.tcp.socket;

import cn.heshiqian.lycoris.core.connection.ConnectionInfo;
import cn.heshiqian.lycoris.core.connection.LycorisConnection;
import cn.heshiqian.lycoris.module.server.tcp.TCPLycorisConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

/**
 * @author Heshiqian
 */
public final class TCPServerSocket implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(TCPServerSocket.class);

    private final AtomicBoolean loop = new AtomicBoolean(true);
    private final ServerSocket serverSocket;

    private Consumer<LycorisConnection> onConnectionConnect;

    public TCPServerSocket(int serverPort) throws IOException {
        // Set backlog size is max integer, which is prevented to masses clients connect to server socket queue is blocked.
        // Theoretically, this operate can raise client's handle count up to Integer.MAX_VALUE max.
        ServerSocket serverSocket = new ServerSocket(serverPort, Integer.MAX_VALUE);
        logger.debug("[TCP] TCP Server is start. port:[{}] bind:[{} --> {}]",
                serverSocket.getLocalPort(),
                Arrays.toString(serverSocket.getInetAddress().getAddress()),
                serverSocket.getInetAddress().getHostAddress()
        );
        this.serverSocket = serverSocket;
    }

    @Override
    public void run() {
        while (loop.get()) {
            Socket accept = null;
            try {
                // Blocked
                accept = serverSocket.accept();
                // Parse connection
                ConnectionInfo connectionInfo = new ConnectionInfo(
                        accept.getInetAddress().getHostAddress(),
                        ((InetSocketAddress) accept.getRemoteSocketAddress()).getAddress().getAddress(),
                        accept.getInputStream(),
                        accept.getOutputStream()
                );

                connectionInfo.setRecognizeId(String.valueOf(System.currentTimeMillis()));
                connectionInfo.setConnected(true);
                connectionInfo.setConnectTime(System.currentTimeMillis());

                LycorisConnection lycorisConnection = new TCPLycorisConnection(connectionInfo);
                if (onConnectionConnect != null) {
                    onConnectionConnect.accept(lycorisConnection);
                } else {
                    throw new IOException("Not exist consumer defined in this server.");
                }

            } catch (IOException e) {
                // Abandon this connection
                logger.debug("Connection was abandoned.", e);
            }
        }
    }

    public void setOnConnectionConnect(Consumer<LycorisConnection> onConnectionConnect) {
        this.onConnectionConnect = onConnectionConnect;
    }
}
