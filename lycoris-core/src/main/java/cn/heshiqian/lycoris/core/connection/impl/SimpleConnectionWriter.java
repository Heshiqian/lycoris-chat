package cn.heshiqian.lycoris.core.connection.impl;

import cn.heshiqian.lycoris.core.connection.ConnectionWriter;

import java.io.IOException;
import java.io.OutputStream;

/**
 * @author Heshiqian
 * @version 1.0.0
 * @since 2023/9/15
 */
public class SimpleConnectionWriter implements ConnectionWriter {

    private final OutputStream out;

    public SimpleConnectionWriter(OutputStream out) {
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
