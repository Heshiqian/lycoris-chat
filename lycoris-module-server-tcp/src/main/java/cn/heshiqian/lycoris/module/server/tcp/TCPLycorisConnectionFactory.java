package cn.heshiqian.lycoris.module.server.tcp;

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
        return TCPLycorisConnectionFactoryHolder.LYCORIS_CONNECTION_FACTORY;
    }

    private static class TCPLycorisConnectionFactoryHolder {
        private static final TCPLycorisConnectionFactory LYCORIS_CONNECTION_FACTORY = new TCPLycorisConnectionFactory();
    }
}
