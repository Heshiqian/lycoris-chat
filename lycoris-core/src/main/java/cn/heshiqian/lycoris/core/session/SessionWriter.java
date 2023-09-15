package cn.heshiqian.lycoris.core.session;

import java.io.OutputStream;

/**
 * @author Heshiqian
 * @version 1.0.0
 * @since 2023/9/15
 */
public interface SessionWriter {

    OutputStream getOutput();

    int write(byte[] data);

}
