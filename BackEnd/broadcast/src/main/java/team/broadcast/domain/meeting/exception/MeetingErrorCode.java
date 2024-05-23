package team.broadcast.domain.meeting.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import team.broadcast.global.exception.CustomErrorCode;
import team.broadcast.global.exception.ErrorResponse;

@Getter
@AllArgsConstructor
public enum MeetingErrorCode implements CustomErrorCode {

    MEETING_NOT_FOUND(404, "MTG-1", "회의를 찾을 수 없습니다."),
    DUPLICATED_MEETING(409, "MTG-2", "회의가 이미 있습니다."),
    ALLOW_HOST_ROLE(403, "MTG-3", "호스트인 경우만 사용이 가능합니다.");


    private final Integer status;
    private final String code;
    private final String reason;

    @Override
    public ErrorResponse getErrorResponse() {
        return null;
    }
}
