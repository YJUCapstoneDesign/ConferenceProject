package team.broadcast.domain.janus.repository;

import org.springframework.stereotype.Repository;
import team.broadcast.domain.janus.dto.JanusSession;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.stream.Collectors;

@Repository
public class JanusSessionRepository {
    private static Map<Long, JanusSession> sessions = new ConcurrentHashMap<>();

    public JanusSession save(JanusSession session) {
        sessions.put(session.getSessionId(), session);
        return session;
    }

    public Optional<JanusSession> findBySessionId(long sessionId) {
        return Optional.ofNullable(sessions.get(sessionId));
    }

    public List<JanusSession> findByAll() {
        // multi thread safe 환경으로 수정할 필요가 있음.
        return new ArrayList<>(sessions.values());
    }

    public Optional<JanusSession> findByUserId(long userId) {
        return sessions.values().stream()
                /* userId를 가진 session이 있는지 찾고 있으면 그  클래스를 반환한다. */
                .filter(session -> session.getUserId() == userId)
                .findFirst();
    }
}
