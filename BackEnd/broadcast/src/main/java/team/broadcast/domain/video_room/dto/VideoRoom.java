package team.broadcast.domain.video_room.dto;


import lombok.*;
import org.apache.commons.lang3.RandomStringUtils;
import team.broadcast.domain.attender.dto.AttenderDTO;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VideoRoom {
    private Long roomId; // 룸 아이디
    private String roomName; // 룸 이름

    private int currentMemberCount; // 현재 채팅방 인원수
    private int maxMemberCount = 6; // 채팅방 최대 인원 수 지정

    private LocalDateTime startTime; // 방을 생성한 시간

    private Long meetingId; // 회의 아이디

    private List<AttenderDTO> participants; // 방 참가자 리스트

    public static Long generateRandomRoomId() {
        return Long.valueOf(RandomStringUtils.random(6, false, true));
    }

    public void updateRoomName(String newRoomName) {
        this.roomName = newRoomName;
    }
    public void updateParticipants(List<AttenderDTO> newParticipants) {
        this.participants = newParticipants;
        currentMemberCount = participants.size();
    }
}
