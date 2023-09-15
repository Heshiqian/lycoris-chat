package cn.heshiqian.lycoris.core.util.stream;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;

/**
 * <p>This class is a kind of {@link InputStream} decoration that provided a way to prevent call {@link Closeable#close()} method.</p>
 * <p>In some cases, like a socket stream, developer don't want to caller to direct close socket stream,
 * so can use this class decoration, to prevent caller close this stream.</p>
 * <p>Example:
 * <pre>{@code
 *   InputStream someInputStream;
 *   // warp by NoClosableInputStream
 *   InputStream in = new NoClosableInputStream(someInputStream);
 *   // call close
 *   try {
 *     // 1. if close policy is not a 'thrown', this call does not have any effects.
 *     in.close();
 *     // 1.1 end of call.
 *   } catch (RuntimeException e) {
 *     // 2. if close policy is 'thrown', the method call will thrown RuntimeException.
 *   }
 * }
 * </pre>
 * </p>
 * See also: {@link NoClosableOutputStream}, {@link CloseMethodPolicy}
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
