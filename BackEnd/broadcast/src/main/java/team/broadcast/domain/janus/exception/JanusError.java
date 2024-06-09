package team.broadcast.domain.janus.exception;

import lombok.Getter;

@Getter
public class JanusError extends RuntimeException {
    private JanusErrorCode errorCode;

    public JanusError(Integer code, String reason) {
        super("[" + code + "] reason='" + reason + "'");
        errorCode = new JanusErrorCode(code, reason);
    }

    public JanusError(JanusErrorCode errorCode) {
        super("[" + errorCode.getCode() + "] reason='" + errorCode.getReason() + "'");
        this.errorCode = errorCode;
    }

    @Override
    public String toString() {
        return "[" + errorCode.getCode() + "] reason='" + errorCode.getReason() + "'";
    }
}
