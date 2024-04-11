package team.broadcast.domain.video_room.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import team.broadcast.global.exception.CustomErrorCode;
import team.broadcast.global.exception.ErrorResponse;

@Getter
@AllArgsConstructor
public enum RoomErrorCode implements CustomErrorCode {

    DUPLICATED_ROOM(409, "ROOM-1", "이미 방이 존재합니다."),
    ROOM_NOT_FOUND(404, "ROOM-2", "방이 존재하지 않습니다.");

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
