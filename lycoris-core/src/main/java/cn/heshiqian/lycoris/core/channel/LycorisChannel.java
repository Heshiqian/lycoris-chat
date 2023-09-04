package cn.heshiqian.lycoris.core.channel;

import cn.heshiqian.lycoris.core.channel.ChannelTerminal;

/**
 * @author Heshiqian
 * @version 1.0.0
 * @since 2023/8/2
 */
public interface LycorisChannel {

    int FREQUENCY_GLOBAL = 0;
    int FREQUENCY_PRIVATE = 1;
    int LOW_LEVEL = Integer.MIN_VALUE;
    int HIGH_LEVEL = Integer.MAX_VALUE;

    String channelId();

    void join(ChannelTerminal terminal);

    void leave(int terminalId);

    void broadcast(int frequency, int level, byte[] data);

    void p2p(int terminalId, byte[] data);

    void onData(byte[] data);

}
