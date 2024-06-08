package team.broadcast.domain.video_room.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import team.broadcast.domain.video_room.dto.janus.request.VideoRoomCreateRequest;
import team.broadcast.domain.video_room.dto.janus.request.VideoRoomDestroyRequest;
import team.broadcast.domain.video_room.dto.janus.request.VideoRoomExistRequest;
import team.broadcast.domain.video_room.dto.janus.response.RoomResponse;
import team.broadcast.domain.video_room.service.RoomService;
import team.broadcast.global.login.user.CustomUserDetails;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/room")
@Tag(name = "화상회의 API")
public class RoomController {
    private final RoomService roomService;

    @GetMapping("/exist/{teamId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "방 조회",
            description = "방이 있는지 조회하는 코드로 있으면 해당 방아이디를 없으면 null 반환")
    public RoomResponse exist(@PathVariable Long teamId,
                              @AuthenticationPrincipal CustomUserDetails user) {
        try {
            VideoRoomExistRequest existRequest = VideoRoomExistRequest.create(teamId);
            Long existedRoom = roomService.existRoom(existRequest);
            return new RoomResponse(existedRoom, user.getUsername());
        } catch (Exception e) {
            throw new IllegalArgumentException("방 조회에 실패 하였습니다.");
        }
    }

    @PostMapping("/create/{teamId}")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "방 생성",
            description = "방 생성은 버튼을 눌렀을 때 할 수 있도록 한다.")
    public Long createRoom(@PathVariable Long teamId) {
        try {
            VideoRoomCreateRequest createRequest = VideoRoomCreateRequest.builder()
                    .room(teamId)
                    .display(teamId.toString())
                    .build();

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
