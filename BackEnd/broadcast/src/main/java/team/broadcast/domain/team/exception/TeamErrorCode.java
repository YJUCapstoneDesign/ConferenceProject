package team.broadcast.domain.team.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import team.broadcast.global.exception.CustomErrorCode;
import team.broadcast.global.exception.ErrorResponse;

@Getter
@AllArgsConstructor
public enum TeamErrorCode implements CustomErrorCode {

    TEAM_NOT_FOUND(404, "TM-1", "팀을 찾을 수 없습니다."),
    INVALID_PASSWORD(400, "TM-2", "팀 비밀 번호가 틀렸습니다."),
    ALLOW_HOST_ROLE(403, "TM-3", "호스트인 경우만 사용이 가능합니다.");


    private final Integer status;
    private final String code;
    private final String reason;

    @Override
    public ErrorResponse getErrorResponse() {
        return null;
    }
}
