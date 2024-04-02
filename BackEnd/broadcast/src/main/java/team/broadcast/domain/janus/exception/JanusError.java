package team.broadcast.domain.janus.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@AllArgsConstructor
public class JanusError extends RuntimeException {
    private JanusErrorCode errorCode;

    public JanusError(Integer code, String reason) {
        errorCode = new JanusErrorCode(code, reason);
    }

    @Override
    public String toString() {
        return "[" + errorCode.getCode() + "] reason=\'" + errorCode.getReason() + "\'";
    }
}
