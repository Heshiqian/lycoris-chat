package cn.heshiqian.lycoris.core.session;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Heshiqian
 * @version 1.1.0
 * @since 2024/2/21
 */
public class MemorySessionManager implements SessionManager {

    private final ConcurrentHashMap<String, Session> sessions;

    public MemorySessionManager() {
        sessions = new ConcurrentHashMap<>();
    }

    @Override
    public void manage(Session session) {
        sessions.put(session.getId(), session);
    }

    @Override
    public Session getSession(String sessionId) {
        return sessions.get(sessionId);
    }

    @Override
    public Session[] getSessions(String sessionName) {
        return sessions.values().stream()
                .filter(session -> sessionName.equals(session.getName()))
                .toArray(Session[]::new);
    }

    @Override
    public void removeSession(String sessionId) {
        sessions.remove(sessionId);
    }
}
