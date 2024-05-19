package team.broadcast.global.jwt.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import team.broadcast.global.exception.CustomErrorCode;
import team.broadcast.global.exception.ErrorResponse;

@Getter
@AllArgsConstructor
public enum JwtErrorCode implements CustomErrorCode {
    MALFORMED(401, "JWT-1", "유효하지 않은 JWT 토큰"),
    EXPIRED(401, "JWT-2", "지원하지 않는 JWT 토큰 형식"),
    UNSUPPORTED(401, "JWT-3", "지원하지 않는 JWT 토큰"),
    ILLEGAL_ARGUMENT(401, "JWT-4", "유효하지 않는 compact JWT 토큰"),
    NOT_FOUND_ACCESS(404, "JWT-5", "Access Token을 찾을 수 없습니다."),
    NOT_FOUND_REFRESH(404, "JWT-6", "Refresh Token을 찾을 수 없습니다.");

    private final Integer status;
    private final String code;
    private final String reason;

    @Override
    public ErrorResponse getErrorResponse() {
        return ErrorResponse.builder()
                .reason(reason)
                .code(code)
                .status(status)
                .build();
    }
}
