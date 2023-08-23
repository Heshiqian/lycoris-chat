package cn.heshiqian.lycoris.core.channel.impl;

import cn.heshiqian.lycoris.core.channel.ChannelWorker;

/**
 * @author Heshiqian
 * @version 1.0.0
 * @since 2023/8/3
 */
public class StandardWorker implements ChannelWorker {

    private final String workerId;

    public StandardWorker(String workerId) {
        this.workerId = workerId;
    }

    @Override
    public String workerId() {
        return workerId;
    }

    @Override
    public void execute(Runnable runnable) {

    }

}
