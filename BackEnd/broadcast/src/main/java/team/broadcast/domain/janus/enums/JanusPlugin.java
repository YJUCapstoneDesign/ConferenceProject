package team.broadcast.domain.janus.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum JanusPlugin {
    ECHO_TEST("janus.plugin.echotest"),
    VIDEO_ROOM("janus.plugin.videoroom"),
    TEXT_ROOM("janus.plugin.textroom");

    private final String plugin;
}
