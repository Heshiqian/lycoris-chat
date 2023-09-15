package cn.heshiqian.lycoris.core.util.stream;

import java.io.Closeable;
import java.io.IOException;
import java.io.OutputStream;

/**
 * <p>This class is a kind of {@link OutputStream} decoration that provided a way to prevent call {@link Closeable#close()} method.</p>
 * <p>In some cases, like a socket stream, developer don't want to caller to direct close socket stream,
 * so can use this class decoration, to prevent caller close this stream.</p>
 * <p>Example:
 * <pre>{@code
 *   OutputStream someOutputStream;
 *   // warp by NoClosableInputStream
 *   OutputStream out = new NoClosableOutputStream(someOutputStream);
 *   // call close
 *   try {
 *     // 1. if close policy is not a 'thrown', this call does not have any effects.
 *     out.close();
 *     // 1.1 end of call.
 *   } catch (RuntimeException e) {
 *     // 2. if close policy is 'thrown', the method call will thrown RuntimeException.
 *   }
 * }
 * </pre>
 * </p>
 * See also: {@link NoClosableInputStream}, {@link CloseMethodPolicy}
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
