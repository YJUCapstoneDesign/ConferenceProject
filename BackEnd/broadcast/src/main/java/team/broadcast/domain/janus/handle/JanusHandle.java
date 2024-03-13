package team.broadcast.domain.janus.handle;

import team.broadcast.domain.janus.enums.JanusPlugin;

public interface JanusHandle {
    JanusPlugin getName();

    String getOpaqueId();

    Long getId();
}
