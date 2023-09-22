package cn.heshiqian.lycoris.core.server.connection;

import lombok.Getter;
import lombok.Setter;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author Heshiqian
 * @version 1.0.0
 * @since 2023/9/15
 */
@Getter
@Setter
public class ConnectionInfo {

    //~ Primary args

    /**
     * The IPv4 address string.
     */
    private final String address;

    /**
     * The bits of IP address.
     */
    private final byte[] ipAddress;

    /**
     * The connection input stream.
     */
    private final InputStream in;

    /**
     * The connection output stream.
     */
    private final OutputStream out;

    public ConnectionInfo(String address, byte[] ipAddress, InputStream in, OutputStream out) {
        this.address = address;
        this.ipAddress = ipAddress;
        this.in = in;
        this.out = out;
    }

    //~ Other information

    private String recognizeId;

    private long connectTime;

    private boolean connected;



}
