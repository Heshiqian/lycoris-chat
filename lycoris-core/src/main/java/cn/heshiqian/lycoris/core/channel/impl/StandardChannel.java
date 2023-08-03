package cn.heshiqian.lycoris.core.channel.impl;

import cn.heshiqian.lycoris.core.channel.ChannelTerminal;
import cn.heshiqian.lycoris.core.channel.TickChannel;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Heshiqian
 * @version 1.0.0
 * @since 2023/8/2
 */
public class StandardChannel extends TickChannel {

    public static final int STANDARD_TICK = 20;
    private static final String CHANNEL_PREFIX = "standard-channel-";
    private static final AtomicInteger nameId = new AtomicInteger(1);

    private final String channelId;

    public StandardChannel() {
        super(STANDARD_TICK);
        channelId = CHANNEL_PREFIX + nameId.getAndIncrement();
    }

    @Override
    public String channelId() {
        return channelId;
    }

    @Override
    public void join(ChannelTerminal terminal) {

    }

    @Override
    public void leave(int terminalId) {

    }

    @Override
    public void broadcast(int frequency, int level, byte[] data) {

    }

    @Override
    public void p2p(int terminalId, byte[] data) {

    }

    @Override
    public void onData(byte[] data) {

    }

    @Override
    protected void onStartBefore() {

    }

    @Override
    protected void tick() {
        // 1000ms / 20tick = 50ms/tick
        
    }
}
