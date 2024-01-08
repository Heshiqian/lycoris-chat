package cn.heshiqian.lycoris.core.exception;

/**
 * @author Heshiqian
 * @version 1.0.0
 * @since 2023/10/1
 */
public class WorkerPoolException extends LycorisException{
    public WorkerPoolException(String message) {
        super(message);
    }

    public WorkerPoolException(String message, Throwable cause) {
        super(message, cause);
    }
}
