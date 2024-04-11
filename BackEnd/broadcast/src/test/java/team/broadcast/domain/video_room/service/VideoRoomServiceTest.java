package team.broadcast.domain.video_room.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import team.broadcast.domain.user.dto.SignupUser;
import team.broadcast.domain.user.service.UserService;
import team.broadcast.domain.video_room.dto.VideoRoom;
import team.broadcast.domain.video_room.dto.request.VideoRoomCreate;
import team.broadcast.domain.video_room.dto.request.VideoRoomDestroyRequest;
import team.broadcast.domain.video_room.dto.request.VideoRoomEditRequest;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 방 생성 및 수정, 삭제 테스트
 */
@SpringBootTest
class VideoRoomServiceTest {

    @Autowired
    private VideoRoomService videoRoomService;
    @Autowired
    private UserService userService;

    private String name;

    @BeforeEach
    void init() throws Exception {
        name = userService.generateRandomName(10);
        SignupUser test = SignupUser.builder()
                .email(name + "@gmail.com")
                .username("test")
                .password("1234")
                .phone("01011111111")
                .build();

        userService.join(test);
    }

    @Test
    @DisplayName("방 생성")
    void createRoom() throws Exception {
        Long roomId = 12341234L;
        String email = this.name + "@gmail.com";
        // 방 생성
        VideoRoomCreate testRoom = VideoRoomCreate.builder()
                .room(roomId)
                .display("임시 테스트 방")
                .build();

        VideoRoom room = videoRoomService.createRoom(email, testRoom);

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

    @Test
    @DisplayName("방 수정")
    void updateRoom() throws Exception {
        Long roomId = 5678L;
        String newSecret = "1234";
        String email = this.name + "@gmail.com";

        // 방 생성
        VideoRoomCreate testRoom = VideoRoomCreate.builder()
                .room(roomId)
                .display("임시 테스트 방")
                .build();

        VideoRoom room = videoRoomService.createRoom(email, testRoom);

        assertThat(room).isNotNull();

        // 방 수정
        VideoRoomEditRequest editRequest = VideoRoomEditRequest.builder()
                .room(room.getRoomId())
                .newDescription("안녕하세요")
                .newSecret(newSecret)
                .build();

        VideoRoom updatedRoom = videoRoomService.updateRoom(editRequest);

        // 방 이름이 바뀌었는지 확인
        assertThat(updatedRoom.getRoomName()).isEqualTo("안녕하세요");

        // 방삭제
        VideoRoomDestroyRequest destroy = VideoRoomDestroyRequest.builder()
                .room(room.getRoomId())
                .secret(newSecret)
                .build();

        videoRoomService.destroyRoom(destroy);
    }
}