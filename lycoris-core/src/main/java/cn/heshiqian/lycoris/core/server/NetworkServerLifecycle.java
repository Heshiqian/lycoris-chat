package cn.heshiqian.lycoris.core.server;

/**
 * @author Heshiqian
 * @version 1.0.0
 * @since 2023/9/7
 */
public interface NetworkServerLifecycle {

    void onServerCreate();

    void onServerStart();

    void onServerClose();

    void onServerShutdown();

    void onServerDestroy();

}
