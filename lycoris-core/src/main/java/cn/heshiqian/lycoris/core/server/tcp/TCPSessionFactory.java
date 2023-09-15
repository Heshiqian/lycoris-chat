package cn.heshiqian.lycoris.core.server.tcp;

import cn.heshiqian.lycoris.core.session.Session;
import cn.heshiqian.lycoris.core.session.SessionConnectionInfo;
import cn.heshiqian.lycoris.core.session.SessionFactory;

/**
 * @author Heshiqian
 * @version 1.0.0
 * @since 2023/9/15
 */
public class TCPSessionFactory implements SessionFactory {

    @Override
    public Session buildSession(SessionConnectionInfo connectionInfo) {
        return null;
    }

    public static SessionFactory getInstance() {
        return TCPSessionFactoryHolder.sessionFactory;
    }

    private static class TCPSessionFactoryHolder {
        private static final TCPSessionFactory sessionFactory = new TCPSessionFactory();
    }
}
