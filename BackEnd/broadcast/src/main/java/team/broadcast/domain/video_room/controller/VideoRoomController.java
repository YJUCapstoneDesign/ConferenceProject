package team.broadcast.domain.video_room.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import team.broadcast.domain.video_room.dto.VideoRoom;
import team.broadcast.domain.video_room.dto.request.VideoRoomCreate;
import team.broadcast.domain.video_room.dto.request.VideoRoomDestroyRequest;
import team.broadcast.domain.video_room.service.VideoRoomService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/room")
public class VideoRoomController {
    private final VideoRoomService videoRoomService;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public VideoRoom createRoom(@RequestBody VideoRoomCreate createRequest) {
        try {
            return videoRoomService.createRoom(createRequest.getEmail(), createRequest);

        } catch (Exception e) {
            throw new RuntimeException("방 생성에 실패 했습니다.");
        }
    }

    @PostMapping("/{videoRoomId}")
    @ResponseStatus(HttpStatus.OK)
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
