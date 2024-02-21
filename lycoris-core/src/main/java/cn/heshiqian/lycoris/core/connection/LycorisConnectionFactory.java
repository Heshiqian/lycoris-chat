package cn.heshiqian.lycoris.core.connection;

/**
 * @author Heshiqian
 * @version 1.0.0
 * @since 2023/9/22
 */
public interface LycorisConnectionFactory {

    LycorisConnection buildConnection(ConnectionInfo connectionInfo);

}
