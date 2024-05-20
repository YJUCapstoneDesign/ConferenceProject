package team.broadcast.domain.video_room.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import team.broadcast.domain.meeting.service.MeetingService;
import team.broadcast.domain.video_room.dto.RoomResponse;
import team.broadcast.domain.video_room.dto.janus.request.VideoRoomCreate;
import team.broadcast.domain.video_room.dto.janus.request.VideoRoomDestroyRequest;
import team.broadcast.domain.video_room.entity.Room;
import team.broadcast.domain.video_room.service.RoomService;
import team.broadcast.global.login.user.CustomUserDetails;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/room")
@Tag(name = "화상회의 API")
public class VideoRoomController {
    private final RoomService roomService;

    @PostMapping("/{meetingId}/create")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "방 생성",
            description = "방 생성 후 자동으로 입장이 된다. 회의 Host 가 방을 생성할 수 있다.")
    public RoomResponse createRoom(@PathVariable Long meetingId, @RequestBody VideoRoomCreate createRequest) {
        try {
            Long randomRoomId = Room.generateRandomRoomId();
            createRequest.setRoom(randomRoomId);
            return roomService.createRoom(meetingId, createRequest.getEmail(), createRequest);

        } catch (Exception e) {
            throw new RuntimeException("방 생성에 실패 했습니다.");
        }
    }

    @GetMapping("/{videoRoomId}/invite")
    @ResponseStatus(HttpStatus.OK)
    public String inviteRoomLink(@PathVariable Long videoRoomId,
                                 @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        return roomService.inviteLink(videoRoomId, customUserDetails.getId());
    }

    @DeleteMapping("/{videoRoomId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "방 삭제",
            description = "Host 만 방을 삭제 할 수 있다.")
    public String deleteRoom(@PathVariable Long videoRoomId,
                             @RequestBody String secret,
                             @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        try {
            VideoRoomDestroyRequest videoRoomDestroyRequest = new VideoRoomDestroyRequest(videoRoomId, secret);
            roomService.destroyRoom(customUserDetails.getUser(), videoRoomDestroyRequest);
            return "deleted room [" + videoRoomId + "]";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

}
