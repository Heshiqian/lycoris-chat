package cn.heshiqian.lycoris.core.server;

/**
 * @author Heshiqian
 * @version 1.0.0
 * @since 2023/9/7
 */
public interface LycorisServerLifecycle {

    void onServerCreate();

    void onServerStart();

    void onServerShutdown();

    void onServerDestroy();

}
