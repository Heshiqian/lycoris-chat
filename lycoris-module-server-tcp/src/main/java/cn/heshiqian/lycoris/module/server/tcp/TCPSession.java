package cn.heshiqian.lycoris.module.server.tcp;

import cn.heshiqian.lycoris.core.session.Session;
import cn.heshiqian.lycoris.core.server.connection.LycorisConnection;
import lombok.Setter;

/**
 * @author Heshiqian
 * @version 1.0.0
 * @since 2023/9/8
 */
@Setter
public class TCPSession implements Session {

    private final String address;
    private final String id;
    private final String name;

    private final LycorisConnection lycorisConnection;

    public TCPSession(String address,
                      String id,
                      String name,
                      LycorisConnection lycorisConnection) {
        this.address = address;
        this.id = id;
        this.name = name;
        this.lycorisConnection = lycorisConnection;
    }

    @Override
    public String getAddress() {
        return address;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public LycorisConnection getConnection() {
        return lycorisConnection;
    }

}
