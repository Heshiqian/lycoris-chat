package cn.heshiqian.lycoris.core.util.stream;

import java.io.IOException;
import java.io.OutputStream;

/**
 *
 * @author Heshiqian
 * @version 1.0.0
 * @since 2023/9/9
 */
public class NoClosableOutputStream extends OutputStream {

    private final OutputStream origin;

    public NoClosableOutputStream(OutputStream outputStream) {
        this.origin = outputStream;
    }


    public OutputStream getOrigin() {
        return origin;
    }

    @Override
    public void write(int b) throws IOException {
        origin.write(b);
    }

    @Override
    public void close() throws IOException {
        CloseMethodPolicy.handleCloseCall();
    }
}
