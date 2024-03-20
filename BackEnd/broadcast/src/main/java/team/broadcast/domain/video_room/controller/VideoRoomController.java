package team.broadcast.domain.video_room.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import team.broadcast.domain.video_room.dto.VideoRoomCreateRequest;
import team.broadcast.domain.video_room.dto.VideoRoomDTO;
import team.broadcast.domain.video_room.dto.VideoRoomDestroyRequest;
import team.broadcast.domain.video_room.service.VideoRoomService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/{userId}/room")
public class VideoRoomController {
    private final VideoRoomService videoRoomService;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public VideoRoomDTO createRoom(@PathVariable Long userId,
                                   @RequestBody VideoRoomCreateRequest createRequest) {
        try {
            return videoRoomService.createRoom(userId, createRequest);
        } catch (Exception e) {
            return null;
        }
    }

    @PostMapping("/{videoRoomId}")
    @ResponseStatus(HttpStatus.OK)
    public String deleteRoom(@PathVariable String userId, @PathVariable Long videoRoomId, String secret) {
        try {
            VideoRoomDestroyRequest videoRoomDestroyRequest = new VideoRoomDestroyRequest(videoRoomId, secret);
            videoRoomService.destroyRoom(videoRoomDestroyRequest);
            return "deleted room [" + videoRoomId + "]";
        } catch (Exception e) {
            return e.getMessage();
        }
    }
}
