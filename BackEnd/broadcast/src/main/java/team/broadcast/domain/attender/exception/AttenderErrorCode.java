package team.broadcast.domain.attender.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import team.broadcast.global.exception.CustomErrorCode;
import team.broadcast.global.exception.ErrorResponse;

@Getter
@AllArgsConstructor
public enum AttenderErrorCode implements CustomErrorCode {

    DUPLICATED_ATTENDER(409, "Attender-1", "참석자가 이미 존재합니다."),
    ATTENDER_NOT_FOUND(404, "Attender-02", "해당 참석자를 찾을 수 없습니다."),
    ;

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
