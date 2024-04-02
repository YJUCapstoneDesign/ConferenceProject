package team.broadcast.domain.janus.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class JanusErrorCode {
    private Integer code;
    private String reason;
}
