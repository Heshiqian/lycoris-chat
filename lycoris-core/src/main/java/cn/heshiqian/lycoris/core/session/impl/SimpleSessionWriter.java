package cn.heshiqian.lycoris.core.session.impl;

import cn.heshiqian.lycoris.core.session.SessionWriter;

import java.io.IOException;
import java.io.OutputStream;

/**
 * @author Heshiqian
 * @version 1.0.0
 * @since 2023/9/15
 */
public class SimpleSessionWriter implements SessionWriter {

    private final OutputStream out;

    public SimpleSessionWriter(OutputStream out) {
        this.out = out;
    }

    @Override
    public OutputStream getOutput() {
        return out;
    }

    @Override
    public int write(byte[] data) {
        try {
            out.write(data);
            return data.length;
        } catch (IOException e) {
            // simple writer will ignore any exception, only return -1 to mark this time write is fail.
            return -1;
        }
    }
}
