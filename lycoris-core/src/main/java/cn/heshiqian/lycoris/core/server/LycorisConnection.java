package cn.heshiqian.lycoris.core.server;

import java.util.function.Supplier;

/**
 * @author Heshiqian
 * @version 1.0.0
 * @since 2023/9/9
 */
public interface LycorisConnection {

    boolean isOpen();

    void writeData(byte[] byteData);

    int incomingData(Supplier<byte[]> handler);

}
