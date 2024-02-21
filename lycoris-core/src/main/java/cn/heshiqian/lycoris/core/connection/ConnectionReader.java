package cn.heshiqian.lycoris.core.connection;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author Heshiqian
 * @since 2023/9/15
 */
public interface ConnectionReader {

    InputStream getInput();

    byte[] read() throws IOException;

    boolean hasMore();

}
