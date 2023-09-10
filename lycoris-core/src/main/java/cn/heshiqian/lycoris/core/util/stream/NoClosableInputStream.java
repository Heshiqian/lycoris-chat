package cn.heshiqian.lycoris.core.util.stream;

import java.io.IOException;
import java.io.InputStream;

/**
 *
 * @author Heshiqian
 * @version 1.0.0
 * @since 2023/9/9
 */
public class NoClosableInputStream extends InputStream {

    private final InputStream origin;

    public NoClosableInputStream(InputStream inputStream) {
        this.origin = inputStream;
    }

    @Override
    public int read() throws IOException {
        return origin.read();
    }

    public InputStream getOrigin() {
        return origin;
    }

    @Override
    public void close() throws IOException {
        CloseMethodPolicy.handleCloseCall();
    }
}
