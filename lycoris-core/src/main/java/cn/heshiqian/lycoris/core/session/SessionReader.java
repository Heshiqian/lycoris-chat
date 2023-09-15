package cn.heshiqian.lycoris.core.session;

import java.io.InputStream;

/**
 * @author Heshiqian
 * @since 2023/9/15
 */
public interface SessionReader {

    InputStream getInput();

    byte[] read();

    boolean hasMore();

}
