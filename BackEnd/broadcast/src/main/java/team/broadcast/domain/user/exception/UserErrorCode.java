package team.broadcast.domain.user.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import team.broadcast.global.exception.CustomErrorCode;
import team.broadcast.global.exception.ErrorResponse;

@Getter
@AllArgsConstructor
public enum UserErrorCode implements CustomErrorCode {

    DUPLICATED_EMAIL(409, "USER-01", "중복되는 이메일입니다."),
    USER_NOT_FOUND(404, "USER-02", "해당 유저를 찾을 수 없습니다."),
    INVALID_PASSWORD(400, "USER-03", "비밀번호가 기존 비밀번호와 일치하지 않습니다."),
    INVALID_USERNAME(400, "USER-04", "유저 이름을 입력해주세요."),
    INVALID_EMAIL(400, "USER-05", "이메일 형식이 올바르지 않습니다.");
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
