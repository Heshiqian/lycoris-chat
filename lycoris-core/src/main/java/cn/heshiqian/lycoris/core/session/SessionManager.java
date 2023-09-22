package cn.heshiqian.lycoris.core.session;

/**
 * @author Heshiqian
 * @version 1.0.0
 * @since 2023/9/22
 */
public interface SessionManager {

    void manage(Session session);

    Session getSession(String sessionId);

    Session[] getSessions(String sessionName);


}
