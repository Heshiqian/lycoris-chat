package cn.heshiqian.lycoris.module.server.tcp.auth;

import cn.heshiqian.lycoris.core.server.connection.LycorisConnection;
import lombok.Data;

/**
 * @author Heshiqian
 * @version 1.0.0
 * @since 2024/1/15
 */
@Data
public final class AuthSession {

    private String address;
    private String id;
    private String name;
    private LycorisConnection lycorisConnection;

}
