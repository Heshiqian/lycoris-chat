package cn.heshiqian.lycoris.core.exception;


/**
 * @author Heshiqian
 * @version 1.0.0
 * @since 2023/7/25
 */
public abstract class LycorisException extends RuntimeException{

    public LycorisException(String message) {
        super(message);
    }

    public LycorisException(String message, Throwable cause) {
        super(message, cause);
    }

    public LycorisException(Throwable cause) {
        super(cause);
    }

}
