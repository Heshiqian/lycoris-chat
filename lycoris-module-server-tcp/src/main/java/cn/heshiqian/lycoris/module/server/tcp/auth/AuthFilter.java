package cn.heshiqian.lycoris.module.server.tcp.auth;

import cn.heshiqian.lycoris.module.server.tcp.TCPLycorisConnection;

/**
 * @author Heshiqian
 * @since 2024/1/15
 */
public interface AuthFilter {

    boolean filter(AuthSession authSession);

}
