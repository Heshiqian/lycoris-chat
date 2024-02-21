package cn.heshiqian.lycoris.module.server.tcp;

import cn.heshiqian.lycoris.core.connection.ConnectionInfo;
import cn.heshiqian.lycoris.core.connection.LycorisConnection;
import cn.heshiqian.lycoris.core.connection.ConnectionReader;
import cn.heshiqian.lycoris.core.connection.ConnectionWriter;
import cn.heshiqian.lycoris.core.connection.impl.ReactiveConnectionReader;
import cn.heshiqian.lycoris.core.connection.impl.SimpleConnectionWriter;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author Heshiqian
 * @since 2023/9/21
 */
public class TCPLycorisConnection implements LycorisConnection {

    private final ReactiveConnectionReader reader;
    private final SimpleConnectionWriter writer;

    private final ConnectionInfo info;

    public TCPLycorisConnection(ConnectionInfo info) {
        this.info = info;
        reader = new ReactiveConnectionReader(info.getIn());
        writer = new SimpleConnectionWriter(info.getOut());
    }

    @Override
    public ConnectionInfo info() {
        return info;
    }

    @Override
    public ConnectionWriter writer() {
        return writer;
    }

    @Override
    public ConnectionReader reader() {
        return reader;
    }

    @Override
    public void close() {
        try {
            info.getIn().close();
            info.getOut().close();
        } catch (IOException ignore) { }
    }
}
