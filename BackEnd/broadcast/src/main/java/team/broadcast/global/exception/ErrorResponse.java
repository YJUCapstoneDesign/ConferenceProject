package team.broadcast.global.exception;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ErrorResponse {
    private final Integer status;
    private final String code;
    private final String reason;
}
