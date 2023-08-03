package cn.heshiqian.lycoris.core.channel;

/**
 * @author Heshiqian
 * @version 1.0.0
 * @since 2023/8/3
 */
public interface ChannelWorker {

    String workerId();

    void execute(Runnable runnable);

}
