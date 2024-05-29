package team.broadcast.domain.video_room.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import team.broadcast.domain.video_room.dto.RoomResponse;
import team.broadcast.domain.video_room.dto.janus.request.VideoRoomCreate;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 방 생성 및 삭제 테스트
 */
@SpringBootTest
class RoomServiceTest {

    @Autowired
    private RoomService roomService;

    private String name = "test";


    @Test
    @DisplayName("방 생성")
    void createRoom() throws Exception {
        Long roomId = 12341234L;
        String email = this.name + "@naver.com";
        // 방 생성
        VideoRoomCreate testRoom = VideoRoomCreate.builder()
                .room(roomId)
                .display("임시 테스트 방")
                .publishers(6)
                .build();

        RoomResponse room = roomService.createRoom(1L, email, testRoom);

        // 방이 생성이 되었는지 확인
        assertThat(room).isNotNull();

        // 방 삭제
//        VideoRoomDestroyRequest destroy = VideoRoomDestroyRequest.builder()
//                .room(room.getRoomId())
//                .secret(secret)
//                .request("destroy")
//                .build();
//
//        videoRoomService.destroyRoom(destroy);
    }


}