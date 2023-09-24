package cn.heshiqian.lycoris.module.server.tcp.protocol;

/**
 * Defined TCP communication protocol.
 * @author Heshiqian
 * @version 1.0.0
 * @since 2023/9/24
 */
public enum TCPProtocol {

    HELLO(new byte[]{0x68, 0x65, 0x68, 0x69}),

    ALLOW(new byte[]{0x68, 0x65, 0x00, 0x01}),

    REFUSE(new byte[]{0x68, 0x65, 0x00, -0x01}),






    ;

    final byte[] header;

    TCPProtocol(byte[] header) {
        this.header = header;
    }

}
