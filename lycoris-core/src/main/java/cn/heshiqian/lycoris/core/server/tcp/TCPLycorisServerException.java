package cn.heshiqian.lycoris.core.server.tcp;

import cn.heshiqian.lycoris.core.exception.LycorisException;

/**
 * @author Heshiqian
 * @version 1.0.0
 * @since 2023/9/8
 */
public class TCPLycorisServerException extends LycorisException {

    public TCPLycorisServerException(String message) {
        super(message);
    }

    public TCPLycorisServerException(String message, Throwable cause) {
        super(message, cause);
    }

}
