package cn.heshiqian.lycoris.core.server.tcp;

import cn.heshiqian.lycoris.core.session.Session;
import lombok.AllArgsConstructor;
import lombok.Setter;

/**
 * @author Heshiqian
 * @version 1.0.0
 * @since 2023/9/8
 */
@AllArgsConstructor
@Setter
public class TCPSession implements Session {

    private final String address;
    private final String id;
    private final String name;

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
}
