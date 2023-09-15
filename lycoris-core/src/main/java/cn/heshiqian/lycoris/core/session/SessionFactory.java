package cn.heshiqian.lycoris.core.session;

/**
 * @author Heshiqian
 * @since 2023/9/15
 */
public interface SessionFactory {

    Session buildSession(SessionConnectionInfo connectionInfo);

}
