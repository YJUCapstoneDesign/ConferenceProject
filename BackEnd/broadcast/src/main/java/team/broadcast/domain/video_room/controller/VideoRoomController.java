package team.broadcast.domain.video_room.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import team.broadcast.domain.user.entity.User;
import team.broadcast.domain.user.service.UserService;
import team.broadcast.domain.video_room.dto.VideoRoom;
import team.broadcast.domain.video_room.dto.request.InviteRequest;
import team.broadcast.domain.video_room.dto.request.VideoRoomCreate;
import team.broadcast.domain.video_room.dto.request.VideoRoomDestroyRequest;
import team.broadcast.domain.video_room.exception.RoomErrorCode;
import team.broadcast.domain.video_room.service.InvitationAttenderService;
import team.broadcast.domain.video_room.service.VideoRoomService;
import team.broadcast.global.exception.CustomException;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/room")
@Tag(name = "화상회의 API")
public class VideoRoomController {
    private final VideoRoomService videoRoomService;
    private final UserService userService;
    private final InvitationAttenderService invitationAttenderService;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "방 생성",
            description = "방 생성 후 자동으로 입장이 된다. 입장된 사람은 host가 된다.")
    public VideoRoom createRoom(@RequestBody VideoRoomCreate createRequest) {
        try {
            Long randomRoomId = VideoRoom.generateRandomRoomId();
            createRequest.setRoom(randomRoomId);
            return videoRoomService.createRoom(createRequest.getEmail(), createRequest);

        } catch (Exception e) {
            throw new RuntimeException("방 생성에 실패 했습니다.");
        }
    }

    @DeleteMapping("/{videoRoomId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "방 삭제",
            description = "host 만 방을 삭제 할 수 있다.")
    public String deleteRoom(@PathVariable Long videoRoomId) {
        try {
            VideoRoomDestroyRequest videoRoomDestroyRequest = new VideoRoomDestroyRequest();
            videoRoomService.destroyRoom(videoRoomDestroyRequest);
            return "deleted room [" + videoRoomId + "]";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    /**
     * @param videoRoomId - 방 아이디
     * @param request     - 보낼 참석자 이메일 주소
     */

    @PostMapping("/{videoRoomId}/invite")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "방 초대 코드 발송",
            description = "참석자에 소속되어 있는 사람에게 이메일로 초대 링크를 보낸다.")
    public String inviteAttender(@PathVariable Long videoRoomId, @RequestBody InviteRequest request) {
        log.info("user email={}", request.getEmail());
        User user = userService.findUserByEmail(request.getEmail());
        VideoRoom room = videoRoomService.findByRoomId(videoRoomId);
        if (room == null) {
            throw new CustomException(RoomErrorCode.ROOM_NOT_FOUND);
        }
        String code = "http://localhost:8080/api/room/" + videoRoomId;
        invitationAttenderService.sendInviteMail(user, code);
        return "ok";
    }
}
