package team.broadcast.domain.video_room.dto;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VideoRoomTest {

    @Test
    @DisplayName("랜덤 방 번호 6자리 생성")
    void randomRoomId() {
        Long randomRoomId = VideoRoom.generateRandomRoomId();
        // 해당 번호 길이가 6자인지 확인한다.
        Assertions.assertThat(randomRoomId+"").hasSize(6);
    }
}