package cn.heshiqian.lycoris.core.server.tcp;

import cn.heshiqian.lycoris.core.server.connection.ConnectionInfo;
import cn.heshiqian.lycoris.core.server.connection.LycorisConnection;
import cn.heshiqian.lycoris.core.server.connection.ConnectionReader;
import cn.heshiqian.lycoris.core.server.connection.ConnectionWriter;
import cn.heshiqian.lycoris.core.server.connection.impl.ReactiveConnectionReader;
import cn.heshiqian.lycoris.core.server.connection.impl.SimpleConnectionWriter;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author Heshiqian
 * @since 2023/9/21
 */
public class TCPLycorisConnection implements LycorisConnection {

    private final InputStream in;
    private final OutputStream out;
    private final ReactiveConnectionReader reader;
    private final SimpleConnectionWriter writer;

    private final ConnectionInfo info;

    public TCPLycorisConnection(InputStream in, OutputStream out, ConnectionInfo info) {
        this.in = in;
        this.out = out;
        this.info = info;
        reader = new ReactiveConnectionReader(in);
        writer = new SimpleConnectionWriter(out);
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
            in.close();
            out.close();
        } catch (IOException ignore) { }
    }
}
