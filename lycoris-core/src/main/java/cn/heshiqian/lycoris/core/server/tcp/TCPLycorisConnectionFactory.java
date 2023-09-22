package cn.heshiqian.lycoris.core.server.tcp;

import cn.heshiqian.lycoris.core.server.connection.ConnectionInfo;
import cn.heshiqian.lycoris.core.server.connection.LycorisConnection;
import cn.heshiqian.lycoris.core.server.connection.LycorisConnectionFactory;

/**
 * @author Heshiqian
 * @version 1.0.0
 * @since 2023/9/15
 */
public class TCPLycorisConnectionFactory implements LycorisConnectionFactory {

    @Override
    public LycorisConnection buildConnection(ConnectionInfo connectionInfo) {

        connectionInfo.setRecognizeId(createTimeBaseSessionId());
        connectionInfo.setConnected(true);
        connectionInfo.setConnectTime(System.currentTimeMillis());

        return new TCPLycorisConnection(
                connectionInfo.getIn(),
                connectionInfo.getOut(),
                connectionInfo);
    }

    private String createTimeBaseSessionId() {
        return String.valueOf(System.currentTimeMillis());
    }

    public static LycorisConnectionFactory getInstance() {
        return TCPSessionFactoryHolder.sessionFactory;
    }

    private static class TCPSessionFactoryHolder {
        private static final TCPLycorisConnectionFactory sessionFactory = new TCPLycorisConnectionFactory();
    }
}
