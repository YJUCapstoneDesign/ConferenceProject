package team.broadcast.domain.video_room.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import team.broadcast.domain.user.dto.UserDto;
import team.broadcast.domain.user.service.UserService;
import team.broadcast.domain.video_room.dto.VideoRoomDTO;
import team.broadcast.domain.video_room.dto.request.VideoRoomCreate;
import team.broadcast.domain.video_room.dto.request.VideoRoomDestroyRequest;
import team.broadcast.domain.video_room.dto.request.VideoRoomEditRequest;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

/**
 *  방 생성 및 수정, 삭제 테스트
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
        UserDto test = UserDto.builder()
                .id(1L)
                .email(name + "@gmail.com")
                .username("test")
                .nickname(name)
                .password("1234")
                .phone("01011111111")
                .build();

        userService.join(test);
    }

    @Test
    @DisplayName("방 생성")
    void createRoom() throws Exception {
        Long roomId = 5678L;
        String secret = "5678";
        String email = this.name + "@gmail.com";
        // 방 생성
        VideoRoomCreate testRoom = VideoRoomCreate.builder()
                .room(roomId)
                .display("임시 테스트 방")
                .secret(secret)
                .request("create")
                .build();

        VideoRoomDTO room = videoRoomService.createRoom(email, testRoom);

        // 방이 생성이 되었는지 확인
        assertThat(room).isNotNull();

        // 방 삭제
        VideoRoomDestroyRequest destroy = VideoRoomDestroyRequest.builder()
                .room(room.getRoomId())
                .secret(secret)
                .request("destroy")
                .build();

        videoRoomService.destroyRoom(destroy);
    }

    @Test
    @DisplayName("방 수정")
    void updateRoom() throws Exception {
        Long roomId = 5678L;
        String secret = "5678";
        String newSecret = "1234";
        String email = this.name + "@gmail.com";

        // 방 생성
        VideoRoomCreate testRoom = VideoRoomCreate.builder()
                .room(roomId)
                .display("임시 테스트 방")
                .secret(secret)
                .request("create")
                .build();

        VideoRoomDTO room = videoRoomService.createRoom(email, testRoom);

        assertThat(room).isNotNull();

        // 방 수정
        VideoRoomEditRequest editRequest = VideoRoomEditRequest.builder()
                .room(room.getRoomId())
                .secret(secret)
                .newDescription("안녕하세요")
                .newSecret(newSecret)
                .build();

        VideoRoomDTO updatedRoom = videoRoomService.updateRoom(editRequest);

        // 방 이름이 바뀌었는지 확인
        assertThat(updatedRoom.getRoomName()).isEqualTo("안녕하세요");

        // 방삭제
        VideoRoomDestroyRequest destroy = VideoRoomDestroyRequest.builder()
                .room(room.getRoomId())
                .secret(newSecret)
                .request("destroy")
                .build();

        videoRoomService.destroyRoom(destroy);
    }
}