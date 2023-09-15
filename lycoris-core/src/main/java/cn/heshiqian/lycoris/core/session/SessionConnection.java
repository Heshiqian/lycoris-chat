package cn.heshiqian.lycoris.core.session;

/**
 * @author Heshiqian
 * @version 1.0.0
 * @since 2023/9/15
 */
public interface SessionConnection {

    SessionWriter writer();

    SessionReader reader();

    void close();

}
