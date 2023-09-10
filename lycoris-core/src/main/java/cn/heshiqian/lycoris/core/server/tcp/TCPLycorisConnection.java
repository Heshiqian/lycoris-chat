package cn.heshiqian.lycoris.core.server.tcp;

import cn.heshiqian.lycoris.core.server.LycorisConnection;

import java.util.function.Supplier;

/**
 * @author Heshiqian
 * @version 1.0.0
 * @since 2023/9/9
 */
public class TCPLycorisConnection implements LycorisConnection {



    @Override
    public boolean isOpen() {
        return false;
    }

    @Override
    public void writeData(byte[] byteData) {

    }

    @Override
    public int incomingData(Supplier<byte[]> handler) {
        return 0;
    }

}
