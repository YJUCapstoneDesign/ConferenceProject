package team.broadcast.domain.janus.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@Getter
@ToString
public class JanusError extends Throwable {
    private final Integer code;
    private final String reason;

}
