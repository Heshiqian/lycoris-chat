package cn.heshiqian.lycoris.core.session;

import cn.heshiqian.lycoris.core.server.connection.ConnectionInfo;

/**
 * @author Heshiqian
 * @since 2023/9/15
 */
public interface SessionFactory {

    Session buildSession(ConnectionInfo connectionInfo);

}
