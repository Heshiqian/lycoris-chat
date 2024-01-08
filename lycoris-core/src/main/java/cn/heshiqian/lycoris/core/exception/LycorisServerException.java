package cn.heshiqian.lycoris.core.exception;

/**
 * @author Heshiqian
 * @version 1.0.0
 * @since 2023/10/1
 */
public class LycorisServerException extends Exception{

    public LycorisServerException() {
    }

    public LycorisServerException(String message) {
        super(message);
    }

    public LycorisServerException(String message, Throwable cause) {
        super(message, cause);
    }
}
