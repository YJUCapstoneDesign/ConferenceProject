package team.broadcast.domain.video_room.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import team.broadcast.domain.video_room.dto.janus.request.VideoRoomCreateRequest;
import team.broadcast.domain.video_room.dto.janus.request.VideoRoomDestroyRequest;
import team.broadcast.domain.video_room.service.RoomService;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/room")
@Tag(name = "화상회의 API")
public class RoomController {
    private final RoomService roomService;

    @PostMapping("/create/{teamId}")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "방 생성",
            description = "방 생성은 버튼을 눌렀을 때 할 수 있도록 한다.")
    public Long createRoom(@PathVariable Long teamId, @RequestBody VideoRoomCreateRequest createRequest) {
        try {
            createRequest.setRoom(teamId);
            return roomService.createRoom(createRequest);

        } catch (Exception e) {
            throw new IllegalArgumentException("방 생성에 실패 했습니다.");
        }
    }

    @DeleteMapping("/delete/{teamId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "방 삭제",
            description = "방 삭제는 아이디로 삭제 된다.")
    public void deleteRoom(@PathVariable Long teamId, @RequestBody String secret) {
        try {
            VideoRoomDestroyRequest videoRoomDestroyRequest = VideoRoomDestroyRequest.create(teamId, secret);
            roomService.destroyRoom(videoRoomDestroyRequest);
        } catch (Exception e) {
            throw new IllegalArgumentException("방 삭제에 실패 했습니다.");
        }
    }
}
