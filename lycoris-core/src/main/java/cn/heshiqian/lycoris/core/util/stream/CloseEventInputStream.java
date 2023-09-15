package cn.heshiqian.lycoris.core.util.stream;

import java.io.IOException;
import java.io.InputStream;
import java.util.function.Consumer;

/**
 * Decorate the {@link InputStream} with an event consumer.<br/>
 * When the {@link InputStream#close()} method called,
 * <b>will call superclass {@link #close()}.</b>
 * @author Heshiqian
 * @version 1.0.0
 * @since 2023/9/15
 */
public final class CloseEventInputStream extends InputStream {

    private final InputStream inputStream;
    private final Consumer<Void> onClose;

    public CloseEventInputStream(InputStream inputStream, Consumer<Void> onClose) {
        this.inputStream = inputStream;
        this.onClose = onClose;
    }

    @Override
    public int read() throws IOException {
        return inputStream.read();
    }

    @Override
    public void close() throws IOException {
        onClose.accept(null);
        super.close();
    }

}
