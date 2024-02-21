package cn.heshiqian.lycoris.core.connection;

/**
 * @author Heshiqian
 * @version 1.0.0
 * @since 2023/9/15
 */
public interface LycorisConnection {

    ConnectionInfo info();

    ConnectionWriter writer();

    ConnectionReader reader();

    void close();

}
