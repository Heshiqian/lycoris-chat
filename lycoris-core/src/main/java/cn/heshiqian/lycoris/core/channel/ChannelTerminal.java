package cn.heshiqian.lycoris.core.channel;

/**
 * @author Heshiqian
 * @version 1.0.0
 * @since 2023/8/2
 */
public interface ChannelTerminal {

    default int terminalId() {
        return Long.hashCode(System.currentTimeMillis());
    }

    boolean handle(byte[] data);

}
