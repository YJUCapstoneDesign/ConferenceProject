package team.broadcast.domain.video_room.dto;


import lombok.*;
import org.apache.commons.lang3.RandomStringUtils;
import team.broadcast.domain.attender.dto.AttenderDTO;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VideoRoom {
    private Long roomId; // 룸 아이디
    private String roomName; // 룸 이름

    private int currentMemberCount; // 현재 채팅방 인원수
    private int maxMemberCount; // 채팅방 최대 인원 수 지정

    private LocalDateTime startTime; // 방을 생성한 시간
    private LocalDateTime endTime; // 방 종료시간
    private Integer activeMinute; // 방 활성화 가능 시간(분 단위) null 인 경우 무제한

    private Long publisherId; // 방 host ID
    private List<AttenderDTO> participants; // 방 참가자 리스트

    public static Long generateRandomRoomId() {
        return Long.valueOf(RandomStringUtils.random(6, false, true));
    }
}
