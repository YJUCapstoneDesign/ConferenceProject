package team.broadcast.domain.video_room.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import team.broadcast.domain.video_room.dto.janus.request.VideoRoomCreateRequest;
import team.broadcast.domain.video_room.dto.janus.request.VideoRoomDestroyRequest;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 방 생성 및 삭제 테스트
 */
@SpringBootTest
class RoomServiceTest {

    @Autowired
    private RoomService roomService;

    @Test
    @DisplayName("방 생성")
    void createRoom() throws Exception {
        Long roomId = 12341234L;
        // 방 생성
        VideoRoomCreateRequest testRoom = VideoRoomCreateRequest.builder()
                .room(roomId)
                .display("임시 테스트 방")
                .publishers(6)
                .build();

        Long createdRoomId = roomService.createRoom(testRoom);

        // 방이 생성이 되었는지 확인
        assertThat(createdRoomId).isEqualTo(roomId);

        // 방 삭제
        VideoRoomDestroyRequest destroy = VideoRoomDestroyRequest.builder()
                .room(roomId)
                .build();

        roomService.destroyRoom(destroy);
    }


}