package team.broadcast.domain.video_room.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import team.broadcast.domain.video_room.dto.VideoRoom;
import team.broadcast.domain.video_room.dto.janus.request.VideoRoomCreate;
import team.broadcast.domain.video_room.dto.janus.request.VideoRoomDestroyRequest;
import team.broadcast.domain.video_room.service.VideoRoomService;
import team.broadcast.global.login.user.CustomUserDetails;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/room")
@Tag(name = "화상회의 API")
public class VideoRoomController {
    private final VideoRoomService videoRoomService;

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

}
