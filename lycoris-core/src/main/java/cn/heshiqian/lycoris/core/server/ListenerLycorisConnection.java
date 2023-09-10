package cn.heshiqian.lycoris.core.server;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author Heshiqian
 * @version 1.0.0
 * @since 2023/9/9
 */
public abstract class ListenerLycorisConnection implements LycorisConnection{

    private final InputStream in;
    private final OutputStream out;

    public ListenerLycorisConnection(InputStream in, OutputStream out) {
        this.in = in;
        this.out = out;
    }



}
